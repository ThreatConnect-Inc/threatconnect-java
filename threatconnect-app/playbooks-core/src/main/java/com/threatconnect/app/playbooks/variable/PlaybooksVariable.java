package com.threatconnect.app.playbooks.variable;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

import java.util.regex.Pattern;

/**
 * Represents a playbooks variable
 *
 * @author Greg Marut
 */
public class PlaybooksVariable
{
	private final PlaybooksVariableNamespace namespace;
	private final long id;
	private final String name;
	private final String playbookVariableType;
	
	public PlaybooksVariable(final PlaybooksVariableNamespace namespace, final long id, final String name,
		final StandardPlaybookType playbookVariableType)
	{
		this(namespace, id, name, playbookVariableType.toString());
	}
	
	public PlaybooksVariable(final PlaybooksVariableNamespace namespace, final long id, final String name,
		final String playbookVariableType)
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
	
	public long getId()
	{
		return id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public String getPlaybookVariableType()
	{
		return playbookVariableType;
	}
	
	/**
	 * Returns the regex value to use for replacement
	 *
	 * @return
	 */
	public String toRegexReplaceString()
	{
		return Pattern.quote(toString());
	}
	
	@Override
	public String toString()
	{
		return "#" + namespace.toString() + ":" + id + ":" + name + "!" + playbookVariableType;
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
