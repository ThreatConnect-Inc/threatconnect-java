package com.threatconnect.app.execution.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.execution.entity.KeyValue;

public class KeyValueConverter extends DefaultContentConverter<KeyValue>
{
	public KeyValueConverter()
	{
		super(KeyValue.class, StandardPlaybookType.KeyValue);
	}
}
