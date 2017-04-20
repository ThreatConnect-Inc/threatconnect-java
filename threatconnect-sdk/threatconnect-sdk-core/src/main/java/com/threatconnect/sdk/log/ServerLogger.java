package com.threatconnect.sdk.log;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.sdk.app.LoggerUtil;
import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.client.writer.LogWriterAdapter;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;

import java.io.IOException;
import java.util.AbstractQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * This singleton maintains the logic needed for recording and reporting log entries to the server.
 * 2 queues which are used to accomplish this task. The first queue is used to store log entries
 * until a threshold is reached. Once this occurs, a task is created which will be responsible for
 * making a batch API call to the server with all of the log entries. The second queue is used to
 * track the actual executing api calls. In the event that multiple batch requests are created
 * before the previous can finish executing, this queue helps maintain the order of all log entries
 * as well as track actively executing api calls.
 *
 * @author Greg Marut
 */
public class ServerLogger
{
	// holds the default threshold for when the log entries will be flushed to the server as a batch
	public static final int DEFAULT_BATCH_THRESHOLD = 100;

	// holds the instance of this singleton
	private static ServerLogger instance;
	private static final Object lock = new Object();

	// holds the executor service for flushing the logs to the server
	private final ExecutorService taskExecutorService;

	// holds the queue of server log entries that are pending submit
	private final AbstractQueue<LogEntry> logEntryQueue;
	private final AbstractQueue<LogWriterTask> logWriterTasks;

	// determines whether or not server logging is enabled
	private volatile boolean enabled;

	// holds the configuration object for connecting to the api
	private Configuration configuration;

	// holds the batch threshold for how many log entries are needed before they are flushed to the
	// server
	private int batchLogEntryThreshold;

	private ServerLogger()
	{
		logEntryQueue = new ConcurrentLinkedQueue<LogEntry>();
		logWriterTasks = new ConcurrentLinkedQueue<LogWriterTask>();
		taskExecutorService = Executors.newSingleThreadExecutor();
		setBatchLogEntryThreshold(DEFAULT_BATCH_THRESHOLD);
		setConfiguration(createConfiguration());
		setEnabled(true);
	}

	public void addLogEntry(final LogEntry logEntry)
	{
		// ensure that server logging is enabled
		if (isEnabled())
		{
			// adds a log entry to the queue
			logEntryQueue.add(logEntry);

			// check to see if the log entries need to be flushed
			flushToServerIfNeeded();
		}
	}

	public int getBatchLogEntryThreshold()
	{
		return batchLogEntryThreshold;
	}

	public void setBatchLogEntryThreshold(int batchLogEntryThreshold)
	{
		this.batchLogEntryThreshold = batchLogEntryThreshold;
	}

	/**
	 * Writes all remaining log entries to the server. This method blocks until all entries have
	 * been uploaded to the server
	 */
	public void flushToServer()
	{
		// ensure that server logging is enabled
		if (isEnabled())
		{
			// prepare any remaining log entries from the queue
			prepareLogWriterTask();

			// acquire a thread lock on the queue
			synchronized (logWriterTasks)
			{
				// while there are more tasks to write
				while (!logWriterTasks.isEmpty())
				{
					// execute the next pending task
					logWriterTasks.poll().run();
				}
			}
		}
	}

	/**
	 * First checks to see if the log entry queue has reached the required threshold, if so, it is
	 * flushed to the server
	 */
	private void flushToServerIfNeeded()
	{
		// ensure that server logging is enabled
		if (isEnabled())
		{
			// check to see if the size of the queue exceeds the threshold
			if (logEntryQueue.size() >= getBatchLogEntryThreshold())
			{
				// run this inside of a new thread
				taskExecutorService.execute(new Runnable()
				{
					@Override
					public void run()
					{
						flushToServer();
					}
				});
			}
		}
	}

	/**
	 * Converts the queue of log entries into a task that is ready to be sent to the server
	 */
	private void prepareLogWriterTask()
	{
		// ensure that server logging is enabled
		if (isEnabled())
		{
			// acquire a thread lock on the queue
			synchronized (logEntryQueue)
			{
				// convert the queue to an array
				final LogEntry[] logEntryArray = logEntryQueue.toArray(new LogEntry[logEntryQueue.size()]);

				// make sure there are log entries
				if (logEntryArray.length > 0)
				{
					// clear the queue
					logEntryQueue.clear();

					try
					{
						// create the runnable task that will send the batch of log entries to the
						// server
						LogWriterTask task = new LogWriterTask(new LogWriterAdapter(createConnection()), logEntryArray);
						logWriterTasks.add(task);
					}
					catch (IOException e)
					{
						LoggerUtil.logErr(e, e.getMessage());
					}
				}
			}
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
		AppConfig appConfig = SdkAppConfig.getInstance();
		Configuration configuration = new Configuration(appConfig.getTcApiPath(), appConfig.getTcApiAccessID(),
			appConfig.getTcApiUserSecretKey(), appConfig.getApiDefaultOrg(), appConfig.getTcToken(),
			appConfig.getTcTokenExpires());

		return configuration;
	}

	public boolean isEnabled()
	{
		return enabled;
	}

	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
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
