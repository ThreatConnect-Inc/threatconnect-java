package com.threatconnect.sdk.parser.service.save;

import com.threatconnect.sdk.model.Item;

/**
 * Represents when the main item failed to save
 *
 * @author Greg Marut
 */
public class SaveItemFailedException extends Exception
{
	private static final long serialVersionUID = 1L;
	
	public SaveItemFailedException(final Item item, final String message)
	{
		super(buildMessage(item, message));
	}
	
	public SaveItemFailedException(final String message)
	{
		super(message);
	}
	
	public SaveItemFailedException(final Item item, final Exception cause)
	{
		super(buildMessage(item, cause.getMessage()), cause);
	}
	
	public SaveItemFailedException(final Exception cause)
	{
		super(cause);
	}
	
	private static String buildMessage(final Item item, final String message)
	{
		return "Failed to save item \"" + item.toString() + "\": " + message;
	}
}
