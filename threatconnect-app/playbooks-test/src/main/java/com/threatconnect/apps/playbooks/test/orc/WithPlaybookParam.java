package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.app.addons.util.config.install.type.PlaybookVariableType;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.entity.StringKeyValue;
import com.threatconnect.app.playbooks.content.entity.TCEntity;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;
import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class WithPlaybookParam extends AbstractThen<PlaybooksOrchestration>
{
	WithPlaybookParam(final PlaybooksOrchestration playbooksOrchestration)
	{
		super(playbooksOrchestration);
	}
	
	public WithPlaybookParam asString(final String playbookParam, final String value) throws ContentException
	{
		if (!PlaybooksVariableUtil.isVariable(value))
		{
			//store this object in the local content service
			final String variable =
				getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.String);
			getContentService().writeString(variable, value);
			getPlaybookParams().put(playbookParam, variable);
		}
		else
		{
			asStringVariable(playbookParam, value);
		}
		
		return this;
	}
	
	public WithPlaybookParam asStringVariable(final String playbookParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.String, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithPlaybookParam asStringList(final String playbookParam, final List<String> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.StringArray);
		getContentService().writeStringList(variable, value);
		getPlaybookParams().put(playbookParam, variable);
		
		return this;
	}
	
	public WithPlaybookParam asStringListVariable(final String playbookParam, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.StringArray, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithPlaybookParam asBinary(final String playbookParam, final byte[] value) throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.Binary);
		getContentService().writeBinary(variable, value);
		getPlaybookParams().put(playbookParam, variable);
		
		return this;
	}
	
	public WithPlaybookParam asBinaryVariable(final String playbookParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.Binary, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithPlaybookParam asBinaryArray(final String playbookParam, final byte[][] value) throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.BinaryArray);
		getContentService().writeBinaryArray(variable, value);
		getPlaybookParams().put(playbookParam, variable);
		
		return this;
	}
	
	public WithPlaybookParam asBinaryArrayVariable(final String playbookParam, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.BinaryArray, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithPlaybookParam asKeyValue(final String playbookParam, final StringKeyValue value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.KeyValue);
		getContentService().writeKeyValue(variable, value);
		getPlaybookParams().put(playbookParam, variable);
		
		return this;
	}
	
	public WithPlaybookParam asKeyValueVariable(final String playbookParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.KeyValue, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithPlaybookParam asKeyValueArray(final String playbookParam, final List<StringKeyValue> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.KeyValueArray);
		getContentService().writeKeyValueArray(variable, value);
		getPlaybookParams().put(playbookParam, variable);
		
		return this;
	}
	
	public WithPlaybookParam asKeyValueArrayVariable(final String playbookParam, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.KeyValueArray, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithPlaybookParam asTCEntity(final String playbookParam, final TCEntity value) throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.TCEntity);
		getContentService().writeTCEntity(variable, value);
		getPlaybookParams().put(playbookParam, variable);
		
		return this;
	}
	
	public WithPlaybookParam asTCEntityVariable(final String playbookParam, final String value) throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.TCEntity, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	public WithPlaybookParam asTCEntityList(final String playbookParam, final List<TCEntity> value)
		throws ContentException
	{
		//store this object in the local content service
		final String variable =
			getPlaybookConfig().createVariableForInputParam(playbookParam, PlaybookVariableType.TCEntityArray);
		getContentService().writeTCEntityList(variable, value);
		getPlaybookParams().put(playbookParam, variable);
		
		return this;
	}
	
	public WithPlaybookParam asTCEntityArrayVariable(final String playbookParam, final String value)
		throws ContentException
	{
		if (PlaybooksVariableUtil.isVariable(value))
		{
			Assert.assertEquals(PlaybookVariableType.TCEntityArray, PlaybooksVariableUtil.extractVariableType(value));
			getPlaybookParams().put(playbookParam, value);
		}
		else
		{
			throw new IllegalArgumentException(value + " is not a valid Playbooks variable");
		}
		
		return this;
	}
	
	/**
	 * Sets the playbook param for this app to be the value from the output of an upstream app
	 *
	 * @param playbookParam            the playbook param name
	 * @param upstreamPlaybookAppClass the class from the upstream app to pull the value from after it executes
	 * @param outputParam              the name of the upstream app's output param
	 * @param outputType               the type of the upstream app's output param
	 * @return
	 */
	public WithPlaybookParam fromLastRunUpstreamApp(final String playbookParam,
		final Class<? extends PlaybooksApp> upstreamPlaybookAppClass,
		final String outputParam, final PlaybookVariableType outputType)
	{
		//find the last run upstream app with this playbooks class
		PlaybooksOrchestration upstream = getThen().findLastRunUpsteamApp(upstreamPlaybookAppClass);
		
		//make sure the upstream app was found
		if (null == upstream)
		{
			throw new IllegalArgumentException("Upstream app \"" + upstreamPlaybookAppClass.getName()
				+ "\" was not found. Please make sure that it was previously registered to run.");
		}
		
		//retrieve the output from the upstream app
		final String outputVariable = upstream.getVariableForOutputVariable(outputParam, outputType);
		
		//add this to the playbook params map
		getPlaybookParams().put(playbookParam, outputVariable);
		
		return this;
	}
	
	private PlaybookConfig getPlaybookConfig()
	{
		return getThen().getPlaybookConfig();
	}
	
	private Map<String, String> getPlaybookParams()
	{
		return getThen().getPlaybookParams();
	}
	
	private ContentService getContentService()
	{
		return getThen().getContentService();
	}
}
