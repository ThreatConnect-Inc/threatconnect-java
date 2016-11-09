package com.threatconnect.plugin.pkg;

import com.threatconnect.app.addons.util.config.install.InstallJson;

import java.io.File;


public class Profile
{
	private final String profileName;
	private final File installFile;
	private final InstallJson installJson;
	
	public Profile(final String profileName, final File installFile, final InstallJson installJson)
	{
		this.profileName = profileName;
		this.installFile = installFile;
		this.installJson = installJson;
	}
	
	public String getProfileName()
	{
		return profileName;
	}
	
	public File getInstallFile()
	{
		return installFile;
	}
	
	public InstallJson getInstallJson()
	{
		return installJson;
	}
}
