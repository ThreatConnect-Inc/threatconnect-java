package com.threatconnect.sdk.app.writer;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import com.google.gson.Gson;
import com.threatconnect.sdk.app.model.Address;
import com.threatconnect.sdk.app.model.Attribute;
import com.threatconnect.sdk.app.model.EmailAddress;
import com.threatconnect.sdk.app.model.File;
import com.threatconnect.sdk.app.model.Group;
import com.threatconnect.sdk.app.model.GroupType;
import com.threatconnect.sdk.app.model.Host;
import com.threatconnect.sdk.app.model.Indicator;
import com.threatconnect.sdk.app.model.Url;
import com.threatconnect.sdk.app.service.save.AssociateFailedException;
import com.threatconnect.sdk.app.service.save.SaveItemFailedException;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Group.Type;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

public abstract class GroupWriter<E extends Group, T extends com.threatconnect.sdk.server.entity.Group>
	extends Writer
{
	private final E groupSource;
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
	 * @return
	 * @throws SaveItemFailedException
	 * @throws IOException
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
						writer.addAttribute(getSavedGroupID(),
							mapper.map(attribute, com.threatconnect.sdk.server.entity.Attribute.class));
					}
				}
				
				// make sure the list of tags is not empty
				if (!groupSource.getTags().isEmpty())
				{
					// for each of the tags
					for (String tag : groupSource.getTags())
					{
						// save the tag for this group
						writer.associateTag(getSavedGroupID(), tag);
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
	 * Associates a group with the saved group object of this writer class
	 * 
	 * @param groupType
	 * @param savedID
	 * @throws AssociateFailedException
	 * @throws IOException
	 */
	@Override
	public void associateGroup(final GroupType groupType, final Integer savedID)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new group writer to do the association
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			
			// switch based on the group type
			switch (groupType)
			{
				case ADVERSARY:
					writer.associateGroupAdversary(getSavedGroupID(), savedID);
					break;
				case EMAIL:
					writer.associateGroupEmail(getSavedGroupID(), savedID);
					break;
				case INCIDENT:
					writer.associateGroupIncident(getSavedGroupID(), savedID);
					break;
				case SIGNATURE:
					writer.associateGroupSignature(getSavedGroupID(), savedID);
					break;
				case THREAT:
					writer.associateGroupThreat(getSavedGroupID(), savedID);
					break;
				default:
					break;
			}
		}
		catch (FailedResponseException e)
		{
			throw new AssociateFailedException(e);
		}
	}
	
	/**
	 * Associates an indicator with the saved group object of this writer class
	 * 
	 * @param indicator
	 * @param writer
	 * @throws AssociateFailedException
	 * @throws IOException
	 */
	@Override
	public void associateIndicator(final Indicator indicator)
		throws AssociateFailedException, IOException
	{
		try
		{
			// create a new group writer to do the association
			AbstractGroupWriterAdapter<T> writer = createWriterAdapter();
			
			// switch based on the indicator type
			switch (indicator.getIndicatorType())
			{
				case ADDRESS:
					writer.associateIndicatorAddress(getSavedGroupID(), ((Address) indicator).getIp());
					break;
				case EMAIL_ADDRESS:
					writer.associateIndicatorEmailAddress(getSavedGroupID(), ((EmailAddress) indicator).getAddress());
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
							writer.associateIndicatorFile(getSavedGroupID(), hash);
						}
					}
					break;
				case HOST:
					writer.associateIndicatorHost(getSavedGroupID(), ((Host) indicator).getHostName());
					break;
				case URL:
					writer.associateIndicatorUrl(getSavedGroupID(), ((Url) indicator).getText());
					break;
				default:
					break;
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
	 * @return
	 */
	private Integer getSavedGroupID()
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
	 * @return
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
