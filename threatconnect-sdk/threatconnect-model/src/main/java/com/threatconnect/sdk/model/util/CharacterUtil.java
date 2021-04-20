package com.threatconnect.sdk.model.util;

public class CharacterUtil
{
	public static String replaceSpecialCharacters(final String string)
	{
		return string
			//replace left single quotes
			.replace((char) 145, '\'')
			.replace((char) 8216, '\'')
			//replace right single quotes
			.replace((char) 146, '\'')
			.replace((char) 8217, '\'')
			//replace left double quotes
			.replace((char) 8220, '\"')
			.replace((char) 147, '\"')
			//replace right double quotes
			.replace((char) 8221, '\"')
			.replace((char) 148, '\"')
			//replace em dash
			.replace((char) 8211, '-')
			.replace((char) 150, '-');
	}
}
