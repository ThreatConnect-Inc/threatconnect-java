package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;

/**
 * @author Greg Marut
 */
public final class PlaybooksOrchestrationBuilder
{
	//holds the initial playbook runner to execute
	private final PlaybooksOrchestration playbooksOrchestration;
	
	private PlaybooksOrchestrationBuilder(final PlaybooksApp playbookApp, final boolean addAllOutputParams)
	{
		playbooksOrchestration = createPlaybookOrchestration(playbookApp, this, null, addAllOutputParams);
	}
	
	public PlaybookRunner build()
	{
		return new PlaybookRunner(playbooksOrchestration);
	}
	
	public PlaybooksOrchestration getPlaybooksOrchestration()
	{
		return playbooksOrchestration;
	}
	
	static PlaybooksOrchestration createPlaybookOrchestration(PlaybooksApp playbookApp,
		final PlaybooksOrchestrationBuilder builder, final PlaybooksOrchestration parent,
		final boolean addAllOutputParams)
	{
		//look up the configuration for this
		PlaybookConfig playbookConfig =
			PlaybooksTestConfiguration.getInstance().getConfigurationMap().get(playbookApp.getClass());
		
		//make sure the playbooks app is not null
		if (null != playbookConfig)
		{
			//create the new playbook orchestration
			return new PlaybooksOrchestration(playbookConfig, playbookApp, builder, parent, addAllOutputParams);
		}
		else
		{
			throw new IllegalArgumentException(playbookApp.getClass().getName() + " was not detected by the " +
				PlaybooksTestConfiguration.class.getSimpleName()
				+ ". Please make sure there is a valid install.json file configured for this app.");
		}
	}
	
	public static PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		return runApp(playbookAppClass, true);
	}
	
	public static PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass,
		final boolean addAllOutputParams)
	{
		try
		{
			return new PlaybooksOrchestrationBuilder(playbookAppClass.newInstance(), addAllOutputParams)
				.getPlaybooksOrchestration();
		}
		catch (IllegalAccessException | InstantiationException e)
		{
			throw new PlaybooksOrchestrationRuntimeException(e);
		}
	}
	
	public static PlaybooksOrchestration runApp(final PlaybooksApp playbookApp, final boolean addAllOutputParams)
	{
		return new PlaybooksOrchestrationBuilder(playbookApp, addAllOutputParams).getPlaybooksOrchestration();
	}
}
