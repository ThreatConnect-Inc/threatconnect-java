package com.threatconnect.sdk.log;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;

/**
 * This filter allows each thread to independently enable or disable logging temporarily. This is
 * needed to help solve an issue that was experienced where log events were recursively being
 * generated due to the addition of the ServerLoggerAppender.
 * 
 * @author Greg Marut
 */
public class ThreadLogFilter extends AbstractFilter
{
	// holds the instance of this singleton
	private static ThreadLogFilter instance;
	private static final Object lock = new Object();
	
	// holds the object that contains variables that are local to each thread. Each thread that
	// access this object store has its own copy of the variable (boolean in this case)
	private final ThreadLocal<Boolean> enabled;
	
	private ThreadLogFilter()
	{
		this.enabled = new ThreadLocal<Boolean>();
	}
	
	public void set(final boolean enabled)
	{
		this.enabled.set(enabled);
	}
	
	public Boolean get()
	{
		return this.enabled.get();
	}
	
	private Result filter()
	{
		// if there either is not a boolean already set for this thread or the boolean is true
		if (null == this.enabled.get() || this.enabled.get())
		{
			return onMatch;
		}
		// logging is not enabled for this thread at this time
		else
		{
			return onMismatch;
		}
	}
	
	@Override
	public Result filter(final LogEvent event)
	{
		return filter();
	}
	
	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final Message msg,
		final Throwable t)
	{
		return filter();
	}
	
	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final Object msg,
		final Throwable t)
	{
		return filter();
	}
	
	@Override
	public Result filter(final Logger logger, final Level level, final Marker marker, final String msg,
		final Object... params)
	{
		return filter();
	}
	
	public static ThreadLogFilter getInstance()
	{
		// check to see if the instance is null
		if (null == instance)
		{
			// acquire a lock on the lock object for thread synchronization
			synchronized (lock)
			{
				// now that a lock is in place, check again to see if the instance is still null
				if (null == instance)
				{
					// create the new instance
					instance = new ThreadLogFilter();
				}
			}
		}
		
		return instance;
	}
}
