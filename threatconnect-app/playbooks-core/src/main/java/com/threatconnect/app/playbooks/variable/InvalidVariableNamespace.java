package com.threatconnect.app.playbooks.variable;

/**
 * @author Greg Marut
 */
public class InvalidVariableNamespace extends RuntimeException
{
	public InvalidVariableNamespace(final String namespace)
	{
		super(namespace + " is not valid.");
	}
}
