package com.threatconnect.sdk.parser.service.writer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.parser.model.Address;
import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.EmailAddress;
import com.threatconnect.sdk.parser.model.File;
import com.threatconnect.sdk.parser.model.GroupType;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Url;
import com.threatconnect.sdk.parser.service.save.AssociateFailedException;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Indicator.Type;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

public abstract class IndicatorWriter<E extends Indicator, T extends com.threatconnect.sdk.server.entity.Indicator>
	extends Writer
{
	protected final E indicatorSource;
	private final Class<T> tcModelClass;
	private final Type tcIndicatorType;
	
	private T savedIndicator;
	
	public IndicatorWriter(final Connection connection, final E source, final Class<T> tcModelClass,
		final Type tcIndicatorType)
	{
		super(connection);
		this.indicatorSource = source;
		this.tcModelClass = tcModelClass;
		this.tcIndicatorType = tcIndicatorType;
	}
	
	/**
	 * Saves the indicator with the associated owner
	 * 
	 * @param ownerName
	 * @return
	 * @throws SaveItemFailedException
	 * @throws IOException
	 */
	public T saveIndicator(final String ownerName)
		throws SaveItemFailedException, IOException
	{
		try
		{
			// create the writer
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			
			// map the object
			T indicator = mapper.map(indicatorSource, tcModelClass);
			
			// attempt to lookup the indicator by the id
			T readIndicator = lookupIndicator(buildID());
			
			// check to see if the indicator was found on the server
			if (null != readIndicator)
			{
				// use this indicator as the saved indicator
				savedIndicator = readIndicator;
				return savedIndicator;
			}
			
			if (logger.isDebugEnabled())
			{
				Gson gson = new Gson();
				logger.info("Saving indicator: {}", gson.toJson(indicator));
			}
			
			// save the object
			ApiEntitySingleResponse<T, ?> response = writer.create(indicator);
			
			// check to see if this call was successful
			if (response.isSuccess())
			{
				savedIndicator = response.getItem();
				
				// make sure the list of attributes is not empty
				if (!indicatorSource.getAttributes().isEmpty())
				{
					// for each of the attributes of this group
					for (Attribute attribute : indicatorSource.getAttributes())
					{
						// save the attributes for this indicator
						ApiEntitySingleResponse<?, ?> attrResponse = writer.addAttribute(buildID(),
							mapper.map(attribute, com.threatconnect.sdk.server.entity.Attribute.class));
							
						// check to see if this was not successful
						if (!attrResponse.isSuccess())
						{
							logger.warn("Failed to save attribute \"{}\" for: {}", attribute.getKey(), buildID());
							logger.warn(attrResponse.getMessage());
						}
					}
				}
				
				// make sure the list of tags is not empty
				if (!indicatorSource.getTags().isEmpty())
				{
					// for each of the tags
					for (String tag : indicatorSource.getTags())
					{
						// save the tag for this indicator
						ApiEntitySingleResponse<?, ?> tagResponse = writer.associateTag(buildID(), tag);
						
						// check to see if this was not successful
						if (!tagResponse.isSuccess())
						{
							logger.warn("Failed to save tag \"{}\" for: {}", tag, buildID());
							logger.warn(tagResponse.getMessage());
						}
					}
				}
				
				return getSavedIndicator();
			}
			else
			{
				throw new SaveItemFailedException(response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new SaveItemFailedException(e);
		}
	}
	
	@Override
	public void associateGroup(final GroupType groupType, final Integer savedID)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new indicator writer to do the association
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> response = null;
			
			// holds the uniqueid of this indicator
			final String uniqueID = buildID();
			
			// switch based on the group type
			switch (groupType)
			{
				case ADVERSARY:
					response = writer.associateGroupAdversary(uniqueID, savedID);
					break;
				case DOCUMENT:
					response = writer.associateGroupDocument(uniqueID, savedID);
					break;
				case EMAIL:
					response = writer.associateGroupEmail(uniqueID, savedID);
					break;
				case INCIDENT:
					response = writer.associateGroupIncident(uniqueID, savedID);
					break;
				case SIGNATURE:
					response = writer.associateGroupSignature(uniqueID, savedID);
					break;
				case THREAT:
					response = writer.associateGroupThreat(uniqueID, savedID);
					break;
				default:
					response = null;
					break;
			}
			
			// check to see if this was not successful
			if (null != response && !response.isSuccess())
			{
				logger.warn("Failed to associate group id \"{}\" with indicator: {}", savedID, uniqueID);
				logger.warn(response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new AssociateFailedException(e);
		}
	}
	
	@Override
	public void associateIndicator(final Indicator indicator)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new indicator writer to do the association
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> response = null;
			String indicatorID = null;
			
			// holds the uniqueid of this indicator
			final String uniqueID = buildID();
			
			// switch based on the indicator type
			switch (indicator.getIndicatorType())
			{
				case ADDRESS:
					indicatorID = ((Address) indicator).getIp();
					response = writer.associateIndicatorAddress(uniqueID, indicatorID);
					break;
				case EMAIL_ADDRESS:
					indicatorID = ((EmailAddress) indicator).getAddress();
					response = writer.associateIndicatorEmailAddress(uniqueID, indicatorID);
					break;
				case FILE:
					File file = (File) indicator;
					List<String> hashes = Arrays.asList(new String[]
					{
						file.getMd5(), file.getSha1(), file.getSha256()
					});
					
					// :TODO: the plural version of this method throws an internal sdk error so we
					// have
					// to use the single method and loop through the hashes
					// for each of the hashes
					for (String hash : hashes)
					{
						// make sure the hash is not null or empty
						if (null != hash && !hash.isEmpty())
						{
							indicatorID = hash;
							response = writer.associateIndicatorFile(uniqueID, indicatorID);
						}
					}
					break;
				case HOST:
					indicatorID = ((Host) indicator).getHostName();
					response = writer.associateIndicatorHost(uniqueID, indicatorID);
					break;
				case URL:
					indicatorID = ((Url) indicator).getText();
					response = writer.associateIndicatorUrl(uniqueID, indicatorID);
					break;
				default:
					indicatorID = null;
					response = null;
					break;
			}
			
			// check to see if this was not successful
			if (null != response && !response.isSuccess())
			{
				logger.warn("Failed to associate indicator \"{}\" with indicator: {}", indicatorID, uniqueID);
				logger.warn(response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new AssociateFailedException(e);
		}
	}
	
	protected abstract String buildID();
	
	/**
	 * Looks up an indicator by the id
	 * 
	 * @param lookupID
	 * @return
	 * @throws FailedResponseException
	 * @throws IOException
	 */
	private T lookupIndicator(final String lookupID)
	{
		AbstractIndicatorReaderAdapter<T> reader = createReaderAdapter();
		
		// make sure the lookup id is not null
		if (null != lookupID)
		{
			try
			{
				// lookup the indicator by the id
				T readIndicator = reader.getById(lookupID);
				
				// check to see if the read indicator is not null
				if (null != readIndicator)
				{
					return readIndicator;
				}
			}
			catch (FailedResponseException | IOException e)
			{
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Creates a reader adapter for this class
	 * 
	 * @return
	 */
	protected AbstractIndicatorReaderAdapter<T> createReaderAdapter()
	{
		return ReaderAdapterFactory.createIndicatorReader(tcIndicatorType, connection);
	}
	
	/**
	 * A convenience method for creating a writer adapter for this class
	 * 
	 * @return
	 */
	protected AbstractIndicatorWriterAdapter<T> createWriterAdapter()
	{
		return WriterAdapterFactory.createIndicatorWriter(tcIndicatorType, connection);
	}
	
	public T getSavedIndicator()
	{
		return savedIndicator;
	}
}
