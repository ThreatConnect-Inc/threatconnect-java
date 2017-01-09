package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;

import java.lang.reflect.Type;

/**
 * @author Greg Marut
 */
public class PlaybookVariableTypeJsonSerializer extends EnumJsonSerializer<PlaybookVariableType>
{
	public PlaybookVariableTypeJsonSerializer()
	{
		super(PlaybookVariableType.class);
	}
	
	@Override
	public Type getType()
	{
		return new TypeToken<PlaybookVariableType>(){}.getType();
	}
}