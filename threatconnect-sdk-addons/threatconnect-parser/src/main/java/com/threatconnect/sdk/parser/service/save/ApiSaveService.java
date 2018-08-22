package com.threatconnect.sdk.parser.service.save;

import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Adversary;
import com.threatconnect.sdk.model.CustomIndicator;
import com.threatconnect.sdk.model.Document;
import com.threatconnect.sdk.model.Email;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.Signature;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.parser.service.writer.AddressWriter;
import com.threatconnect.sdk.parser.service.writer.AdversaryWriter;
import com.threatconnect.sdk.parser.service.writer.CustomIndicatorWriter;
import com.threatconnect.sdk.parser.service.writer.DocumentWriter;
import com.threatconnect.sdk.parser.service.writer.EmailAddressWriter;
import com.threatconnect.sdk.parser.service.writer.EmailWriter;
import com.threatconnect.sdk.parser.service.writer.FileWriter;
import com.threatconnect.sdk.parser.service.writer.GroupWriter;
import com.threatconnect.sdk.parser.service.writer.HostWriter;
import com.threatconnect.sdk.parser.service.writer.IncidentWriter;
import com.threatconnect.sdk.parser.service.writer.IndicatorWriter;
import com.threatconnect.sdk.parser.service.writer.SignatureWriter;
import com.threatconnect.sdk.parser.service.writer.ThreatWriter;
import com.threatconnect.sdk.parser.service.writer.UrlWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Responsible for saving the model to the server using the threatconnect sdk
 *
 * @author Greg Marut
 */
public class ApiSaveService implements SaveService
{
	private static final Logger logger = LoggerFactory.getLogger(ApiSaveService.class);
	
	private final Configuration configuration;
	private final String ownerName;
	
	// holds the hashmap to resolve the set of associated group ids for a given indicator
	protected final Map<Indicator, Set<GroupIdentifier>> associatedIndicatorGroupsIDs;
	protected final Map<Group, Set<GroupIdentifier>> associatedGroupGroupsIDs;
	
	public ApiSaveService(final Configuration configuration, final String ownerName)
	{
		this(configuration, ownerName, new HashMap<Indicator, Set<GroupIdentifier>>(),
			new HashMap<Group, Set<GroupIdentifier>>());
	}
	
