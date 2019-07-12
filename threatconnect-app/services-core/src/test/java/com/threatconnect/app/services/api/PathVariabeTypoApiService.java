package com.threatconnect.app.services.api;

import com.threatconnect.app.services.api.mapping.ApiMapping;
import com.threatconnect.app.services.api.mapping.Method;
import com.threatconnect.app.services.api.mapping.PathVariable;

public class PathVariabeTypoApiService extends ApiService
{
	//the path variable is purposely mispelled to check for errors
	@ApiMapping(path = "/say/hi/{name}", method = Method.GET)
	public String sayHi(@PathVariable("names") final String name)
	{
		return "Hi " + name;
	}
	
	@Override
	public void onShutdown()
	{
	
	}
}
