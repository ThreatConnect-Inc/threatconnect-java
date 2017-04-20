package com.threatconnect.sdk.model.util;

import com.threatconnect.sdk.model.Indicator;

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
}
