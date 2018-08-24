package com.threatconnect.stix.read.parser;

import com.threatconnect.sdk.parser.util.XPathUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants
{
	public static final String ELEMENT_STIX_PACAKGE = "STIX_Package";
	
	public static final XPathUtil XPATH_UTIL = new XPathUtil();
	public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";
	public static final DateFormat DEFAULT_DATE_FORMATTER = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
}
