package com.threatconnect.sdk.parser.util;

import java.util.regex.Pattern;

public class RegexUtil
{
	/**
	 * Matches the host portion of a url.
	 * <p>
	 * Group 1: The domain with the scheme
	 * Group 2: The scheme
	 * group 3: The host name
	 * </p>
	 */
	public static final Pattern REGEX_HOST = Pattern.compile("^((https?\\:\\/\\/)?(?:[^@\\/\\n]+@)?([^:\\/\\n]+))");
	
	/**
	 * Matches text that is an email address
	 */
	public static final Pattern REGEX_EMAIL = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
	
	/**
	 * Matches text that is in the format of an IP address. This does not validate that each octet
	 * is valid, just simply that it follows an IPv4 format
	 */
	public static final Pattern REGEX_IP_FORMAT =
		Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");
		
	/**
	 * Matches text that is in the format of an IP address. This does not validate that each octet
	 * is valid, just simply that it follows an IPv4 format
	 */
	public static final Pattern REGEX_IP_EXTRACT =
		Pattern.compile("([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3})");
		
	/**
	 * Identifies any unnecessary leading zeros in an ip address
	 */
	public static final Pattern REGEX_LEADING_ZEROS = Pattern.compile("^0+(?=[0-9])|(?<=\\.)0+(?=[0-9])");
	
	/**
	 * Matches text that is an MD5 hash
	 */
	public static final Pattern REGEX_MD5 = Pattern.compile("^[a-fA-F0-9]{32}$");
	public static final Pattern REGEX_MD5_EXTRACT = Pattern.compile("([a-fA-F0-9]{32})");
	
	/**
	 * Matches text that is a SHA1 hash
	 */
	public static final Pattern REGEX_SHA1 = Pattern.compile("^[a-fA-F0-9]{40}$");
	public static final Pattern REGEX_SHA1_EXTRACT = Pattern.compile("([a-fA-F0-9]{40})");
	
	/**
	 * Matches text that is a SHA256 hash
	 */
	public static final Pattern REGEX_SHA256 = Pattern.compile("^[a-fA-F0-9]{64}$");
	public static final Pattern REGEX_SHA256_EXTRACT = Pattern.compile("([a-fA-F0-9]{64})");
	
	private RegexUtil()
	{
	
	}
}
