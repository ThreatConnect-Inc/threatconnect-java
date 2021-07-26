package com.threatconnect.app.execution.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

public class StringConverter extends DefaultContentConverter<String>
{
	public StringConverter()
	{
		super(String.class, StandardPlaybookType.String);
	}
}
