package com.threatconnect.app.addons.util.config.install;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.threatconnect.app.addons.util.config.install.serialize.RuntimeContextJsonSerializer;
import com.threatconnect.app.addons.util.config.install.serialize.RuntimeLevelJsonSerializer;
import com.threatconnect.app.addons.util.config.install.validation.InstallValidator;
import com.threatconnect.app.addons.util.config.install.validation.ValidationException;

import java.io.ByteArrayInputStream;
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
	public static Install load(final String json) throws ValidationException
	{
		Install install = createGson().fromJson(json, Install.class);
		new InstallValidator().validate(install);
		return install;
	}
	
	public static Install load(final byte[] bytes) throws IOException, ValidationException
	{
		try (InputStream inputStream = new ByteArrayInputStream(bytes))
		{
			return load(inputStream);
		}
	}
	
	public static Install load(final File file) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream);
		}
	}
	
	public static Install load(final InputStream inputStream) throws ValidationException
	{
		Install install = createGson().fromJson(new InputStreamReader(inputStream), Install.class);
		new InstallValidator().validate(install);
		return install;
	}
	
	private static Gson createGson()
	{
		RuntimeLevelJsonSerializer runtimeLevelJsonSerializer = new RuntimeLevelJsonSerializer();
		RuntimeContextJsonSerializer runtimeContextJsonSerializer = new RuntimeContextJsonSerializer();
		
		//create a new gson builder and register the serializer objects
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(runtimeLevelJsonSerializer.getType(), runtimeLevelJsonSerializer);
		builder.registerTypeAdapter(runtimeContextJsonSerializer.getType(), runtimeContextJsonSerializer);
		
		return builder.create();
	}
}
