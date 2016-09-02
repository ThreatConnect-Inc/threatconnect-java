package com.threatconnect.sdk.blueprints.db;

import com.threatconnect.sdk.blueprints.app.BlueprintsAppConfig;
import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RocksDBService implements DBService
{
	private static final Logger logger = LoggerFactory.getLogger(RocksDBService.class);

	// number of seconds before keys are deleted
	// we use the trigger cache purely as an ephemeral k/v while the blueprint execution pulls
	// the variables to the execution cache
	private static final Integer TRIGGER_TTL_SECONDS = 60 * 60;

	//holds the path to the database
	private String databasePath;

	public RocksDBService()
	{
		this.databasePath = BlueprintsAppConfig.getInstance().getDBPath();

		// a static method that loads the RocksDB C++ library.
		RocksDB.loadLibrary();
	}

	@Override
	public void saveValue(String key, byte[] value) throws DBWriteException
	{
		logger.debug("savingValue: key={}, value={}\n", key, new String(value));

		RocksDB db = null;
		Options options = createOptions();

		try
		{
			//open the database with write access
			db = openDatabase(options, true);
			db.put(key.getBytes(), value);
		}
		catch (RocksDBException e)
		{
			throw new DBWriteException(e);
		}
		finally
		{
			close(db, options);
		}
	}

	@Override
	public byte[] getValue(String key) throws DBReadException
	{
		RocksDB db = null;
		Options options = createOptions();

		try
		{
			//open the database in read only
			db = openDatabase(options, false);
			return db.get(key.getBytes());
		}
		catch (RocksDBException e)
		{
			throw new DBReadException(e);
		}
		finally
		{
			close(db, options);
		}
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

	/**
	 * Opens the RocksDB database
	 *
	 * @param options
	 * @param allowWrites whether or not to opean the database with write access
	 * @return
	 * @throws RocksDBException
	 */
	private RocksDB openDatabase(Options options, boolean allowWrites) throws RocksDBException
	{
		try
		{
			return allowWrites ? RocksDB.open(options, databasePath) : RocksDB.openReadOnly(options, databasePath);
		}
		catch (RocksDBException e)
		{
			logger.error("Failed to create db: " + databasePath);
			throw e;
		}
	}
}
