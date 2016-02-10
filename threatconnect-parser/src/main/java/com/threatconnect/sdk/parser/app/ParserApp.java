package com.threatconnect.sdk.parser.app;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.app.LoggerUtil;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.parser.Parser;
import com.threatconnect.sdk.parser.ParserException;
import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.model.ItemType;
import com.threatconnect.sdk.parser.service.save.SaveApiService;
import com.threatconnect.sdk.parser.service.save.SaveResults;
import com.threatconnect.sdk.parser.service.save.SaveService;

public abstract class ParserApp extends App
{
	@Override
	public ExitStatus execute(AppConfig appConfig) throws Exception
	{
		// holds the map of all the counts
		Map<String, Count> parserCountMap = new HashMap<String, Count>();
		
		// holds the results of each feed
		Map<String, SaveResults> parserSaveResults = new HashMap<String, SaveResults>();
		
		// holds the number of indicators and groups that were found
		int totalIndicatorsFound = 0;
		int totalGroupsFound = 0;
		
		// holds the number of indicators and groups that were saved
		int totalIndicatorsSaved = 0;
		int totalGroupsSaved = 0;
		
		// create the parser
		Parser[] parsers = createParsers(appConfig);
		
		try
		{
			// make sure there are parsers
			if (null != parsers && parsers.length > 0)
			{
				// for each of the parsers
				for (Parser parser : parsers)
				{
					try
					{
						// parse the data
						List<Item> items = parser.parseData(getParserStartDate(appConfig));
						getLogger().info("Successfully parsed {} records", items.size());
						
						// allow child classes to do something with the parsed data
						onParsingFinished(items);
						
						// create a save service that to write the data
						SaveService saveService = getSaveService(appConfig);
						
						// save the list of items
						SaveResults saveResults = saveService.saveItems(items);
						parserSaveResults.put(parser.getUniqueName(), saveResults);
						
						// add up all of the indicators and groups
						Count count =
							new Count(count(items, ItemType.INDICATOR, true), count(items, ItemType.GROUP, true));
						parserCountMap.put(parser.getUniqueName(), count);
						
						// add up the totals
						totalIndicatorsFound += count.getIndicators();
						totalGroupsFound += count.getGroups();
						
						totalIndicatorsSaved +=
							count.getIndicators() - saveResults.countFailedItems(ItemType.INDICATOR);
						totalGroupsSaved += count.getGroups() - saveResults.countFailedItems(ItemType.GROUP);
					}
					catch (ParserException e)
					{
						getLogger().error(e.getMessage(), e);
					}
					catch (Exception e)
					{
						getLogger().error(e.getMessage(), e);
					}
				}
				
				// determine the exit status for this app
				if (parserSaveResults.isEmpty())
				{
					// no parsers have failed
					return ExitStatus.Failure;
				}
				else if (parserSaveResults.size() == parsers.length)
				{
					// add parsers have failed
					return ExitStatus.Success;
				}
				else
				{
					// some of the parsers have failed
					return ExitStatus.Partial_Failure;
				}
			}
			else
			{
				LoggerUtil.logErr("No parsers were created. Expecting at least 1 or more parsers.");
				
				// there are no parsers
				return ExitStatus.Failure;
			}
		}
		finally
		{
			// for each entry
			for (Map.Entry<String, Count> entry : parserCountMap.entrySet())
			{
				// retrieve the save results for this parser
				SaveResults saveResults = parserSaveResults.get(entry.getKey());
				
				// build and log a message for what this parser found
				StringBuilder foundMessage = new StringBuilder();
				foundMessage.append(entry.getKey());
				foundMessage.append(" / ");
				foundMessage.append("Indicators Found: ");
				foundMessage.append(entry.getValue().getIndicators());
				foundMessage.append(" / ");
				foundMessage.append("Groups Found: ");
				foundMessage.append(entry.getValue().getGroups());
				getLogger().info(foundMessage.toString());
				
				// make sure that the save results is not null
				if (null != saveResults)
				{
					// build and log a message for what this parser saved
					StringBuilder savedMessage = new StringBuilder();
					savedMessage.append(entry.getKey());
					savedMessage.append(" / ");
					savedMessage.append("Indicators Saved: ");
					savedMessage
						.append(entry.getValue().getIndicators() - saveResults.countFailedItems(ItemType.INDICATOR));
					savedMessage.append(" / ");
					savedMessage.append("Groups Found: ");
					savedMessage.append(entry.getValue().getGroups() - saveResults.countFailedItems(ItemType.GROUP));
					getLogger().info(savedMessage.toString());
				}
				else
				{
					// nothing was saved for this parser
					StringBuilder savedMessage = new StringBuilder();
					savedMessage.append(entry.getKey());
					savedMessage.append("No indicators or groups saved!");
					getLogger().warn(savedMessage.toString());
				}
			}
			
			// build the message for total indicators/groups found
			StringBuilder foundMessage = new StringBuilder();
			foundMessage.append("Total Indicators Found: ");
			foundMessage.append(totalIndicatorsFound);
			foundMessage.append(" / ");
			foundMessage.append("Total Groups Found: ");
			foundMessage.append(totalGroupsFound);
			
			// build the message for total indicators/groups saved
			StringBuilder savedMessage = new StringBuilder();
			savedMessage.append("Total Indicators Saved: ");
			savedMessage.append(totalIndicatorsSaved);
			savedMessage.append(" / ");
			savedMessage.append("Total Groups Saved: ");
			savedMessage.append(totalGroupsSaved);
			
			// log the messages for the app
			getLogger().info(foundMessage.toString());
			getLogger().info(savedMessage.toString());
			
			// write the final message out to the file
			writeMessageTc(foundMessage.toString() + " | " + savedMessage.toString());
		}
	}
	
