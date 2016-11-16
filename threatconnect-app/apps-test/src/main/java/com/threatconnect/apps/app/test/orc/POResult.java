package com.threatconnect.apps.app.test.orc;

import com.threatconnect.app.apps.App;
import com.threatconnect.apps.app.test.orc.test.Testable;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines what should be done after a playbook has been run
 */
public class POResult<A extends App>
{
	private final AppOrchestration<A> appOrchestration;
	private final AppOrchestrationBuilder<A> builder;
	
	//holds the list of runnable tests after the playbooks app runs
	private final List<Testable<A>> tests;
	
	private AppOrchestration<A> runApp;
	
	POResult(final AppOrchestration<A> appOrchestration, final AppOrchestrationBuilder<A> builder)
	{
		this.appOrchestration = appOrchestration;
		this.builder = builder;
		this.tests = new ArrayList<Testable<A>>();
	}
	
	public AppOrchestration<A> runApp(final Class<? extends A> appClass)
	{
		this.runApp = builder.createAppOrchestration(appClass);
		return runApp;
	}
	
	public AppOrchestration<A> runApp(final A app)
	{
		this.runApp = builder.createAppOrchestration(app);
		return runApp;
	}
	
	public AssertOutput<A> assertOutput()
	{
		return new AssertOutput<A>(this);
	}
	
	/**
	 * Builds an AppRunner object
	 *
	 * @return
	 */
	public AppRunner<A> build()
	{
		return builder.build(appOrchestration);
	}
	
	AppOrchestration<A> getAppOrchestration()
	{
		return appOrchestration;
	}
	
	AppOrchestration<A> getRunApp()
	{
		return runApp;
	}
	
	List<Testable<A>> getTests()
	{
		return tests;
	}
}
