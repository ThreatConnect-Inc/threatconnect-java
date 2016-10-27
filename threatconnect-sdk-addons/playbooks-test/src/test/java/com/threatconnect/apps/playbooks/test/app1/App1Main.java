package com.threatconnect.apps.playbooks.test.app1;

import com.threatconnect.app.apps.App;
import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.AppMain;

/**
 * @author Greg Marut
 */
public class App1Main extends AppMain
{
	@Override
	public Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException
	{
		return App1.class;
	}
	
	public static void main(String[] args)
	{
		new App1Main().execute();
	}
}
