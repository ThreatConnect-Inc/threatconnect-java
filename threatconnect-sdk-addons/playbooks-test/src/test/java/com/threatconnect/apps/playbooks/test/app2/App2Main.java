package com.threatconnect.apps.playbooks.test.app2;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.AppMain;

/**
 * @author Greg Marut
 */
public class App2Main extends AppMain
{
	@Override
	public Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		return App2.class;
	}
	
	public static void main(String[] args)
	{
		new App2Main().execute();
	}
}
