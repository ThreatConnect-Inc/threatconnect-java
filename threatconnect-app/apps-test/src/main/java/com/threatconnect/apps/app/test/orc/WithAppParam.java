package com.threatconnect.apps.app.test.orc;

import com.threatconnect.app.apps.App;

import java.util.Map;

/**
 * @author Greg Marut
 */
public class WithAppParam<A extends App> implements Then<AppOrchestration<A>>
{
	private final AppOrchestration<A> appOrchestration;
	
	WithAppParam(final AppOrchestration<A> appOrchestration)
	{
		this.appOrchestration = appOrchestration;
	}
	
	public WithAppParam<A> set(final String name, final String value)
	{
		getAppParams().put(name, value);
		
		return this;
	}
	
	private Map<String, String> getAppParams()
	{
		return then().getAppParams();
	}
	
	@Override
	public AppOrchestration<A> then()
	{
		return appOrchestration;
	}
}
