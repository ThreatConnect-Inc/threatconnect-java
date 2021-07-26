package com.threatconnect.app.execution.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

public class StringListConverter extends ListContentConverter<String>
{
	public StringListConverter()
	{
		super(String.class, StandardPlaybookType.StringArray);
	}
}
