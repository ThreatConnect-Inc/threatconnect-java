package com.threatconnect.app.addons.util.config.install;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Greg Marut
 */
public class InstallUtil
{
	public static Install load(final File file) throws IOException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream);
		}
	}
	
	public static Install load(final InputStream inputStream)
	{
		return new Gson().fromJson(new InputStreamReader(inputStream), Install.class);
	}
}
