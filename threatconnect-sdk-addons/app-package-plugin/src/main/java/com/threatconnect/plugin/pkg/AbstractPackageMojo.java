package com.threatconnect.plugin.pkg;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

public abstract class AbstractPackageMojo extends AbstractMojo
{
	public static final Pattern PATTERN_INSTALL_JSON = Pattern.compile("^(?:(.*)\\.)?install\\.json$");
	
	/**
	 * The base directory for the application
	 */
	@Parameter(defaultValue = "${basedir}", required = true)
	private String baseDirectory;
	
	/**
	 * The directory for the generated app.
	 */
	@Parameter(defaultValue = "${project.build.directory}", required = true)
	private String outputDirectory;
	
	/**
	 * The name of the generated app name.
	 */
	@Parameter(defaultValue = "${project.build.finalName}", required = true)
	private String appName;
	
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		getLog().info("Building ThreatConnect App file");
		
		// retrieve the list of profiles
		List<Profile> profiles = getProfiles();
		
		try
		{
			// check to see if there are any profiles
			if (!profiles.isEmpty())
			{
				// for each of the profiles
				for (Profile profile : profiles)
				{
					// package an app profile
					packageProfile(profile);
				}
			}
			else
			{
				// package a legacy app (no install.json file)
				packageLegacy();
			}
		}
		catch (IOException e)
		{
			throw new MojoFailureException(e.getMessage(), e);
		}
	}
	
	protected void packageProfile(final Profile profile) throws IOException
	{
		// determine what this app name will be
		final String appName = (null == profile.getProfileName() || profile.getProfileName().isEmpty()) ? getAppName()
			: profile.getProfileName();
			
		getLog().info("Packaging Profile " + appName);
		
		File explodedDir = getExplodedDir(appName);
		explodedDir.mkdirs();
		
		// copy the install.json file
		File installJsonDestination = new File(explodedDir.getAbsolutePath() + File.separator + "install.json");
		FileUtils.copyFile(profile.getInstallFile(), installJsonDestination);
		
		// write the rest of the app contents out to the target folder
		writeAppContentsToDirectory(explodedDir);
		
		// zip up the app
		ZipUtil.zipFolder(explodedDir);
	}
	
	protected void packageLegacy() throws IOException
	{
		File explodedDir = getExplodedDir(getAppName());
		explodedDir.mkdirs();
		
		// copy the install conf file if it exists
		copyFileToDirectoryIfExists(getInstallConfFile(), explodedDir);
		
		// write the rest of the app contents out to the target folder
		writeAppContentsToDirectory(explodedDir);
		
		// zip up the app
		ZipUtil.zipFolder(explodedDir);
	}
	
	protected abstract void writeAppContentsToDirectory(final File targetDirectory) throws IOException;
	
	/**
	 * Returns the list of profiles for this app packager
	 * 
	 * @return
	 */
	protected List<Profile> getProfiles()
	{
		// holds the list of install
		List<Profile> profiles = new ArrayList<Profile>();
		
		// retrieve the base directory file
		File root = new File(baseDirectory);
		
		// for each file in the root
		for (File file : root.listFiles())
		{
			// make sure this is a file (not a directory)
			if (file.isFile())
			{
				// retrieve the matcher for this filename
				Matcher matcher = PATTERN_INSTALL_JSON.matcher(file.getName());
				
				// make sure this is an install file
				if (matcher.matches())
				{
					// retrieve the profile name for this install file
					final String profileName = matcher.group(1);
					
					// create a new profile
					profiles.add(new Profile(profileName, file));
				}
			}
		}
		
		return profiles;
	}
	
	protected File getInstallConfFile()
	{
		return new File(baseDirectory + "/install.conf");
	}
	
	protected File getExplodedDir(final String appName)
	{
		return new File(getOutputDirectory() + "/" + appName);
	}
	
	/**
	 * Copies a file to a destination directory if the source file exists
	 * 
	 * @param source
	 * @param destinationDirectory
	 * @throws IOException
	 */
	protected void copyFileToDirectoryIfExists(final File source, final File destinationDirectory)
		throws IOException
	{
		// check to see if the source file exists
		if (source.exists())
		{
			// check to see if this is a directory
			if (source.isDirectory())
			{
				// for each of the files
				for (File file : source.listFiles())
				{
					// check to see if this file is a directory
					if (file.isDirectory())
					{
						// create the new destination folder
						final File destination =
							new File(destinationDirectory.getAbsoluteFile() + File.separator + file.getName());
							
						// recursively copy this file
						copyFileToDirectoryIfExists(file, destination);
					}
					else
					{
						// recursively copy this file
						copyFileToDirectoryIfExists(file, destinationDirectory);
					}
				}
			}
			else
			{
				// check to see if the destination directory does not exist
				if (!destinationDirectory.exists())
				{
					// make all the directories as needed
					destinationDirectory.mkdirs();
				}
				
				// copy this file to the destination directory
				FileUtils.copyFileToDirectory(source, destinationDirectory);
			}
		}
	}
	
	public String getOutputDirectory()
	{
		return outputDirectory;
	}
	
	public String getAppName()
	{
		return appName;
	}
	
	public String getBaseDirectory()
	{
		return baseDirectory;
	}
}
