package com.threatconnect.opendxl.client.message;

import org.msgpack.core.MessageFormat;
import org.msgpack.core.MessagePacker;
import org.msgpack.core.MessageUnpacker;
import org.msgpack.value.ValueType;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class DXLMessage implements Packable
{
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DXLMessage.class);
	
	//The message version
	public static final int MESSAGE_VERSION = 2;
	
	//The numeric type identifier for the Request message type
	public static final int MESSAGE_TYPE_REQUEST = 0;
	
	//The numeric type identifier for the Response message type
	public static final int MESSAGE_TYPE_RESPONSE = 1;
	
	//The numeric type identifier for the Event message type
	public static final int MESSAGE_TYPE_EVENT = 2;
	
	//The numeric type identifier for the ErrorResponse message type
	public static final int MESSAGE_TYPE_ERROR = 3;
	
	private int version;
	private int messageType;
	private String messageId;
	private String sourceClientId;
	private String sourceBrokerId;
	private List<String> brokerIds;
	private List<String> clientIds;
	private byte[] payload;
	private String replyToTopic;
	private String serviceID;
	
	public int getVersion()
	{
		return version;
	}
	
	public void setVersion(final int version)
	{
		this.version = version;
	}
	
	public int getMessageType()
	{
		return messageType;
	}
	
	public void setMessageType(final int messageType)
	{
		this.messageType = messageType;
	}
	
	public String getMessageId()
	{
		return messageId;
	}
	
	public void setMessageId(final String messageId)
	{
		this.messageId = messageId;
	}
	
	public String getSourceClientId()
	{
		return sourceClientId;
	}
	
	public void setSourceClientId(final String sourceClientId)
	{
		this.sourceClientId = sourceClientId;
	}
	
	public String getSourceBrokerId()
	{
		return sourceBrokerId;
	}
	
	public void setSourceBrokerId(final String sourceBrokerId)
	{
		this.sourceBrokerId = sourceBrokerId;
	}
	
	public List<String> getBrokerIds()
	{
		return brokerIds;
	}
	
	public void setBrokerIds(final List<String> brokerIds)
	{
		this.brokerIds = brokerIds;
	}
	
	public List<String> getClientIds()
	{
		return clientIds;
	}
	
	public void setClientIds(final List<String> clientIds)
	{
		this.clientIds = clientIds;
	}
	
	public byte[] getPayload()
	{
		return payload;
	}
	
	public void setPayload(final byte[] payload)
	{
		this.payload = payload;
	}
	
	public String getReplyToTopic()
	{
		return replyToTopic;
	}
	
	public void setReplyToTopic(final String replyToTopic)
	{
		this.replyToTopic = replyToTopic;
	}
	
	public String getServiceID()
	{
		return serviceID;
	}
	
	public void setServiceID(final String serviceID)
	{
		this.serviceID = serviceID;
	}
	
	@Override
	public void pack(final MessagePacker packer) throws IOException
	{
		packer.packInt(version);
		packer.packInt(messageType);
		packString(packer, messageId);
		packString(packer, sourceClientId);
		packString(packer, sourceBrokerId);
		
		packStringList(packer, brokerIds);
		packStringList(packer, clientIds);
		
		if (null != payload)
		{
			packer.packString(new String(payload));
		}
		else
		{
			packer.packString("");
		}
		
		packString(packer, replyToTopic);
		packString(packer, serviceID);
		
		//pack the unused fields
		//array
		packer.packArrayHeader(0);
		
		//source_tenant_guid
		packString(packer, "");
		
		//destination_tenant_guids
		packer.packArrayHeader(0);
	}
	
	@Override
	public void unpack(final MessageUnpacker unpacker) throws IOException
	{
		version = unpacker.unpackInt();
		messageType = unpacker.unpackInt();
		messageId = unpackString(unpacker);
		sourceClientId = unpackString(unpacker);
		sourceBrokerId = unpackString(unpacker);
		
		brokerIds = unpackStringList(unpacker);
		clientIds = unpackStringList(unpacker);
		
		payload = unpacker.unpackString().getBytes();
		
		//:TODO: why does this field sometimes come back as an array and what is it?
		MessageFormat format = unpacker.getNextFormat();
		ValueType type = format.getValueType();
		if (ValueType.ARRAY == type)
		{
			//:TODO: what do we do with this?
			List<String> list = unpackStringList(unpacker);
			logger.debug("Found an array of size " + list.size() +
				" when unpacking dxl message field \"replyToTopic\": [" + String.join(",", list) + "]");
		}
		else
		{
			replyToTopic = unpackString(unpacker);
		}
		
		serviceID = unpackString(unpacker);
		
		//ignore the remaining unused fields
		//array
		//source_tenant_guid
		//destination_tenant_guids
	}
	
	/**
	 * Can pack a string or nil depending on the value of the string
	 *
	 * @param packer
	 * @param value
	 * @throws IOException
	 */
	protected void packString(final MessagePacker packer, final String value) throws IOException
	{
		if (null != value)
		{
			packer.packString(value);
		}
		else
		{
			packer.packString("");
		}
	}
	
	protected String unpackString(final MessageUnpacker unpacker) throws IOException
	{
		MessageFormat format = unpacker.getNextFormat();
		ValueType type = format.getValueType();
		
		//check to see if the value is nil
		if (type == ValueType.NIL)
		{
			unpacker.unpackNil();
			return null;
		}
		else
		{
			return unpacker.unpackString();
		}
	}
	
	/**
	 * Can pack a string list
	 *
	 * @param packer
	 * @param values
	 * @throws IOException
	 */
	protected void packStringList(final MessagePacker packer, final List<String> values) throws IOException
	{
		//make sure the list is not null
		if (null != values)
		{
			packer.packArrayHeader(values.size());
			for (String value : values)
			{
				packer.packString(value);
			}
		}
		else
		{
			packer.packArrayHeader(0);
		}
	}
	
	protected List<String> unpackStringList(final MessageUnpacker unpacker) throws IOException
	{
		MessageFormat format = unpacker.getNextFormat();
		ValueType type = format.getValueType();
		
		//check to see if the value is nil
		if (type == ValueType.NIL)
		{
			unpacker.unpackNil();
			return null;
		}
		else
		{
			//holds the list of strings to return
			List<String> values = new ArrayList<String>();
			
			int count = unpacker.unpackArrayHeader();
			for (int i = 0; i < count; i++)
			{
				values.add(unpacker.unpackString());
			}
			
			return values;
		}
	}
}
