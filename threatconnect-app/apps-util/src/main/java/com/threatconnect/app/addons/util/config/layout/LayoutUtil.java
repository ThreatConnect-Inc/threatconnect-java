package com.threatconnect.app.addons.util.config.layout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.serialize.InvalidEnumException;
import com.threatconnect.app.addons.util.config.layout.validation.LayoutValidator;
import com.threatconnect.app.addons.util.config.validation.ValidationException;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author Greg Marut
 */
public class LayoutUtil
{
	public static Layout load(final String json, final Install install) throws ValidationException
	{
		try
		{
			Layout Layout = createGson().fromJson(json, Layout.class);
			
			if (null != install)
			{
				new LayoutValidator(install).validate(Layout);
			}
			
			return Layout;
		}
		catch (JsonParseException e)
		{
			throw new ValidationException(e);
		}
	}
	
	public static Layout load(final byte[] bytes, final Install install) throws IOException, ValidationException
	{
		try (InputStream inputStream = new ByteArrayInputStream(bytes))
		{
			return load(inputStream, install);
		}
	}
	
	public static Layout load(final File file, final Install install) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, install);
		}
	}
	
	public static Layout load(final InputStream inputStream, final Install install) throws ValidationException
	{
		try
		{
			Layout Layout = createGson().fromJson(new InputStreamReader(inputStream), Layout.class);
			
			if (null != install)
			{
				new LayoutValidator(install).validate(Layout);
			}
			
			return Layout;
		}
		catch (JsonParseException | InvalidEnumException e)
		{
			throw new ValidationException(e);
		}
	}
	
	private static Gson createGson()
	{
		//create a new gson builder and register the serializer objects
		GsonBuilder builder = new GsonBuilder();
		return builder.create();
	}
}
