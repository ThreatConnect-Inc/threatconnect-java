package com.threatconnect.sdk.parser.service.writer;

import java.io.IOException;

import com.threatconnect.sdk.client.writer.FileIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.model.File;
import com.threatconnect.sdk.parser.model.FileOccurrence;
import com.threatconnect.sdk.parser.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Indicator.Type;

public class FileWriter extends IndicatorWriter<File, com.threatconnect.sdk.server.entity.File>
{
	public FileWriter(final Connection connection, final File file)
	{
		super(connection, file, com.threatconnect.sdk.server.entity.File.class, Type.File);
	}
	
	@Override
	public com.threatconnect.sdk.server.entity.File saveIndicator(String ownerName)
		throws SaveItemFailedException, IOException
	{
		// first, call the super class' save method
		com.threatconnect.sdk.server.entity.File file = super.saveIndicator(ownerName);
		
		// create a writer adapter
		FileIndicatorWriterAdapter writer = createWriterAdapter();
		
		// for each of the file occurrences for this file
		for (FileOccurrence fileOccurrence : indicatorSource.getFileOccurrences())
		{
			// map the file occurence object
			com.threatconnect.sdk.server.entity.FileOccurrence fo =
				mapper.map(fileOccurrence, com.threatconnect.sdk.server.entity.FileOccurrence.class);
				
			// write the file occurrence for this file
			writer.createFileOccurrence(buildID(), fo);
		}
		
		return file;
	}
	
	@Override
	protected FileIndicatorWriterAdapter createWriterAdapter()
	{
		return WriterAdapterFactory.createFileIndicatorWriter(connection);
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
}
