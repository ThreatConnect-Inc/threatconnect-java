package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.ProgramLanguageType;

import java.lang.reflect.Type;

/**
 * @author Greg Marut
 */
public class ProgramLanguageTypeJsonSerializer extends EnumJsonSerializer<ProgramLanguageType>
{
	public ProgramLanguageTypeJsonSerializer()
	{
		super(ProgramLanguageType.class);
	}
	
	@Override
	public Type getType()
	{
		return new TypeToken<ProgramLanguageType>(){}.getType();
	}
}