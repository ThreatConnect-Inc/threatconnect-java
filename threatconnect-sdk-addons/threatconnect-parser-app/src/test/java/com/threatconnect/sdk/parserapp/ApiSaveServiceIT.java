package com.threatconnect.sdk.parserapp;

import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.sdk.parserapp.service.save.ApiSaveService;
import com.threatconnect.sdk.parserapp.service.save.SaveService;

/**
 * @author Greg Marut
 */
public class ApiSaveServiceIT extends SaveServiceIT
{
	@Override
	protected SaveService getSaveService()
	{
		return new ApiSaveService(getConfiguration(new SystemPropertiesAppConfig()),
			new SystemPropertiesAppConfig().getApiDefaultOrg());
	}
}
