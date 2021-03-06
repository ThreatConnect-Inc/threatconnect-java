package com.threatconnect.app.playbooks.db;

/**
 * @author Greg Marut
 */
public interface DBService
{
	void saveValue(String key, byte[] value) throws DBWriteException;

	byte[] getValue(String key) throws DBReadException;
}
