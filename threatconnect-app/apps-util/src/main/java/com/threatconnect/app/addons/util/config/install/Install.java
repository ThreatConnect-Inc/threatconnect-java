package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.layout.Layout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Install
{
	private static final String DEFAULT_LIST_DELIMITER = "|";
	
	private String appId;
	private String programName;
	private String applicationName;
	private String displayName;
	private String programVersion;
	private String minServerVersion;
	private ProgramLanguageType programLanguage;
	private String programMain;
	private String mainAppClass;
	private String languageVersion;
	private String listDelimiter;
	private String programIcon;
	private String javaClasspath;
	private RunLevelType runtimeLevel;
	private boolean allowOnDemand;
	private boolean allowRunAsUser;
	private boolean apiUserTokenParam;
	private Integer tokenExpireOffsetMinutes;
	private Integer timeoutMinutes;
	private String note;
	private String sdkVersion;
	private Docker docker;
	
	private String displayPath;
	
	private Playbook playbook;
	private Layout layout;
	private final List<Param> params;
	private final List<Feed> feeds;
	private final List<String> repeatingMinutes;
	private final List<String> publishOutFiles;
	private final List<RuntimeContextType> runtimeContext;
	private final List<String> deprecatesApps;
	private final Set<String> features;
	private final Set<String> labels;
	
	public Install()
	{
		this.params = new ArrayList<Param>();
		this.feeds = new ArrayList<Feed>();
		this.repeatingMinutes = new ArrayList<String>();
		this.publishOutFiles = new ArrayList<String>();
		this.runtimeContext = new ArrayList<RuntimeContextType>();
		this.deprecatesApps = new ArrayList<>();
		this.features = new HashSet<String>();
		this.labels = new HashSet<String>();
		this.listDelimiter = DEFAULT_LIST_DELIMITER;
	}
	
	public String getAppId()
	{
		return appId;
	}
	
	public void setAppId(String appId)
	{
		this.appId = appId;
	}
	
	public String getProgramName()
	{
		return programName;
	}
	
	public void setProgramName(final String programName)
	{
		this.programName = programName;
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
	
	public String getMinServerVersion()
	{
		return minServerVersion;
	}
	
	public void setMinServerVersion(final String minServerVersion)
	{
		this.minServerVersion = minServerVersion;
	}
	
	public ProgramLanguageType getProgramLanguage()
	{
		return programLanguage;
	}
	
	public void setProgramLanguage(final ProgramLanguageType programLanguage)
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
	
	public String getMainAppClass()
	{
		return mainAppClass;
	}
	
	public void setMainAppClass(final String mainAppClass)
	{
		this.mainAppClass = mainAppClass;
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
	
	public String getProgramIcon()
	{
		return programIcon;
	}
	
	public void setProgramIcon(final String programIcon)
	{
		this.programIcon = programIcon;
	}
	
	public String getJavaClasspath()
	{
		return javaClasspath;
	}
	
	public void setJavaClasspath(final String javaClasspath)
	{
		this.javaClasspath = javaClasspath;
	}
	
	public RunLevelType getRuntimeLevel()
	{
		return runtimeLevel;
	}
	
	public void setRuntimeLevel(final RunLevelType runtimeLevel)
	{
		this.runtimeLevel = runtimeLevel;
	}
	
	public boolean isAllowOnDemand()
	{
		return allowOnDemand;
	}
	
	public void setAllowOnDemand(final boolean allowOnDemand)
	{
		this.allowOnDemand = allowOnDemand;
	}
	
	public boolean isAllowRunAsUser()
	{
		return allowRunAsUser;
	}
	
	public void setAllowRunAsUser(final boolean allowRunAsUser)
	{
		this.allowRunAsUser = allowRunAsUser;
	}
	
	public boolean isApiUserTokenParam()
	{
		return apiUserTokenParam;
	}
	
	public void setApiUserTokenParam(final boolean apiUserTokenParam)
	{
		this.apiUserTokenParam = apiUserTokenParam;
	}
	
	public Integer getTokenExpireOffsetMinutes()
	{
		return tokenExpireOffsetMinutes;
	}
	
	public void setTokenExpireOffsetMinutes(final Integer tokenExpireOffsetMinutes)
	{
		this.tokenExpireOffsetMinutes = tokenExpireOffsetMinutes;
	}
	
	public Integer getTimeoutMinutes()
	{
		return timeoutMinutes;
	}
	
	public void setTimeoutMinutes(final Integer timeoutMinutes)
	{
		this.timeoutMinutes = timeoutMinutes;
	}
	
	public String getNote()
	{
		return note;
	}
	
	public void setNote(final String note)
	{
		this.note = note;
	}
	
	public String getSdkVersion()
	{
		return sdkVersion;
	}
	
	public void setSdkVersion(final String sdkVersion)
	{
		this.sdkVersion = sdkVersion;
	}
	
	public Docker getDocker()
	{
		return docker;
	}
	
	public void setDocker(final Docker docker)
	{
		this.docker = docker;
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
	
	public List<String> getRepeatingMinutes()
	{
		return repeatingMinutes;
	}
	
	public List<String> getPublishOutFiles()
	{
		return publishOutFiles;
	}
	
	public List<RuntimeContextType> getRuntimeContext()
	{
		return runtimeContext;
	}
	
	public List<String> getDeprecatesApps()
	{
		return deprecatesApps;
	}
	
	public Set<String> getFeatures()
	{
		return features;
	}
	
	public Set<String> getLabels()
	{
		return labels;
	}
	
	public Layout getLayout()
	{
		return layout;
	}
	
	public void setLayout(final Layout layout)
	{
		this.layout = layout;
	}
	
	public String getDisplayPath()
	{
		return displayPath;
	}
	
	public void setDisplayPath(final String displayPath)
	{
		this.displayPath = displayPath;
	}
	
	public List<Param> getPlaybookParams()
	{
		//holds the list of playbook params
		List<Param> playbookParams = new ArrayList<Param>();
		
		//for each of the playbook params
		for (Param param : getParams())
		{
			if (param.isPlaybookParam())
			{
				//add this param to the playbook param list
				playbookParams.add(param);
			}
		}
		
		return playbookParams;
	}
	
	public boolean isPlaybookApp()
	{
		return getRuntimeLevel().equals(RunLevelType.Playbook);
	}
	
	public boolean isService()
	{
		return RunLevelType.ApiService == getRuntimeLevel() ||
			RunLevelType.TriggerService == getRuntimeLevel() ||
			RunLevelType.WebHookTriggerService == getRuntimeLevel();
	}
}
