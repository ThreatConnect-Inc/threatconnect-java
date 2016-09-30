package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;

/**
 * @author Greg Marut
 */
public class PlaybooksOrchestrationBuilder
{
	//holds the initial playbook runner to execute
	private final PlaybookRunner playbookRunner;
	
	private PlaybooksOrchestrationBuilder(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		playbookRunner = createPlaybookRunner(playbookAppClass, this);
	}
	
	public PlaybooksOrchestration build()
	{
		return new PlaybooksOrchestration(this);
	}
	
	PlaybookRunner getPlaybookRunner()
	{
		return playbookRunner;
	}
	
	static PlaybookRunner createPlaybookRunner(final Class<? extends PlaybooksApp> playbookAppClass,
		final PlaybooksOrchestrationBuilder builder)
	{
		//look up the configuration for this
		PlaybookConfig playbookConfig =
			PlaybooksTestConfiguration.getInstance().getConfigurationMap().get(playbookAppClass);
		
		//make sure the playbooks app is not null
		if (null != playbookConfig)
		{
			//create the new playbook runner
			return new PlaybookRunner(playbookConfig, builder);
		}
		else
		{
			throw new IllegalArgumentException(playbookAppClass.getName() + " was not detected by the " +
				PlaybooksTestConfiguration.class.getSimpleName()
				+ ". Please make sure there is a valid install.json file configured for this app.");
		}
	}
	
	public static PlaybooksOrchestrationBuilder runApp(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		return new PlaybooksOrchestrationBuilder(playbookAppClass);
	}
}
