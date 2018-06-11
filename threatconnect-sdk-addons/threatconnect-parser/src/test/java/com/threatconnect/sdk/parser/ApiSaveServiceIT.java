package com.threatconnect.sdk.parser;

import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.parser.service.save.ApiSaveService;
import com.threatconnect.sdk.parser.service.save.SaveService;

/**
 * @author Greg Marut
 */
public class ApiSaveServiceIT extends SaveServiceIT
{
	@Override
	protected SaveService getSaveService()
	{
		return new ApiSaveService(getConfiguration(SdkAppConfig.getInstance()),
			SdkAppConfig.getInstance().getApiDefaultOrg());
	}
}
