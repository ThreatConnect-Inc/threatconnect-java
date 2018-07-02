package com.threatconnect.stix.read;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.parser.ParserException;
import com.threatconnect.sdk.parser.source.FileDataSource;
import com.threatconnect.stix.read.parser.STIXStreamParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

public abstract class AbstractParserTest
{
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	protected File loadFile(final String fileName)
	{
		File file = new File("src/test/resources/" + fileName);
		return file;
	}
	
	protected List<Item> parse(final String fileName) throws ParserException
	{
		STIXStreamParser parser = createSTIXParser(loadFile(fileName));
		
		// parse the list of items
		List<Item> items = parser.parseData();
		logItems(items);
		return items;
	}
	
	protected void logItems(final List<? extends Item> items)
	{
		Gson gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
		
		for (Item item : items)
		{
			logger.info(gson.toJson(item));
		}
		
		logger.info("Parsed {} items", items.size());
	}
	
	protected STIXStreamParser createSTIXParser(File file)
	{
		return new STIXStreamParser(new FileDataSource(file));
	}
}
