package com.threatconnect.sdk.parser.app;

import java.util.Date;
import java.util.List;

import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.parser.Parser;
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
		// create the parser
		Parser parser = createParser(appConfig);
		
		// parse the data
		List<Item> items = parser.parseData(getParserStartDate(appConfig));
		getLogger().info("Successfully parsed {} records", items.size());
		
		// allow child classes to do something with the parsed data
		onParsingFinished(items);
		
		// create a save service that to write the data
		SaveService saveService = getSaveService(appConfig);
		SaveResults saveResults = saveService.saveItems(items);
		
		// add up all of the indicators and groups
		Count count = new Count(count(items, ItemType.INDICATOR, true), count(items, ItemType.GROUP, true));
		
		// add up the totals
		int totalIndicatorsFound = count.getIndicators();
		int totalGroupsFound = count.getGroups();
		
		int totalIndicatorsSaved = count.getIndicators() - saveResults.countFailedItems(ItemType.INDICATOR);
		int totalGroupsSaved = count.getGroups() - saveResults.countFailedItems(ItemType.GROUP);
		
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
		
		return ExitStatus.Success;
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
	 * Creates the parser for this app to use
	 * 
	 * @param appConfig
	 * @return
	 */
	protected abstract Parser createParser(final AppConfig appConfig);
	
	/**
	 * Retrieve the start date for this parser to use when parsing data. Data older than this date
	 * should not be parsed
	 * 
	 * @param appConfig
	 * @return
	 */
	protected abstract Date getParserStartDate(final AppConfig appConfig);
	
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
