package com.threatconnect.sdk.addons.util.config.install;

import java.io.File;

public class InvalidInstallJsonFileException extends Exception
{
	private static final long serialVersionUID = 4151360644033827467L;
	
	public InvalidInstallJsonFileException(final File file, final Exception cause)
	{
		super(file.getName() + " could not be parsed.", cause);
	}
}
