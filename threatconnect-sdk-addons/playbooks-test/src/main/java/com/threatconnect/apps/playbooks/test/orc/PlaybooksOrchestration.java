package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.db.EmbeddedMapDBService;
import com.threatconnect.plugin.pkg.config.install.PlaybookOutputVariable;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import com.threatconnect.sdk.playbooks.content.ContentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Greg Marut
 */
public class PlaybooksOrchestration
{
	private final PlaybooksOrchestration parent;
	private final PlaybookConfig playbookConfig;
	private final PlaybooksOrchestrationBuilder builder;
	private POResult onSuccess;
	private POResult onFailure;
	
	//holds the list of output params
	private final Set<String> outputParams;
	
	//holds the set of all input params
	private final Map<String, String> inputParams;
	private final ContentService contentService;
	
	PlaybooksOrchestration(final PlaybookConfig playbookConfig, final PlaybooksOrchestrationBuilder builder)
	{
		this(playbookConfig, builder, null);
	}
	
	PlaybooksOrchestration(final PlaybookConfig playbookConfig, final PlaybooksOrchestrationBuilder builder,
		final PlaybooksOrchestration parent)
	{
		this.playbookConfig = playbookConfig;
		this.builder = builder;
		this.parent = parent;
		this.outputParams = new HashSet<String>();
		this.inputParams = new HashMap<String, String>();
		this.contentService = new ContentService(new EmbeddedMapDBService());
		
		//add all output params as the default
		addAllOutputParams();
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
	
	public void addOutputParam(final String... outputVariables)
	{
		//for each of the output variables
		for (String outputVariable : outputVariables)
		{
			//validate that this is a real output param and add it to the set
			outputParams.add(playbookConfig.getOutputVariable(outputVariable).getName());
		}
	}
	
	public void addAllOutputParams()
	{
		//for each of the output variables
		for (PlaybookOutputVariable outputVariable : playbookConfig.getAllOutputVariables())
		{
			//add it to the set
			outputParams.add(outputVariable.getName());
		}
	}
	
	public Set<String> getOutputParams()
	{
		return outputParams;
	}
	
	public PlaybooksOrchestrationBuilder getPlaybooksOrchestrationBuilder()
	{
		return builder;
	}
	
	public String getVariableForOutputVariable(final String param)
	{
		//retrieve this variable
		String variable = playbookConfig.createVariableForOutputVariable(param);
		
		//since this param has been requested, make sure it is added to the out params
		addOutputParam(param);
		
		return variable;
	}
	
	public WithInput withInput()
	{
		return new WithInput(this);
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
			if (parent.getPlaybookConfig().getPlaybookAppClass().equals(playbookAppClass))
			{
				//add it to the list
				list.add(parent);
			}
		}
		
		return list;
	}
	
	PlaybookConfig getPlaybookConfig()
	{
		return playbookConfig;
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
}
