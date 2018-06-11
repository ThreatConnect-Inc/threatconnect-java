package com.threatconnect.plugin.pkg.mojo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.threatconnect.app.addons.util.config.Feature;
import com.threatconnect.app.addons.util.config.InvalidFileException;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.plugin.pkg.JavaPackageFileFilter;
import com.threatconnect.plugin.pkg.PackageFileFilter;
import com.threatconnect.plugin.pkg.Profile;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Mojo(name = "java-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class JavaPackageMojo extends AbstractAppPackageMojo
{
	private final Gson gson;
	
	/**
	 * Classifier to add to the generated App. If given, the artifact will be an attachment instead. The classifier will
	 * not be applied to the JAR file of the project - only to the WAR file.
	 */
	@Parameter
	private String classifier;
	
	public JavaPackageMojo()
	{
		//create a new gson object to write the install file
		this.gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	@Override
	protected PackageFileFilter createPackageFileFilter()
	{
		return new JavaPackageFileFilter(getExclude());
	}
	
	@Override
	protected void writeAppContentsToDirectory(final File targetDirectory, final Profile<Install> profile)
		throws IOException
	{
		super.writeAppContentsToDirectory(targetDirectory, profile);
		
		// copy the jar file into the directory
		copyFileToDirectory(getSourceJarFile(), targetDirectory);
	}
	
	@Override
	protected void writePrimaryInstallJson(final Profile<Install> profile, final File primaryJsonDestination)
		throws IOException, ValidationException, InvalidFileException
	{
		//retrieve the install profile
		Install install = profile.getSource();
		
		//add the features that this sdk supports
		install.getFeatures().add(Feature.SECURE_PARAMS);
		
		try (OutputStream outputStream = new FileOutputStream(primaryJsonDestination))
		{
			//write the install file
			outputStream.write(gson.toJson(install).getBytes());
		}
	}
	
	@Override
	protected String generateReferencedFileMissingMessage(final String fileName)
	{
		return super.generateReferencedFileMissingMessage(fileName)
			+ " Additional files must be added to the \"include\" directory?";
	}
	
	protected File getSourceJarFile()
	{
		return getTargetFile(new File(getOutputDirectory()), getAppName(), getClassifier(), "jar");
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
	 * @param basedir    The basedir
	 * @param finalName  The finalName
	 * @param classifier The classifier.
	 * @param type       The type.
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
