package com.threatconnect.stix.read;

import com.threatconnect.sdk.parser.ParserException;
import org.junit.Test;

public class ParserTest extends AbstractParserTest
{
	@Test
	public void test() throws ParserException
	{
		// parse the list of items
		parse("MIFR-10128830_TLP_WHITE_stix.xml");
	}
}
