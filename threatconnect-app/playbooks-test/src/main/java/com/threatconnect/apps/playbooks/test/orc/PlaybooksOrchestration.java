package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.app.addons.util.config.install.PlaybookOutputVariable;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.apps.app.test.orc.AppOrchestration;
import com.threatconnect.apps.app.test.orc.POResult;
import com.threatconnect.apps.playbooks.test.config.PlaybookConfiguration;
import com.threatconnect.apps.playbooks.test.db.EmbeddedMapDBService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Greg Marut
 */
public class PlaybooksOrchestration extends AppOrchestration<PlaybooksApp>
{
	private final PlaybooksOrchestration parent;
	private final PlaybooksApp playbooksApp;
	private final PlaybookConfiguration playbookConfiguration;
	private final PlaybooksOrchestrationBuilder builder;
	private POResult<PlaybooksApp> onSuccess;
	private POResult<PlaybooksApp> onFailure;
	
	//holds the list of output variables
	private final Set<String> outputVariables;
	
	//holds the set of all input params
	private final Map<String, String> inputParams;
	private final Map<String, String> appParams;
	
	private final ContentService contentService;
	
	private int retryAttempts;
	private int retryDelaySeconds;
	
	PlaybooksOrchestration(final PlaybookConfiguration playbookConfiguration, final PlaybooksApp playbooksApp,
		final PlaybooksOrchestrationBuilder builder)
	{
		this(playbookConfiguration, playbooksApp, builder, null, true);
	}
	
	PlaybooksOrchestration(final PlaybookConfiguration playbookConfiguration, final PlaybooksApp playbooksApp,
		final PlaybooksOrchestrationBuilder builder, final PlaybooksOrchestration parent,
		final boolean addAllOutputParams)
	{
		this(playbookConfiguration, playbooksApp, builder, parent);
		this.playbookConfiguration = playbookConfiguration;
		this.playbooksApp = playbooksApp;
		this.builder = builder;
		this.parent = parent;
		this.outputVariables = new HashSet<String>();
		this.inputParams = new HashMap<String, String>();
		this.appParams = new HashMap<String, String>();
		this.contentService = new ContentService(new EmbeddedMapDBService());
		
		if (addAllOutputParams)
		{
			//add all output params as the default
			addAllOutputParams();
		}
	}
	
	public synchronized POResult onSuccess()
	{
		if (null == onSuccess)
		{
			this.onSuccess = new POResult(this, builder);
		}
		
		return onSuccess;
	}
	
	public synchronized POResult onFailure()
	{
		if (null == onFailure)
		{
			this.onFailure = new POResult(this, builder);
		}
		
		return onFailure;
	}
	
	public PlaybooksOrchestration addOutputParam(final String outputVariable, final PlaybookVariableType type)
	{
		//retrieve this variable
		String variable = playbookConfiguration.createVariableForOutputVariable(outputVariable, type);
		outputVariables.add(variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addAllOutputParams()
	{
		//for each of the output variables
		for (PlaybookOutputVariable outputVariable : playbookConfiguration.getAllOutputVariables())
		{
			//add this output param
			addOutputParam(outputVariable.getName(), outputVariable.getType());
		}
		
		return this;
	}
	
	/**
	 * In the event of an app failure, this allows the app to be rerun for a certain amount of attempts before it is
	 * considered "failed". If the app completes successfully on any of the retry attempts, the playbook execution
	 * continues as normal
	 *
	 * @param retryAttempts              the number of attempts to retry running this app
	 * @param delayBetweenRetriesSeconds the number of seconds to wait between each retry attempt
	 * @return
	 */
	public PlaybooksOrchestration allowRetries(final int retryAttempts, final int delayBetweenRetriesSeconds)
	{
		//make sure that retry attempts and delay are greater than or equal to 0
		if (retryAttempts < 0)
		{
			throw new IllegalArgumentException("retryAttempts must be greater than or equal to 0");
		}
		if (delayBetweenRetriesSeconds < 0)
		{
			throw new IllegalArgumentException("delayBetweenRetriesSeconds must be greater than or equal to 0");
		}
		
		this.retryAttempts = retryAttempts;
		this.retryDelaySeconds = delayBetweenRetriesSeconds;
		
		return this;
	}
	
	public Set<String> getOutputVariables()
	{
		return outputVariables;
	}
	
	public PlaybooksOrchestrationBuilder getPlaybooksOrchestrationBuilder()
	{
		return builder;
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
	
	public String getVariableForOutputVariable(final String param, final PlaybookVariableType type)
	{
		//retrieve this variable
		String variable = playbookConfiguration.createVariableForOutputVariable(param, type);
		
		//since this param has been requested, make sure it is added to the out params
		addOutputParam(param, type);
		
		return variable;
	}
	
	public WithInput withInput()
	{
		return new WithInput(this);
	}
	
	public WithAppParam withAppParam()
	{
		return new WithAppParam(this);
	}
	
	/**
	 * Searches the chain of PlaybooksOrchestration objects looking for the last run playbook that matches the
	 * playbookAppClass
	 *
	 * @param playbookAppClass
	 * @return
	 */
	PlaybooksOrchestration findLastRunUpsteamApp(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		List<PlaybooksOrchestration> list = findUpstreamApps(playbookAppClass);
		
		//check to see if the list is not empty
		if (!list.isEmpty())
		{
			//return the last item in the list
			return list.get(list.size() - 1);
		}
		else
		{
			return null;
		}
	}
	
	/**
	 * Searches the chain of PlaybooksOrchestration objects looking for any that match the given playbookAppClass
	 *
	 * @param playbookAppClass
	 * @return
	 */
	List<PlaybooksOrchestration> findUpstreamApps(final Class<? extends PlaybooksApp> playbookAppClass)
	{
		List<PlaybooksOrchestration> list = new ArrayList<PlaybooksOrchestration>();
		
		//check to see if the parent is not null
		if (null != parent)
		{
			//recursively call the parent
			list.addAll(parent.findUpstreamApps(playbookAppClass));
			
			//check to see if the parent's app class matches what we are looking for
			if (parent.getPlaybookConfiguration().getPlaybookAppClass().equals(playbookAppClass))
			{
				//add it to the list
				list.add(parent);
			}
		}
		
		return list;
	}
	
	PlaybooksApp getPlaybooksApp()
	{
		return playbooksApp;
	}
	
	PlaybookConfiguration getPlaybookConfiguration()
	{
		return playbookConfiguration;
	}
	
	Map<String, String> getAppParams()
	{
		return appParams;
	}
	
	Map<String, String> getInputParams()
	{
		return inputParams;
	}
	
	ContentService getContentService()
	{
		return contentService;
	}
	
	POResult getOnSuccess()
	{
		return onSuccess;
	}
	
	POResult getOnFailure()
	{
		return onFailure;
	}
	
	int getRetryAttempts()
	{
		return retryAttempts;
	}
	
	int getRetryDelaySeconds()
	{
		return retryDelaySeconds;
	}
}
