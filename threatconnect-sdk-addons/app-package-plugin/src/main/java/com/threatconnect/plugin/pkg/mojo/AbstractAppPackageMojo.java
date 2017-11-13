package com.threatconnect.plugin.pkg.mojo;

import com.threatconnect.app.addons.util.config.InvalidFileException;
import com.threatconnect.app.addons.util.config.attribute.csv.AttributeTypeReaderUtil;
import com.threatconnect.app.addons.util.config.attribute.json.AttributeUtil;
import com.threatconnect.app.addons.util.config.install.Feed;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.InstallUtil;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.plugin.pkg.PackageFileFilter;
import com.threatconnect.plugin.pkg.Profile;
import com.threatconnect.plugin.pkg.ZipUtil;
import org.apache.commons.io.FilenameUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public abstract class AbstractAppPackageMojo extends AbstractPackageMojo<Install>
{
	private static final Logger logger = LoggerFactory.getLogger(AbstractAppPackageMojo.class);
	
	public static final Pattern PATTERN_INSTALL_JSON = Pattern.compile("^(?:(.*)\\.)?install\\.json$");
	public static final String TC_APP_FILE_EXTENSION = "zip";
	public static final String TC_BUNDLED_FILE_EXTENSION = "bundle.zip";
	
	public AbstractAppPackageMojo()
	{
		super(PATTERN_INSTALL_JSON, "install.json", TC_APP_FILE_EXTENSION);
	}
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		getLog().info("Building ThreatConnect App file");
		
		try
		{
			// retrieve the list of profiles
			List<Profile<Install>> profiles = getProfiles();
			List<File> packagedApps = new ArrayList<File>();
			
			// check to see if there are any profiles
			if (!profiles.isEmpty())
			{
				// for each of the profiles
				for (Profile<Install> profile : profiles)
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
		catch (InvalidFileException | ValidationException | IOException e)
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
	 * Given a profile, the name of the app is determined here. First, if the install.json file has an applicationName
	 * and programVersion attribute set, those are used. If not, the prefix of the install.json file is used if it
	 * exists, otherwise the default app name is used.
	 *
	 * @param profile
	 * @return
	 */
	@Override
	protected String determineAppName(final Profile<Install> profile)
	{
		String appName;
		
		// first check to see if the profile is not null
		if (null != profile)
		{
			// retrieve the application name and program version from the install.json file
			final String applicationName = profile.getSource().getApplicationName();
			final String programVersion = profile.getSource().getProgramVersion();
			
			// make sure that both the application name and program version are valid
			if (null != applicationName && !applicationName.trim().isEmpty() && null != programVersion
				&& !programVersion.trim().isEmpty())
			{
				appName = applicationName + "_v" + programVersion;
			}
			else
			{
				appName = super.determineAppName(profile);
			}
		}
		// there is not profile object
		else
		{
			appName = getAppName();
		}
		
		return appName;
	}
	
	@Override
	protected Install loadFile(final File file) throws IOException, ValidationException
	{
		return InstallUtil.load(file);
	}
	
	@Override
	protected void validateRequiredFiles(File explodedDir, final Profile<Install> profile)
		throws ValidationException
	{
		File readmeFile = getReadmeFile();
		if (!readmeFile.exists())
		{
			throw new ValidationException(generateRequiredFileMissingMessage(readmeFile.getName()));
		}
	}
	
	@Override
	protected void validateReferencedFiles(File explodedDir, final Profile<Install> profile)
		throws ValidationException, InvalidFileException, IOException
	{
		//for each of the feeds in the install object
		for (Feed feed : profile.getSource().getFeeds())
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
					//check to see if this is a json file
					if (FilenameUtils.isExtension(file.getName(), "json"))
					{
						AttributeUtil.load(file);
					}
					else if (FilenameUtils.isExtension(file.getName(), "csv"))
					{
						//load the attributes file and validate it
						AttributeTypeReaderUtil.read(file);
					}
					else
					{
						throw new InvalidFileException("Could not read attributes file \"" + feed.getAttributesFile()
							+ "\". File must end in .csv or .json");
					}
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
	
	protected File getInstallConfFile()
	{
		return new File(getBaseDirectory() + File.separator + "install.conf");
	}
	
	protected File getReadmeFile()
	{
		return new File(getBaseDirectory() + File.separator + "README.md");
	}
	
	protected PackageFileFilter createPackageFileFilter()
	{
		return new PackageFileFilter(getExclude());
	}
}
