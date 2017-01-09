package com.threatconnect.app.addons.util.config;

import java.io.File;

public class InvalidJsonFileException extends Exception
{
	private static final long serialVersionUID = 4151360644033827467L;
	
	public InvalidJsonFileException(final String message)
	{
		super(message);
	}
	
	public InvalidJsonFileException(final File file, final Exception cause)
	{
		super(file.getName() + " could not be parsed.", cause);
	}
}
