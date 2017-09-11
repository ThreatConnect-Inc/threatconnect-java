package com.threatconnect.sdk.parser.service.save;

import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Adversary;
import com.threatconnect.sdk.model.Campaign;
import com.threatconnect.sdk.model.Document;
import com.threatconnect.sdk.model.Email;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.Signature;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.util.IndicatorUtil;
import com.threatconnect.sdk.model.util.ItemUtil;
import com.threatconnect.sdk.parser.service.writer.AdversaryWriter;
import com.threatconnect.sdk.parser.service.writer.CampaignWriter;
import com.threatconnect.sdk.parser.service.writer.DocumentWriter;
import com.threatconnect.sdk.parser.service.writer.EmailWriter;
import com.threatconnect.sdk.parser.service.writer.GroupWriter;
import com.threatconnect.sdk.parser.service.writer.IncidentWriter;
import com.threatconnect.sdk.parser.service.writer.LegacyBatchIndicatorWriter;
import com.threatconnect.sdk.parser.service.writer.SignatureWriter;
import com.threatconnect.sdk.parser.service.writer.ThreatWriter;
import com.threatconnect.sdk.server.entity.BatchConfig.AttributeWriteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Deprecated
public class LegacyBatchApiSaveService implements SaveService
{
	private static final Logger logger = LoggerFactory.getLogger(BatchApiSaveService.class);
	
	protected final Configuration configuration;
	protected final String ownerName;
	
	public LegacyBatchApiSaveService(final Configuration configuration, final String ownerName)
	{
		this.configuration = configuration;
		this.ownerName = ownerName;
	}
	
	/**
	 * Saves all of the items to the server using the APIs
	 *
	 * @param items
	 * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of
	 *                     exceptions produced by failed or interrupted I/O operations.
	 */
	@Override
	public SaveResults saveItems(final Collection<? extends Item> items) throws IOException
	{
		// create a new connection object from the configuration
		Connection connection = new Connection(configuration);
		
		return saveItems(items, connection);
	}
	
	/**
	 * Saves all of the items to the server using the APIs
	 *
	 * @param items
	 * @param connection
	 * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of
	 *                     exceptions produced by failed or interrupted I/O operations.
	 */
	public SaveResults saveItems(final Collection<? extends Item> items, final Connection connection) throws IOException
	{
		SaveResults saveResults = new SaveResults();
		
		// break the list of items into sets of groups and indicators
		Set<Group> groups = new HashSet<Group>();
		Set<Indicator> indicators = new HashSet<Indicator>();
		ItemUtil.separateGroupsAndIndicators(items, groups, indicators);
		
		// save all of the groups
		Map<Group, Integer> savedGroupMap = new HashMap<Group, Integer>();
		saveResults.addFailedItems(saveGroups(groups, savedGroupMap, connection));
		
		// now that the groups have been saved, we need to create a map to allow quick lookups of
		// all associated group ids for a given indicator
		Map<Indicator, Set<Integer>> associatedIndicatorGroupsIDs =
			buildAssociatedIndicatorGroupIDs(groups, indicators, savedGroupMap);
		
		// save all of the indicators
		saveResults.addFailedItems(saveIndicators(indicators, associatedIndicatorGroupsIDs, connection));
		
		return saveResults;
	}
	
	protected SaveResults saveGroups(final Collection<Group> groups, final Map<Group, Integer> savedGroupMap,
		final Connection connection) throws IOException
	{
		// create a new save result to return
		SaveResults saveResults = new SaveResults();
		
		// for each of the groups
		for (Group group : groups)
		{
			try
			{
				saveGroup(group, ownerName, savedGroupMap, connection, saveResults);
			}
			catch (SaveItemFailedException e)
			{
				logger.warn(e.getMessage(), e);
				
				// add this item to the list of failed saves
				saveResults.addFailedItems(group);
			}
		}
		
		return saveResults;
	}
	
