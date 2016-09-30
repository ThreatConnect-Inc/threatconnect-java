package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Greg Marut
 */
public class PlaybooksOrchestration
{
	private static final Logger logger = LoggerFactory.getLogger(PlaybooksOrchestration.class);
	
	private final PlaybooksOrchestrationBuilder builder;
	
	public PlaybooksOrchestration(final PlaybooksOrchestrationBuilder builder)
	{
		this.builder = builder;
	}
	
	public void execute()
	{
		execute(builder.getPlaybookRunner());
	}
	
	private void execute(final PlaybookRunner playbookRunner)
	{
		try
		{
			//get the playbook config
			PlaybookConfig playbookConfig = playbookRunner.getPlaybookConfig();
			
			//instantiate the app
			PlaybooksApp playbooksApp = playbookConfig.getPlaybookAppClass().newInstance();
			
			logger.info("Running {}", playbookConfig.getPlaybookAppClass());
			ExitStatus exitStatus = playbooksApp.execute(AppConfig.getInstance());
			
			//check to see if this was successful
			if (ExitStatus.Success.equals(exitStatus))
			{
				logger.info("{} finished successfully", playbookConfig.getPlaybookAppClass());
				
				//check to see if this runner has an app to run
				if (null != playbookRunner.getRunOnSuccess())
				{
					execute(playbookRunner.getRunOnSuccess());
				}
			}
			else
			{
				logger.info("{} finished failed", playbookConfig.getPlaybookAppClass());
				
				//check to see if this runner has an app to run
				if (null != playbookRunner.getRunOnFailure())
				{
					execute(playbookRunner.getRunOnFailure());
				}
			}
		}
		catch (Exception e)
		{
			throw new PlaybookRunnerException(e);
		}
	}
}
