package com.threatconnect.plugin.pkg.mojo;

import com.threatconnect.plugin.pkg.Profile;
import com.threatconnect.plugin.pkg.ZipUtil;
import com.threatconnect.sdk.addons.util.config.install.InvalidInstallJsonFileException;
import com.threatconnect.sdk.addons.util.config.install.InstallJson;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPackageMojo extends AbstractMojo
{
	public static final Pattern PATTERN_INSTALL_JSON = Pattern.compile("^(?:(.*)\\.)?install\\.json$");
	public static final String TC_APP_FILE_EXTENSION = "zip";
	public static final String TC_BUNDLED_FILE_EXTENSION = "bundle.zip";
	
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
	
	/**
	 * The version of the app
	 */
	@Parameter(defaultValue = "${project.version}", required = true)
	private String version;
	
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		getLog().info("Building ThreatConnect App file");
		
		try
		{
			// retrieve the list of profiles
			List<Profile> profiles = getProfiles();
			List<File> packagedApps = new ArrayList<File>();
			
			// check to see if there are any profiles
			if (!profiles.isEmpty())
			{
				// for each of the profiles
				for (Profile profile : profiles)
				{
					// package an app profile
					packagedApps.add(packageProfile(profile));
				}
				
				//check to see if there are multiple profiles
				if (profiles.size() > 1)
				{
					//build a bundled archive of all the apps
					buildBundledArchive(packagedApps);
				}
			}
			else
			{
				// package a legacy app (no install.json file)
				packageLegacy();
			}
		}
		catch (InvalidInstallJsonFileException | IOException e)
		{
			throw new MojoFailureException(e.getMessage(), e);
		}
	}
	
	/**
	 * Given a list of packed app files, this builds a bundled archive containing all of them
	 *
	 * @param packagedApps
	 */
	protected void buildBundledArchive(final List<File> packagedApps) throws IOException
	{
		File bundledFile = new File(getOutputDirectory() + "/" + getAppName() + "." + TC_BUNDLED_FILE_EXTENSION);
		getLog().info("Packaging Bundle " + bundledFile.getName());
		ZipUtil.zipFiles(packagedApps, bundledFile.getAbsolutePath());
	}
	
	/**
	 * Packages a profile. A profile is determined by an install.json file. If multiple install.json
	 * files are found, they are each built using their profile name. Otherwise, if only an
	 * install.json file is found, the default settings are used.
	 *
	 * @param profile
	 * @throws IOException
	 */
	protected File packageProfile(final Profile profile) throws IOException
	{
		// determine what this app name will be
		final String appName = determineAppName(profile);
		
		getLog().info("Packaging Profile " + appName);
		
		File explodedDir = getExplodedDir(appName);
		explodedDir.mkdirs();
		
		// copy the install.json file
		File installJsonDestination = new File(explodedDir.getAbsolutePath() + File.separator + "install.json");
		FileUtils.copyFile(profile.getInstallFile(), installJsonDestination);
		
		// write the rest of the app contents out to the target folder
		writeAppContentsToDirectory(explodedDir);
		
		// zip up the app and return the file of the packaged app
		return ZipUtil.zipFolder(explodedDir, TC_APP_FILE_EXTENSION);
	}
	
	/**
	 * Packages a legacy app which consists of an install.conf file.
	 *
	 * @throws IOException
	 */
	protected void packageLegacy() throws IOException
	{
		File explodedDir = getExplodedDir(determineAppName(null));
		explodedDir.mkdirs();
		
		// copy the install conf file if it exists
		copyFileToDirectoryIfExists(getInstallConfFile(), explodedDir);
		
		// write the rest of the app contents out to the target folder
		writeAppContentsToDirectory(explodedDir);
		
		// zip up the app
		ZipUtil.zipFolder(explodedDir, TC_APP_FILE_EXTENSION);
	}
	
	/**
	 * Given a profile, the name of the app is determined here. First, if the install.json file has
	 * an applicationName and programVersion attribute set, those are used. If not, the prefix of
	 * the install.json file is used if it exists, otherwise the default app name is used.
	 *
	 * @param profile
	 * @return
	 */
	protected String determineAppName(final Profile profile)
	{
		String appName;
		
		// first check to see if the profile is not null
		if (null != profile)
		{
			// retrieve the application name and program version from the install.json file
			final String applicationName = profile.getInstallJson().getApplicationName();
			final String programVersion = profile.getInstallJson().getProgramVersion();
			
			// make sure that both the application name and program version are valid
			if (null != applicationName && !applicationName.trim().isEmpty() && null != programVersion
				&& !programVersion.trim().isEmpty())
			{
				appName = applicationName + "_v" + programVersion;
			}
			else
			{
				// check to see if a valid profile name exists
				if (null != profile.getProfileName() && !profile.getProfileName().trim().isEmpty())
				{
					appName = profile.getProfileName();
					
					// check to see if the app name needs the version
					if (!appName.endsWith(getVersion()))
					{
						appName = appName + "_v" + getVersion();
					}
				}
				// there is a profile, but it does not contain a valid name
				else
				{
					appName = getAppName();
				}
			}
		}
		// there is not profile object
		else
		{
			appName = getAppName();
		}
		
		return appName;
	}
	
	/**
	 * Returns the list of profiles for this app packager
	 *
	 * @return
	 */
	protected List<Profile> getProfiles() throws InvalidInstallJsonFileException
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
					
					// create the install json object
					InstallJson installJson = new InstallJson(file);
					
					// create a new profile
					Profile profile = new Profile(profileName, file, installJson);
					profiles.add(profile);
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
	
	public String getVersion()
	{
		return version;
	}
	
	/**
	 * Called when the app packager is ready to begin writing the files needed for building an app
	 *
	 * @param targetDirectory
	 * @throws IOException
	 */
	protected abstract void writeAppContentsToDirectory(final File targetDirectory) throws IOException;
}
