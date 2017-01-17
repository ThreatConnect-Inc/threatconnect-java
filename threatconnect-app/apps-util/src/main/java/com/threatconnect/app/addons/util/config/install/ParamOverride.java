package com.threatconnect.app.addons.util.config.install;

/**
 * Used to represent a parameter that is overriden with a different value
 *
 * @author Greg Marut
 */
public class ParamOverride
{
	private String param;
	private String value;
	
	public String getParam()
	{
		return param;
	}
	
	public void setParam(final String param)
	{
		this.param = param;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(final String value)
	{
		this.value = value;
	}
}