	/**
	 * Saves the associated items for a given group
	 *
	 * @param group
	 * @param ownerName
	 * @param connection
	 * @param writer
	 * @param saveResults
	 * @throws IOException Signals that an I/O exception of some sort has occurred. This class is the general class of
	 *                     exceptions produced by failed or interrupted I/O operations.
	 */
	protected void saveAssociatedItems(final Group group, final String ownerName,
		final Map<Group, Integer> savedGroupMap, final Connection connection,
		GroupWriter<?, ?> writer, final SaveResults saveResults) throws IOException
	{
		// for each of the associated items of this group
		for (Item associatedItem : group.getAssociatedItems())
		{
			try
			{
				// switch based on the item type
				switch (associatedItem.getItemType())
				{
					case GROUP:
						Group associatedGroup = (Group) associatedItem;
						Integer savedAssociatedGroupId =
							saveGroup(associatedGroup, ownerName, savedGroupMap, connection, saveResults);
						writer.associateGroup(associatedGroup.getGroupType(), savedAssociatedGroupId);
						break;
					case INDICATOR:
						// Ignore the indicators for now, this is done later in batch save
						break;
					default:
						break;
				}
			}
			catch (SaveItemFailedException e)
			{
				logger.warn(e.getMessage(), e);
				
				// add to the list of failed items
				saveResults.addFailedItems(associatedItem);
			}
			catch (AssociateFailedException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
	}
	
	protected SaveResults saveIndicators(final Collection<Indicator> indicators,
		final Map<Indicator, Set<Integer>> associatedIndicatorGroupsIDs, final Connection connection) throws IOException
	{
		return saveIndicators(indicators, associatedIndicatorGroupsIDs, connection, AttributeWriteType.Replace);
	}
	
	protected SaveResults saveIndicators(final Collection<Indicator> indicators,
		final Map<Indicator, Set<Integer>> associatedIndicatorGroupsIDs, final Connection connection,
		final AttributeWriteType attributeWriteType) throws IOException
	{
		try
		{
			// create a new batch indicator writer
			LegacyBatchIndicatorWriter batchIndicatorWriter =
				new LegacyBatchIndicatorWriter(connection, indicators, associatedIndicatorGroupsIDs);
			
			// save the indicators
			SaveResults batchSaveResults = batchIndicatorWriter.saveIndicators(ownerName, attributeWriteType);
			
			//TODO: the batch api does not handle saving file occurrences so we have to revert to the ApiSaveService to do so
			saveFileOccurrences(indicators, batchSaveResults);
			
			return batchSaveResults;
		}
		catch (SaveItemFailedException e)
		{
			logger.warn(e.getMessage(), e);
			SaveResults saveResults = new SaveResults();
			saveResults.addFailedItems(ItemType.INDICATOR, indicators.size());
			return saveResults;
		}
	}
	
	/**
	 * TODO: the batch api does not handle saving file occurrences so we have to revert to the ApiSaveService to do so
	 *
	 * @return
	 */
	private void saveFileOccurrences(final Collection<Indicator> indicators, final SaveResults saveResults)
	{
		//extract all of the file objects from the set of indicators
		Set<File> files = IndicatorUtil.extractIndicatorSet(indicators, File.class);
		ApiSaveService apiSaveService = new ApiSaveService(configuration, ownerName);
		
		//for each of the files
		for (File file : files)
		{
			//check to see if this file has occurrences
			if (!file.getFileOccurrences().isEmpty())
			{
				try
				{
					saveResults.addFailedItems(apiSaveService.saveItems(Arrays.asList(file)));
				}
				catch (IOException e)
				{
					logger.warn(e.getMessage(), e);
					saveResults.addFailedItems(file);
				}
			}
		}
	}
	
	protected SaveResults deleteIndicators(final Collection<Indicator> indicators, final Connection connection)
		throws IOException
	{
		try
		{
			// create a new batch indicator writer
			LegacyBatchIndicatorWriter batchIndicatorWriter = new LegacyBatchIndicatorWriter(connection, indicators);
			
			// delete the indicators
			return batchIndicatorWriter.deleteIndicators(ownerName);
		}
		catch (SaveItemFailedException e)
		{
			logger.warn(e.getMessage(), e);
			SaveResults saveResults = new SaveResults();
			saveResults.addFailedItems(ItemType.INDICATOR, indicators.size());
			return saveResults;
		}
	}
	
	/**
	 * Retrieves the specific writer implementation for this group
	 *
	 * @param group
	 * @param connection
	 * @return
	 */
	protected GroupWriter<?, ?> getGroupWriter(final Group group, final Connection connection)
	{
		GroupWriter<?, ?> writer = null;
		
		// switch based on the indicator type
		switch (group.getGroupType())
		{
			case ADVERSARY:
				writer = new AdversaryWriter(connection, (Adversary) group);
				break;
			case CAMPAIGN:
				writer = new CampaignWriter(connection, (Campaign) group);
				break;
			case DOCUMENT:
				writer = new DocumentWriter(connection, (Document) group);
				break;
			case EMAIL:
				writer = new EmailWriter(connection, (Email) group);
				break;
			case INCIDENT:
				writer = new IncidentWriter(connection, (Incident) group);
				break;
			case SIGNATURE:
				writer = new SignatureWriter(connection, (Signature) group);
				break;
			case THREAT:
				writer = new ThreatWriter(connection, (Threat) group);
				break;
			default:
				throw new IllegalArgumentException("invalid group type");
		}
		
		return writer;
	}
	
	protected Integer saveGroup(final Group group, final String ownerName,
		final Map<Group, Integer> savedGroupMap, final Connection connection, final SaveResults saveResults)
		throws IOException, SaveItemFailedException
	{
		GroupWriter<?, ?> writer = getGroupWriter(group, connection);
		
		// check to see if this group has not already been saved
		if (!savedGroupMap.containsKey(group))
		{
			// save the group
			com.threatconnect.sdk.server.entity.Group savedGroup = writer.saveGroup(ownerName);
			
			// store the id in the map
			savedGroupMap.put(group, savedGroup.getId());
			
			return savedGroup.getId();
		}
		else
		{
			return savedGroupMap.get(group);
		}
	}
	
	/**
	 * Looks at all of the possible associations for each group/indicator and builds a map that can
	 * lookup all of the associated group ids for any given indicator object
	 *
	 * @param groups
	 * @param indicators
	 * @param savedGroupMap
	 * @return
	 */
	private Map<Indicator, Set<Integer>> buildAssociatedIndicatorGroupIDs(Set<Group> groups,
		Set<Indicator> indicators, Map<Group, Integer> savedGroupMap)
	{
		// holds the hashmap to resolve the set of associated group ids for a given indicator
		Map<Indicator, Set<Integer>> associatedIndicatorGroupsIDs = new HashMap<Indicator, Set<Integer>>();
		
		// for each of the groups
		for (Group group : groups)
		{
			// look up the group id for this group
			Integer groupID = savedGroupMap.get(group);
			
			// make sure this group id is not null
			if (null != groupID)
			{
				// for each of the associated items of this group
				for (Item item : group.getAssociatedItems())
				{
					// check to see if this item is an indicator
					if (item.getItemType().equals(ItemType.INDICATOR))
					{
						// add this group id to the set of associated items for this indicator
						Indicator indicator = (Indicator) item;
						getOrCreateGroupIDSet(indicator, associatedIndicatorGroupsIDs).add(groupID);
					}
				}
			}
			else
			{
				// :TODO: the group id doesn't exist so it failed to save
			}
		}
		
		// for each of the indicators
		for (Indicator indicator : indicators)
		{
			// for each of the associated groups of this indicator
			for (Group group : indicator.getAssociatedItems())
			{
				// look up the group id for this group
				Integer groupID = savedGroupMap.get(group);
				
				// make sure this group id is not null
				if (null != groupID)
				{
					// add this group id to the set of associated items for this indicator
					getOrCreateGroupIDSet(indicator, associatedIndicatorGroupsIDs).add(groupID);
				}
				else
				{
					// :TODO: the group id doesn't exist so it failed to save
				}
			}
		}
		
		return associatedIndicatorGroupsIDs;
	}
	
	private Set<Integer> getOrCreateGroupIDSet(final Indicator indicator,
		Map<Indicator, Set<Integer>> associatedIndicatorGroupsIDs)
	{
		// check to see if the map contains this set of ids
		Set<Integer> ids = associatedIndicatorGroupsIDs.get(indicator);
		if (null == ids)
		{
			ids = new HashSet<Integer>();
			associatedIndicatorGroupsIDs.put(indicator, ids);
		}
		
		return ids;
	}
}
