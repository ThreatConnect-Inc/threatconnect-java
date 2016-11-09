package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.app.playbooks.app.PlaybooksApp;

/**
 * @author Greg Marut
 */
public final class PlaybooksOrchestrationBuilder
{
	//holds the initial playbook runner to execute
	private final PlaybooksOrchestration playbooksOrchestration;
	
	private PlaybooksOrchestrationBuilder(final Class<? extends PlaybooksApp> playbookAppClass,
		final boolean addAllOutputParams)
	{
		playbooksOrchestration = createPlaybookOrchestration(playbookAppClass, this, null, addAllOutputParams);
	}
	
	public PlaybookRunner build()
	{
		return new PlaybookRunner(playbooksOrchestration);
	}
	
	public PlaybooksOrchestration getPlaybooksOrchestration()
	{
		return playbooksOrchestration;
	}
	
	static PlaybooksOrchestration createPlaybookOrchestration(final Class<? extends PlaybooksApp> playbookAppClass,
		final PlaybooksOrchestrationBuilder builder, final PlaybooksOrchestration parent,
		final boolean addAllOutputParams)
	{
		//look up the configuration for this
		PlaybookConfig playbookConfig =
			PlaybooksTestConfiguration.getInstance().getConfigurationMap().get(playbookAppClass);
		
		//make sure the playbooks app is not null
		if (null != playbookConfig)
		{
			//create the new playbook runner
			return new PlaybooksOrchestration(playbookConfig, builder, parent, addAllOutputParams);
		}
		else
		{
			throw new IllegalArgumentException(playbookAppClass.getName() + " was not detected by the " +
				PlaybooksTestConfiguration.class.getSimpleName()
				+ ". Please make sure there is a valid install.json file configured for this app.");
		}
	}
	
	public static PlaybooksOrchestrationBuilder create(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		return create(playbookAppClass, true);
	}
	
	public static PlaybooksOrchestrationBuilder create(final Class<? extends PlaybooksApp> playbookAppClass,
		final boolean addAllOutputParams)
	{
		return new PlaybooksOrchestrationBuilder(playbookAppClass, addAllOutputParams);
	}
	
	public static PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		return runApp(playbookAppClass, true);
	}
	
	public static PlaybooksOrchestration runApp(final Class<? extends PlaybooksApp> playbookAppClass,
		final boolean addAllOutputParams)
	{
		return new PlaybooksOrchestrationBuilder(playbookAppClass, addAllOutputParams).getPlaybooksOrchestration();
	}
}
