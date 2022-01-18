package com.threatconnect.app.addons.util.config.im;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.threatconnect.app.addons.util.config.install.serialize.InvalidEnumException;
import com.threatconnect.app.addons.util.config.install.serialize.ParamDataTypeJsonSerializer;
import com.threatconnect.app.addons.util.config.validation.IntegrationManagerDefinitionValidator;
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
public class IntegrationManagerUtil
{
	private static final boolean VALIDATE_BY_DEFAULT = true;
	
	public static IntegrationManagerDefinition load(final String json) throws ValidationException
	{
		return load(json, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManagerDefinition load(final String json, final boolean validate) throws ValidationException
	{
		try
		{
			IntegrationManagerDefinition IntegrationManagerDefinition = createGson().fromJson(json, IntegrationManagerDefinition.class);
			
			if (validate)
			{
				new IntegrationManagerDefinitionValidator().validate(IntegrationManagerDefinition);
			}
			
			return IntegrationManagerDefinition;
		}
		catch (JsonParseException | InvalidEnumException e)
		{
			throw new ValidationException(e);
		}
	}
	
	public static IntegrationManagerDefinition load(final byte[] bytes) throws IOException, ValidationException
	{
		return load(bytes, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManagerDefinition load(final byte[] bytes, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new ByteArrayInputStream(bytes))
		{
			return load(inputStream, validate);
		}
	}
	
	public static IntegrationManagerDefinition load(final File file) throws IOException, ValidationException
	{
		return load(file, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManagerDefinition load(final File file, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, validate);
		}
	}
	
	public static IntegrationManagerDefinition load(final File file,
		final IntegrationManagerDefinitionValidator IntegrationManagerDefinitionValidator) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, IntegrationManagerDefinitionValidator);
		}
	}
	
	public static IntegrationManagerDefinition load(final InputStream inputStream) throws ValidationException
	{
		return load(inputStream, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManagerDefinition load(final InputStream inputStream, final boolean validate) throws ValidationException
	{
		return load(inputStream, (validate ? new IntegrationManagerDefinitionValidator() : null));
	}
	
	public static IntegrationManagerDefinition load(final InputStream inputStream,
		final IntegrationManagerDefinitionValidator IntegrationManagerDefinitionValidator) throws ValidationException
	{
		try
		{
			IntegrationManagerDefinition IntegrationManagerDefinition =
				createGson().fromJson(new InputStreamReader(inputStream), IntegrationManagerDefinition.class);
			
			if (null != IntegrationManagerDefinitionValidator)
			{
				IntegrationManagerDefinitionValidator.validate(IntegrationManagerDefinition);
			}
			
			return IntegrationManagerDefinition;
		}
		catch (JsonParseException | InvalidEnumException e)
		{
			throw new ValidationException(e);
		}
	}
	
	private static Gson createGson()
	{
		ParamDataTypeJsonSerializer paramDataTypeJsonSerializer = new ParamDataTypeJsonSerializer();
		
		//create a new gson builder and register the serializer objects
		GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapter(paramDataTypeJsonSerializer.getType(), paramDataTypeJsonSerializer);
		
		return builder.create();
	}
}
