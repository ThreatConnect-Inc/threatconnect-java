package com.threatconnect.sdk.blueprints.db;

/**
 * @author Greg Marut
 */
public interface DBService
{
	boolean saveValue(String key, byte[] value) throws DBException;

	byte[] getValue(String key) throws DBException;
}
