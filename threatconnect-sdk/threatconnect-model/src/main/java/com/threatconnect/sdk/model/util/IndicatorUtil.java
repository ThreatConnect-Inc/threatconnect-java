package com.threatconnect.sdk.model.util;

import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.serialize.InvalidIndicatorException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Greg Marut
 */
public class IndicatorUtil
{
	private IndicatorUtil()
	{
	
	}
	
	/**
	 * Extracts a specific type of indicator from the set
	 *
	 * @param indicators
	 * @param clazz
	 */
	public static <T extends Indicator> Set<T> extractIndicatorSet(final Collection<Indicator> indicators,
		final Class<T> clazz)
	{
		final Set<T> results = new HashSet<T>();
		
		//for each of the indicators
		for (Indicator indicator : indicators)
		{
			//check to see if this indicator is of this type
			if (clazz.isAssignableFrom(indicator.getClass()))
			{
				results.add((T) indicator);
			}
		}
		
		return results;
	}
	
	public static File createFile(final String summary) throws InvalidIndicatorException
	{
		//create a new file
		File file = new File();
		file.setMd5(extractHash(summary, File.MD5_LENGTH));
		file.setSha1(extractHash(summary, File.SHA1_LENGTH));
		file.setSha256(extractHash(summary, File.SHA256_LENGTH));
		
		//make sure that one of the values was set
		if (null == file.getMd5() && null == file.getSha1() && null == file.getSha256())
		{
			throw new InvalidIndicatorException("summary does not contain any valid hash codes");
		}
		
		return file;
	}
	
	private static String extractHash(final String text, final int hashLength)
	{
		//make sure the text is not null
		if (null != text)
		{
			//split the text on the separator
			String[] hashCodes = text.split(":");
			
			//for each of the parts
			for (String hash : hashCodes)
			{
				//check to see if this is the expected length
				if (hash.trim().length() == hashLength)
				{
					//the hash was found
					return hash.trim();
				}
			}
		}
		
		//no hashcode was found
		return null;
	}
}
