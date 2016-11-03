package com.threatconnect.app.apps;

/**
 * @author Greg Marut
 */
public interface AppExecutor
{
	Class<? extends App> getAppClassToExecute(final AppConfig appConfig) throws ClassNotFoundException;
}
