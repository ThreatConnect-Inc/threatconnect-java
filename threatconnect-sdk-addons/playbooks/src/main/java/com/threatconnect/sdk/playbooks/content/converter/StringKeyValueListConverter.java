package com.threatconnect.sdk.playbooks.content.converter;

import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;

/**
 * @author Greg Marut
 */
public class StringKeyValueListConverter extends ListContentConverter<StringKeyValue>
{
	public StringKeyValueListConverter()
	{
		super(StringKeyValue.class);
	}
}