package com.threatconnect.apps.playbooks.test.orc;

import java.util.Map;

/**
 * @author Greg Marut
 */
public class WithAppParam extends AbstractThen<PlaybooksOrchestration>
{
	WithAppParam(final PlaybooksOrchestration playbooksOrchestration)
	{
		super(playbooksOrchestration);
	}
	
	public WithAppParam set(final String name, final String value)
	{
		getAppParams().put(name, value);
		
		return this;
	}
	
	private Map<String, String> getAppParams()
	{
		return getThen().getAppParams();
	}
}
