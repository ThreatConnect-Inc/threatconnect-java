package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.ParamDataType;

import java.lang.reflect.Type;

/**
 * @author Greg Marut
 */
public class ParamDataTypeJsonSerializer extends EnumJsonSerializer<ParamDataType>
{
	public ParamDataTypeJsonSerializer()
	{
		super(ParamDataType.class);
	}
	
	@Override
	public Type getType()
	{
		return new TypeToken<ParamDataType>(){}.getType();
	}
}