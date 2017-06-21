package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.serialize.BatchItemDeserializer;
import com.threatconnect.sdk.model.serialize.BatchItemSerializer;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import java.util.List;

public class TCEnhancedEntityConverter extends DefaultContentConverter<Item>
{
	public TCEnhancedEntityConverter()
	{
		super(Item.class);
	}
	
	@Override
	public byte[] toByteArray(final Item source) throws ConversionException
	{
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(source);
		return batchItemSerializer.convertToJsonString().getBytes();
	}
	
	@Override
	public Item fromByteArray(final byte[] raw) throws ConversionException
	{
		List<Item> items = new BatchItemDeserializer(new String(raw)).convertToItems();
		
		if (!items.isEmpty())
		{
			return items.get(0);
		}
		else
		{
			return null;
		}
	}
	
	@Override
	protected JavaType constructType(final TypeFactory typeFactory)
	{
		return typeFactory.constructType(String.class);
	}
}
