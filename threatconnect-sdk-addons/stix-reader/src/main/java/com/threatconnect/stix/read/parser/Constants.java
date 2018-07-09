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
	
	public static final String ARG_PARSER = "parser";
	//:TODO: change the name of this for the next version to "create_document".
	public static final String ARG_CREATE_DOCUMENT = "associate_document";
	public static final String ARG_ASSOCIATE_ITEMS = "associate_items";
	public static final String ARG_DEFAULT_CONFIDENCE = "default_confidence";
	public static final String ARG_DEFAULT_RATING = "default_rating";
	public static final String ARG_FEED_NAME = "feed_name";
	public static final String ARG_DOCUMENT_NAME = "document_name";
}
