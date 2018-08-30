package com.threatconnect.sdk.parser.service.writer;

import com.google.gson.Gson;
import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.GroupType;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.parser.service.save.AssociateFailedException;
import com.threatconnect.sdk.parser.service.save.DeleteItemFailedException;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

import java.io.IOException;
import java.util.List;

public abstract class IndicatorWriter<E extends Indicator, T extends com.threatconnect.sdk.server.entity.Indicator>
	extends Writer
{
	protected final E indicatorSource;
	private final Class<T> tcModelClass;
	
	private T savedIndicator;
	
	public IndicatorWriter(final Connection connection, final E source, final Class<T> tcModelClass)
	{
		super(connection);
		this.indicatorSource = source;
		this.tcModelClass = tcModelClass;
	}
	
	/**
	 * Saves the indicator with the associated owner
	 *
	 * @param ownerName
	 * @return
	 * @throws SaveItemFailedException
	 * @throws IOException             if there was an exception communicating with the server
	 */
	public T saveIndicator(final String ownerName) throws SaveItemFailedException, IOException
	{
		return saveIndicator(ownerName, false, true, true);
	}
	
	/**
	 * Saves the indicator with the associated owner
	 *
	 * @param ownerName
	 * @param forceSaveIndicator force the save even if the indicator exists on the server
	 * @param saveAttributes     whether or not the attributes should be saved
	 * @param saveTags           whether or not the tags should be saved
	 * @return
	 * @throws SaveItemFailedException
	 * @throws IOException             if there was an exception communicating with the server
	 */
	@SuppressWarnings("unchecked")
	public T saveIndicator(final String ownerName, final boolean forceSaveIndicator, final boolean saveAttributes,
		final boolean saveTags) throws SaveItemFailedException, IOException
	{
		try
		{
			// create the writer
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			
			// map the object
			final T indicator = mapper.map(indicatorSource, tcModelClass);
			
			// attempt to lookup the indicator by the id
			final T readIndicator = lookupIndicator(buildID(), ownerName);
			
			// check to see if the indicator was found on the server
			if (null != readIndicator)
			{
				// use this indicator as the saved indicator
				savedIndicator = readIndicator;
				
				//check to see if force saving is not enabled
				if (!forceSaveIndicator)
				{
					return savedIndicator;
				}
				else
				{
					//do not destroy the existing rating and confidence unless it was updated
					if (null == indicator.getRating())
					{
						indicator.setRating(savedIndicator.getRating());
					}
					if (null == indicator.getConfidence())
					{
						indicator.setConfidence(savedIndicator.getConfidence());
					}
				}
			}
			
			if (logger.isDebugEnabled())
			{
				Gson gson = new Gson();
				logger.debug("Saving indicator: {}", gson.toJson(indicator));
			}
			
			// save or update the object
			ApiEntitySingleResponse<T, ?> response =
				(null == readIndicator) ? writer.create(indicator, ownerName) : writer.update(indicator, ownerName);
			
			// check to see if this call was successful
			if (response.isSuccess())
			{
				savedIndicator = response.getItem();
				
				// make sure the list of attributes is not empty
				if (saveAttributes && !indicatorSource.getAttributes().isEmpty())
				{
					// for each of the attributes of this group
					for (Attribute attribute : indicatorSource.getAttributes())
					{
						// map the attribute to a server entity
						com.threatconnect.sdk.server.entity.Attribute attr =
							mapper.map(attribute, com.threatconnect.sdk.server.entity.Attribute.class);
						
						// save the attributes for this indicator
						ApiEntitySingleResponse<?, ?> attrResponse = writer.addAttribute(buildID(), attr, ownerName);
						
						// check to see if this was not successful
						if (!attrResponse.isSuccess())
						{
							logger.warn("Failed to save attribute \"{}\" for: {}", attribute.getType(), buildID());
							logger.warn(attrResponse.getMessage());
							logger.warn(attribute.getValue());
						}
					}
				}
				
				// make sure the list of tags is not empty
				if (saveTags && !indicatorSource.getTags().isEmpty())
				{
					// for each of the tags
					for (String tag : indicatorSource.getTags())
					{
						// make sure this tag is not empty
						if (null != tag && !tag.isEmpty())
						{
							// save the tag for this indicator
							ApiEntitySingleResponse<?, ?> tagResponse = writer.associateTag(buildID(), tag, ownerName);
							
							// check to see if this was not successful
							if (!tagResponse.isSuccess())
							{
								logger.warn("Failed to save tag \"{}\" for: {}", tag, buildID());
								logger.warn(tagResponse.getMessage());
							}
						}
						else
						{
							logger.warn("Skipping blank tag for: {}", buildID());
						}
					}
				}
				
				// make sure the list of security labels is not empty
				associateSecurityLabels(indicatorSource.getSecurityLabels(), ownerName);
				
				return getSavedIndicator();
			}
			else
			{
				logger.warn("Failed to save indicator \"{}\": {}", buildID(), response.getMessage());
				throw new SaveItemFailedException(indicatorSource, response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new SaveItemFailedException(indicatorSource, e);
		}
	}
	
	public void associateSecurityLabels(final List<SecurityLabel> securityLabels, final String ownerName) throws IOException
	{
		// make sure the list of security labels is not empty
		if (!securityLabels.isEmpty())
		{
			//for each of the security labels
			for (SecurityLabel securityLabel : securityLabels)
			{
				createWriterAdapter().associateSecurityLabel(buildID(), securityLabel.getName(), ownerName);
			}
		}
	}
	
	public void associateGroup(final GroupType groupType, final Integer savedID, final String ownerName)
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
					response = writer.associateGroupAdversary(uniqueID, savedID, ownerName);
					break;
				case DOCUMENT:
					response = writer.associateGroupDocument(uniqueID, savedID, ownerName);
					break;
				case EMAIL:
					response = writer.associateGroupEmail(uniqueID, savedID, ownerName);
					break;
				case INCIDENT:
					response = writer.associateGroupIncident(uniqueID, savedID, ownerName);
					break;
				case SIGNATURE:
					response = writer.associateGroupSignature(uniqueID, savedID, ownerName);
					break;
				case THREAT:
					response = writer.associateGroupThreat(uniqueID, savedID, ownerName);
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
	
	/**
	 * Deletes the indicator from the server
	 *
	 * @param ownerName the owner name of the indicator
	 * @throws DeleteItemFailedException if there was any reason the indicator could not be deleted
	 */
	public void deleteIndicator(final String ownerName) throws DeleteItemFailedException
	{
		try
		{
			// create the writer
			AbstractIndicatorWriterAdapter<T> writer = createWriterAdapter();
			
			// lookup the indicator from the server
			ApiEntitySingleResponse<?, ?> response = writer.delete(buildID(), ownerName);
			
			// check to see if this was not successful
			if (!response.isSuccess())
			{
				throw new DeleteItemFailedException(response.getMessage());
			}
		}
		catch (IOException e)
		{
			throw new DeleteItemFailedException(e);
		}
	}
	
	protected abstract String buildID();
	
	/**
	 * Looks up an indicator
	 *
	 * @param lookupID  the id of the indicator to look up
	 * @param ownerName the name of the owner to use when looking up the indicator
	 * @return the existing indicator
	 * @throws FailedResponseException if the server returned an invalid response
	 * @throws IOException             if there was an exception communicating with the server
	 */
	protected T lookupIndicator(final String lookupID, final String ownerName)
	{
		AbstractIndicatorReaderAdapter<T> reader = createReaderAdapter();
		
		// make sure the lookup id is not null
		if (null != lookupID)
		{
			try
			{
				// lookup the indicator by the id
				T readIndicator = reader.getById(lookupID, ownerName);
				
				// check to see if the read indicator is not null
				if (null != readIndicator)
				{
					return readIndicator;
				}
			}
			catch (FailedResponseException | IOException e)
			{
				logger.trace("Failed to lookup indicator \"{}\"", lookupID);
				logger.trace(e.getMessage(), e);
				return null;
			}
		}
		
		return null;
	}
	
	/**
	 * Creates a reader adapter for this class
	 *
	 * @return the reader adapter for this indicator
	 */
	protected abstract AbstractIndicatorReaderAdapter<T> createReaderAdapter();
	
	/**
	 * A convenience method for creating a writer adapter for this class
	 *
	 * @return the writer adapter for this indicator
	 */
	protected abstract AbstractIndicatorWriterAdapter<T> createWriterAdapter();
	
	public T getSavedIndicator()
	{
		return savedIndicator;
	}
}
