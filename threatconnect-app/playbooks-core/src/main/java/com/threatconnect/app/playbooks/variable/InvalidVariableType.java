package com.threatconnect.app.playbooks.variable;

/**
 * @author Greg Marut
 */
public class InvalidVariableType extends RuntimeException
{
	public InvalidVariableType(final String type)
	{
		super(type + " is not valid.");
	}
}
