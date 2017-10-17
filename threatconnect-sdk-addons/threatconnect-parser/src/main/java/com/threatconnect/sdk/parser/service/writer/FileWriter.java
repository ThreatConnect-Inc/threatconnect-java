package com.threatconnect.sdk.parser.service.writer;

import com.threatconnect.sdk.client.reader.FileIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.writer.FileIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.FileOccurrence;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Indicator.Type;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class FileWriter extends TypedIndicatorWriter<File, com.threatconnect.sdk.server.entity.File>
{
	public FileWriter(final Connection connection, final File file)
	{
		super(connection, file, com.threatconnect.sdk.server.entity.File.class, Type.File);
	}
	
	public com.threatconnect.sdk.server.entity.File saveIndicator(final String ownerName,
		final boolean forceSaveIndicator, final boolean saveAttributes, final boolean saveTags)
		throws SaveItemFailedException, IOException
	{
		com.threatconnect.sdk.server.entity.File file =
			super.saveIndicator(ownerName, forceSaveIndicator, saveAttributes, saveTags);
		saveFileOccurrences(ownerName);
		return file;
	}
	
	@Override
	public com.threatconnect.sdk.server.entity.File saveIndicator(String ownerName)
		throws SaveItemFailedException, IOException
	{
		// first, call the super class' save method
		com.threatconnect.sdk.server.entity.File file = super.saveIndicator(ownerName);
		saveFileOccurrences(ownerName);
		return file;
	}
	
	private void saveFileOccurrences(final String ownerName) throws IOException
	{
		// create a writer adapter
		FileIndicatorWriterAdapter writer = createWriterAdapter();
		
		// check to see if there are file occurrences
		if (!indicatorSource.getFileOccurrences().isEmpty())
		{
			// holds the map for file name to file occurrence object
			Map<String, com.threatconnect.sdk.server.entity.FileOccurrence> existingFileOccurrences =
				retrieveExistingFileOccurrences(buildID(), ownerName);
			
			// for each of the file occurrences for this file
			for (FileOccurrence fileOccurrence : indicatorSource.getFileOccurrences())
			{
				// check to see if this file occurrence already exists
				if (existingFileOccurrences.containsKey(fileOccurrence.getFileName()))
				{
					// make sure the new date is not null
					if (null != fileOccurrence.getDate())
					{
						// retrieve the existing file occurrence to see if it needs to be updated
						com.threatconnect.sdk.server.entity.FileOccurrence existingFileOccurrence =
							existingFileOccurrences.get(fileOccurrence.getFileName());
						
						// check to see if the new date is before the existing date
						if (fileOccurrence.getDate().before(existingFileOccurrence.getDate()))
						{
							try
							{
								// the existing file needs to be updated with the new date
								existingFileOccurrence.setDate(fileOccurrence.getDate());
								writer.updateFileOccurrence(buildID(), existingFileOccurrence, ownerName);
							}
							catch (FailedResponseException | IOException e)
							{
								// something went wrong updating the file occurrence so just log it
								// for the user to review and move on
								logger.warn(e.getMessage(), e);
							}
						}
					}
				}
				// create a new file occurrence for this file hash
				else
				{
					// map the file occurrence object
					com.threatconnect.sdk.server.entity.FileOccurrence fo =
						mapper.map(fileOccurrence, com.threatconnect.sdk.server.entity.FileOccurrence.class);
					
					// write the file occurrence for this file
					writer.createFileOccurrence(buildID(), fo);
				}
			}
		}
	}
	
	@Override
	protected FileIndicatorWriterAdapter createWriterAdapter()
	{
		return WriterAdapterFactory.createFileIndicatorWriter(connection);
	}
	
	@Override
	protected FileIndicatorReaderAdapter createReaderAdapter()
	{
		return ReaderAdapterFactory.createFileIndicatorReader(connection);
	}
	
	@Override
	protected String buildID()
	{
		// check to see if this file has an md5 hash
		if (null != indicatorSource.getMd5())
		{
			return indicatorSource.getMd5();
		}
		// check to see if this file has a sha1 hash
		else if (null != indicatorSource.getSha1())
		{
			return indicatorSource.getSha1();
		}
		// check to see if this file has a sha256 hash
		else if (null != indicatorSource.getSha256())
		{
			return indicatorSource.getSha256();
		}
		else
		{
			// no suitable id for this file
			return null;
		}
	}
	
	/**
	 * Retrieves the existing file occurrences if any exist and enters them into a hash map using the file name as the
	 * key
	 *
	 * @param hash
	 * @param ownerName
	 * @return
	 * @throws FailedResponseException
	 * @throws IOException
	 */
	private Map<String, com.threatconnect.sdk.server.entity.FileOccurrence> retrieveExistingFileOccurrences(
		final String hash, final String ownerName)
	{
		// holds the map for file name to file occurrence object
		Map<String, com.threatconnect.sdk.server.entity.FileOccurrence> existingFileOccurrences =
			new HashMap<String, com.threatconnect.sdk.server.entity.FileOccurrence>();
		
		try
		{
			// create a new reader adapter
			FileIndicatorReaderAdapter reader = createReaderAdapter();
			
			// check to see if there are any existing file occurrences for this file
			IterableResponse<com.threatconnect.sdk.server.entity.FileOccurrence> fileOccurrenceResponse =
				reader.getFileOccurrences(hash, ownerName);
			
			// for each of the file occurrences
			for (com.threatconnect.sdk.server.entity.FileOccurrence fileOccurrence : fileOccurrenceResponse)
			{
				// add this file occurrence to the map
				existingFileOccurrences.put(fileOccurrence.getFileName(), fileOccurrence);
			}
		}
		
		catch (FailedResponseException | IOException e)
		{
			logger.warn(e.getMessage(), e);
		}
		
		return existingFileOccurrences;
	}
}
