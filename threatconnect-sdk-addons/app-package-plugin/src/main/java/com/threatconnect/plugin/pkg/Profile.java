package com.threatconnect.plugin.pkg;

import java.io.File;

public class Profile
{
	private final String profileName;
	private final File installFile;
	
	public Profile(final String profileName, final File installFile)
	{
		this.profileName = profileName;
		this.installFile = installFile;
	}
	
	public String getProfileName()
	{
		return profileName;
	}
	
	public File getInstallFile()
	{
		return installFile;
	}
}
