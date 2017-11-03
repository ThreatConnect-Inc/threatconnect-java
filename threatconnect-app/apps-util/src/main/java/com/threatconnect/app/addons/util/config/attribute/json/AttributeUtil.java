package com.threatconnect.app.addons.util.config.attribute.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.json.AttributeValidator;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Greg Marut
 */
public class AttributeUtil
{
	private static final boolean VALIDATE_BY_DEFAULT = true;
	
	public static Attribute load(final File file) throws IOException, ValidationException
	{
		return load(file, VALIDATE_BY_DEFAULT, new AttributeValidator());
	}
	
	public static Attribute load(final File file, final boolean validate, final AttributeValidator attributeValidator)
		throws IOException, ValidationException
	{
		try (InputStream inputStream = new FileInputStream(file))
		{
			return load(inputStream, validate, attributeValidator);
		}
	}
	
	public static Attribute load(final String json, final boolean validate, final AttributeValidator attributeValidator)
		throws ValidationException
	{
		Attribute attribute = createGson().fromJson(json, Attribute.class);
		
		if (validate)
		{
			attributeValidator.validate(attribute);
		}
		
		return attribute;
	}
	
	public static Attribute load(final InputStream inputStream, final boolean validate,
		final AttributeValidator attributeValidator) throws ValidationException
	{
		Attribute attribute = createGson().fromJson(new InputStreamReader(inputStream), Attribute.class);
		
		if (validate)
		{
			//load the map of attribute validation rules by name
			Map<String, AttributeValidationRule> validationRuleMap = attribute.getValidationRules().stream().collect(
				Collectors.toMap(AttributeValidationRule::getName, a -> a));
			
			attributeValidator.validate(attribute);
		}
		
		return attribute;
	}
	
	private static Gson createGson()
	{
		GsonBuilder builder = new GsonBuilder();
		return builder.create();
	}
}