	public ApiSaveService(final Configuration configuration, final String ownerName,
		final Map<Indicator, Set<GroupIdentifier>> associatedIndicatorGroupsIDs,
		final Map<Group, Set<GroupIdentifier>> associatedGroupGroupsIDs)
	{
		if (null == configuration)
		{
			throw new IllegalArgumentException("configuration cannot be null");
		}
		
		if (null == ownerName)
		{
			throw new IllegalArgumentException("ownerName cannot be null");
		}
		
		if (null == associatedIndicatorGroupsIDs)
		{
			throw new IllegalArgumentException("associatedIndicatorGroupsIDs cannot be null");
		}
		
		if (null == associatedGroupGroupsIDs)
		{
			throw new IllegalArgumentException("associatedGroupGroupsIDs cannot be null");
		}
		
		this.configuration = configuration;
		this.ownerName = ownerName;
		this.associatedIndicatorGroupsIDs = associatedIndicatorGroupsIDs;
		this.associatedGroupGroupsIDs = associatedGroupGroupsIDs;
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
	protected SaveResults saveItems(final Collection<? extends Item> items, final Connection connection)
		throws IOException
	{
		// create a new save result to return
		SaveResults saveResults = new SaveResults();
		
		// for each of the items
		for (Item item : items)
		{
			saveItem(item, ownerName, connection, saveResults);
		}
		
		return saveResults;
	}
	
	/**
	 * Saves a single item
	 *
	 * @param item
	 * @param ownerName
	 * @param connection
	 * @param saveResults
	 * @throws IOException
	 * @throws SaveItemFailedException
	 */
	protected void saveItem(final Item item, final String ownerName, final Connection connection,
		final SaveResults saveResults) throws IOException
	{
		try
		{
			// switch based on the item type
			switch (item.getItemType())
			{
				case GROUP:
					saveGroup((Group) item, ownerName, connection, saveResults);
					break;
				case INDICATOR:
					saveIndicator((Indicator) item, ownerName, connection, saveResults);
					break;
				default:
					break;
			}
		}
		catch (SaveItemFailedException e)
		{
			logger.warn(e.getMessage(), e);
			
			// add this item to the list of failed saves
			saveResults.addFailedItems(item);
			
			// this item failed to save so attempt to save the associated items individually if they
			// exist without the associations
			SaveResults childItemsSaveResults = saveItems(item.getAssociatedItems(), connection);
			saveResults.addFailedItems(childItemsSaveResults.getFailedItems());
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
	
	/**
	 * Retrieves the specific writer implementation for this indicator
	 *
	 * @param indicator
	 * @param connection
	 * @return
	 */
	protected IndicatorWriter<?, ?> getIndicatorWriter(final Indicator indicator, final Connection connection)
	{
		IndicatorWriter<?, ?> writer = null;
		
		// switch based on the indicator type
		switch (indicator.getIndicatorType())
		{
			case Address.INDICATOR_TYPE:
				writer = new AddressWriter(connection, (Address) indicator);
				break;
			case EmailAddress.INDICATOR_TYPE:
				writer = new EmailAddressWriter(connection, (EmailAddress) indicator);
				break;
			case File.INDICATOR_TYPE:
				writer = new FileWriter(connection, (File) indicator);
				break;
			case Host.INDICATOR_TYPE:
				writer = new HostWriter(connection, (Host) indicator);
				break;
			case Url.INDICATOR_TYPE:
				writer = new UrlWriter(connection, (Url) indicator);
				break;
			default:
				//this must be a custom indicator type
				writer = new CustomIndicatorWriter(connection, (CustomIndicator) indicator);
		}
		
		return writer;
	}
	
	protected com.threatconnect.sdk.server.entity.Group saveGroup(final Group group, final String ownerName,
		final Connection connection, final SaveResults saveResults) throws IOException, SaveItemFailedException
	{
		GroupWriter<?, ?> writer = getGroupWriter(group, connection);
		
		// save the group
		com.threatconnect.sdk.server.entity.Group savedGroup = writer.saveGroup(ownerName, false, true, true);
		
		if (associatedGroupGroupsIDs.containsKey(group))
		{
			Set<GroupIdentifier> groupIdentifiersToAssociate = associatedGroupGroupsIDs.get(group);
			for (GroupIdentifier groupIdentifier : groupIdentifiersToAssociate)
			{
				try
				{
					writer.associateGroup(groupIdentifier.getType(), groupIdentifier.getId(), ownerName);
				}
				catch (AssociateFailedException e)
				{
					logger.warn(e.getMessage(), e);
					saveResults.addFailedItems(group);
				}
			}
		}
		
		// save the associated indicators for this group
		saveAssociatedItems(group, ownerName, connection, writer, saveResults);
		
		// return the saved group
		return savedGroup;
	}
	
	protected com.threatconnect.sdk.server.entity.Indicator saveIndicator(final Indicator indicator,
		final String ownerName, final Connection connection, final SaveResults saveResults)
		throws IOException, SaveItemFailedException
	{
		IndicatorWriter<?, ?> writer = getIndicatorWriter(indicator, connection);
		
		// save the indicator
		com.threatconnect.sdk.server.entity.Indicator savedIndicator = writer.saveIndicator(ownerName, true, true, true);
		
		if (associatedIndicatorGroupsIDs.containsKey(indicator))
		{
			Set<GroupIdentifier> groupIdentifiersToAssociate = associatedIndicatorGroupsIDs.get(indicator);
			for (GroupIdentifier groupIdentifier : groupIdentifiersToAssociate)
			{
				try
				{
					writer.associateGroup(groupIdentifier.getType(), groupIdentifier.getId(), ownerName);
				}
				catch (AssociateFailedException e)
				{
					logger.warn(e.getMessage(), e);
					saveResults.addFailedItems(indicator);
				}
			}
		}
		
		// save the associated groups for this indicator
		saveAssociatedGroups(indicator, ownerName, connection, writer, saveResults);
		
		// return the saved indicator
		return savedIndicator;
	}
	
	/**
	 * Saves the associated groups for a given indicator
	 *
	 * @param indicator
	 * @param ownerName
	 * @param connection
	 * @param writer
	 * @throws IOException             Signals that an I/O exception of some sort has occurred. This class is the
	 *                                 general class of exceptions produced by failed or interrupted I/O operations.
	 * @throws SaveItemFailedException
	 */
	protected void saveAssociatedGroups(final Indicator indicator, final String ownerName, final Connection connection,
		IndicatorWriter<?, ?> writer, final SaveResults saveResults) throws IOException
	{
		// for each of the associated items of this group
		for (Group associatedGroup : indicator.getAssociatedItems())
		{
			try
			{
				com.threatconnect.sdk.server.entity.Group savedAssociatedGroup =
					saveGroup(associatedGroup, ownerName, connection, saveResults);
				writer.associateGroup(associatedGroup.getGroupType(), savedAssociatedGroup.getId(), ownerName);
			}
			catch (SaveItemFailedException e)
			{
				logger.warn(e.getMessage(), e);
				
				// add to the list of failed items
				saveResults.addFailedItems(associatedGroup);
				
				// this item failed to save so attempt to save the associated items individually if
				// they exist without the associations
				SaveResults childItemsSaveResults = saveItems(associatedGroup.getAssociatedItems(), connection);
				saveResults.addFailedItems(childItemsSaveResults.getFailedItems());
			}
			catch (AssociateFailedException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
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
	protected void saveAssociatedItems(final Group group, final String ownerName, final Connection connection,
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
						com.threatconnect.sdk.server.entity.Group savedAssociatedGroup =
							saveGroup(associatedGroup, ownerName, connection, saveResults);
						writer.associateGroup(associatedGroup.getGroupType(), savedAssociatedGroup.getId(), ownerName);
						break;
					case INDICATOR:
						Indicator associatedIndicator = (Indicator) associatedItem;
						saveIndicator(associatedIndicator, ownerName, connection, saveResults);
						writer.associateIndicator(associatedIndicator, ownerName);
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
				
				// this item failed to save so attempt to save the associated items individually if
				// they exist without the associations
				SaveResults childItemsSaveResults = saveItems(associatedItem.getAssociatedItems(), connection);
				saveResults.addFailedItems(childItemsSaveResults.getFailedItems());
			}
			catch (AssociateFailedException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
	}
}
