package com.threatconnect.sdk.playbooks.app;

import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.playbooks.content.ContentService;
import com.threatconnect.sdk.playbooks.content.accumulator.ContentException;
import com.threatconnect.sdk.playbooks.content.entity.StringKeyValue;
import com.threatconnect.sdk.playbooks.content.entity.TCEntity;
import com.threatconnect.sdk.playbooks.db.DBService;
import com.threatconnect.sdk.playbooks.db.DBServiceFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public abstract class PlaybooksApp extends App
{
	private static final String OUTPUT_PARAMS_DELIM = ",";
	
	//holds the content service object for reading and writing content
	private final ContentService contentService;
	
	//holds the list of output parameters for this app
	private List<String> outputParams;
	
	public PlaybooksApp()
	{
		this(DBServiceFactory.buildFromAppConfig());
	}
	
	public PlaybooksApp(final DBService dbService)
	{
		this.contentService = new ContentService(dbService);
	}
	
	@Override
	public ExitStatus execute(AppConfig appConfig) throws Exception
	{
		return execute(PlaybooksAppConfig.getInstance());
	}
	
	public ContentService getContentService()
	{
		return contentService;
	}
	
	/**
	 * Returns the list of output parameters that this app is expected to write. This will be a subset of any number
	 * of of the output parameters that are originally defined by the app's install file.
	 *
	 * @return the list of output params that this app is expected to write
	 */
	protected final List<String> getOutputParams()
	{
		//check to see if the list of output params is null
		if (null == outputParams)
		{
			//acquire a thread lock on this object to prevent this list from being built twice
			synchronized (this)
			{
				//now that a lock is in place, check again for null
				if (null == outputParams)
				{
					//read the output params that were passed to this app
					String outputVars = PlaybooksAppConfig.getInstance().getOutputVars();
					
					//make sure the output params is not null
					if (null != outputVars && !outputVars.isEmpty())
					{
						//split the output params by the delimiter and all all of them to the result
						String[] outputParamsArray = outputVars.split(Pattern.quote(OUTPUT_PARAMS_DELIM));
						outputParams = Arrays.asList(outputParamsArray);
					}
					else
					{
						outputParams = new ArrayList<String>();
					}
				}
			}
		}
		
		return outputParams;
	}
	
	/**
	 * Checks to see if a specific output param is expected to be written by this app
	 *
	 * @param outputParam the output param to check
	 * @return whether or not this app is expected to write this output parameter
	 */
	protected final boolean isOutputParamExpected(final String outputParam)
	{
		return getOutputParams().contains(outputParam);
	}
	
	/**
	 * Serves as a shorthand method for reading a string value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeStringContent(final String param, final String value) throws ContentException
	{
		getContentService().writeString(getAppConfig().getString(param), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a string list value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeStringListContent(final String param, final List<String> values) throws ContentException
	{
		getContentService().writeStringList(getAppConfig().getString(param), values);
	}
	
	/**
	 * Serves as a shorthand method for reading a binary value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeBinaryContent(final String param, final byte[] value) throws ContentException
	{
		getContentService().writeBinary(getAppConfig().getString(param), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a binary array value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeBinaryArrayContent(final String param, final byte[][] value) throws ContentException
	{
		getContentService().writeBinaryArray(getAppConfig().getString(param), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a key/value from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeKeyValueContent(final String param, final StringKeyValue value) throws ContentException
	{
		getContentService().writeKeyValue(getAppConfig().getString(param), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a key/value list from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeKeyValueArrayContent(final String param, final List<StringKeyValue> value)
		throws ContentException
	{
		getContentService().writeKeyValueArray(getAppConfig().getString(param), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a TCEntity from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeTCEntityContent(final String param, final TCEntity value) throws ContentException
	{
		getContentService().writeTCEntity(getAppConfig().getString(param), value);
	}
	
	/**
	 * Serves as a shorthand method for reading a TCEntity from the database where the param is a database key
	 *
	 * @param param the app parameter which represents a playbooks variable
	 * @return
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
	 * @throws ContentException
	 */
	public final void writeTCEntityListContent(final String param, final List<TCEntity> value) throws ContentException
	{
		getContentService().writeTCEntityList(getAppConfig().getString(param), value);
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
