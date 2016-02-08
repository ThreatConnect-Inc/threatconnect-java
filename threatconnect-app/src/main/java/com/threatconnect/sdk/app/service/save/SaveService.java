package com.threatconnect.sdk.app.service.save;

import java.io.IOException;
import java.util.List;

import com.threatconnect.sdk.app.model.Item;

public interface SaveService
{
	/**
	 * Saves all of the items
	 * 
	 * @param items
	 * @throws IOException
	 */
	public SaveResults saveItems(List<Item> items) throws IOException;
}
