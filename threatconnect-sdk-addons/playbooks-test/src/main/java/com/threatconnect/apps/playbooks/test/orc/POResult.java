package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.sdk.playbooks.app.PlaybooksApp;

/**
 * Defines what should be done after a playbook has been run
 */
public class POResult
{
	private final PlaybooksOrchestration playbooksOrchestration;
	private final PlaybooksOrchestrationBuilder builder;
	
	private PlaybooksOrchestration runApp;
	
	POResult(final PlaybooksOrchestration playbooksOrchestration, final PlaybooksOrchestrationBuilder builder)
	{
		this.playbooksOrchestration = playbooksOrchestration;
		this.builder = builder;
	}
	
	public PlaybooksOrchestration runApp(PlaybooksOrchestration playbooksOrchestration)
	{
		this.runApp = playbooksOrchestration;
		return runApp;
	}
	
	public PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		return runApp(playbookAppClass, true);
	}
	
	public PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass,
		final boolean addAllOutputParams)
	{
		return runApp(PlaybooksOrchestrationBuilder
			.createPlaybookOrchestration(playbookAppClass, builder, playbooksOrchestration, addAllOutputParams));
	}
	
	PlaybooksOrchestration getRunApp()
	{
		return runApp;
	}
}
