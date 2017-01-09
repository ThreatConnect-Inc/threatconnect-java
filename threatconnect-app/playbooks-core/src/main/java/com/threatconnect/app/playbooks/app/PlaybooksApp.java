package com.threatconnect.app.playbooks.app;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.ExitStatus;
import com.threatconnect.app.playbooks.content.ContentService;
import com.threatconnect.app.playbooks.content.accumulator.ContentException;
import com.threatconnect.app.playbooks.content.entity.StringKeyValue;
import com.threatconnect.app.playbooks.content.entity.TCEntity;
import com.threatconnect.app.playbooks.db.DBServiceFactory;
import com.threatconnect.app.playbooks.util.PlaybooksVariableUtil;

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
		super.init(appConfig);
		this.playbooksAppConfig = new PlaybooksAppConfig(appConfig);
		this.contentService = new ContentService(DBServiceFactory.buildFromAppConfig(appConfig));
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
	protected final boolean isInputParamOfPlaybookType(final String inputParam, final PlaybookVariableType type)
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
				PlaybookVariableType actualType = PlaybooksVariableUtil.extractVariableType(key);
				return actualType.equals(type);
			}
			else
			{
				//since its not a variable, it can just be a literal string so we should check if the expected type is a string
				return PlaybookVariableType.String.equals(type);
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
	 * @return the {@link PlaybookVariableType} that this inputParam represents
	 * @throws IllegalArgumentException if the inputParam resolves to null
	 */
	protected final PlaybookVariableType getPlaybookTypeOfInputParam(final String inputParam)
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
				return PlaybookVariableType.String;
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
	protected final boolean isOutputParamExpected(final String outputParam, final PlaybookVariableType type)
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
	protected final String findOutputVariable(final String outputParam, final PlaybookVariableType type)
	{
		//for each of the output params
		for (String outputVariable : getOutputVariables())
		{
			final String variableName = PlaybooksVariableUtil.extractVariableName(outputVariable);
			final PlaybookVariableType variableType = PlaybooksVariableUtil.extractVariableType(outputVariable);
			
			//check to see if this output param was found in one of the variables
			if (variableName.equals(outputParam) && variableType.equals(type))
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
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeStringContent(final String param, final String value) throws ContentException
	{
		getContentService().writeString(findOutputVariable(param, PlaybookVariableType.String), value);
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
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeStringListContent(final String param, final List<String> values) throws ContentException
	{
		getContentService().writeStringList(findOutputVariable(param, PlaybookVariableType.StringArray), values);
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
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeBinaryContent(final String param, final byte[] value) throws ContentException
	{
		getContentService().writeBinary(findOutputVariable(param, PlaybookVariableType.Binary), value);
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
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeBinaryArrayContent(final String param, final byte[][] value) throws ContentException
	{
		getContentService().writeBinaryArray(findOutputVariable(param, PlaybookVariableType.BinaryArray), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a key/value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final StringKeyValue readKeyValueContent(final String param) throws ContentException
	{
		return getContentService().readKeyValue(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a key/value to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeKeyValueContent(final String param, final StringKeyValue value) throws ContentException
	{
		getContentService().writeKeyValue(findOutputVariable(param, PlaybookVariableType.KeyValue), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a key/value list from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return the value of the parameter in the database
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final List<StringKeyValue> readKeyValueArrayContent(final String param) throws ContentException
	{
		return getContentService().readKeyValueArray(getAppConfig().getString(param));
	}
	
	/**
	 * Serves as a shorthand method for writing a key/value list to the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @param value the value to write to the variable
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeKeyValueArrayContent(final String param, final List<StringKeyValue> value)
		throws ContentException
	{
		getContentService().writeKeyValueArray(findOutputVariable(param, PlaybookVariableType.KeyValueArray), value);
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
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeTCEntityContent(final String param, final TCEntity value) throws ContentException
	{
		getContentService().writeTCEntity(findOutputVariable(param, PlaybookVariableType.TCEntity), value);
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
	 * @throws ContentException if there was an issue reading/writing to the database.
	 */
	public final void writeTCEntityListContent(final String param, final List<TCEntity> value) throws ContentException
	{
		getContentService().writeTCEntityList(findOutputVariable(param, PlaybookVariableType.TCEntityArray), value);
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
