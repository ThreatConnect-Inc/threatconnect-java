package com.threatconnect.apps.playbooks.test.util;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
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
		String type = PlaybooksVariableUtil.extractVariableType(variable);
		
		if (StandardPlaybookType.String.toString().equalsIgnoreCase(type))
		{
			return contentService.readString(variable);
		}
		else if (StandardPlaybookType.StringArray.toString().equalsIgnoreCase(type))
		{
			return contentService.readStringList(variable);
		}
		else if (StandardPlaybookType.TCEntity.toString().equalsIgnoreCase(type))
		{
			return contentService.readTCEntity(variable);
		}
		else if (StandardPlaybookType.TCEntityArray.toString().equalsIgnoreCase(type))
		{
			return contentService.readTCEntityList(variable);
		}
		else if (StandardPlaybookType.TCEnhancedEntity.toString().equalsIgnoreCase(type))
		{
			return contentService.readTCEnhancedEntity(variable);
		}
		else if (StandardPlaybookType.TCEnhancedEntityArray.toString().equalsIgnoreCase(type))
		{
			return contentService.readTCEnhancedEntityList(variable);
		}
		else if (StandardPlaybookType.Binary.toString().equalsIgnoreCase(type))
		{
			return contentService.readBinary(variable);
		}
		else if (StandardPlaybookType.BinaryArray.toString().equalsIgnoreCase(type))
		{
			return contentService.readBinaryArray(variable);
		}
		else if (StandardPlaybookType.KeyValue.toString().equalsIgnoreCase(type))
		{
			return contentService.readKeyValue(variable);
		}
		else if (StandardPlaybookType.KeyValueArray.toString().equalsIgnoreCase(type))
		{
			return contentService.readKeyValueArray(variable);
		}
		else
		{
			return contentService.readCustomType(variable);
		}
	}
}
