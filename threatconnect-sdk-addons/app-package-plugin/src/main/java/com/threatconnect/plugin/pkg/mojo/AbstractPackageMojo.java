package com.threatconnect.plugin.pkg.mojo;

import com.threatconnect.app.addons.util.config.InvalidCsvFileException;
import com.threatconnect.app.addons.util.config.InvalidJsonFileException;
import com.threatconnect.app.addons.util.config.attribute.AttributeReaderUtil;
import com.threatconnect.app.addons.util.config.install.Feed;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.InstallUtil;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.plugin.pkg.PackageFileFilter;
import com.threatconnect.plugin.pkg.Profile;
import com.threatconnect.plugin.pkg.ZipUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPackageMojo extends AbstractMojo
{
	private static final Logger logger = LoggerFactory.getLogger(AbstractPackageMojo.class);
	
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
	
	@Override
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
		catch (InvalidJsonFileException | ValidationException | IOException | InvalidCsvFileException e)
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
		File bundledFile =
			new File(getOutputDirectory() + File.separator + getAppName() + "." + TC_BUNDLED_FILE_EXTENSION);
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
	protected File packageProfile(final Profile profile)
		throws IOException, ValidationException, InvalidCsvFileException
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
		
		//validate that all of the files referenced in the install.json file exist
		validateReferencedFiles(explodedDir, profile);
		
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
			final String applicationName = profile.getInstall().getApplicationName();
			final String programVersion = profile.getInstall().getProgramVersion();
			
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
	protected List<Profile> getProfiles() throws InvalidJsonFileException, IOException, ValidationException
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
					Install install = InstallUtil.load(file);
					
					// create a new profile
					Profile profile = new Profile(profileName, file, install);
					profiles.add(profile);
				}
			}
		}
		
		return profiles;
	}
	
	protected void validateReferencedFiles(File explodedDir, final Profile profile)
		throws ValidationException, InvalidCsvFileException
	{
		//for each of the feeds in the install object
		for (Feed feed : profile.getInstall().getFeeds())
		{
			//check to see if there is an attribute file
			if (null != feed.getAttributesFile())
			{
				//make sure this file exists or throw an exception
				File file = new File(explodedDir.getAbsolutePath() + File.separator + feed.getAttributesFile());
				if (!file.exists())
				{
					throw new ValidationException(generateReferencedFileMissingMessage(feed.getAttributesFile()));
				}
				else
				{
					//load the attributes file and validate it
					AttributeReaderUtil.read(file);
				}
			}
			
			//check to see if there is a job file
			if (null != feed.getJobFile())
			{
				//make sure this file exists or throw an exception
				File file = new File(explodedDir.getAbsolutePath() + File.separator + feed.getJobFile());
				if (!file.exists())
				{
					throw new ValidationException(generateReferencedFileMissingMessage(feed.getJobFile()));
				}
			}
		}
	}
	
	protected String generateReferencedFileMissingMessage(final String fileName)
	{
		return "Referenced file \"" + fileName + " does not exist. Make sure it is in the correct directory.";
	}
	
	protected File getInstallConfFile()
	{
		return new File(baseDirectory + File.separator + "install.conf");
	}
	
	protected File getExplodedDir(final String appName)
	{
		return new File(getOutputDirectory() + File.separator + appName);
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
				for (File file : source.listFiles(createPackageFileFilter().createFilenameFilter()))
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
				copyFileToDirectory(source, destinationDirectory);
			}
		}
	}
	
	protected PackageFileFilter createPackageFileFilter()
	{
		return new PackageFileFilter();
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
	
	protected final void copyFileToDirectory(final File source, final File destinationDirectory)
		throws IOException
	{
		// copy this file to the destination directory
		logger.info("\tAdding file \"{}\" to package.", source.getName());
		FileUtils.copyFileToDirectory(source, destinationDirectory);
	}
	
	/**
	 * Called when the app packager is ready to begin writing the files needed for building an app
	 *
	 * @param targetDirectory
	 * @throws IOException
	 */
	protected void writeAppContentsToDirectory(File targetDirectory) throws IOException
	{
		// retrieve the base directory folder
		File baseDirectory = new File(getBaseDirectory());
		File outputDirectory = new File(getOutputDirectory());
		
		// loop through each of the files
		for (File child : baseDirectory.listFiles(createPackageFileFilter().createFilenameFilter()))
		{
			// make sure that this file is not hidden and that it is not the output folder
			if (!child.isHidden() && !child.equals(outputDirectory))
			{
				// copy this directory to the target folder
				final File target;
				
				//check to see if this child is a directory
				if (child.isDirectory())
				{
					target = new File(targetDirectory.getAbsolutePath() + File.separator + child.getName());
				}
				else
				{
					target = targetDirectory;
				}
				
				copyFileToDirectoryIfExists(child, target);
			}
		}
	}
}
