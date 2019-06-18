package com.threatconnect.app.apps.service;

import com.threatconnect.app.apps.service.api.ApiService;
import com.threatconnect.app.apps.service.api.mapping.ApiMapping;
import com.threatconnect.app.apps.service.api.mapping.Method;
import com.threatconnect.app.apps.service.message.RunService;

public class SimpleApiService extends ApiService
{
	@ApiMapping(uri = "/say/hi", method = Method.GET)
	public String sayHi(RunService runService)
	{
		return "Hi";
	}
}
