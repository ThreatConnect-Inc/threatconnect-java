package com.threatconnect.sdk.blueprints.db;

import com.threatconnect.sdk.blueprints.app.BlueprintsAppConfig;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.rocksdb.TtlDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocksDBService implements DBService
{
	private static final Logger logger = LoggerFactory.getLogger(RocksDBService.class);

	// number of seconds before keys are deleted
	// we use the trigger cache purely as an ephemeral k/v while the blueprint execution pulls
	// the variables to the execution cache
	private static final Integer TRIGGER_TTL_SECONDS = 60 * 60;

	public RocksDBService()
	{
		// a static method that loads the RocksDB C++ library.
		RocksDB.loadLibrary();
	}

	@Override
	public boolean saveValue(String key, byte[] value)
	{
		String path = BlueprintsAppConfig.getBlueprintsAppConfig().getDBPath();
		logger.debug("savingValue: key={}, value={}\n", key, new String(value));

		Options options = createOptions();
		RocksDB db = getDb(path, options, /*allowWrites=*/true);

		try
		{
			db.put(key.getBytes(), value);
		}
		catch (RocksDBException e)
		{
			logger.error(e.getMessage(), e);
			return false;
		}
		finally
		{
			close(db, options);
		}

		return true;
	}

	@Override
	public byte[] getValue(String key) throws DBException
	{
		String path = BlueprintsAppConfig.getBlueprintsAppConfig().getDBPath();

		Options options = createOptions();
		RocksDB db = getDb(path, options, /*allowWrites=*/false);
		byte[] out = new byte[0];
		try
		{
			out = db.get(key.getBytes());
		}
		catch (RocksDBException e)
		{
			logger.error(e.getMessage(), e);
			return null;
		}
		finally
		{
			close(db, options);
		}

		return out;
	}

	// the Options class contains a set of configurable DB options
	// that determines the behavior of a database.
	private Options createOptions()
	{
		return new Options().setCreateIfMissing(true);
	}

	private void close(RocksDB db, Options options)
	{
		if (db != null)
		{
			db.close();
		}
		if (options != null)
		{
			options.dispose();
		}
	}

	private RocksDB getTtlDb(String dbPath, Options options, boolean allowWrites)
	{
		try
		{
			return TtlDB.open(options, dbPath, TRIGGER_TTL_SECONDS, !allowWrites);

		}
		catch (RocksDBException e)
		{
			logger.error("Failed to create ttl db: " + dbPath);
			logger.error(e.getMessage(), e);
		}

		return null;
	}

	private RocksDB getDb(String dbPath, Options options, boolean allowWrites)
	{
		try
		{
			return allowWrites ? RocksDB.open(options, dbPath) : RocksDB.openReadOnly(options, dbPath);
		}
		catch (RocksDBException e)
		{
			logger.error("Failed to create db: " + dbPath);
			logger.error(e.getMessage(), e);
		}

		return null;
	}
}
