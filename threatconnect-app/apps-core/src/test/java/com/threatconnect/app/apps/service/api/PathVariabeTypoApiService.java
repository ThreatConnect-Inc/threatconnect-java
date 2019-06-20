package com.threatconnect.app.apps.service.api;

import com.threatconnect.app.apps.service.api.mapping.ApiMapping;
import com.threatconnect.app.apps.service.api.mapping.Method;
import com.threatconnect.app.apps.service.api.mapping.PathVariable;

public class PathVariabeTypoApiService extends ApiService
{
	//the path variable is purposely mispelled to check for errors
	@ApiMapping(path = "/say/hi/{name}", method = Method.GET)
	public String sayHi(@PathVariable("names") final String name)
	{
		return "Hi " + name;
	}
}
