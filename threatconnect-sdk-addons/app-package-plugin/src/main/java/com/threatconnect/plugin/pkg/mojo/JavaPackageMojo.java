package com.threatconnect.plugin.pkg.mojo;

import com.threatconnect.app.addons.util.config.Feature;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.InstallUtil;
import com.threatconnect.app.addons.util.config.install.RunLevelType;
import com.threatconnect.app.addons.util.config.validation.InstallValidator;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.services.api.ApiService;
import com.threatconnect.app.services.api.mapping.ApiMapper;
import com.threatconnect.app.services.api.mapping.ApiMapping;
import com.threatconnect.plugin.pkg.JavaPackageFileFilter;
import com.threatconnect.plugin.pkg.PackageFileFilter;
import com.threatconnect.plugin.pkg.Profile;
import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

@Mojo(name = "java-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class JavaPackageMojo extends AbstractAppPackageMojo
{
	public static final String APP_MAIN_CLASSNAME = "com.threatconnect.sdk.app.AppMain";
	public static final String SERVICE_MAIN_CLASSNAME = "com.threatconnect.sdk.app.service.ServiceMain";
	
	/**
	 * Classifier to add to the generated App. If given, the artifact will be an attachment instead. The classifier will
	 * not be applied to the JAR file of the project - only to the WAR file.
	 */
	@Parameter
	private String classifier;
	
	@Parameter(defaultValue = "${project}", readonly = true, required = true)
	private MavenProject project;
	
	public JavaPackageMojo()
	{
	
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
		throws IOException
	{
		//retrieve the install profile
		Install install = profile.getSource();
		
		//check to see if this is a not service app (service apps do not support secure or file params at this time)
		if (!install.isService())
		{
			//add the features that this sdk supports
			install.getFeatures().add(Feature.SECURE_PARAMS);
		}
		
		//set the sdk version
		install.setSdkVersion(loadAppPackagerVersion());
		
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
	
	@Override
	protected Install loadFile(final File file) throws IOException, ValidationException
	{
		return InstallUtil.load(file, new InstallValidator()
			{
				@Override
				public void validate(final Install object) throws ValidationException
				{
					//check to see if the program main is null
					if (null == object.getProgramMain())
					{
						//check to see if this is a service app
						if (object.isService())
						{
							//use the service main launch this service
							object.setProgramMain(SERVICE_MAIN_CLASSNAME);
						}
						else
						{
							//use the default app main to launch this app
							object.setProgramMain(APP_MAIN_CLASSNAME);
						}
					}
					
					super.validate(object);
					
					if (RunLevelType.ApiService == object.getRuntimeLevel())
					{
						loadAndValidateApiServiceClass(object.getMainAppClass());
					}
				}
			}
		);
	}
	
	private void loadAndValidateApiServiceClass(final String mainAppClass) throws ValidationException
	{
		//validate the program language
		if (null == mainAppClass)
		{
			throw new ValidationException("mainAppClass is not defined.");
		}
		
		try
		{
			List<String> runtimeClasspathElements = project.getRuntimeClasspathElements();
			URL[] runtimeUrls = new URL[runtimeClasspathElements.size()];
			for (int i = 0; i < runtimeClasspathElements.size(); i++)
			{
				String element = runtimeClasspathElements.get(i);
				runtimeUrls[i] = new File(element).toURI().toURL();
			}
			URLClassLoader newLoader = new URLClassLoader(runtimeUrls, Thread.currentThread().getContextClassLoader());
			
			//retrieve the main app class
			final Class<? extends ApiService> apiServiceClass = (Class<? extends ApiService>) newLoader.loadClass(mainAppClass);
			
			if (!ApiService.class.isAssignableFrom(apiServiceClass))
			{
				throw new ValidationException(mainAppClass + " must extend " + ApiService.class.getName());
			}
			
			//retrieve all of the service items from the main class
			ApiMapper apiMapper = new ApiMapper(apiServiceClass);
			if (apiMapper.getServiceItems().isEmpty())
			{
				throw new ValidationException(
					mainAppClass + " does not define any api endpoints. Please add at least one @" + ApiMapping.class.getName()
						+ " annotated method.");
			}
		}
		catch (ClassNotFoundException e)
		{
			throw new ValidationException("Unable to load class: " + mainAppClass);
		}
		catch (ClassCastException | MalformedURLException | DependencyResolutionRequiredException e)
		{
			throw new RuntimeException(e);
		}
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
