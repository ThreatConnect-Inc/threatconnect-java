package com.threatconnect.plugin.pkg.mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.IOException;

@Mojo(name = "python-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class PythonPackageMojo extends AbstractPackageMojo
{
	
	@Override
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
