package com.threatconnect.apps.app.test.orc;

import com.threatconnect.app.apps.App;
import com.threatconnect.apps.app.test.config.AppConfiguration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class AppOrchestration<A extends App>
{
	private final AppOrchestration<A> parent;
	private final A app;
	private final AppOrchestrationBuilder<A> builder;
	private final AppConfiguration appConfiguration;
	
	//holds the set of all app params
	private final Map<String, String> appParams;
	
	private POResult<A> onSuccess;
	private POResult<A> onFailure;
	
	AppOrchestration(final AppConfiguration appConfiguration, final A app, final AppOrchestrationBuilder<A> builder)
	{
		this(appConfiguration, app, builder, null);
	}
	
	AppOrchestration(final AppConfiguration appConfiguration, final A app, final AppOrchestrationBuilder<A> builder,
		final AppOrchestration<A> parent)
	{
		this.appConfiguration = appConfiguration;
		this.app = app;
		this.builder = builder;
		this.parent = parent;
		this.appParams = new HashMap<String, String>();
	}
	
	public synchronized POResult<A> onSuccess()
	{
		if (null == onSuccess)
		{
			this.onSuccess = new POResult<A>(this, builder);
		}
		
		return onSuccess;
	}
	
	public synchronized POResult<A> onFailure()
	{
		if (null == onFailure)
		{
			this.onFailure = new POResult<A>(this, builder);
		}
		
		return onFailure;
	}
	
	public AppOrchestrationBuilder<A> getAppOrchestrationBuilder()
	{
		return builder;
	}
	
	/**
	 * Builds an AppRunner object
	 *
	 * @return
	 */
	public AppRunner<A> build()
	{
		return builder.build(this);
	}
	
	public WithAppParam withAppParam()
	{
		return new WithAppParam(this);
	}
	
	A getApp()
	{
		return app;
	}
	
	AppConfiguration getAppConfiguration()
	{
		return appConfiguration;
	}
	
	Map<String, String> getAppParams()
	{
		return appParams;
	}
	
	POResult<A> getOnSuccess()
	{
		return onSuccess;
	}
	
	POResult<A> getOnFailure()
	{
		return onFailure;
	}
	
	AppOrchestration<A> getParent()
	{
		return parent;
	}
}
