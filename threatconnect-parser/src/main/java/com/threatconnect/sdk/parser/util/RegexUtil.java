package com.threatconnect.sdk.parser.util;

public class RegexUtil
{
	/**
	 * Matches the host portion of a url.
	 * <br/>
	 * Group 1: The domain with the scheme
	 * Group 2: The scheme
	 * group 3: The host name
	 */
	public static final String REGEX_HOST = "^((https?\\:\\/\\/)?(?:[^@\\/\\n]+@)?([^:\\/\\n]+))";
	
	/**
	 * Matches text that is in the format of an IP address. This does not validate that each octet
	 * is valid, just simply that it follows an IPv4 format
	 */
	public static final String REGEX_IP_FORMAT = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$";
	
	/**
	 * Matches text that is in the format of an IP address. This does not validate that each octet
	 * is valid, just simply that it follows an IPv4 format
	 */
	public static final String REGEX_IP_EXTRACT = "([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})";
	
	/**
	 * Identifies any unnecessary leading zeros in an ip address
	 */
	public static final String REGEX_LEADING_ZEROS = "^0+(?=[0-9])|(?<=\\.)0+(?=[0-9])";
	
	/**
	 * Matches text that is an MD5 hash
	 */
	public static final String REGEX_MD5 = "^[a-fA-F0-9]{32}$";
	
	/**
	 * Matches text that is a SHA1 hash
	 */
	public static final String REGEX_SHA1 = "^[a-fA-F0-9]{40}$";
	
	/**
	 * Matches text that is a SHA256 hash
	 */
	public static final String REGEX_SHA256 = "^[a-fA-F0-9]{64}$";
	
	private RegexUtil()
	{
	
	}
}
