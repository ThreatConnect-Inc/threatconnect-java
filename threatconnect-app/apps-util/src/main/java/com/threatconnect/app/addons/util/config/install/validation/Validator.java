package com.threatconnect.app.addons.util.config.install.validation;

/**
 * @author Greg Marut
 */
public abstract class Validator<T>
{
	public abstract void validate(T object) throws ValidationException;
	
	protected boolean isNullOrEmpty(final String string)
	{
		return (null == string || string.trim().isEmpty());
	}
}
