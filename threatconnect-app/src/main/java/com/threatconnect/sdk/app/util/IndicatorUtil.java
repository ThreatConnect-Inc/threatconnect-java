package com.threatconnect.sdk.app.util;

import java.util.regex.Pattern;

import com.threatconnect.sdk.app.model.Address;
import com.threatconnect.sdk.app.model.Host;
import com.threatconnect.sdk.app.model.Indicator;
import com.threatconnect.sdk.app.model.Url;
import com.threatconnect.sdk.app.util.regex.HostNameExtractor;
import com.threatconnect.sdk.app.util.regex.MatchNotFoundException;

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
	public static Indicator exractHostOrAddress(final String url) throws MatchNotFoundException
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
			address.setIp(ipAddressOrHostName);
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
}
