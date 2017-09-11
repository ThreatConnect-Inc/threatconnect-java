package com.threatconnect.plugin.pkg.mojo;

import com.threatconnect.app.addons.util.config.InvalidCsvFileException;
import com.threatconnect.app.addons.util.config.InvalidJsonFileException;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.plugin.pkg.PackageFileFilter;
import com.threatconnect.plugin.pkg.Profile;
import com.threatconnect.plugin.pkg.ZipUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractPackageMojo<T> extends AbstractMojo
{
	private static final Logger logger = LoggerFactory.getLogger(AbstractPackageMojo.class);
	
	public static Pattern REGEX_PACKAGE_VERSION_SUFFIX = Pattern.compile("_v[0-9]+\\.[0-9]+$");
	
	private final Pattern profilePattern;
	private final String primaryJsonFileName;
	private final String packageExtension;
	
	public AbstractPackageMojo(final Pattern profilePattern, final String primaryJsonFileName,
		final String packageExtension)
	{
		this.profilePattern = profilePattern;
		this.primaryJsonFileName = primaryJsonFileName;
		this.packageExtension = packageExtension;
	}
	
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
	
	@Parameter
	private String[] exclude;
	
	/**
	 * Packages a profile. A profile is determined by an install.json file. If multiple install.json
	 * files are found, they are each built using their profile name. Otherwise, if only an
	 * install.json file is found, the default settings are used.
	 *
	 * @param profile
	 * @throws IOException
	 */
	protected File packageProfile(final Profile<T> profile)
		throws IOException, ValidationException, InvalidCsvFileException
	{
		// determine what this app name will be
		final String appName = determineAppName(profile);
		
		getLog().info("Packaging Profile " + appName);
		
		File explodedDir = getExplodedDir(appName);
		explodedDir.mkdirs();
		
		// copy the primary json file
		File primaryJsonDestination = new File(explodedDir.getAbsolutePath() + File.separator + primaryJsonFileName);
		FileUtils.copyFile(profile.getSourceFile(), primaryJsonDestination);
		
		// write the rest of the app contents out to the target folder
		writeAppContentsToDirectory(explodedDir);
		
		//validate all of the required files are there
		validateRequiredFiles(explodedDir, profile);
		
		//validate that all of the files referenced in the install.json file exist
		validateReferencedFiles(explodedDir, profile);
		
		// zip up the app and return the file of the packaged app
		return ZipUtil.zipFolder(explodedDir, packageExtension);
	}
	
	/**
	 * Given a profile, the name of the app is determined here. First, if the install.json file has
	 * an applicationName and programVersion attribute set, those are used. If not, the prefix of
	 * the install.json file is used if it exists, otherwise the default app name is used.
	 *
	 * @param profile
	 * @return
	 */
	protected String determineAppName(final Profile<T> profile)
	{
		String appName;
		
		// first check to see if the profile is not null
		if (null != profile)
		{
			// check to see if a valid profile name exists
			if (null != profile.getProfileName() && !profile.getProfileName().trim().isEmpty())
			{
				appName = profile.getProfileName();
				
				// check to see if the app name needs the version
				if (!REGEX_PACKAGE_VERSION_SUFFIX.matcher(appName).find())
				{
					appName = appName + "_v" + getVersion();
				}
			}
			// check to see if the app name does not already contain the version at the end
			else if (!REGEX_PACKAGE_VERSION_SUFFIX.matcher(getAppName()).find())
			{
				appName = getAppName() + "_v" + getVersion();
			}
			else
			{
				appName = getAppName();
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
	protected List<Profile<T>> getProfiles() throws InvalidJsonFileException, IOException, ValidationException
	{
		// holds the list of install
		List<Profile<T>> profiles = new ArrayList<Profile<T>>();
		
		// retrieve the base directory file
		File root = new File(getBaseDirectory());
		
		// for each file in the root
		for (File file : root.listFiles())
		{
			// make sure this is a file (not a directory)
			if (file.isFile())
			{
				// retrieve the matcher for this filename
				Matcher matcher = profilePattern.matcher(file.getName());
				
				// make sure this is a valid profile file
				if (matcher.matches())
				{
					// retrieve the profile name for this profile file
					final String profileName = matcher.group(1);
					
					// create the json object
					T source = loadFile(file);
					
					// create a new profile
					Profile<T> profile = new Profile<T>(profileName, file, source);
					profiles.add(profile);
				}
			}
		}
		
		return profiles;
	}
	
	protected abstract T loadFile(final File file) throws IOException, ValidationException;
	
	protected abstract void validateRequiredFiles(File explodedDir, final Profile<T> profile)
		throws ValidationException;
	
	protected abstract void validateReferencedFiles(File explodedDir, final Profile<T> profile)
		throws ValidationException, InvalidCsvFileException;
	
	protected String generateRequiredFileMissingMessage(final String fileName)
	{
		return "Required file \"" + fileName + " does not exist. Make sure it is in the correct directory.";
	}
	
	protected String generateReferencedFileMissingMessage(final String fileName)
	{
		return "Referenced file \"" + fileName + " does not exist. Make sure it is in the correct directory.";
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
		return new PackageFileFilter(getExclude());
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
	
	public List<String> getExclude()
	{
		List<String> excludeList = new ArrayList<String>();
		
		if (null != exclude)
		{
			//add all of the custom excludes to the list
			excludeList.addAll(Arrays.asList(exclude));
		}
		
		return excludeList;
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
