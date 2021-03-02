package com.threatconnect.app.playbooks.content.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.serialize.BatchItemDeserializer;
import com.threatconnect.sdk.model.serialize.BatchItemSerializer;

import java.util.List;

public class TCEnhancedEntityListConverter extends ListContentConverter<Item>
{
	public TCEnhancedEntityListConverter()
	{
		super(Item.class, StandardPlaybookType.TCEnhancedEntityArray);
	}
	
	@Override
	public byte[] toByteArray(final List<Item> source) throws ConversionException
	{
		BatchItemSerializer batchItemSerializer = new BatchItemSerializer(source);
		return batchItemSerializer.convertToJsonString().getBytes();
	}
	
	@Override
	public List<Item> fromByteArray(final byte[] raw) throws ConversionException
	{
		return new BatchItemDeserializer(new String(raw)).convertToItems();
	}
	
	@Override
	protected JavaType constructType(final TypeFactory typeFactory)
	{
		return typeFactory.constructType(String.class);
	}
}
