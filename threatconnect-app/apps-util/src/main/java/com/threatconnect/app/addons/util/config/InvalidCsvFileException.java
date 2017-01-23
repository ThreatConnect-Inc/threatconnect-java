package com.threatconnect.app.addons.util.config;

import java.io.File;

/**
 * @author Greg Marut
 */
public class InvalidCsvFileException extends Exception
{
	public InvalidCsvFileException(final String message)
	{
		super(message);
	}
	
	public InvalidCsvFileException(final File file, final Exception cause)
	{
		super(file.getName() + " could not be parsed.", cause);
	}
}
