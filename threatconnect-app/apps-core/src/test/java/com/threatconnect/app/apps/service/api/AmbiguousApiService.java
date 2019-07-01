package com.threatconnect.app.apps.service.api;

import com.threatconnect.app.apps.service.api.mapping.ApiMapping;
import com.threatconnect.app.apps.service.api.mapping.Method;
import com.threatconnect.app.apps.service.api.mapping.PathVariable;

public class AmbiguousApiService extends ApiService
{
	@ApiMapping(path = "/say/hi/{name}", method = Method.GET)
	public String sayHi(@PathVariable("name") final String name)
	{
		return "Hi " + name;
	}
	
	@ApiMapping(path = "/say/{action}/{name}", method = Method.GET)
	public String saySomething(@PathVariable("action") final String action, @PathVariable("name") final String name)
	{
		return action + " " + name;
	}
	
	@Override
	public void onShutdown()
	{
	
	}
}
