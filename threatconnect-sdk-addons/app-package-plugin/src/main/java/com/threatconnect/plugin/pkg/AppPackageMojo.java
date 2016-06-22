package com.threatconnect.plugin.pkg;

import java.io.File;
import java.io.IOException;
import java.util.regex.Pattern;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.codehaus.plexus.util.FileUtils;

@Mojo(name = "app-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class AppPackageMojo extends AbstractPackageMojo
{
	public static final Pattern PATTERN_INSTALL_JSON = Pattern.compile("^(?:(.*)\\.)?install\\.json$");
	
	/**
	 * Classifier to add to the generated App. If given, the artifact will be an attachment instead.
	 * The classifier will not be applied to the JAR file of the project - only to the WAR file.
	 */
	@Parameter
	private String classifier;
	
	@Override
	protected void writeAppContentsToDirectory(File targetDirectory) throws IOException
	{
		// copy the files into the directory
		FileUtils.copyFileToDirectory(getSourceJarFile(), targetDirectory);
		
		// copy the attributes json file if it exists
		copyFileToDirectoryIfExists(getAttributesCsvFile(), targetDirectory);
		
		// copy all of the files in the include folder
		copyFileToDirectoryIfExists(getIncludeFolder(), targetDirectory);
	}
	
	protected File getSourceJarFile()
	{
		return getTargetFile(new File(getOutputDirectory()), getAppName(), getClassifier(), "jar");
	}
	
	protected File getAttributesCsvFile()
	{
		return new File(getBaseDirectory() + "/attributes.csv");
	}
	
	protected File getIncludeFolder()
	{
		return new File(getBaseDirectory() + "/include");
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
	
	public String getClassifier()
	{
		return classifier;
	}
}
