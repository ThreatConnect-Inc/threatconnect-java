package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.RunLevelType;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Greg Marut
 */
public class RuntimeLevelJsonSerializer extends EnumListJsonSerializer<RunLevelType>
{
	public RuntimeLevelJsonSerializer()
	{
		super(RunLevelType.class);
	}
	
	@Override
	public Type getType()
	{
		return new TypeToken<List<RunLevelType>>(){}.getType();
	}
}