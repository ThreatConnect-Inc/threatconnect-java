package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.db.EmbeddedMapDBService;
import com.threatconnect.plugin.pkg.config.install.PlaybookOutputVariable;
import com.threatconnect.sdk.playbooks.content.ContentService;
import com.threatconnect.sdk.playbooks.content.StandardType;
import com.threatconnect.sdk.playbooks.content.accumulator.ContentException;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.content.entity.TCEntity;
import com.threatconnect.sdk.playbooks.util.PlaybooksVariableUtil;
import org.junit.Assert;

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
		this.playbookConfig = playbookConfig;
		this.builder = builder;
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
	
	public PlaybooksOrchestration addStringInput(final String param, final String value) throws ContentException
	{
		if (!PlaybooksVariableUtil.isVariable(value))
		{
			//store this object in the local content service
			final String variable = playbookConfig.createVariableForInputParam(param, StandardType.String);
			contentService.writeString(variable, value);
			inputParams.put(param, variable);
		}
		else
		{
			addStringVariable(param, value);
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addStringVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.String, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addStringListInput(final String param, final List<String> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = playbookConfig.createVariableForInputParam(param, StandardType.StringArray);
		contentService.writeStringList(variable, value);
		inputParams.put(param, variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addStringListVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.StringArray, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addBinaryInput(final String param, final byte[] value) throws ContentException
	{
		//store this object in the local content service
		final String variable = playbookConfig.createVariableForInputParam(param, StandardType.Binary);
		contentService.writeBinary(variable, value);
		inputParams.put(param, variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addBinaryVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.Binary, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addBinaryArrayInput(final String param, final byte[][] value) throws ContentException
	{
		//store this object in the local content service
		final String variable = playbookConfig.createVariableForInputParam(param, StandardType.BinaryArray);
		contentService.writeBinaryArray(variable, value);
		inputParams.put(param, variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addBinaryArrayVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.BinaryArray, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addKeyValueInput(final String param, final StringKeyValue value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = playbookConfig.createVariableForInputParam(param, StandardType.KeyValue);
		contentService.writeKeyValue(variable, value);
		inputParams.put(param, variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addKeyValueVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.KeyValue, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addKeyValueArrayInput(final String param, final List<StringKeyValue> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = playbookConfig.createVariableForInputParam(param, StandardType.KeyValueArray);
		contentService.writeKeyValueArray(variable, value);
		inputParams.put(param, variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addKeyValueArrayVariable(final String param, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.KeyValueArray, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addTCEntityInput(final String param, final TCEntity value) throws ContentException
	{
		//store this object in the local content service
		final String variable = playbookConfig.createVariableForInputParam(param, StandardType.TCEntity);
		contentService.writeTCEntity(variable, value);
		inputParams.put(param, variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addTCEntityVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.TCEntity, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public PlaybooksOrchestration addTCEntityListInput(final String param, final List<TCEntity> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = playbookConfig.createVariableForInputParam(param, StandardType.TCEntityArray);
		contentService.writeTCEntityList(variable, value);
		inputParams.put(param, variable);
		
		return this;
	}
	
	public PlaybooksOrchestration addTCEntityArrayVariable(final String param, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(StandardType.TCEntityArray, PlaybooksVariableUtil.extractVariableType(value));
			inputParams.put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
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
