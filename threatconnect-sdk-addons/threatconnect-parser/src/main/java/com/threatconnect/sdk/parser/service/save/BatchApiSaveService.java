package com.threatconnect.sdk.parser.service.save;

import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.util.IndicatorUtil;
import com.threatconnect.sdk.model.util.ItemUtil;
import com.threatconnect.sdk.parser.service.writer.BatchWriter;
import com.threatconnect.sdk.server.entity.BatchConfig.AttributeWriteType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class BatchApiSaveService implements SaveService
{
	private static final Logger logger = LoggerFactory.getLogger(BatchApiSaveService.class);
	
	protected final Configuration configuration;
	protected final String ownerName;
	
	public BatchApiSaveService(final Configuration configuration, final String ownerName)
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
	protected SaveResults saveItems(final Collection<? extends Item> items, final Connection connection)
		throws IOException
	{
		return saveItems(items, connection, AttributeWriteType.Replace);
	}
	
	protected SaveResults saveItems(final Collection<? extends Item> items, final Connection connection,
		final AttributeWriteType attributeWriteType) throws IOException
	{
		try
		{
			// create a new batch indicator writer
			BatchWriter batchWriter = new BatchWriter(connection, items);
			
			// save the indicators
			SaveResults batchSaveResults = batchWriter.save(ownerName, attributeWriteType);
			
			//TODO: the batch api does not handle saving file occurrences so we have to revert to the ApiSaveService to do so
			//split all of the items based on groups and indicators
			Set<Group> groups = new HashSet<Group>();
			Set<Indicator> indicators = new HashSet<Indicator>();
			ItemUtil.separateGroupsAndIndicators(items, groups, indicators);
			saveFileOccurrences(indicators, batchSaveResults);
			
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
					saveResults.addFailedItems(apiSaveService.saveItems(Collections.singletonList(file)));
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
			BatchWriter batchWriter = new BatchWriter(connection, indicators);
			
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
}
