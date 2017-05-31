package com.threatconnect.sdk.parser.service.save;

import com.threatconnect.sdk.model.Item;

import java.io.IOException;
import java.util.Collection;

public interface SaveService
{
	/**
	 * Saves all of the items
	 * 
	 * @param items
	 * the list of items to save
	 * @return holds the results of the save operation
	 * @throws IOException
	 * Signals that an I/O exception of some sort has occurred. This
	 * class is the general class of exceptions produced by failed or
	 * interrupted I/O operations.
	 */
	SaveResults saveItems(Collection<? extends Item> items) throws IOException;
}
