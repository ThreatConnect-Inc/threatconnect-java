package com.threatconnect.sdk.parser.source;

import java.io.IOException;
import java.io.InputStream;

/**
 * An interface that defines a source for data to parse
 * 
 * @author Greg Marut
 */
public interface DataSource
{
	/**
	 * Reads the content from the datasource and returns an input stream to the data
	 * 
	 * @return the inputstream containing the content
	 * @throws IOException
	 * if there was an error trying to retrieve an input stream
	 */
	InputStream read() throws IOException;
}
