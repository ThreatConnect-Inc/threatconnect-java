package com.threatconnect.app.addons.util.config.install;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.threatconnect.app.addons.util.config.validation.JobValidator;
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
public class JobUtil
{
	private static final boolean VALIDATE_BY_DEFAULT = true;
	
	public static Job load(final String json) throws ValidationException
	{
		return load(json, VALIDATE_BY_DEFAULT, new JobValidator());
	}
	
	public static Job load(final String json, final boolean validate, final JobValidator jobValidator) throws ValidationException
	{
		Job job = createGson().fromJson(json, Job.class);
		
		if (validate)
		{
			jobValidator.validate(job);
		}
		
		return job;
	}
	
	public static Job load(final byte[] bytes) throws IOException, ValidationException
	{
		return load(bytes, VALIDATE_BY_DEFAULT);
	}
	
	public static Job load(final byte[] bytes, final boolean validate) throws IOException, ValidationException
	{
		try (InputStream inputStream = new ByteArrayInputStream(bytes))
		{
			return load(inputStream, validate, new JobValidator());
		}
	}
	
	public static Job load(final File file) throws IOException, ValidationException
	{
		return load(file, VALIDATE_BY_DEFAULT, new JobValidator());
	}
	
	public static Job load(final File file, final boolean validate, final JobValidator jobValidator) throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, validate, jobValidator);
		}
	}
	
	public static Job load(final InputStream inputStream) throws ValidationException
	{
		return load(inputStream, VALIDATE_BY_DEFAULT, new JobValidator());
	}
	
	public static Job load(final InputStream inputStream, final boolean validate, final JobValidator jobValidator) throws ValidationException
	{
		Job Job = createGson().fromJson(new InputStreamReader(inputStream), Job.class);
		
		if (validate)
		{
			jobValidator.validate(Job);
		}
		
		return Job;
	}
	
	private static Gson createGson()
	{
		GsonBuilder builder = new GsonBuilder();
		return builder.create();
	}
}
