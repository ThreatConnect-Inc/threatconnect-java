package com.threatconnect.sdk.parser.util;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.validator.routines.UrlValidator;

public class UrlUtil
{
	/**
	 * Converts a URL to an absolute url if needed
	 * 
	 * @param url
	 * the url to use
	 * @param domain
	 * the domain to add if the url is relative
	 * @return the absolute url
	 */
	public static String toAbsoluteURL(final String url, final String domain)
	{
		// check to see if this url is relative
		if (!isAbsoluteURL(url))
		{
			StringBuilder sb = new StringBuilder();
			sb.append(domain);
			
			// check to see if a leading slash is needed
			if (!url.startsWith("/") && !domain.endsWith("/"))
			{
				// add a slash
				sb.append("/");
			}
			// check to see if there are too many slashes
			else if (url.startsWith("/") && domain.endsWith("/"))
			{
				// remove a slash
				sb.deleteCharAt(sb.toString().length() - 1);
			}
			
			sb.append(url);
			
			// update the next url to be an absolute url
			return sb.toString();
		}
		else
		{
			return url;
		}
	}
	
	/**
	 * Tests to see if a URL is absolute or relative
	 * 
	 * @param urlString
	 * the url to check
	 * @return whether or not this url is absolute
	 */
	public static boolean isAbsoluteURL(final String urlString)
	{
		boolean result = false;
		try
		{
			URL url = new URL(urlString);
			String protocol = url.getProtocol();
			if (protocol != null && protocol.trim().length() > 0)
			{
				result = true;
			}
		}
		catch (MalformedURLException e)
		{
			return false;
		}
		return result;
	}
	
	public static boolean isValid(final String urlString)
	{
		return new UrlValidator().isValid(urlString);
	}
}
