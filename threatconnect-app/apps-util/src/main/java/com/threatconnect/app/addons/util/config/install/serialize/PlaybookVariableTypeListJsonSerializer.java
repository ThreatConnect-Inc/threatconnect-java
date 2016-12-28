package com.threatconnect.app.addons.util.config.install.serialize;

import com.google.gson.reflect.TypeToken;
import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;

import java.lang.reflect.Type;
import java.util.List;

/**
 * @author Greg Marut
 */
public class PlaybookVariableTypeListJsonSerializer extends EnumListJsonSerializer<PlaybookVariableType>
{
	public PlaybookVariableTypeListJsonSerializer()
	{
		super(PlaybookVariableType.class);
	}
	
	@Override
	public Type getType()
	{
		return new TypeToken<List<PlaybookVariableType>>(){}.getType();
	}
}