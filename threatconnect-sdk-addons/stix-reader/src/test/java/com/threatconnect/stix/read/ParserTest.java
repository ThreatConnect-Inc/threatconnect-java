package com.threatconnect.stix.read;

import com.threatconnect.sdk.parser.ParserException;
import org.junit.Test;

public class ParserTest extends AbstractParserTest
{
	@Test
	public void test() throws ParserException
	{
		// parse the list of items
		parse("CISCP_Log@07-13-2018T00_30_46.xml");
	}
}
