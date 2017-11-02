package com.threatconnect.plugin.pkg.mojo;

import com.threatconnect.app.addons.util.config.InvalidCsvFileException;
import com.threatconnect.app.addons.util.config.InvalidJsonFileException;
import com.threatconnect.app.addons.util.config.attribute.AttributeTypeReaderUtil;
import com.threatconnect.app.addons.util.config.install.Feed;
import com.threatconnect.app.addons.util.config.install.FeedUtil;
import com.threatconnect.app.addons.util.config.install.JobUtil;
import com.threatconnect.app.addons.util.config.validation.JobValidator;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.plugin.pkg.PackageFileFilter;
import com.threatconnect.plugin.pkg.Profile;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.regex.Pattern;

@Mojo(name = "feed-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class FeedPackageMojo extends AbstractPackageMojo<Feed>
{
	private static final Logger logger = LoggerFactory.getLogger(FeedPackageMojo.class);
	
	public static final Pattern PATTERN_FEED_JSON = Pattern.compile("^(?:(.*)\\.)?feed\\.json$");
	public static final String TC_FEED_FILE_EXTENSION = "tcf";
	
	public FeedPackageMojo()
	{
		super(PATTERN_FEED_JSON, "feed.json", TC_FEED_FILE_EXTENSION);
	}
	
	@Override
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		getLog().info("Building ThreatConnect Feed file");
		
		try
		{
			// retrieve the list of profiles
			List<Profile<Feed>> profiles = getProfiles();
			
			// check to see if there are any profiles
			if (!profiles.isEmpty())
			{
				// for each of the profiles
				for (Profile<Feed> profile : profiles)
				{
					// package an feed profile
					packageProfile(profile);
				}
			}
			else
			{
				throw new RuntimeException("No feeds were found");
			}
		}
		catch (InvalidJsonFileException | ValidationException | IOException | InvalidCsvFileException e)
		{
			throw new MojoFailureException(e.getMessage(), e);
		}
	}
	
	@Override
	protected Feed loadFile(final File file) throws IOException, ValidationException
	{
		return FeedUtil.load(file);
	}
	
	@Override
	protected void validateRequiredFiles(final File explodedDir, final Profile<Feed> profile) throws ValidationException
	{
	
	}
	
	@Override
	protected void validateReferencedFiles(File explodedDir, final Profile<Feed> profile)
		throws ValidationException, InvalidCsvFileException
	{
		Feed feed = profile.getSource();
		
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
				AttributeTypeReaderUtil.read(file);
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
			else
			{
				try
				{
					//validate the job file
					JobUtil.load(file, true, new JobValidator(true));
				}
				catch (IOException e)
				{
					throw new ValidationException(e.getMessage());
				}
			}
		}
	}
	
	protected PackageFileFilter createPackageFileFilter()
	{
		return new PackageFileFilter(getExclude());
	}
}
