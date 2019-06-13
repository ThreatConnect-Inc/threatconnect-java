package com.threatconnect.app.apps;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.threatconnect.app.apps.security.AES;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public class ParamFileAppConfig extends AppConfig
{
	public static final String ENV_APP_PARAM_FILE = "TC_APP_PARAM_FILE";
	public static final String ENV_APP_PARAM_KEY = "TC_APP_PARAM_KEY";
	
	private final JsonObject jsonObject;
	
	public ParamFileAppConfig(final JsonObject jsonObject)
	{
		this.jsonObject = jsonObject;
	}
	
	@Override
	protected String loadSetting(final String key)
	{
		JsonElement element = jsonObject.get(key);
		return (null != element ? element.getAsString() : null);
	}
	
	/**
	 * Attempts to initialize the app config from the file based parameters. If this type of app configuration is not supported for this job
	 * execution, a {@link UnsupportedAppConfigException} is thrown.
	 *
	 * @return
	 * @throws IOException
	 * @throws UnsupportedAppConfigException
	 * @throws GeneralSecurityException
	 */
	public static ParamFileAppConfig attemptInitialization() throws IOException, UnsupportedAppConfigException, GeneralSecurityException
	{
		final String appParamFile = System.getenv(ENV_APP_PARAM_FILE);
		final String appParamKey = System.getenv(ENV_APP_PARAM_KEY);
		
		System.out.println("File: " + appParamFile);
		System.out.println("Key: " + appParamFile);
		
		//check to see if both values are set
		if (null != appParamFile && null != appParamKey)
		{
			//retrieve the app param file
			File file = new File(appParamFile);
			
			//make sure the file exists
			try (InputStream inputStream = new FileInputStream(file))
			{
				//read the encrypted contents of the file
				byte[] encrypted = IOUtils.toByteArray(inputStream);
				
				//decrypt the file using the secret key
				String decrypted = AES.decrypt(encrypted, appParamKey);
				
				return new ParamFileAppConfig(new JsonParser().parse(decrypted).getAsJsonObject());
			}
		}
		else
		{
			throw new UnsupportedAppConfigException();
		}
	}
}
