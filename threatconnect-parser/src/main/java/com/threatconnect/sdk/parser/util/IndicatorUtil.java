package com.threatconnect.sdk.parser.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.threatconnect.sdk.parser.model.Address;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Url;
import com.threatconnect.sdk.parser.util.regex.HostNameExtractor;
import com.threatconnect.sdk.parser.util.regex.MatchNotFoundException;

public class IndicatorUtil
{
	private IndicatorUtil()
	{
	
	}
	
	/**
	 * Creates a URL indicator and ensures that the url is wellformed
	 * 
	 * @param url
	 * @return
	 */
	public static Url createUrl(final String url) throws InvalidURLException
	{
		// make sure the url is not null or empty
		if (null != url && !url.isEmpty())
		{
			String fullUrl;
			
			if (!UrlUtil.isAbsoluteURL(url))
			{
				fullUrl = "http://" + url;
			}
			else
			{
				fullUrl = url;
			}
			
			// check to see if the url is valid
			if (UrlUtil.isValid(fullUrl))
			{
				Url result = new Url();
				result.setText(fullUrl);
				return result;
			}
			else
			{
				throw new InvalidURLException(fullUrl + " is not a valid URL");
			}
		}
		else
		{
			throw new InvalidURLException("url cannot be null or empty");
		}
	}
	
	/**
	 * Given a URL, this extracts either the hostname or the ipaddress of the url and returns the
	 * indicator with the appropriate field populated
	 * 
	 * @param url
	 * @return
	 * @throws MatchNotFoundException
	 */
	public static Indicator extractHostOrAddress(final String url) throws MatchNotFoundException
	{
		// retrieve the hostname of the url
		String ipAddressOrHostName = new HostNameExtractor(url.trim()).getHostName();
		return createHostOrAddress(ipAddressOrHostName);
	}
	
	/**
	 * Given a hostname or an ip address, an appropriate indicator is created
	 * 
	 * @param ipAddressOrHostName
	 * @return
	 */
	public static Indicator createHostOrAddress(final String ipAddressOrHostName)
	{
		// check to see if this is an IP address
		if (Pattern.matches(RegexUtil.REGEX_IP_FORMAT, ipAddressOrHostName))
		{
			// create an address indicator
			Address address = new Address();
			address.setIp(cleanIP(ipAddressOrHostName));
			return address;
		}
		else
		{
			// create a host indicator
			Host host = new Host();
			host.setHostName(ipAddressOrHostName);
			return host;
		}
	}
	
	/**
	 * Cleans an IP address.<br />
	 * 1. Strips all leading 0s that are not necessary
	 * 
	 * @return
	 */
	public static String cleanIP(final String ip)
	{
		// create a matcher for this ip
		Matcher matcher = Pattern.compile(RegexUtil.REGEX_LEADING_ZEROS).matcher(ip);
		
		// check to see if there are any matches
		if (matcher.find())
		{
			// remove all of the leading zeros and recursively call this again to ensure that the ip
			// is now clean
			return cleanIP(ip.replaceAll(RegexUtil.REGEX_LEADING_ZEROS, ""));
		}
		else
		{
			// the ip address is good
			return ip;
		}
	}
}
