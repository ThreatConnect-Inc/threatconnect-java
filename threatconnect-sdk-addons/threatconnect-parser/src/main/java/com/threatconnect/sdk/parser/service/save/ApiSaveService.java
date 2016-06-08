package com.threatconnect.sdk.parser.service.save;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.Address;
import com.threatconnect.sdk.parser.model.Adversary;
import com.threatconnect.sdk.parser.model.Document;
import com.threatconnect.sdk.parser.model.Email;
import com.threatconnect.sdk.parser.model.EmailAddress;
import com.threatconnect.sdk.parser.model.File;
import com.threatconnect.sdk.parser.model.Group;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.parser.model.Incident;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.model.Signature;
import com.threatconnect.sdk.parser.model.Threat;
import com.threatconnect.sdk.parser.model.Url;
import com.threatconnect.sdk.parser.service.writer.AddressWriter;
import com.threatconnect.sdk.parser.service.writer.AdversaryWriter;
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
	
	public ApiSaveService(final Configuration configuration, final String ownerName)
	{
		this.configuration = configuration;
		this.ownerName = ownerName;
	}
	
	/**
	 * Saves all of the items to the server using the APIs
	 * 
	 * @param items
	 * @throws IOException
	 * Signals that an I/O exception of some sort has occurred. This
	 * class is the general class of exceptions produced by failed or
	 * interrupted I/O operations.
	 */
	@Override
	public SaveResults saveItems(final List<? extends Item> items) throws IOException
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
	 * @throws IOException
	 * Signals that an I/O exception of some sort has occurred. This
	 * class is the general class of exceptions produced by failed or
	 * interrupted I/O operations.
	 */
	protected SaveResults saveItems(final List<? extends Item> items, final Connection connection) throws IOException
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
		final SaveResults saveResults)
			throws IOException
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
			case ADDRESS:
				writer = new AddressWriter(connection, (Address) indicator);
				break;
			case EMAILADDRESS:
				writer = new EmailAddressWriter(connection, (EmailAddress) indicator);
				break;
			case FILE:
				writer = new FileWriter(connection, (File) indicator);
				break;
			case HOST:
				writer = new HostWriter(connection, (Host) indicator);
				break;
			case URL:
				writer = new UrlWriter(connection, (Url) indicator);
				break;
			default:
				throw new IllegalArgumentException("invalid indicator type");
		}
		
		return writer;
	}
	
	protected com.threatconnect.sdk.server.entity.Group saveGroup(final Group group, final String ownerName,
		final Connection connection, final SaveResults saveResults) throws IOException, SaveItemFailedException
	{
		GroupWriter<?, ?> writer = getGroupWriter(group, connection);
		
		// save the group
		com.threatconnect.sdk.server.entity.Group savedGroup = writer.saveGroup(ownerName);
		
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
		com.threatconnect.sdk.server.entity.Indicator savedIndicator = writer.saveIndicator(ownerName);
		
		// save the associated groups for this indicator
		saveAssociatedGroups(indicator, ownerName, connection, writer, saveResults);
		
		// return the saved indicator
		return savedIndicator;
	}
	
	/**
	 * Saves the associated groups for a given indicator
	 * 
	 * @param item
	 * @param ownerName
	 * @param connection
	 * @param writer
	 * @throws IOException
	 * Signals that an I/O exception of some sort has occurred. This
	 * class is the general class of exceptions produced by failed or
	 * interrupted I/O operations.
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
				writer.associateGroup(associatedGroup.getGroupType(), savedAssociatedGroup.getId());
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
	 * @throws IOException
	 * Signals that an I/O exception of some sort has occurred. This
	 * class is the general class of exceptions produced by failed or
	 * interrupted I/O operations.
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
						writer.associateGroup(associatedGroup.getGroupType(), savedAssociatedGroup.getId());
						break;
					case INDICATOR:
						Indicator associatedIndicator = (Indicator) associatedItem;
						saveIndicator(associatedIndicator, ownerName, connection, saveResults);
						writer.associateIndicator(associatedIndicator);
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
