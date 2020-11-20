package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.entity.KeyValue;

public class KeyValueConverter extends DefaultContentConverter<KeyValue>
{
	public KeyValueConverter()
	{
		super(KeyValue.class, StandardPlaybookType.KeyValue);
	}
}
