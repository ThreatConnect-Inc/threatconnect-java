package com.threatconnect.plugin.pkg;

import java.io.File;
import java.io.IOException;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.codehaus.plexus.util.FileUtils;

@Mojo(name = "app-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class AppPackageMojo extends AbstractMojo
{
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
	 * Classifier to add to the generated App. If given, the artifact will be an attachment instead.
	 * The classifier will not be applied to the JAR file of the project - only to the WAR file.
	 */
	@Parameter
	private String classifier;
	
	public void execute() throws MojoExecutionException, MojoFailureException
	{
		getLog().info("Building ThreatConnect App file");
		
		File explodedDir = getExplodedDir();
		File log4jDir = new File(explodedDir.getAbsolutePath() + "/src/main/resources");
		explodedDir.mkdirs();
		log4jDir.mkdirs();
		
		try
		{
			// copy the files into the directory
			FileUtils.copyFileToDirectory(getSourceJarFile(), explodedDir);
			
			// copy the install conf file if it exists
			copyFileToDirectoryIfExists(getInstallConfFile(), explodedDir);
			
			// copy the install json file if it exists
			copyFileToDirectoryIfExists(getInstallJsonFile(), explodedDir);
			
			// copy the attributes json file if it exists
			copyFileToDirectoryIfExists(getAttributesCsvFile(), explodedDir);
			
			// copy all of the files in the include folder
			copyFileToDirectoryIfExists(getIncludeFolder(), explodedDir);
			
			// zip up the app
			ZipUtil.zipFolder(explodedDir);
		}
		catch (IOException e)
		{
			throw new MojoFailureException(e.getMessage(), e);
		}
	}
	
	protected File getSourceJarFile()
	{
		return getTargetFile(new File(getOutputDirectory()), getAppName(), getClassifier(), "jar");
	}
	
	protected File getInstallConfFile()
	{
		return new File(baseDirectory + "/install.conf");
	}
	
	protected File getAttributesCsvFile()
	{
		return new File(baseDirectory + "/attributes.csv");
	}
	
	protected File getIncludeFolder()
	{
		return new File(baseDirectory + "/include");
	}
	
	/**
	 * @return the install.json {@link File}
	 */
	protected File getInstallJsonFile()
	{
		return new File(baseDirectory + "/install.json");
	}
	
	protected File getExplodedDir()
	{
		return new File(getOutputDirectory() + "/" + getAppName());
	}
	
	protected String getFinalNameAndClassifier(String finalName, String classifier)
	{
		if (classifier == null)
		{
			classifier = "";
		}
		else if (classifier.trim().length() > 0 && !classifier.startsWith("-"))
		{
			classifier = "-" + classifier;
		}
		
		return finalName + classifier;
	}
	
	/**
	 * @param basedir
	 * The basedir
	 * @param finalName
	 * The finalName
	 * @param classifier
	 * The classifier.
	 * @param type
	 * The type.
	 * @return {@link File}
	 */
	protected File getTargetFile(File basedir, String finalName, String classifier, String type)
	{
		return new File(basedir, getFinalNameAndClassifier(finalName, classifier) + "." + type);
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
	
	public String getClassifier()
	{
		return classifier;
	}
}
