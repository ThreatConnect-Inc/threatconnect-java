package com.threatconnect.sdk.parser.service.writer;

import com.google.gson.Gson;
import com.threatconnect.sdk.client.reader.AbstractGroupReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.Attribute;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.GroupType;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.sdk.parser.service.save.AssociateFailedException;
import com.threatconnect.sdk.parser.service.save.DeleteItemFailedException;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Group.Type;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.util.ApiFilterType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
	 * @param ownerName the owner name of the group
	 * @return the saved group
	 * @throws SaveItemFailedException if there was an issue saving this item
	 * @throws IOException             if there was an exception communicating with the server
	 */
	public T saveGroup(final String ownerName) throws SaveItemFailedException, IOException
	{
		try
		{
			// create the writer
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			
			// map the object
			T group = mapper.map(groupSource, tcModelClass);
			
			// attempt to lookup the indicator by the id
			T readGroup = lookupGroup(groupSource.getName(), ownerName);
			
			// check to see if the indicator was found on the server
			if (null != readGroup)
			{
				// use this group as the saved group
				savedGroup = readGroup;
				return savedGroup;
			}
			
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
						// map the attribute to a server entity
						com.threatconnect.sdk.server.entity.Attribute attr =
							mapper.map(attribute, com.threatconnect.sdk.server.entity.Attribute.class);
						
						// save the attributes for this group
						ApiEntitySingleResponse<?, ?> attrResponse = writer.addAttribute(getSavedGroupID(), attr);
						
						// check to see if this was not successful
						if (!attrResponse.isSuccess())
						{
							logger.warn("Failed to save attribute \"{}\" for group id: {}", attribute.getType(),
								getSavedGroupID());
							logger.warn(attrResponse.getMessage());
							logger.warn(attribute.getValue());
						}
					}
				}
				
				// make sure the list of tags is not empty
				if (!groupSource.getTags().isEmpty())
				{
					// for each of the tags
					for (String tag : groupSource.getTags())
					{
						// make sure this tag is not empty
						if (null != tag && !tag.isEmpty())
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
						else
						{
							logger.warn("Skipping blank tag for: {}", getSavedGroupID());
						}
					}
				}
				
				return getSavedGroup();
			}
			else
			{
				throw new SaveItemFailedException(groupSource, response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new SaveItemFailedException(groupSource, e);
		}
	}
	
	/**
	 * Associates an indicator with the saved group object of this writer class
	 *
	 * @param indicator the indicator to associate to this group
	 * @throws AssociateFailedException if there as an issue associating the indicator to this group
	 * @throws IOException              if there was an exception communicating with the server
	 */
	public void associateIndicator(final Indicator indicator)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new group writer to do the association
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> response = null;
			String indicatorID = indicator.getIdentifier();
			
			// switch based on the indicator type
			switch (indicator.getIndicatorType())
			{
				case Address.INDICATOR_TYPE:
					response = writer.associateIndicatorAddress(getSavedGroupID(), indicatorID);
					break;
				case EmailAddress.INDICATOR_TYPE:
					response = writer.associateIndicatorEmailAddress(getSavedGroupID(), indicatorID);
					break;
				case File.INDICATOR_TYPE:
					File file = (File) indicator;
					List<String> hashes = Arrays.asList(file.getMd5(), file.getSha1(), file.getSha256());
					
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
				case Host.INDICATOR_TYPE:
					response = writer.associateIndicatorHost(getSavedGroupID(), indicatorID);
					break;
				case Url.INDICATOR_TYPE:
					response = writer.associateIndicatorUrl(getSavedGroupID(), indicatorID);
					break;
				default:
					response = null;
					//:FIXME: associate custom indicator to group? This method does not yet exist
					//response = writer.associateIndicatorCustom(getSavedGroupID(), indicatorID);
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
	
	public void associateGroup(final GroupType groupType, final Integer savedID)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new indicator writer to do the association
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> response = null;
			
			// switch based on the group type
			switch (groupType)
			{
				case ADVERSARY:
					response = writer.associateGroupAdversary(getSavedGroupID(), savedID);
					break;
				case DOCUMENT:
					response = writer.associateGroupDocument(getSavedGroupID(), savedID);
					break;
				case EMAIL:
					response = writer.associateGroupEmail(getSavedGroupID(), savedID);
					break;
				case INCIDENT:
					response = writer.associateGroupIncident(getSavedGroupID(), savedID);
					break;
				case SIGNATURE:
					response = writer.associateGroupSignature(getSavedGroupID(), savedID);
					break;
				case THREAT:
					response = writer.associateGroupThreat(getSavedGroupID(), savedID);
					break;
				default:
					response = null;
					break;
			}
			
			// check to see if this was not successful
			if (null != response && !response.isSuccess())
			{
				logger.warn("Failed to associate group id \"{}\" with group: {}", savedID, getSavedGroupID());
				logger.warn(response.getMessage());
			}
		}
		catch (FailedResponseException e)
		{
			throw new AssociateFailedException(e);
		}
	}
	
	/**
	 * Deletes the group from the server if it exists
	 *
	 * @param ownerName the owner name of the group
	 * @throws DeleteItemFailedException if there was any reason the group could not be deleted
	 */
	public void deleteGroup(final String ownerName) throws DeleteItemFailedException
	{
		try
		{
			// create the writer
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			
			// look up the group from the server
			T readGroup = lookupGroup(groupSource.getName(), ownerName);
			
			// make sure the group is not null
			if (null != readGroup)
			{
				// lookup the indicator from the server
				ApiEntitySingleResponse<?, ?> response = writer.delete(readGroup.getId(), ownerName);
				
				// check to see if this was not successful
				if (!response.isSuccess())
				{
					throw new DeleteItemFailedException(response.getMessage());
				}
			}
		}
		catch (IOException e)
		{
			throw new DeleteItemFailedException(e);
		}
	}
	
	/**
	 * Looks up a group by the group name
	 *
	 * @param groupName the name of the group to look up
	 * @return the existing indicator
	 * @throws FailedResponseException if the server returned an invalid response
	 * @throws IOException             if there was an exception communicating with the server
	 */
	protected T lookupGroup(final String groupName, final String ownerName)
	{
		AbstractGroupReaderAdapter<T> reader = createReaderAdapter();
		
		// make sure the group name is not null
		if (null != groupName)
		{
			try
			{
				// lookup the group by the group name
				IterableResponse<T> readGroups =
					reader.getForFilters(ownerName, false, ApiFilterType.filterName().equal(groupName));
				
				// check to see if the read groups is not null
				if (null != readGroups)
				{
					// for each of the indicators
					for (T group : readGroups)
					{
						// need to perform another lookup on the server to get all of the
						// information for this group and not just the summary
						return reader.getById(group.getId());
					}
				}
			}
			catch (FailedResponseException | IOException e)
			{
				logger.trace("Failed to lookup group name \"{}\"", groupName);
				logger.trace(e.getMessage(), e);
				return null;
			}
		}
		
		return null;
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
	 * Creates a reader adapter for this class
	 *
	 * @return the reader adapter for this indicator
	 */
	protected AbstractGroupReaderAdapter<T> createReaderAdapter()
	{
		return ReaderAdapterFactory.createGroupReader(tcGroupType, connection);
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
