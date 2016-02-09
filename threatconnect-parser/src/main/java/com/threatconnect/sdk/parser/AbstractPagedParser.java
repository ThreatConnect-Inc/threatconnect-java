package com.threatconnect.sdk.parser;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.threatconnect.sdk.parser.model.Item;
import com.threatconnect.sdk.parser.result.PageResult;
import com.threatconnect.sdk.parser.util.UrlUtil;

/**
 * Represents a parser that can contain multiple pages of data to parse
 * 
 * @author Greg Marut
 */
public abstract class AbstractPagedParser extends AbstractParser
{
	public AbstractPagedParser(final String url)
	{
		super(url);
	}
	
	@Override
	public List<Item> parseData(Date startDate) throws ParserException
	{
		// holds the list of items to return
		List<Item> items = new ArrayList<Item>();
		
		// holds the set of all of the urls that have been parsed so that we can ensure that an
		// infinite loop does not occur
		Set<String> parsedURLs = new HashSet<String>();
		
		// holds the next url to parse
		String nextUrl = getUrl();
		
		// while the next url is not null and if the set does not contain this url and while the url
		// is valid
		while (null != nextUrl && !parsedURLs.contains(nextUrl) && UrlUtil.isValid(nextUrl))
		{
			// hold onto the current url and set the next url to null
			String currentUrl = nextUrl;
			nextUrl = null;
			
			// parse the page
			logger.info("Parsing: {}", currentUrl);
			PageResult pageResult = parsePage(currentUrl, startDate);
			parsedURLs.add(currentUrl);
			
			// make sure the page is not null
			if (null != pageResult)
			{
				// add all the items to the list
				items.addAll(pageResult.getItems());
				
				// make sure the next url is not null
				if (null != pageResult.getNextPageUrl())
				{
					// ensure that this url is an absolute url
					nextUrl = UrlUtil.toAbsoluteURL(pageResult.getNextPageUrl(), getDomain());
				}
			}
		}
		
		return items;
	}
	
	protected abstract PageResult parsePage(final String pageUrl, final Date startDate)
		throws ParserException;
}
