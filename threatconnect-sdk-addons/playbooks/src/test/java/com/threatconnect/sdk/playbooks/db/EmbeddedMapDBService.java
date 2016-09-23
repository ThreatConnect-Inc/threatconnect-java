package com.threatconnect.sdk.playbooks.db;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Greg Marut
 */
public class EmbeddedMapDBService implements DBService
{
	private final Map<String, byte[]> database;

	public EmbeddedMapDBService()
	{
		this.database = new HashMap<String, byte[]>();
	}

	@Override
	public void saveValue(String key, byte[] value) throws DBWriteException
	{
		database.put(key, value);
	}

	@Override
	public byte[] getValue(String key) throws DBReadException
	{
		return database.get(key);
	}
}
