package com.threatconnect.sdk.parser.service.save;

import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Document;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.util.GroupUtil;
import com.threatconnect.sdk.model.util.ItemUtil;
import com.threatconnect.sdk.parser.service.writer.BatchWriter;
import com.threatconnect.sdk.parser.service.writer.DocumentWriter;
import com.threatconnect.sdk.server.entity.BatchConfig.AttributeWriteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class BatchApiSaveService implements SaveService
{
	private static final Logger logger = LoggerFactory.getLogger(BatchApiSaveService.class);
	
	protected final Configuration configuration;
	protected final String ownerName;
	
	protected final AttributeWriteType attributeWriteType;
	
	public BatchApiSaveService(final Configuration configuration, final String ownerName)
	{
		this(configuration, ownerName, AttributeWriteType.Replace);
	}
	
	public BatchApiSaveService(final Configuration configuration, final String ownerName,
		final AttributeWriteType attributeWriteType)
	{
		this.configuration = configuration;
		this.ownerName = ownerName;
		this.attributeWriteType = attributeWriteType;
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
		try
		{
			// create a new batch indicator writer
			BatchWriter batchWriter = createBatchWriter(connection, items);
			
			// save the indicators
			SaveResults batchSaveResults = batchWriter.save(ownerName, attributeWriteType);
			
			Set<Group> groups = new HashSet<Group>();
			Set<Indicator> indicators = new HashSet<Indicator>();
			ItemUtil.separateGroupsAndIndicators(items, groups, indicators);
			
			//upload the documents
			Set<Document> documents = GroupUtil.extractIndicatorSet(groups, Document.class);
			
			//for each of the documents
			for (Document document : documents)
			{
				//upload the document if there is one
				DocumentWriter documentWriter = new DocumentWriter(connection, document);
				documentWriter.saveGroup(ownerName);
			}
			
			return batchSaveResults;
		}
		catch (SaveItemFailedException e)
		{
			logger.warn(e.getMessage(), e);
			SaveResults saveResults = new SaveResults();
			saveResults.addFailedItems(ItemType.INDICATOR, items.size());
			return saveResults;
		}
	}
	
	public SaveResults deleteIndicators(final Collection<Indicator> indicators, final Connection connection)
		throws IOException
	{
		try
		{
			// create a new batch indicator writer
			BatchWriter batchWriter = createBatchWriter(connection, indicators);
			
			// delete the indicators
			return batchWriter.deleteIndicators(ownerName);
		}
		catch (SaveItemFailedException e)
		{
			logger.warn(e.getMessage(), e);
			SaveResults saveResults = new SaveResults();
			saveResults.addFailedItems(ItemType.INDICATOR, indicators.size());
			return saveResults;
		}
	}
	
	protected BatchWriter createBatchWriter(final Connection connection, final Collection<? extends Item> source)
	{
		return new BatchWriter(connection, source);
	}
}
