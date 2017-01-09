package com.threatconnect.app.playbooks.variable;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;

/**
 * Represents
 *
 * @author Greg Marut
 */
public class PlaybooksVariable
{
	private final PlaybooksVariableNamespace namespace;
	private final int id;
	private final String name;
	private final PlaybookVariableType playbookVariableType;
	
	public PlaybooksVariable(final PlaybooksVariableNamespace namespace, final int id, final String name,
		final PlaybookVariableType playbookVariableType)
	{
		this.namespace = namespace;
		this.id = id;
		this.name = name;
		this.playbookVariableType = playbookVariableType;
	}
	
	public PlaybooksVariableNamespace getNamespace()
	{
		return namespace;
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public PlaybookVariableType getPlaybookVariableType()
	{
		return playbookVariableType;
	}
	
	@Override
	public String toString()
	{
		return "#" + namespace.toString() + ":" + id + ":" + name + "!" + playbookVariableType.toString();
	}
	
	@Override
	public boolean equals(final Object obj)
	{
		if (obj instanceof PlaybooksVariable)
		{
			return toString().equals(obj.toString());
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public int hashCode()
	{
		return toString().hashCode();
	}
}
