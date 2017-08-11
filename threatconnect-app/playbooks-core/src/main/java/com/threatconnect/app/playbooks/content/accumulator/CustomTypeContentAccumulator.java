package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.converter.ContentConverter;
import com.threatconnect.app.playbooks.db.DBService;

public class CustomTypeContentAccumulator extends ContentAccumulator<byte[]>
{
	public CustomTypeContentAccumulator(final DBService dbService, final ContentConverter<byte[]> contentConverter)
	{
		super(dbService, contentConverter);
	}
	
	protected String verifyKey(final String key)
	{
		//extract the type from this key
		String actualType = super.verifyKey(key);
		
		//check to see if the actual type is a reserved data type
		if (StandardPlaybookType.isValid(actualType))
		{
			throw new IllegalArgumentException("\"" + actualType
				+ "\" is a reserved data type and may not be used to read or write custom data type values");
		}
		
		return actualType;
	}
}
