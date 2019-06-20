package com.threatconnect.app.apps.service.api;

import com.threatconnect.app.apps.service.api.mapping.ApiMapping;
import com.threatconnect.app.apps.service.api.mapping.Method;
import com.threatconnect.app.apps.service.api.mapping.PathParam;
import com.threatconnect.app.apps.service.message.RunService;

public class AmbiguousApiService extends ApiService
{
	@ApiMapping(path = "/say/hi/{name}", method = Method.GET)
	public String sayHi(@PathParam("name") final String name)
	{
		return "Hi " + name;
	}
	
	@ApiMapping(path = "/say/{action}/{name}", method = Method.GET)
	public String saySomething(@PathParam("action") final String action, @PathParam("name") final String name)
	{
		return action + " " + name;
	}
}
