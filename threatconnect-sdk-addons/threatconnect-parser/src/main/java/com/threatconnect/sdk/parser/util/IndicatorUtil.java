package com.threatconnect.sdk.parser.util;

import com.threatconnect.sdk.parser.model.Address;
import com.threatconnect.sdk.parser.model.Host;
import com.threatconnect.sdk.parser.model.Indicator;
import com.threatconnect.sdk.parser.model.Url;
import com.threatconnect.sdk.parser.util.regex.HostNameExtractor;
import com.threatconnect.sdk.parser.util.regex.MatchNotFoundException;

import java.util.regex.Matcher;

public class IndicatorUtil
{
	private IndicatorUtil()
	{
	
	}
	
	/**
	 * Creates a URL indicator and ensures that the url is wellformed
	 * 
	 * @param url
	 * the url to use
	 * @return a Url indicator object
	 * @throws InvalidURLException
	 * if the url is not valid
	 */
	public static Url createUrl(final String url) throws InvalidURLException
	{
		// make sure the url is not null or empty
		if (null != url && !url.trim().isEmpty())
		{
			String fullUrl;
			
			if (!UrlUtil.isAbsoluteURL(url.trim()))
			{
				fullUrl = "http://" + url.trim();
			}
			else
			{
				fullUrl = url.trim();
			}
			
			Url result = new Url();
			result.setText(fullUrl);
			return result;
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
	 * the url string to use
	 * @return the indicator that was created
	 * @throws MatchNotFoundException
	 * if the url could not be identified as an indicator
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
	 * the text to use
	 * @return the indicator that was created
	 */
	public static Indicator createHostOrAddress(final String ipAddressOrHostName)
	{
		// check to see if this is an IP address
		if (RegexUtil.REGEX_IP_FORMAT.matcher(ipAddressOrHostName.trim()).matches())
		{
			// create an address indicator
			Address address = new Address();
			address.setIp(cleanIP(ipAddressOrHostName.trim()));
			return address;
		}
		else
		{
			// create a host indicator
			Host host = new Host();
			host.setHostName(ipAddressOrHostName.trim());
			return host;
		}
	}
	
	/**
	 * Cleans an IP address
	 * <p>
	 * 1. Strips all leading 0s that are not necessary
	 * </p>
	 * 
	 * @param ip
	 * the ip address to clean up
	 * @return the clean ip address
	 */
	public static String cleanIP(final String ip)
	{
		// create a matcher for this ip
		Matcher matcher = RegexUtil.REGEX_LEADING_ZEROS.matcher(ip.trim());
		
		// check to see if there are any matches
		if (matcher.find())
		{
			// remove all of the leading zeros and recursively call this again to ensure that the ip
			// is now clean
			return cleanIP(ip.trim().replaceAll(RegexUtil.REGEX_LEADING_ZEROS.pattern(), ""));
		}
		else
		{
			// the ip address is good
			return ip.trim();
		}
	}
}
