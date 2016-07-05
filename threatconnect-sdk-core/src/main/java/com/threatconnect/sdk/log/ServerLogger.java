package com.threatconnect.sdk.log;

import java.io.IOException;
import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.LoggerUtil;
import com.threatconnect.sdk.client.writer.LogWriterAdapter;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;

public class ServerLogger
{
	// holds the default threshold for when the log entries will be flushed to the server as a batch
	public static final int DEFAULT_BATCH_THRESHOLD = 100;
	
	// holds the instance of this singleton
	private static ServerLogger instance;
	private static final Object lock = new Object();
	
	// holds the executor service for flushing the logs to the server
	private final ExecutorService executorService;
	
	// holds the queue of server log entries that are pending submit
	private final AbstractQueue<LogEntry> logEntryQueue;
	
	// holds the configuration object for connecting to the api
	private Configuration configuration;
	
	// holds the batch threshold for how many log entries are needed before they are flushed to the
	// server
	private int batchLogEntryThreshold;
	
	private ServerLogger()
	{
		logEntryQueue = new ConcurrentLinkedQueue<LogEntry>();
		executorService = Executors.newSingleThreadExecutor();
		setBatchLogEntryThreshold(DEFAULT_BATCH_THRESHOLD);
		setConfiguration(createConfiguration());
	}
	
	public void addLogEntry(final LogEntry logEntry)
	{
		// adds a log entry to the queue
		logEntryQueue.add(logEntry);
		
		// check to see if the log entries need to be flushed
		flushToServerIfNeeded();
	}
	
	public int getBatchLogEntryThreshold()
	{
		return batchLogEntryThreshold;
	}
	
	public void setBatchLogEntryThreshold(int batchLogEntryThreshold)
	{
		this.batchLogEntryThreshold = batchLogEntryThreshold;
	}
	
	public void flushToServerIfNeeded()
	{
		// check to see if the size of the queue exceeds the threshold
		if (logEntryQueue.size() >= getBatchLogEntryThreshold())
		{
			flushToServer(true);
		}
	}
	
	public void flushToServer(final boolean async)
	{
		final LogEntry[] logEntryArray;
		
		try
		{
			final LogWriterAdapter logWriterAdapter = new LogWriterAdapter(createConnection());
			
			// acquire a thread lock on the queue
			synchronized (logEntryQueue)
			{
				// convert the queue to an array
				logEntryArray = logEntryQueue.toArray(new LogEntry[logEntryQueue.size()]);
				
				// clear the queue
				logEntryQueue.clear();
				
				// create the runnable task that will send the batch of log entries to the server
				Runnable task = new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							// send the log entry array to the server
							logWriterAdapter.writeLogEntires(logEntryArray);
						}
						catch (IOException e)
						{
							LoggerUtil.logErr(e, e.getMessage());
						}
					}
				};
				
				// check to see if this should be executed asynchronously
				if (async)
				{
					// execute synchronously
					executorService.execute(task);
				}
				else
				{
					// execute synchronously
					task.run();
				}
			}
		}
		catch (IOException e)
		{
			LoggerUtil.logErr(e, e.getMessage());
		}
	}
	
	public Configuration getConfiguration()
	{
		return configuration;
	}
	
	public void setConfiguration(Configuration configuration)
	{
		// make sure the configuration object is not null
		if (null == configuration)
		{
			throw new IllegalArgumentException("configuration cannot be null");
		}
		
		this.configuration = configuration;
	}
	
	private Connection createConnection() throws IOException
	{
		return new Connection(getConfiguration());
	}
	
	private Configuration createConfiguration()
	{
		// create the configuration for the threatconnect server
		AppConfig appConfig = AppConfig.getInstance();
		Configuration configuration = new Configuration(appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg());
			
		// add the tc token if it exists
		configuration.setTcToken(appConfig.getTcToken());
		
		return configuration;
	}
	
	public static ServerLogger getInstance()
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
					instance = new ServerLogger();
				}
			}
		}
		
		return instance;
	}
}
