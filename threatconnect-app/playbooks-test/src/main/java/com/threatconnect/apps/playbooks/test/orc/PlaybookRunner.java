package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.apps.SystemPropertiesAppConfig;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.app.PlaybooksAppConfig;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.db.DBReadException;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;
import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.config.PlaybooksTestConfiguration;
import com.threatconnect.apps.playbooks.test.orc.test.TestFailureException;
import com.threatconnect.apps.playbooks.test.orc.test.Testable;
import com.threatconnect.apps.playbooks.test.util.AppTestUtil;
import com.threatconnect.apps.playbooks.test.util.ContentServiceUtil;
import org.apache.commons.lang3.StringUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class PlaybookRunner implements Runnable
{
	private static final Logger logger = LoggerFactory.getLogger(PlaybookRunner.class);
	
	private final PlaybooksOrchestration rootPlaybooksOrchestration;
	
	public PlaybookRunner(final PlaybooksOrchestration rootPlaybooksOrchestration)
	{
		this.rootPlaybooksOrchestration = rootPlaybooksOrchestration;
	}
	
	public void run()
	{
		run(rootPlaybooksOrchestration);
	}
	
	private void run(final PlaybooksOrchestration playbooksOrchestration)
	{
		try
		{
			//create an instance of the app config object and copy the default params from the test config
			AppConfig appConfig = new SystemPropertiesAppConfig()
				.copyFrom(PlaybooksTestConfiguration.getInstance().getGlobalAppConfig());
			
			//get the playbook config
			PlaybookConfig playbookConfig = playbooksOrchestration.getPlaybookConfig();
			
			//retrieve the playbooks app
			PlaybooksApp playbooksApp = playbooksOrchestration.getPlaybooksApp();
			
			//set up the test directories for the app
			AppTestUtil.configureAppTestDirectories(playbooksApp.getClass().getName(), appConfig);
			
			//initialize the playbooks app
			playbooksApp.init(appConfig);
			
			//configure the parameters before running this app
			logger.info("Configuring params for {}", playbookConfig.getPlaybookAppClass());
			configureParams(playbooksOrchestration, playbooksApp, appConfig);
			
			//retrieve the retry information for this app
			final int retryAttempts = playbooksOrchestration.getRetryAttempts();
			
			//run this app
			run(playbooksOrchestration, playbooksApp, appConfig, retryAttempts);
		}
		catch (Exception e)
		{
			throw new PlaybookRunnerException(e);
		}
	}
	
	private void run(final PlaybooksOrchestration playbooksOrchestration, final PlaybooksApp playbooksApp,
		final AppConfig appConfig, final int retryAttemptsRemaining) throws Exception
	{
		//retrieve the list of pre run actions
		List<PlaybooksAction> beforeExecute = playbooksOrchestration.getBeforeExecute();
		
		//for each of the runnable actions
		for (PlaybooksAction action : beforeExecute)
		{
			//run this action
			logger.info("BeforeRun: Executing action \"{}\"", action);
			action.run(appConfig);
		}
		
		//get the playbook config
		PlaybookConfig playbookConfig = playbooksOrchestration.getPlaybookConfig();
		
		logger.info("Running {}", playbookConfig.getPlaybookAppClass());
		ExitStatus exitStatus = playbooksApp.execute(appConfig);
		
		//check to see if this was successful
		if (ExitStatus.Success.equals(exitStatus))
		{
			//log the output variables
			logOutputs(playbooksOrchestration, playbooksApp);
			
			//validate that all the output variables were written out
			validateOutputVariablesWereWritten(playbooksOrchestration, playbooksApp);
			
			logger.info("{} finished successfully", playbookConfig.getPlaybookAppClass());
			
			//check to see if there is an onsuccess defined
			if (null != playbooksOrchestration.getOnSuccess())
			{
				//check to see if there are tests to run
				if (!playbooksOrchestration.getOnSuccess()
					.getTests().isEmpty())
				{
					logger.info("Running tests for {}", playbookConfig.getPlaybookAppClass());
					runTests(playbooksOrchestration.getOnSuccess().getTests(), playbooksApp);
				}
				
				//check to see if this runner has an app to run
				if (null != playbooksOrchestration.getOnSuccess().getRunApp())
				{
					run(playbooksOrchestration.getOnSuccess().getRunApp());
				}
			}
			//check to see if there is an onfailure definied
			else if (null != playbooksOrchestration.getOnFailure())
			{
				//we were expecting the app to fail but it was successful
				throw new PlaybookRunnerException(
					playbookConfig.getPlaybookAppClass() + " finished with an unexpected status of \"" + exitStatus
						.toString() + "\"");
			}
		}
		else
		{
			logger.info("{} failed", playbookConfig.getPlaybookAppClass());
			
			//check to see if retries are allowed
			if (retryAttemptsRemaining > 0)
			{
				//hold for the delay
				final int delaySeconds = playbooksOrchestration.getRetryDelaySeconds();
				logger.info("Waiting {} seconds before retry", delaySeconds);
				Thread.sleep(delaySeconds * 1000);
				
				//rerun this app
				final int totalRetryAttempts = playbooksOrchestration.getRetryAttempts();
				final int attempt = totalRetryAttempts - retryAttemptsRemaining + 1;
				logger.info("Attempting retry {}/{}", attempt, totalRetryAttempts);
				run(playbooksOrchestration, playbooksApp, appConfig, retryAttemptsRemaining - 1);
			}
			else
			{
				//log the output variables
				logOutputs(playbooksOrchestration, playbooksApp);
				
				//check to see if there is an onfailure defined
				if (null != playbooksOrchestration.getOnFailure())
				{
					//check to see if there are tests to run
					if (!playbooksOrchestration.getOnFailure().getTests().isEmpty())
					{
						logger.info("Running tests for {}", playbookConfig.getPlaybookAppClass());
						runTests(playbooksOrchestration.getOnFailure().getTests(), playbooksApp);
					}
					
					//check to see if this runner has an app to run
					if (null != playbooksOrchestration.getOnFailure().getRunApp())
					{
						run(playbooksOrchestration.getOnFailure().getRunApp());
					}
				}
				//check to see if there is an onsuccess defined
				else if (null != playbooksOrchestration.getOnSuccess())
				{
					//we were expecting the app to succeed but it was failed
					throw new PlaybookRunnerException(
						playbookConfig.getPlaybookAppClass() + " finished with an unexpected status of \"" + exitStatus
							.toString() + "\"");
				}
			}
		}
	}
	
	private void configureParams(final PlaybooksOrchestration playbooksOrchestration, final PlaybooksApp playbooksApp,
		final AppConfig appConfig) throws ContentException
	{
		//add all of the output params
		final String paramOutVars = StringUtils.join(playbooksOrchestration.getOutputVariables(), ",");
		appConfig.set(PlaybooksAppConfig.PARAM_OUT_VARS, paramOutVars);
		logger.debug("Setting \"{}\":\"{}\"", PlaybooksAppConfig.PARAM_OUT_VARS, paramOutVars);
		
		//for each of the app params
		for (Map.Entry<String, String> appParams : playbooksOrchestration.getAppParams().entrySet())
		{
			//set this app param
			appConfig.set(appParams.getKey(), appParams.getValue());
			logger.debug("Setting App Param \"{}\":\"{}\"", appParams.getKey(), appParams.getValue());
		}
		
		//for each of the playbook params
		for (Map.Entry<String, String> playbookParam : playbooksOrchestration.getPlaybookParams().entrySet())
		{
			//set this playbook param
			appConfig.set(playbookParam.getKey(), playbookParam.getValue());
			logger.debug("Setting Playbook Param \"{}\":\"{}\"", playbookParam.getKey(), playbookParam.getValue());
			
			//write the data to the content service
			writePlaybookParam(playbooksOrchestration, playbooksApp, playbookParam);
		}
	}
	
	private void writePlaybookParam(final PlaybooksOrchestration playbooksOrchestration,
		final PlaybooksApp playbooksApp, final Map.Entry<String, String> entry) throws ContentException
	{
		final String variable = entry.getValue();
		PlaybookVariableType type = PlaybooksVariableUtil.extractVariableType(variable);
		
		//retrieve the source and target content services for copying the data
		ContentService source = playbooksOrchestration.getContentService();
		ContentService target = playbooksApp.getContentService();
		
		if (PlaybookVariableType.String.equals(type))
		{
			if (null != source.readString(variable))
			{
				target.writeString(variable, source.readString(variable));
			}
			else if (null == target.readString(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.StringArray.equals(type))
		{
			if (null != source.readStringList(variable))
			{
				target.writeStringList(variable, source.readStringList(variable));
			}
			else if (null == target.readStringList(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.TCEntity.equals(type))
		{
			if (null != source.readTCEntity(variable))
			{
				target.writeTCEntity(variable, source.readTCEntity(variable));
			}
			else if (null == target.readTCEntity(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.TCEntityArray.equals(type))
		{
			if (null != source.readTCEntityList(variable))
			{
				target.writeTCEntityList(variable, source.readTCEntityList(variable));
			}
			else if (null == target.readTCEntityList(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.TCEnhancedEntity.equals(type))
		{
			if (null != source.readTCEntity(variable))
			{
				target.writeTCEnhancedEntity(variable, source.readTCEnhancedEntity(variable));
			}
			else if (null == target.readTCEnhancedEntity(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.TCEnhancedEntityArray.equals(type))
		{
			if (null != source.readTCEnhancedEntityList(variable))
			{
				target.writeTCEnhancedEntityList(variable, source.readTCEnhancedEntityList(variable));
			}
			else if (null == target.readTCEnhancedEntityList(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.Binary.equals(type))
		{
			if (null != source.readBinary(variable))
			{
				target.writeBinary(variable, source.readBinary(variable));
			}
			else if (null == target.readBinary(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.BinaryArray.equals(type))
		{
			if (null != source.readBinaryArray(variable))
			{
				target.writeBinaryArray(variable, source.readBinaryArray(variable));
			}
			else if (null == target.readBinaryArray(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.KeyValue.equals(type))
		{
			if (null != source.readKeyValue(variable))
			{
				target.writeKeyValue(variable, source.readKeyValue(variable));
			}
			else if (null == target.readKeyValue(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else if (PlaybookVariableType.KeyValueArray.equals(type))
		{
			if (null != source.readKeyValueArray(variable))
			{
				target.writeKeyValueArray(variable, source.readKeyValueArray(variable));
			}
			else if (null == target.readKeyValueArray(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
		else
		{
			if (null != source.readCustomType(variable))
			{
				target.writeCustomType(variable, source.readCustomType(variable));
			}
			else if (null == target.readCustomType(variable))
			{
				throw new IllegalStateException(variable
					+ " could not be resolved. Please make sure that this value was previously set by the PlaybooksOrchestration or from an upstream Playbooks app.");
			}
		}
	}
	
	private void validateOutputVariablesWereWritten(final PlaybooksOrchestration playbooksOrchestration,
		final PlaybooksApp playbooksApp)
	{
		//get the raw db service
		DBService dbService = playbooksApp.getContentService().getDbService();
		
		//for each of the outputs
		for (String variable : playbooksOrchestration.getOutputVariables())
		{
			try
			{
				//verify that these output are not null
				final byte[] value = dbService.getValue(variable);
				Assert.assertNotNull(value);
			}
			catch (AssertionError e)
			{
				logger.warn("Output Variable \"" + variable + "\" was expected but null. Was this intended?");
			}
			catch (DBReadException e)
			{
				throw new RuntimeException(e);
			}
		}
	}
	
	private void runTests(final List<Testable> tests, final PlaybooksApp playbooksApp)
	{
		try
		{
			//for each test
			for (Testable test : tests)
			{
				//run the test
				test.run(playbooksApp);
			}
		}
		catch (Exception e)
		{
			throw new TestFailureException(e);
		}
	}
	
	private void logOutputs(final PlaybooksOrchestration playbooksOrchestration, final PlaybooksApp playbooksApp)
		throws ContentException
	{
		ContentService contentService = playbooksApp.getContentService();
		
		//for each of the outputs
		for (String outputVariable : playbooksOrchestration.getOutputVariables())
		{
			logger.debug("\"{}\" = \"{}\"", outputVariable, ContentServiceUtil.read(outputVariable, contentService));
		}
	}
}
