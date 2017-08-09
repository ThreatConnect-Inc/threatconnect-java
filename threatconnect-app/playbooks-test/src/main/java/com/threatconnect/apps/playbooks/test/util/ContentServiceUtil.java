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
		
		if (PlaybookVariableType.String.equals(type))
		{
			return contentService.readString(variable);
		}
		else if (PlaybookVariableType.StringArray.equals(type))
		{
			return contentService.readStringList(variable);
		}
		else if (PlaybookVariableType.TCEntity.equals(type))
		{
			return contentService.readTCEntity(variable);
		}
		else if (PlaybookVariableType.TCEntityArray.equals(type))
		{
			return contentService.readTCEntityList(variable);
		}
		else if (PlaybookVariableType.TCEnhancedEntity.equals(type))
		{
			return contentService.readTCEnhancedEntity(variable);
		}
		else if (PlaybookVariableType.TCEnhancedEntityArray.equals(type))
		{
			return contentService.readTCEnhancedEntityList(variable);
		}
		else if (PlaybookVariableType.Binary.equals(type))
		{
			return contentService.readBinary(variable);
		}
		else if (PlaybookVariableType.BinaryArray.equals(type))
		{
			return contentService.readBinaryArray(variable);
		}
		else if (PlaybookVariableType.KeyValue.equals(type))
		{
			return contentService.readKeyValue(variable);
		}
		else if (PlaybookVariableType.KeyValueArray.equals(type))
		{
			return contentService.readKeyValueArray(variable);
		}
		else
		{
			return contentService.readCustomType(variable);
		}
	}
}
