package com.threatconnect.app.addons.util.config.install;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Job
{
	private String programName;
	private String programVersion;
	private String jobName;

	private final List<JobParam> jobParams;
	
	public Job()
	{
		this.jobParams = new ArrayList<JobParam>();
	}
	
	public String getProgramName()
	{
		return programName;
	}
	
	public void setProgramName(final String programName)
	{
		this.programName = programName;
	}
	
	public String getProgramVersion()
	{
		return programVersion;
	}
	
	public void setProgramVersion(final String programVersion)
	{
		this.programVersion = programVersion;
	}
	
	public String getJobName()
	{
		return jobName;
	}
	
	public void setJobName(final String jobName)
	{
		this.jobName = jobName;
	}
	
	public List<JobParam> getJobParams()
	{
		return jobParams;
	}
}
