package com.threatconnect.app.addons.util.config.install;

import java.util.ArrayList;
import java.util.List;

public class Install
{
	private String applicationName;
	private String displayName;
	private String programVersion;
	private String programLanguage;
	private String programMain;
	private String languageVersion;
	private String listDelimiter;
	private String runtimeLevel;
	
	private Playbook playbook;
	private final List<Param> params;
	private final List<Feed> feeds;
	
	public Install()
	{
		this.params = new ArrayList<Param>();
		this.feeds = new ArrayList<Feed>();
	}
	
	public String getApplicationName()
	{
		return applicationName;
	}
	
	public void setApplicationName(final String applicationName)
	{
		this.applicationName = applicationName;
	}
	
	public String getDisplayName()
	{
		return displayName;
	}
	
	public void setDisplayName(final String displayName)
	{
		this.displayName = displayName;
	}
	
	public String getProgramVersion()
	{
		return programVersion;
	}
	
	public void setProgramVersion(final String programVersion)
	{
		this.programVersion = programVersion;
	}
	
	public String getProgramLanguage()
	{
		return programLanguage;
	}
	
	public void setProgramLanguage(final String programLanguage)
	{
		this.programLanguage = programLanguage;
	}
	
	public String getProgramMain()
	{
		return programMain;
	}
	
	public void setProgramMain(final String programMain)
	{
		this.programMain = programMain;
	}
	
	public String getLanguageVersion()
	{
		return languageVersion;
	}
	
	public void setLanguageVersion(final String languageVersion)
	{
		this.languageVersion = languageVersion;
	}
	
	public String getListDelimiter()
	{
		return listDelimiter;
	}
	
	public void setListDelimiter(final String listDelimiter)
	{
		this.listDelimiter = listDelimiter;
	}
	
	public String getRuntimeLevel()
	{
		return runtimeLevel;
	}
	
	public void setRuntimeLevel(final String runtimeLevel)
	{
		this.runtimeLevel = runtimeLevel;
	}
	
	public Playbook getPlaybook()
	{
		return playbook;
	}
	
	public void setPlaybook(final Playbook playbook)
	{
		this.playbook = playbook;
	}
	
	public List<Param> getParams()
	{
		return params;
	}
	
	public List<Feed> getFeeds()
	{
		return feeds;
	}
	
	public List<Param> getPlaybookParams()
	{
		//holds the list of playbook params
		List<Param> playbookParams = new ArrayList<Param>();
		
		//for each of the playbook params
		for (Param param : getParams())
		{
			if (null != param.getPlaybookDataType() && !param.getPlaybookDataType().isEmpty())
			{
				//add this param to the playbook param list
				playbookParams.add(param);
			}
		}
		
		return playbookParams;
	}
	
	public boolean isPlaybookApp()
	{
		return !getPlaybookParams().isEmpty();
	}
}
