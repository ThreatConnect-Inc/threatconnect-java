package com.threatconnect.sdk.app;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.AbstractRequestExecutor;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.HttpRequestExecutor;
import com.threatconnect.sdk.conn.HttpResponse;
import com.threatconnect.sdk.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Greg Marut
 */
public class SecureParamAppConfig extends SdkAppConfig
{
	private static final Logger logger = LoggerFactory.getLogger(SecureParamAppConfig.class);
	
	private static final String API_PARAMETERS_ENDPOINT = "internal.jobexecution.params";
	private static final String INPUTS = "inputs";
	
	private static SecureParamAppConfig instance;
	
	protected SecureParamAppConfig()
	{
		try
		{
			retrieveParams();
		}
		catch (IOException e)
		{
			logger.warn(e.getMessage(), e);
		}
	}
	
	private void retrieveParams() throws IOException
	{
		//create the configuration for the threatconnect server
		Configuration configuration = new Configuration(getTcApiPath(), getTcApiAccessID(),
			getTcApiUserSecretKey(), getApiDefaultOrg(), getTcToken(), getTcTokenExpires());
		
		//create a new connection object
		Connection connection = new Connection(configuration);
		
		//create a new http request executor that will be able to communicate with the server
		HttpRequestExecutor httpRequestExecutor = new HttpRequestExecutor(connection);
		
		//retrieve the params from the server
		final HttpResponse response =
			httpRequestExecutor.execute(AbstractRequestExecutor.HttpMethod.GET, API_PARAMETERS_ENDPOINT);
		
		//check to see if the response contains an entity
		if (null != response.getEntity())
		{
			//parse the json response
			JsonParser jsonParser = new JsonParser();
			JsonElement root = jsonParser.parse(response.getEntityAsString());
			
			//retrieve the inputs object and make sure it is not null
			JsonObject inputsObject = JsonUtil.getAsJsonObject(root, INPUTS);
			if (null != inputsObject)
			{
				inputsObject.entrySet().forEach(e ->
				{
					//make sure the value is not null
					if (null != e.getValue() && !e.getValue().isJsonNull())
					{
						//parse the value
						final String value =
							(e.getValue().isJsonPrimitive() ? e.getValue().getAsString() : e.getValue().toString());
						super.configurationMap.put(e.getKey(), value);
					}
					else
					{
						//the value is null
						super.configurationMap.put(e.getKey(), null);
					}
				});
			}
		}
		else
		{
			logger.warn("SecureParams returned no response.");
		}
	}
	
	/**
	 * Retrieves the instance of this singleton
	 *
	 * @return Instance of singleton
	 */
	public static synchronized SecureParamAppConfig getInstance()
	{
		// check to see if the instance is null
		if (null == instance)
		{
			instance = new SecureParamAppConfig();
		}
		
		return instance;
	}
}
