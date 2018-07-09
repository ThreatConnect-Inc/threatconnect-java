package com.threatconnect.sdk.parserapp.service.writer;

import com.threatconnect.sdk.client.writer.ReportWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.model.Report;
import com.threatconnect.sdk.parserapp.service.save.SaveItemFailedException;
import com.threatconnect.sdk.server.entity.Group.Type;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;
import com.threatconnect.sdk.util.UploadMethodType;
import org.apache.commons.io.FileUtils;

import java.io.IOException;

public class ReportWriter extends GroupWriter<Report, com.threatconnect.sdk.server.entity.Report>
{
	public static final String SUCCESS = "Success";
	
	public ReportWriter(Connection connection, Report source)
	{
		super(connection, source, com.threatconnect.sdk.server.entity.Report.class, Type.Report);
	}
	
	@Override
	public com.threatconnect.sdk.server.entity.Report saveGroup(String ownerName)
		throws SaveItemFailedException, IOException
	{
		// first, call the super class' save method
		com.threatconnect.sdk.server.entity.Report report = super.saveGroup(ownerName);
		
		// check to see if there is a file
		if (null != groupSource.getFile() && groupSource.getFile().exists())
		{
			// create the report writer adapter and upload the file
			ReportWriterAdapter reportWriterAdapter = createWriterAdapter();
			ApiEntitySingleResponse<?, ?> uploadResponse;
			
			logger.debug("Report \"{}\" Status: {}", report.getId(), report.getStatus());
			
			// check to see if this report's file is in a success state
			if (SUCCESS.equals(report.getStatus()))
			{
				// a file already exists and updating it requires PUT
				uploadResponse = reportWriterAdapter.uploadFile(getSavedGroupID(), groupSource.getFile(), ownerName,
					UploadMethodType.PUT);
			}
			else
			{
				// upload a new file using POST
				uploadResponse = reportWriterAdapter.uploadFile(getSavedGroupID(), groupSource.getFile(), ownerName,
					UploadMethodType.POST);
			}
			
			// check to see if this was not successful
			if (!uploadResponse.isSuccess())
			{
				logger.warn("Failed to upload file \"{}\" of size {} for group id: {}",
					groupSource.getFile().getAbsolutePath(),
					FileUtils.byteCountToDisplaySize(groupSource.getFile().length()), getSavedGroupID());
				logger.warn(uploadResponse.getMessage());
			}
		}
		
		return report;
	}
	
	@Override
	protected ReportWriterAdapter createWriterAdapter()
	{
		return WriterAdapterFactory.createReportWriter(connection);
	}
}
