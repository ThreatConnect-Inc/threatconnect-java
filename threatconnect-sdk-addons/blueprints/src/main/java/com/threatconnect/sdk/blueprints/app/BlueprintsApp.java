package com.threatconnect.sdk.blueprints.app;

import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.blueprints.content.ContentService;
import com.threatconnect.sdk.blueprints.db.DBService;
import com.threatconnect.sdk.blueprints.db.DBServiceFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public abstract class BlueprintsApp extends App
{
	private static final String OUTPUT_PARAMS_DELIM = ",";

	//holds the content service object for reading and writing content
	private final ContentService contentService;

	//holds the list of output parameters for this app
	private List<String> outputParams;

	public BlueprintsApp()
	{
		this(DBServiceFactory.buildFromAppConfig());
	}

	public BlueprintsApp(final DBService dbService)
	{
		this.contentService = new ContentService(dbService);
	}

	@Override
	public ExitStatus execute(AppConfig appConfig) throws Exception
	{
		return execute(BlueprintsAppConfig.getInstance());
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
	protected List<String> getOutputParams()
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
					String outputVars = BlueprintsAppConfig.getInstance().getOutputVars();

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
	protected boolean isOutputParamExpected(final String outputParam)
	{
		return getOutputParams().contains(outputParam);
	}

	/**
	 * Executes the blueprint app
	 *
	 * @param blueprintsAppConfig
	 * @return
	 * @throws Exception
	 */
	protected abstract ExitStatus execute(BlueprintsAppConfig blueprintsAppConfig) throws Exception;
}
