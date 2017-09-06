package com.threatconnect.app.addons.util.config.install;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.threatconnect.app.addons.util.config.validation.FeedValidator;
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
public class FeedUtil
{
	private static final boolean VALIDATE_BY_DEFAULT = true;
	
	public static Feed load(final String json) throws ValidationException
	{
		return load(json, VALIDATE_BY_DEFAULT);
	}
	
	public static Feed load(final String json, final boolean validate) throws ValidationException
	{
		Feed Feed = createGson().fromJson(json, Feed.class);
		
		if (validate)
		{
			new FeedValidator().validate(Feed);
		}
		
		return Feed;
	}
	
	public static Feed load(final byte[] bytes) throws IOException, ValidationException
	{
		return load(bytes, VALIDATE_BY_DEFAULT);
	}
	
	public static Feed load(final byte[] bytes, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new ByteArrayInputStream(bytes))
		{
			return load(inputStream, validate);
		}
	}
	
	public static Feed load(final File file) throws IOException, ValidationException
	{
		return load(file, VALIDATE_BY_DEFAULT);
	}
	
	public static Feed load(final File file, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, validate);
		}
	}
	
	public static Feed load(final InputStream inputStream) throws ValidationException
	{
		return load(inputStream, VALIDATE_BY_DEFAULT);
	}
	
	public static Feed load(final InputStream inputStream, final boolean validate) throws ValidationException
	{
		Feed Feed = createGson().fromJson(new InputStreamReader(inputStream), Feed.class);
		
		if (validate)
		{
			new FeedValidator().validate(Feed);
		}
		
		return Feed;
	}
	
	private static Gson createGson()
	{
		GsonBuilder builder = new GsonBuilder();
		return builder.create();
	}
}
