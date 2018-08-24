package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.app.exception.TCMessageException;
import com.threatconnect.sdk.client.reader.BatchReaderAdapter;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.conn.exception.HttpResourceNotFoundException;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.parser.service.save.SaveResults;
import com.threatconnect.sdk.server.entity.BatchStatus;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.server.response.entity.data.BatchStatusResponseData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;

public abstract class AbstractBatchWriter extends Writer
{
	public static final int POLL_TIMEOUT_MINUTES = 120;
	private static final long POLL_INITIAL_DELAY = 1000L;
	private static final long POLL_MAX_DELAY = 30000L;
	
	public AbstractBatchWriter(final Connection connection)
	{
		super(connection);
	}
	
	protected SaveResults pollBatch(final BatchWriter.BatchUploadResponse batchUploadResponse, final String ownerName,
		final int batchIndex, final int batchTotal) throws IOException, SaveItemFailedException
	{
		// check to see if the response was successful
		if (batchUploadResponse.getResponse().isSuccess())
		{
			Integer batchID = batchUploadResponse.getBatchID();
			
			//make sure the batch id is not null
			if (null != batchID)
			{
				boolean processing = true;
				long delay = POLL_INITIAL_DELAY;
				
				// create the batch reader object
				BatchReaderAdapter<com.threatconnect.sdk.server.entity.Indicator> batchReaderAdapter = createReaderAdapter();
				
				// holds the response object
				ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> batchStatusResponse = null;
				
				//determine when this polling will timeout
				final LocalDateTime pollTimeout = LocalDateTime.now().plusMinutes(POLL_TIMEOUT_MINUTES);
				
				// continue while the batch job is still processing
				while (processing)
				{
					try
					{
						logger.debug("Waiting {}ms for batch job {}/{}: {}", delay, batchIndex, batchTotal, batchID);
						Thread.sleep(delay);
					}
					catch (InterruptedException e)
					{
						throw new IOException(e);
					}
					
					// check the status of the batch job
					batchStatusResponse = batchReaderAdapter.getStatus(batchID, ownerName);
					BatchStatus.Status status = batchStatusResponse.getItem().getStatus();
					
					// this job is still considered processing as long as the status is not
					// completed
					processing = (status != BatchStatus.Status.Completed);
					
					// make sure the delay is less than the maximum
					if (delay < POLL_MAX_DELAY)
					{
						// increment the delay
						delay *= 2;
					}
					
					// make sure that the delay does not exceed the maximum
					if (delay > POLL_MAX_DELAY)
					{
						delay = POLL_MAX_DELAY;
					}
					
					//check to see if the job timed out
					if (processing && LocalDateTime.now().isAfter(pollTimeout))
					{
						throw new TCMessageException(
							"Batch job \"" + batchID + "\" timed out with a status of \"" + status + "\" after "
								+ POLL_TIMEOUT_MINUTES + " minutes.");
					}
				}
				
				// make sure the response is not null
				if (null != batchStatusResponse)
				{
					//download the batch errors for this job
					downloadBatchErrors(batchUploadResponse, batchStatusResponse, batchReaderAdapter, ownerName, true);
					
					// create a new save result
					SaveResults saveResults = new SaveResults();
					saveResults.addFailedItems(ItemType.INDICATOR, batchStatusResponse.getItem().getErrorCount());
					return saveResults;
				}
				else
				{
					// this should never happen
					throw new IllegalStateException();
				}
			}
			else
			{
				//:TODO: is this how we want to handle this situation?
				return new SaveResults();
			}
		}
		else
		{
			throw new SaveItemFailedException(batchUploadResponse.getResponse().getMessage());
		}
	}
	
	protected byte[] downloadBatchErrors(
		final BatchWriter.BatchUploadResponse batchUploadResponse,
		final ApiEntitySingleResponse<BatchStatus, BatchStatusResponseData> batchStatusResponse,
		final BatchReaderAdapter<Indicator> batchReaderAdapter,
		final String ownerName,
		final boolean allowRetry)
	{
		// check to see if there are errors
		if (batchStatusResponse.getItem().getErrorCount() > 0)
		{
			try
			{
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				batchReaderAdapter.downloadErrors(batchUploadResponse.getBatchID(), ownerName, baos);
				return baos.toByteArray();
			}
			catch (HttpResourceNotFoundException e)
			{
				//check to see if a retry is allowed
				if (allowRetry)
				{
					try
					{
						//sleep for 5 seconds before trying again
						logger.info("Batch Errors not yet available. Waiting 5 seconds and trying again.");
						Thread.sleep(5000);
					}
					catch (InterruptedException e1)
					{
						logger.warn(e1.getMessage(), e1);
					}
					
					return downloadBatchErrors(batchUploadResponse, batchStatusResponse, batchReaderAdapter, ownerName, false);
				}
				else
				{
					logger.warn(e.getMessage(), e);
					return null;
				}
			}
			catch (FailedResponseException e)
			{
				logger.warn(e.getMessage(), e);
				return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	protected abstract BatchReaderAdapter<com.threatconnect.sdk.server.entity.Indicator> createReaderAdapter();
	
	public class BatchUploadResponse
	{
		private final Integer batchID;
		private final ApiEntitySingleResponse<?, ?> response;
		
		public BatchUploadResponse(final Integer batchID, final ApiEntitySingleResponse<?, ?> response)
		{
			this.batchID = batchID;
			this.response = response;
		}
		
		public Integer getBatchID()
		{
			return batchID;
		}
		
		public ApiEntitySingleResponse<?, ?> getResponse()
		{
			return response;
		}
	}
}
