package com.threatconnect.apps.playbooks.test.app4;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.AppMain;

/**
 * @author Greg Marut
 */
public class App4Main extends AppMain
{
	@Override
	public Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		return App4DynamicConfig.class;
	}
	
	public static void main(String[] args)
	{
		new App4Main().execute();
	}
}
