package com.threatconnect.apps.playbooks.test.util;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;

/**
 * @author Greg Marut
 */
public class ContentServiceUtil
{
	public static Object read(final String variable, final ContentService contentService) throws ContentException
	{
		PlaybookVariableType type = PlaybooksVariableUtil.extractVariableType(variable);
		
		switch (type)
		{
			case String:
				return contentService.readString(variable);
			case StringArray:
				return contentService.readStringList(variable);
			case TCEntity:
				return contentService.readTCEntity(variable);
			case TCEntityArray:
				return contentService.readTCEntityList(variable);
			case Binary:
				return contentService.readBinary(variable);
			case BinaryArray:
				return contentService.readBinaryArray(variable);
			case KeyValue:
				return contentService.readKeyValue(variable);
			case KeyValueArray:
				return contentService.readKeyValueArray(variable);
			default:
				throw new IllegalArgumentException("Could not resolve the type of variable: " + variable);
		}
	}
}
