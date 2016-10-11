package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.sdk.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import com.threatconnect.sdk.playbooks.content.ContentService;
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
	
	public WithInput asString(final String inputParam, final String value) throws ContentException
	{
		if (!PlaybooksVariableUtil.isVariable(value))
		{
			//store this object in the local content service
			final String variable =
				getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.String);
			getContentService().writeString(variable, value);
			getInputParams().put(inputParam, variable);
		}
		else
		{
			asStringVariable(inputParam, value);
		}
		
		return this;
	}
	
	public WithInput asStringVariable(final String inputParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.String, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asStringList(final String inputParam, final List<String> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.StringArray);
		getContentService().writeStringList(variable, value);
		getInputParams().put(inputParam, variable);
		
		return this;
	}
	
	public WithInput asStringListVariable(final String inputParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.StringArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asBinary(final String inputParam, final byte[] value) throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.Binary);
		getContentService().writeBinary(variable, value);
		getInputParams().put(inputParam, variable);
		
		return this;
	}
	
	public WithInput asBinaryVariable(final String inputParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.Binary, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asBinaryArray(final String inputParam, final byte[][] value) throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.BinaryArray);
		getContentService().writeBinaryArray(variable, value);
		getInputParams().put(inputParam, variable);
		
		return this;
	}
	
	public WithInput asBinaryArrayVariable(final String inputParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.BinaryArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asKeyValue(final String inputParam, final StringKeyValue value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.KeyValue);
		getContentService().writeKeyValue(variable, value);
		getInputParams().put(inputParam, variable);
		
		return this;
	}
	
	public WithInput asKeyValueVariable(final String inputParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.KeyValue, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asKeyValueArray(final String inputParam, final List<StringKeyValue> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.KeyValueArray);
		getContentService().writeKeyValueArray(variable, value);
		getInputParams().put(inputParam, variable);
		
		return this;
	}
	
	public WithInput asKeyValueArrayVariable(final String inputParam, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.KeyValueArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asTCEntity(final String inputParam, final TCEntity value) throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.TCEntity);
		getContentService().writeTCEntity(variable, value);
		getInputParams().put(inputParam, variable);
		
		return this;
	}
	
	public WithInput asTCEntityVariable(final String inputParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.TCEntity, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithInput asTCEntityList(final String inputParam, final List<TCEntity> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(inputParam, PlaybookVariableType.TCEntityArray);
		getContentService().writeTCEntityList(variable, value);
		getInputParams().put(inputParam, variable);
		
		return this;
	}
	
	public WithInput asTCEntityArrayVariable(final String inputParam, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.TCEntityArray, PlaybooksVariableUtil.extractVariableType(value));
			getInputParams().put(inputParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	/**
	 * Sets the input param for this app to be the value from the output of an upstream app
	 *
	 * @param inputParam               the input param name
	 * @param upstreamPlaybookAppClass the class from the upstream app to pull the value from after it executes
	 * @param outputParam              the name of the upstream app's output param
	 * @param outputType               the type of the upstream app's output param
	 * @return
	 */
	public WithInput fromLastRunUpstreamApp(final String inputParam,
		final Class<? extends PlaybooksApp> upstreamPlaybookAppClass,
		final String outputParam, final PlaybookVariableType outputType)
	{
		//find the last run upstream app with this playbooks class
		PlaybooksOrchestration upstream = playbooksOrchestration.findLastRunUpsteamApp(upstreamPlaybookAppClass);
		
		//make sure the upstream app was found
		if (null == upstream)
		{
			throw new IllegalArgumentException("Upstream app \"" + upstreamPlaybookAppClass.getName()
				+ "\" was not found. Please make sure that it was previously registered to run.");
		}
		
		//retrieve the output from the upstream app
		final String outputVariable = upstream.getVariableForOutputVariable(outputParam, outputType);
		
		//add this to the inputParams map
		getInputParams().put(inputParam, outputVariable);
		
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
