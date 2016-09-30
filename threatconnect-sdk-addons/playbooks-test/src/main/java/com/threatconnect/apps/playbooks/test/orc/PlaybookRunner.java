package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;

/**
 * @author Greg Marut
 */
public class PlaybookRunner
{
	private final PlaybookConfig playbookConfig;
	private final PlaybooksOrchestrationBuilder builder;
	private PlaybookRunner runOnSuccess;
	private PlaybookRunner runOnFailure;
	
	PlaybookRunner(final PlaybookConfig playbookConfig, final PlaybooksOrchestrationBuilder builder)
	{
		this.playbookConfig = playbookConfig;
		this.builder = builder;
	}
	
	public void runAppOnSuccess(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		this.runOnSuccess = PlaybooksOrchestrationBuilder.createPlaybookRunner(playbookAppClass, builder);
	}
	
	public void runAppOnFailure(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		this.runOnFailure = PlaybooksOrchestrationBuilder.createPlaybookRunner(playbookAppClass, builder);
	}
	
	public PlaybookRunner getRunOnSuccess()
	{
		return runOnSuccess;
	}
	
	public PlaybookRunner getRunOnFailure()
	{
		return runOnFailure;
	}
	
	public PlaybooksOrchestrationBuilder getPlaybooksOrchestrationBuilder()
	{
		return builder;
	}
	
	PlaybookConfig getPlaybookConfig()
	{
		return playbookConfig;
	}
}
