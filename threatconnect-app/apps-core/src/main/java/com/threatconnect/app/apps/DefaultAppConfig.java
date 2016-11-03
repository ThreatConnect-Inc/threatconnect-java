package com.threatconnect.app.apps;

/**
 * An implementation of {@link AppConfig} which does not have any mechanism for loading settings that are not already
 * defined
 *
 * @author Greg Marut
 */
public class DefaultAppConfig extends AppConfig
{
	@Override
	protected String loadSetting(final String key)
	{
		return null;
	}
}
