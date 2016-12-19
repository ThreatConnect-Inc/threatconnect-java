package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.app.apps.AppConfig;

/**
 * @author Greg Marut
 */
public abstract class PlaybooksAction
{
	private final String name;
	
	public PlaybooksAction(final String name)
	{
		this.name = name;
	}
	
	public abstract void run(final AppConfig appConfig);
	
	public String getName()
	{
		return name;
	}
}
