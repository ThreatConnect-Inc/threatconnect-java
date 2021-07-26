package com.threatconnect.app.execution.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.execution.entity.KeyValue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class KeyValueListConverter extends ListContentConverter<KeyValue>
{
	private static final String FIX_EMBEDDED_VARIABLE_REGEX =
		"\\\"value\\\":\\s?(#([A-Za-z]+):([\\d]+):([A-Za-z0-9_.-]+)!([A-Za-z0-9_-]+))\\}";
	private static final Pattern FIX_EMBEDDED_VARIABLE_PATTERN = Pattern.compile(FIX_EMBEDDED_VARIABLE_REGEX);
	
	public KeyValueListConverter()
	{
		super(KeyValue.class, StandardPlaybookType.KeyValueArray);
	}
	
	@Override
	public List<KeyValue> fromByteArray(final byte[] raw) throws ConversionException
	{
		//convert the raw data to a string to fix the json by replacing the embedded variables as needed
		String data = replaceAllEmbeddedVariables(new String(raw));
		return super.fromByteArray(data.getBytes());
	}
	
	/**
	 * There is an issue with KeyValue arrays and embedded variables. The json may not be valid because of this.
	 * To compensate, whenever a value is found that that is only an embedded variable, we need to wrap it in
	 * quotes to form valid json so that it can be properly deserialzed
	 */
	private String replaceAllEmbeddedVariables(final String data)
	{
		Matcher matcher = FIX_EMBEDDED_VARIABLE_PATTERN.matcher(data);
		
		if (matcher.find())
		{
			//replace the first instance of the variable
			String replaced = data.replaceFirst(FIX_EMBEDDED_VARIABLE_REGEX,
				Matcher.quoteReplacement("\"value\": \"" + matcher.group(1) + "\"}"));
			
			//continue replacing to make sure the entire string is fixed
			return replaceAllEmbeddedVariables(replaced);
		}
		else
		{
			return data;
		}
	}
}