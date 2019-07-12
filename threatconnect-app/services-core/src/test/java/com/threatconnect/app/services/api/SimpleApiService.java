package com.threatconnect.app.services.api;

import com.threatconnect.app.services.api.mapping.ApiMapping;
import com.threatconnect.app.services.api.mapping.Method;
import com.threatconnect.app.services.api.mapping.PathVariable;
import com.threatconnect.app.services.message.RunService;

public class SimpleApiService extends ApiService
{
	@ApiMapping(path = "/say/hi", method = Method.GET)
	public String sayHi(RunService runService)
	{
		return "Hi";
	}
	
	@ApiMapping(path = "/say/hi/{name}", method = Method.GET)
	public String sayHi(@PathVariable("name") final String name)
	{
		return "Hi " + name;
	}
	
	@ApiMapping(path = "/say/{action}/{firstName}/{lastName}", method = Method.GET)
	public String saySomething(@PathVariable("action") final String action, @PathVariable("firstName") final String firstName,
		@PathVariable("lastName") final String lastName)
	{
		return action + " " + firstName + " " + lastName;
	}
	
	@Override
	public void onShutdown()
	{
	
	}
}
