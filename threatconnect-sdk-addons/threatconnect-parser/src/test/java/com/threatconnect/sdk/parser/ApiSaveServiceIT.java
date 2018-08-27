package com.threatconnect.sdk.parser;

import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.model.CustomIndicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.parser.service.save.ApiSaveService;
import com.threatconnect.sdk.parser.service.save.SaveService;

import java.util.Arrays;
import java.util.List;

/**
 * @author Greg Marut
 */
public class ApiSaveServiceIT extends SaveServiceIT
{
	@Override
	protected List<Item> buildItems()
	{
		CustomIndicator mutex = new CustomIndicator("Mutex");
		mutex.setValue("MyKeepLive1009");
		return Arrays.asList(mutex);
	}
	
	@Override
	protected SaveService getSaveService()
	{
		return new ApiSaveService(getConfiguration(SdkAppConfig.getInstance()),
			SdkAppConfig.getInstance().getApiDefaultOrg());
	}
}
