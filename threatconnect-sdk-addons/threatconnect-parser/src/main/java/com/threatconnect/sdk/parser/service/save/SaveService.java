package com.threatconnect.sdk.parser.service.save;

import java.io.IOException;
import java.util.List;

import com.threatconnect.sdk.parser.model.Item;

public interface SaveService
{
	/**
	 * Saves all of the items
	 * 
	 * @param items
	 * @throws IOException
	 */
	public SaveResults saveItems(List<? extends Item> items) throws IOException;
}
