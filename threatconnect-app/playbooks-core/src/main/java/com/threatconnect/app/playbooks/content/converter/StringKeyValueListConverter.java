package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.app.playbooks.content.entity.StringKeyValue;

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