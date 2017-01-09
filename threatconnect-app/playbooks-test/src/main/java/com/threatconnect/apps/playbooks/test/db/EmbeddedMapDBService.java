package com.threatconnect.apps.playbooks.test.db;

import com.threatconnect.app.playbooks.db.DBReadException;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.db.DBWriteException;

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
