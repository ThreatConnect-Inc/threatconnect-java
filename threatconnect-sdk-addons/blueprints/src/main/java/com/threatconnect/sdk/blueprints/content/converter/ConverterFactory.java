package com.threatconnect.sdk.blueprints.content.converter;

import com.threatconnect.sdk.blueprints.content.StandardType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConverterFactory
{
	private static final Logger logger = LoggerFactory.getLogger(ConverterFactory.class);

	private ConverterFactory()
	{

	}

	private static final Map<StandardType, ContentConverter> CONVERTER_MAP =
		new HashMap<StandardType, ContentConverter>()
		{
			{
				put(StandardType.String, new ContentConverter<String>(String.class));
				put(StandardType.StringArray, new ListContentConverter<String>(String.class));
			}
		};

	/**
	 * Retrieves the content converter for a standard type if it exists. Otherwise, null
	 *
	 * @param standardType
	 * @return
	 */
	public static ContentConverter getConverter(final StandardType standardType) throws UnknownConverterException
	{
		if (CONVERTER_MAP.containsKey(standardType))
		{
			return CONVERTER_MAP.get(standardType);
		}
		else
		{
			//no suitable converter was found
			final String message =
				"Failed to find a ContentConverter match in for standardType: " + standardType.toString();
			throw new UnknownConverterException(message);
		}
	}

	public static ContentConverter getConverter(final String key, final StandardType... standardTypes)
		throws UnknownConverterException
	{
		//for each of the standard types
		for (StandardType standardType : standardTypes)
		{
			String endsText = String.format("!%s", standardType);
			logger.debug("key=" + key + ", endsWith=" + endsText);

			if (key.endsWith(endsText))
			{
				return CONVERTER_MAP.get(standardType);
			}
		}

		//no suitable converter was found
		final String message =
			"Failed to find a ContentConverter match in " + Arrays.toString(standardTypes) + " for key: " + key;
		throw new UnknownConverterException(message);
	}
}
