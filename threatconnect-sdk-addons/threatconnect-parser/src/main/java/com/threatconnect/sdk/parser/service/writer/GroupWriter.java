package com.threatconnect.sdk.parser.service.writer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.parser.model.Address;
import com.threatconnect.sdk.parser.model.Attribute;
import com.threatconnect.sdk.parser.model.EmailAddress;
import com.threatconnect.sdk.parser.model.File;
import com.threatconnect.sdk.parser.model.Group;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Url;
import com.threatconnect.sdk.parser.service.save.AssociateFailedException;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Group.Type;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

public abstract class GroupWriter<E extends Group, T extends com.threatconnect.sdk.server.entity.Group>
	extends Writer
{
	protected final E groupSource;
	private final Class<T> tcModelClass;
	private final Type tcGroupType;
	
	private T savedGroup;
	
	public GroupWriter(final Connection connection, final E source, final Class<T> tcModelClass,
		final Type tcGroupType)
	{
		super(connection);
		this.groupSource = source;
		this.tcModelClass = tcModelClass;
		this.tcGroupType = tcGroupType;
	}
	
	/**
	 * Saves the group with the associated owner
	 * 
	 * @param ownerName
	 * the owner name of the group
	 * @return the saved group
	 * @throws SaveItemFailedException
	 * if there was an issue saving this item
	 * @throws IOException
	 * if there was an exception communicating with the server
	 */
	public T saveGroup(final String ownerName)
		throws SaveItemFailedException, IOException
	{
		try
		{
			// create the writer
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			
			// map the object
			T group = mapper.map(groupSource, tcModelClass);
			
			if (logger.isDebugEnabled())
			{
				Gson gson = new Gson();
				logger.info("Saving group: {}", gson.toJson(group));
			}
			
			// save the object
			ApiEntitySingleResponse<T, ?> response = writer.create(group);
			
			// check to see if this call was successful
			if (response.isSuccess())
			{
				savedGroup = response.getItem();
				
				// make sure the list of attributes is not empty
				if (!groupSource.getAttributes().isEmpty())
				{
					// for each of the attributes of this group
					for (Attribute attribute : groupSource.getAttributes())
					{
						// save the attributes for this group
						ApiEntitySingleResponse<?, ?> attrResponse = writer.addAttribute(getSavedGroupID(),
							mapper.map(attribute, com.threatconnect.sdk.server.entity.Attribute.class));
							
						// check to see if this was not successful
						if (!attrResponse.isSuccess())
						{
							logger.warn("Failed to save attribute \"{}\" for group id: {}", attribute.getType(),
								getSavedGroupID());
							logger.warn(attrResponse.getMessage());
						}
					}
				}
				
				// make sure the list of tags is not empty
				if (!groupSource.getTags().isEmpty())
				{
					// for each of the tags
					for (String tag : groupSource.getTags())
					{
						// save the tag for this group
						ApiEntitySingleResponse<?, ?> tagResponse = writer.associateTag(getSavedGroupID(), tag);
						
						// check to see if this was not successful
						if (!tagResponse.isSuccess())
						{
							logger.warn("Failed to save tag \"{}\" for group id: {}", tag, getSavedGroupID());
							logger.warn(tagResponse.getMessage());
						}
					}
				}
				
				return getSavedGroup();
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
	
	/**
	 * Associates an indicator with the saved group object of this writer class
	 * 
	 * @param indicator
	 * the indicator to associate to this group
	 * @throws AssociateFailedException
	 * if there as an issue associating the indicator to this group
	 * @throws IOException
	 * if there was an exception communicating with the server
	 */
	public void associateIndicator(final Indicator indicator)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new group writer to do the association
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> response = null;
			String indicatorID = null;
			
			// switch based on the indicator type
			switch (indicator.getIndicatorType())
			{
				case ADDRESS:
					indicatorID = ((Address) indicator).getIp();
					response = writer.associateIndicatorAddress(getSavedGroupID(), indicatorID);
					break;
				case EMAIL_ADDRESS:
					indicatorID = ((EmailAddress) indicator).getAddress();
					response = writer.associateIndicatorEmailAddress(getSavedGroupID(), indicatorID);
					break;
				case FILE:
					File file = (File) indicator;
					List<String> hashes = Arrays.asList(new String[]
					{
						file.getMd5(), file.getSha1(), file.getSha256()
					});
					
					// :TODO: the plural version of this method throws an internal sdk error so we
					// have to use the single method and loop through the hashes for each of the
					// hashes
					for (String hash : hashes)
					{
						// make sure the hash is not null or empty
						if (null != hash && !hash.isEmpty())
						{
							indicatorID = hash;
							response = writer.associateIndicatorFile(getSavedGroupID(), indicatorID);
						}
					}
					break;
				case HOST:
					indicatorID = ((Host) indicator).getHostName();
					response = writer.associateIndicatorHost(getSavedGroupID(), indicatorID);
					break;
				case URL:
					indicatorID = ((Url) indicator).getText();
					response = writer.associateIndicatorUrl(getSavedGroupID(), indicatorID);
					break;
				default:
					indicatorID = null;
					response = null;
					break;
			}
			
			// check to see if this was not successful
			if (null != response && !response.isSuccess())
			{
				logger.warn("Failed to associate indicator \"{}\" with group id: {}", indicatorID, getSavedGroupID());
				logger.warn(response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new AssociateFailedException(e);
		}
	}
	
	/**
	 * Retrieves the id of the saved group
	 * 
	 * @return the id of the saved group
	 */
	protected Integer getSavedGroupID()
	{
		com.threatconnect.sdk.server.entity.Group savedGroup = getSavedGroup();
		
		// make sure the saved group is not null
		if (null != savedGroup)
		{
			return savedGroup.getId();
		}
		
		throw new IllegalStateException("group is not saved");
	}
	
	/**
	 * A convenience method for creating a writer adapter for this class
	 * 
	 * @return the group writer adapter
	 */
	protected AbstractGroupWriterAdapter<T> createWriterAdapter()
	{
		return WriterAdapterFactory.createGroupWriter(tcGroupType, connection);
	}
	
	public T getSavedGroup()
	{
		return savedGroup;
	}
}
