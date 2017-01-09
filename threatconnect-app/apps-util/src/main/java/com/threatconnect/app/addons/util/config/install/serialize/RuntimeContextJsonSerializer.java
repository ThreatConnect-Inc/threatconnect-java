package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.RuntimeContextType;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Greg Marut
 */
public class RuntimeContextJsonSerializer extends EnumListJsonSerializer<RuntimeContextType>
{
	public RuntimeContextJsonSerializer()
	{
		super(RuntimeContextType.class);
	}
	
	@Override
	public Type getType()
	{
		return new TypeToken<List<RuntimeContextType>>(){}.getType();
	}
}