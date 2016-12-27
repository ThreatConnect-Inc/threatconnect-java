package com.threatconnect.plugin.pkg;

import com.threatconnect.app.addons.util.config.install.Install;

import java.io.File;


public class Profile
{
	private final String profileName;
	private final File installFile;
	private final Install install;
	
	public Profile(final String profileName, final File installFile, final Install install)
	{
		this.profileName = profileName;
		this.installFile = installFile;
		this.install = install;
	}
	
	public String getProfileName()
	{
		return profileName;
	}
	
	public File getInstallFile()
	{
		return installFile;
	}
	
	public Install getInstall()
	{
		return install;
	}
}
