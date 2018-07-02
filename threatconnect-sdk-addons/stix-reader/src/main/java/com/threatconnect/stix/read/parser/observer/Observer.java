package com.threatconnect.stix.read.parser.observer;

/**
 * @author Greg Marut
 */
public interface Observer<T>
{
	void found(T object);
}
