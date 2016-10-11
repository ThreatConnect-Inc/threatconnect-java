package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import com.threatconnect.sdk.playbooks.content.ContentService;
import com.threatconnect.sdk.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.sdk.playbooks.content.accumulator.ContentException;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.content.entity.TCEntity;
import com.threatconnect.sdk.playbooks.util.PlaybooksVariableUtil;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class WithInput
{
	private final PlaybooksOrchestration playbooksOrchestration;
	
	WithInput(final PlaybooksOrchestration playbooksOrchestration)
	{
		this.playbooksOrchestration = playbooksOrchestration;
	}
	
	public WithInput asString(final String param, final String value) throws ContentException
	{
		if (!PlaybooksVariableUtil.isVariable(value))
		{
			//store this object in the local content service
			final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.String);
			getContentService().writeString(variable, value);
			getInputParams().put(param, variable);
		}
		else
		{
			asStringVariable(param, value);
		}
		
		return this;
	}
	
	public WithInput asStringVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.String, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asStringList(final String param, final List<String> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.StringArray);
		getContentService().writeStringList(variable, value);
		getInputParams().put(param, variable);
		
		return this;
	}
	
	public WithInput asStringListVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.StringArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asBinary(final String param, final byte[] value) throws ContentException
	{
		//store this object in the local content service
		final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.Binary);
		getContentService().writeBinary(variable, value);
		getInputParams().put(param, variable);
		
		return this;
	}
	
	public WithInput asBinaryVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.Binary, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asBinaryArray(final String param, final byte[][] value) throws ContentException
	{
		//store this object in the local content service
		final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.BinaryArray);
		getContentService().writeBinaryArray(variable, value);
		getInputParams().put(param, variable);
		
		return this;
	}
	
	public WithInput asBinaryArrayVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.BinaryArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asKeyValue(final String param, final StringKeyValue value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.KeyValue);
		getContentService().writeKeyValue(variable, value);
		getInputParams().put(param, variable);
		
		return this;
	}
	
	public WithInput asKeyValueVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.KeyValue, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asKeyValueArray(final String param, final List<StringKeyValue> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.KeyValueArray);
		getContentService().writeKeyValueArray(variable, value);
		getInputParams().put(param, variable);
		
		return this;
	}
	
	public WithInput asKeyValueArrayVariable(final String param, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.KeyValueArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asTCEntity(final String param, final TCEntity value) throws ContentException
	{
		//store this object in the local content service
		final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.TCEntity);
		getContentService().writeTCEntity(variable, value);
		getInputParams().put(param, variable);
		
		return this;
	}
	
	public WithInput asTCEntityVariable(final String param, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.TCEntity, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asTCEntityList(final String param, final List<TCEntity> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable = getPlaybookConfig().createVariableForInputParam(param, PlaybookVariableType.TCEntityArray);
		getContentService().writeTCEntityList(variable, value);
		getInputParams().put(param, variable);
		
		return this;
	}
	
	public WithInput asTCEntityArrayVariable(final String param, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.TCEntityArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(param, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput fromLastRunUpstreamApp(final String param, final Class<? extends PlaybooksApp> playbookAppClass,
		final String output, final PlaybookVariableType outputType)
	{
		//find the last run upstream app with this playbooks class
		PlaybooksOrchestration upstream = playbooksOrchestration.findLastRunUpsteamApp(playbookAppClass);
		
		//make sure the upstream app was found
		if (null == upstream)
		{
			throw new IllegalArgumentException("Upstream app \"" + playbookAppClass.getName()
				+ "\" was not found. Please make sure that it was previously registered to run.");
		}
		
		//retrieve the output from the upstream app
		final String outputVariable = upstream.getVariableForOutputVariable(output, outputType);
		
		//add this to the params map
		getInputParams().put(param, outputVariable);
		
		return this;
	}
	
	public PlaybooksOrchestration then()
	{
		return playbooksOrchestration;
	}
	
	private PlaybookConfig getPlaybookConfig()
	{
		return playbooksOrchestration.getPlaybookConfig();
	}
	
	private Map<String, String> getInputParams()
	{
		return playbooksOrchestration.getInputParams();
	}
	
	private ContentService getContentService()
	{
		return playbooksOrchestration.getContentService();
	}
}
