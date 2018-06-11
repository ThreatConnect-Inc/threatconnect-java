package com.threatconnect.app.addons.util.config.install;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.threatconnect.app.addons.util.config.install.serialize.InvalidEnumException;
import com.threatconnect.app.addons.util.config.install.serialize.ParamDataTypeJsonSerializer;
import com.threatconnect.app.addons.util.config.install.serialize.ProgramLanguageTypeJsonSerializer;
import com.threatconnect.app.addons.util.config.install.serialize.RuntimeContextJsonSerializer;
import com.threatconnect.app.addons.util.config.install.serialize.RuntimeLevelJsonSerializer;
import com.threatconnect.app.addons.util.config.validation.InstallValidator;
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
public class InstallUtil
{
	private static final boolean VALIDATE_BY_DEFAULT = true;
	
	public static Install load(final String json) throws ValidationException
	{
		return load(json, VALIDATE_BY_DEFAULT);
	}
	
	public static Install load(final String json, final boolean validate) throws ValidationException
	{
		try
		{
			Install install = createGson().fromJson(json, Install.class);
			
			if (validate)
			{
				new InstallValidator().validate(install);
			}
			
			return install;
		}
		catch (JsonParseException | InvalidEnumException e)
		{
			throw new ValidationException(e);
		}
	}
	
	public static Install load(final byte[] bytes) throws IOException, ValidationException
	{
		return load(bytes, VALIDATE_BY_DEFAULT);
	}
	
	public static Install load(final byte[] bytes, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new ByteArrayInputStream(bytes))
		{
			return load(inputStream, validate);
		}
	}
	
	public static Install load(final File file) throws IOException, ValidationException
	{
		return load(file, VALIDATE_BY_DEFAULT);
	}
	
	public static Install load(final File file, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, validate);
		}
	}
	
	public static Install load(final InputStream inputStream) throws ValidationException
	{
		return load(inputStream, VALIDATE_BY_DEFAULT);
	}
	
	public static Install load(final InputStream inputStream, final boolean validate) throws ValidationException
	{
		try
		{
			Install install = createGson().fromJson(new InputStreamReader(inputStream), Install.class);
			
			if (validate)
			{
				new InstallValidator().validate(install);
			}
			
			return install;
		}
		catch (JsonParseException | InvalidEnumException e)
		{
			throw new ValidationException(e);
		}
	}
	
	private static Gson createGson()
	{
		RuntimeLevelJsonSerializer runtimeLevelJsonSerializer = new RuntimeLevelJsonSerializer();
		RuntimeContextJsonSerializer runtimeContextJsonSerializer = new RuntimeContextJsonSerializer();
		ParamDataTypeJsonSerializer paramDataTypeJsonSerializer = new ParamDataTypeJsonSerializer();
		ProgramLanguageTypeJsonSerializer programLanguageTypeJsonSerializer = new ProgramLanguageTypeJsonSerializer();
		
		//create a new gson builder and register the serializer objects
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(runtimeLevelJsonSerializer.getType(), runtimeLevelJsonSerializer);
		builder.registerTypeAdapter(runtimeContextJsonSerializer.getType(), runtimeContextJsonSerializer);
		builder.registerTypeAdapter(paramDataTypeJsonSerializer.getType(), paramDataTypeJsonSerializer);
		builder.registerTypeAdapter(programLanguageTypeJsonSerializer.getType(), programLanguageTypeJsonSerializer);
		
		return builder.create();
	}
}
