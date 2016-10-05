package com.threatconnect.plugin.pkg.config.install;

import com.google.gson.JsonObject;
import com.threatconnect.sdk.addons.util.JsonUtil;

/**
 * @author Greg Marut
 */
public class PlaybookOutputVariable
{
	private final JsonObject root;
	
	private static final String NAME = "name";
	private static final String TYPE = "type";
	
	public PlaybookOutputVariable(final JsonObject root)
	{
		this.root = root;
	}
	
	public String getName()
	{
		return getAsString(NAME);
	}
	
	public String getType()
	{
		return getAsString(TYPE);
	}
	
	private String getAsString(final String... paths)
	{
		return JsonUtil.getAsString(root, paths);
	}
}