	/**
	 * Creates the configuration object
	 * 
	 * @param appConfig
	 * @return
	 */
	protected Configuration getConfiguration(final AppConfig appConfig)
	{
		// create the configuration for the threatconnect server
		return new Configuration(appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg());
	}
	
	/**
	 * Creates the save service that is used to save the data once parsing has completed
	 * 
	 * @param appConfig
	 * @return
	 */
	protected SaveService getSaveService(final AppConfig appConfig)
	{
		return new SaveApiService(getConfiguration(appConfig), null);
	}
	
	/**
	 * Creates the parsers for this app to use
	 * 
	 * @param appConfig
	 * @return
	 */
	protected abstract Parser[] createParsers(final AppConfig appConfig);
	
	/**
	 * Retrieve the start date for this parser to use when parsing data. Data older than this date
	 * should not be parsed
	 * 
	 * @param appConfig
	 * @return
	 * @throws ParseException
	 */
	protected abstract Date getParserStartDate(final AppConfig appConfig) throws ParseException;
	
	/**
	 * Called once parsing has completed
	 * 
	 * @param items
	 */
	protected void onParsingFinished(final List<Item> items)
	{
	
	}
	
	/**
	 * Counts all of the items of a specific type.
	 * 
	 * @param items
	 * the list of all items
	 * @param itemType
	 * the type of item to count
	 * @param recursive
	 * whether or not the associated items should be counted as well
	 * @return
	 */
	private int count(final List<Item> items, final ItemType itemType, final boolean recursive)
	{
		int count = 0;
		
		// for each of the items
		for (Item item : items)
		{
			// check to see if this item is of the correct type
			if (item.getItemType().equals(itemType))
			{
				// increment the count
				count++;
			}
			
			// check to see if this is recursive
			if (recursive)
			{
				// count all of the associated items as well
				count += count(item.getAssociatedItems(), itemType, recursive);
			}
		}
		
		return count;
	}
	
	protected class Count
	{
		private final int indicators;
		private final int groups;
		
		public Count(final int indicators, final int groups)
		{
			this.indicators = indicators;
			this.groups = groups;
		}
		
		public int getIndicators()
		{
			return indicators;
		}
		
		public int getGroups()
		{
			return groups;
		}
	}
}
