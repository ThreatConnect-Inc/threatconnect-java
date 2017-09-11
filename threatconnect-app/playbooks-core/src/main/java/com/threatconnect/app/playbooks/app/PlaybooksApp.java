package com.threatconnect.app.playbooks.app;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.entity.KeyValue;
import com.threatconnect.app.playbooks.content.entity.TCEntity;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.DBServiceFactory;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;
import com.threatconnect.sdk.model.Item;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public abstract class PlaybooksApp extends App
{
	private static final String OUTPUT_PARAMS_DELIM = ",";
	
	//holds the content service object for reading and writing content
	private ContentService contentService;
	
	//holds the list of output variables for this app
	private Set<String> outputVariables;
	
	// holds the reference to the playbook app config object
	private PlaybooksAppConfig playbooksAppConfig;
	
	@Override
	public void init(final AppConfig appConfig)
	{
		init(appConfig, DBServiceFactory.buildFromAppConfig(appConfig));
	}
	
	public void init(final AppConfig appConfig, final DBService customDBService)
	{
		super.init(appConfig);
		this.playbooksAppConfig = new PlaybooksAppConfig(appConfig);
		this.contentService = new ContentService(customDBService);
	}
	
	@Override
	public final ExitStatus execute(AppConfig appConfig) throws Exception
	{
		return execute(new PlaybooksAppConfig(appConfig));
	}
	
	public ContentService getContentService()
	{
		return contentService;
	}
	
	public PlaybooksAppConfig getPlaybooksAppConfig()
	{
		return playbooksAppConfig;
	}
	
	/**
	 * Returns the list of output parameters that this app is expected to write. This will be a subset of any number
	 * of of the output parameters that are originally defined by the app's install file.
	 *
	 * @return the list of output params that this app is expected to write
	 */
	protected final Set<String> getOutputVariables()
	{
		//check to see if the list of output params is null
		if (null == outputVariables)
		{
			//acquire a thread lock on this object to prevent this list from being built twice
			synchronized (this)
			{
				//now that a lock is in place, check again for null
				if (null == outputVariables)
				{
					//read the output params that were passed to this app
					String outputVars = playbooksAppConfig.getOutputVars();
					
					//make sure the output params is not null
					if (null != outputVars && !outputVars.isEmpty())
					{
						//split the output params by the delimiter and all all of them to the result
						String[] outputParamsArray = outputVars.split(Pattern.quote(OUTPUT_PARAMS_DELIM));
						outputVariables = new HashSet<String>(Arrays.asList(outputParamsArray));
					}
					else
					{
						outputVariables = new HashSet<String>();
					}
				}
			}
		}
		
		return outputVariables;
	}
	
	/**
	 * Given an input app parameter, this will read the underlying playbook key and check to see if the actual type and
	 * the expected type are the same.
	 *
	 * @param inputParam the app param which corresponds to a playbook variable
	 * @param type       the expected variable type to check
	 * @return true if the actual and expected types are the same, false otherwise
	 */
	protected final boolean isInputParamOfPlaybookType(final String inputParam, final StandardPlaybookType type)
	{
		return isInputParamOfPlaybookType(inputParam, type.toString());
	}
	
	/**
	 * Given an input app parameter, this will read the underlying playbook key and check to see if the actual type and
	 * the expected type are the same.
	 *
	 * @param inputParam the app param which corresponds to a playbook variable
	 * @param type       the expected variable type to check
	 * @return true if the actual and expected types are the same, false otherwise
	 */
	protected final boolean isInputParamOfPlaybookType(final String inputParam, final String type)
	{
		//retrieve the DB key for this input param
		final String key = getAppConfig().getString(inputParam);
		
		//make sure this param does not resolve to null
		if (null != key)
		{
			//check to see if this is a valid variable
			if (PlaybooksVariableUtil.isVariable(key))
			{
				//extract the actual type of this playbook variable
				String actualType = PlaybooksVariableUtil.extractVariableType(key);
				return actualType.equals(type);
			}
			else
			{
				//since its not a variable, it can just be a literal string so we should check if the expected type is a string
				return StandardPlaybookType.String.toString().equalsIgnoreCase(type);
			}
		}
		else
		{
			//the param resolves to null
			return false;
		}
	}
	
	/**
	 * Looks up the value of the input param and resolves the type of playbook variable that it represents
	 *
	 * @param inputParam the app param which corresponds to a playbook variable
	 * @return the type that this inputParam represents
	 * @throws IllegalArgumentException if the inputParam resolves to null
	 */
	protected final String getPlaybookTypeOfInputParam(final String inputParam)
	{
		//retrieve the DB key for this input param
		final String key = getAppConfig().getString(inputParam);
		
		//make sure this param does not resolve to null
		if (null != key)
		{
			//check to see if this is a valid variable
			if (PlaybooksVariableUtil.isVariable(key))
			{
				//extract the actual type of this playbook variable
				return PlaybooksVariableUtil.extractVariableType(key);
			}
			else
			{
				//since its not a variable, it can just be a literal string;
				return StandardPlaybookType.String.toString();
			}
		}
		else
		{
			throw new IllegalArgumentException(inputParam + " resolves to a null value");
		}
	}
	
	/**
	 * Checks to see if a specific output param is expected to be written by this app
	 *
	 * @param outputParam the output param to check
	 * @param type        the output type of the output param
	 * @return whether or not this app is expected to write this output parameter
	 */
	protected final boolean isOutputParamExpected(final String outputParam, final StandardPlaybookType type)
	{
		return null != findOutputVariable(outputParam, type.toString());
	}
	
	/**
	 * Checks to see if a specific output param is expected to be written by this app
	 *
	 * @param outputParam the output param to check
	 * @param type        the output type of the output param
	 * @return whether or not this app is expected to write this output parameter
	 */
	protected final boolean isOutputParamExpected(final String outputParam, final String type)
	{
		return null != findOutputVariable(outputParam, type);
	}
	
	/**
	 * Checks the output variables list looking for a specific name/type
	 *
	 * @param outputParam the output param to check
	 * @param type        the output type of the output param
	 * @return the database variable key
	 */
	protected final String findOutputVariable(final String outputParam, final StandardPlaybookType type)
	{
		return findOutputVariable(outputParam, type.toString());
	}
	
	/**
	 * Checks the output variables list looking for a specific name/type
	 *
	 * @param outputParam the output param to check
	 * @param type        the output type of the output param
	 * @return the database variable key
	 */
	protected final String findOutputVariable(final String outputParam, final String type)
	{
		//for each of the output params
		for (String outputVariable : getOutputVariables())
		{
			final String variableName = PlaybooksVariableUtil.extractVariableName(outputVariable);
			final String variableType = PlaybooksVariableUtil.extractVariableType(outputVariable);
			
			//check to see if this output param was found in one of the variables
			if (variableName.equals(outputParam) && variableType.equalsIgnoreCase(type))
			{
				return outputVariable;
			}
		}
		
		//no output variable was found with this name/type
		return null;
	}
	
	/**
	 * Serves as a shorthand method for reading a string value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final String readStringContent(final String param) throws ContentException
	{
		return getContentService().readString(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a string value to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeStringContent(final String param, final String value) throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.String))
		{
			getContentService().writeString(findOutputVariable(param, StandardPlaybookType.String), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a string list value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final List<String> readStringListContent(final String param) throws ContentException
	{
		return getContentService().readStringList(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a list of string values to the database where the param is a database
	 * key
	 *
	 * @param param  the app parameter which represents a playbooks variable
	 * @param values the list of values to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeStringListContent(final String param, final List<String> values) throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.StringArray))
		{
			getContentService().writeStringList(findOutputVariable(param, StandardPlaybookType.StringArray), values);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a binary value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final byte[] readBinaryContent(final String param) throws ContentException
	{
		return getContentService().readBinary(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a binary value to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeBinaryContent(final String param, final byte[] value) throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.Binary))
		{
			getContentService().writeBinary(findOutputVariable(param, StandardPlaybookType.Binary), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a binary array value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final byte[][] readBinaryArrayContent(final String param) throws ContentException
	{
		return getContentService().readBinaryArray(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a binary array value to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeBinaryArrayContent(final String param, final byte[][] value) throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.BinaryArray))
		{
			getContentService().writeBinaryArray(findOutputVariable(param, StandardPlaybookType.BinaryArray), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a key/value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final KeyValue readKeyValueContent(final String param) throws ContentException
	{
		return getContentService().readKeyValue(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a key/value to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeKeyValueContent(final String param, final KeyValue value) throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.KeyValue))
		{
			getContentService().writeKeyValue(findOutputVariable(param, StandardPlaybookType.KeyValue), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a key/value list from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final List<KeyValue> readKeyValueArrayContent(final String param) throws ContentException
	{
		return getContentService().readKeyValueArray(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a key/value list to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeKeyValueArrayContent(final String param, final List<KeyValue> value)
		throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.KeyValueArray))
		{
			getContentService().writeKeyValueArray(
				findOutputVariable(param, StandardPlaybookType.KeyValueArray), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a TCEntity from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final TCEntity readTCEntityContent(final String param) throws ContentException
	{
		return getContentService().readTCEntity(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a TCEntity to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeTCEntityContent(final String param, final TCEntity value) throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.TCEntity))
		{
			getContentService().writeTCEntity(findOutputVariable(param, StandardPlaybookType.TCEntity), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a TCEntity from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final List<TCEntity> readTCEntityListContent(final String param) throws ContentException
	{
		return getContentService().readTCEntityList(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a TCEntity to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeTCEntityListContent(final String param, final List<TCEntity> value)
		throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.TCEntityArray))
		{
			getContentService().writeTCEntityList(findOutputVariable(param, StandardPlaybookType.TCEntityArray), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a TCEnhancedEntity from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final Item readTCEnhancedEntityContent(final String param) throws ContentException
	{
		return getContentService().readTCEnhancedEntity(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a TCEnhancedEntity to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeTCEnhancedEntityContent(final String param, final Item value) throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.TCEnhancedEntity))
		{
			getContentService()
				.writeTCEnhancedEntity(findOutputVariable(param, StandardPlaybookType.TCEnhancedEntity), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a TCEnhancedEntity from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final List<Item> readTCEnhancedEntityListContent(final String param) throws ContentException
	{
		return getContentService().readTCEnhancedEntityList(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a TCEnhancedEntity to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeTCEnhancedEntityListContent(final String param, final List<Item> value)
		throws ContentException
	{
		if (isOutputParamExpected(param, StandardPlaybookType.TCEnhancedEntityArray))
		{
			getContentService()
				.writeTCEnhancedEntityList(findOutputVariable(param, StandardPlaybookType.TCEnhancedEntityArray),
					value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Serves as a shorthand method for reading a custom type value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final byte[] readCustomTypeContent(final String param) throws ContentException
	{
		return getContentService().readCustomType(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a custom type value to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeCustomTypeContent(final String param, final byte[] value, final StandardPlaybookType type)
		throws ContentException
	{
		return writeCustomTypeContent(param, value, type.toString());
	}
	
	/**
	 * Serves as a shorthand method for writing a custom type value to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @return whether or not the value was written
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final boolean writeCustomTypeContent(final String param, final byte[] value, final String type)
		throws ContentException
	{
		if (isOutputParamExpected(param, type))
		{
			getContentService().writeCustomType(findOutputVariable(param, type), value);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * Executes the playbook app
	 *
	 * @param playbooksAppConfig
	 * @return
	 * @throws Exception
	 */
	protected abstract ExitStatus execute(PlaybooksAppConfig playbooksAppConfig) throws Exception;
}
