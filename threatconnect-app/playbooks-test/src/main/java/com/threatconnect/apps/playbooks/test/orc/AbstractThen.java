package com.threatconnect.apps.playbooks.test.orc;

/**
 * @author Greg Marut
 */
public abstract class AbstractThen<T>
{
	private final T then;
	
	AbstractThen(final T then)
	{
		this.then = then;
	}
	
	public final T then()
	{
		return getThen();
	}
	
	protected T getThen()
	{
		return then;
	}
}
