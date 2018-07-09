package com.threatconnect.sdk.parserapp;

import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.sdk.parserapp.service.save.LegacyBatchApiSaveService;
import com.threatconnect.sdk.parserapp.service.save.SaveService;

/**
 * @author Greg Marut
 */
public class LegacyBatchIT extends SaveServiceIT
{
	@Override
	protected SaveService getSaveService()
	{
		return new LegacyBatchApiSaveService(getConfiguration(new SystemPropertiesAppConfig()),
			new SystemPropertiesAppConfig().getApiDefaultOrg());
	}
}
