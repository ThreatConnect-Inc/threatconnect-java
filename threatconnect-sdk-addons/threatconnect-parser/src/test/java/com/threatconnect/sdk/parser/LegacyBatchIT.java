package com.threatconnect.sdk.parser;

import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.parser.service.save.LegacyBatchApiSaveService;
import com.threatconnect.sdk.parser.service.save.SaveService;

/**
 * @author Greg Marut
 */
public class LegacyBatchIT extends SaveServiceIT
{
	@Override
	protected SaveService getSaveService()
	{
		return new LegacyBatchApiSaveService(getConfiguration(SdkAppConfig.getInstance()),
			SdkAppConfig.getInstance().getApiDefaultOrg());
	}
}
