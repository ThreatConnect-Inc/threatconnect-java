package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.apps.playbooks.test.orc.test.Testable;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines what should be done after a playbook has been run
 */
public class POResult
{
	private final PlaybooksOrchestration playbooksOrchestration;
	private final PlaybooksOrchestrationBuilder builder;
	
	//holds the list of runnable tests after the playbooks app runs
	private final List<Testable> tests;
	
	private PlaybooksOrchestration runApp;
	
	POResult(final PlaybooksOrchestration playbooksOrchestration, final PlaybooksOrchestrationBuilder builder)
	{
		this.playbooksOrchestration = playbooksOrchestration;
		this.builder = builder;
		this.tests = new ArrayList<Testable>();
	}
	
	public PlaybooksOrchestration runApp(PlaybooksOrchestration playbooksOrchestration)
	{
		this.runApp = playbooksOrchestration;
		return runApp;
	}
	
	public PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		try
		{
			return runApp(playbookAppClass.newInstance());
		}
		catch (IllegalAccessException | InstantiationException e)
		{
			throw new PlaybooksOrchestrationRuntimeException(e);
		}
	}
	
	public PlaybooksOrchestration runApp(final PlaybooksApp playbookApp)
	{
		return runApp(playbookApp, true);
	}
	
	public PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass,
		final boolean addAllOutputParams)
	{
		try
		{
			return runApp(PlaybooksOrchestrationBuilder
				.createPlaybookOrchestration(playbookAppClass.newInstance(), builder, playbooksOrchestration,
					addAllOutputParams));
		}
		catch (IllegalAccessException | InstantiationException e)
		{
			throw new PlaybooksOrchestrationRuntimeException(e);
		}
	}
	
	public PlaybooksOrchestration runApp(final PlaybooksApp playbookApp,
		final boolean addAllOutputParams)
	{
		return runApp(PlaybooksOrchestrationBuilder
			.createPlaybookOrchestration(playbookApp, builder, playbooksOrchestration, addAllOutputParams));
	}
	
	public AssertOutput assertOutput()
	{
		return new AssertOutput(this);
	}
	
	/**
	 * A convenience method for building the PlaybookRunner without having to first call
	 * getPlaybooksOrchestrationBuilder()
	 *
	 * @return
	 */
	public PlaybookRunner build()
	{
		return builder.build();
	}
	
	PlaybooksOrchestration getPlaybooksOrchestration()
	{
		return playbooksOrchestration;
	}
	
	PlaybooksOrchestration getRunApp()
	{
		return runApp;
	}
	
	List<Testable> getTests()
	{
		return tests;
	}
}
