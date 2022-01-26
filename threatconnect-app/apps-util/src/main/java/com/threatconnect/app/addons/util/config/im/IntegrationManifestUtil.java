package com.threatconnect.app.addons.util.config.im;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParseException;
import com.threatconnect.app.addons.util.config.install.serialize.InvalidEnumException;
import com.threatconnect.app.addons.util.config.install.serialize.ParamDataTypeJsonSerializer;
import com.threatconnect.app.addons.util.config.validation.IntegrationManifestValidator;
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
public class IntegrationManifestUtil
{
	private static final boolean VALIDATE_BY_DEFAULT = true;
	
	public static IntegrationManifest load(final String json) throws ValidationException
	{
		return load(json, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManifest load(final String json, final boolean validate) throws ValidationException
	{
		try
		{
			IntegrationManifest IntegrationManagerDefinition = createGson().fromJson(json, IntegrationManifest.class);
			
			if (validate)
			{
				new IntegrationManifestValidator().validate(IntegrationManagerDefinition);
			}
			
			return IntegrationManagerDefinition;
		}
		catch (JsonParseException | InvalidEnumException e)
		{
			throw new ValidationException(e);
		}
	}
	
	public static IntegrationManifest load(final byte[] bytes) throws IOException, ValidationException
	{
		return load(bytes, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManifest load(final byte[] bytes, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new ByteArrayInputStream(bytes))
		{
			return load(inputStream, validate);
		}
	}
	
	public static IntegrationManifest load(final File file) throws IOException, ValidationException
	{
		return load(file, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManifest load(final File file, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, validate);
		}
	}
	
	public static IntegrationManifest load(final File file,
		final IntegrationManifestValidator IntegrationManagerDefinitionValidator) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, IntegrationManagerDefinitionValidator);
		}
	}
	
	public static IntegrationManifest load(final InputStream inputStream) throws ValidationException
	{
		return load(inputStream, VALIDATE_BY_DEFAULT);
	}
	
	public static IntegrationManifest load(final InputStream inputStream, final boolean validate) throws ValidationException
	{
		return load(inputStream, (validate ? new IntegrationManifestValidator() : null));
	}
	
	public static IntegrationManifest load(final InputStream inputStream,
		final IntegrationManifestValidator IntegrationManagerDefinitionValidator) throws ValidationException
	{
		try
		{
			IntegrationManifest IntegrationManagerDefinition =
				createGson().fromJson(new InputStreamReader(inputStream), IntegrationManifest.class);
			
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
