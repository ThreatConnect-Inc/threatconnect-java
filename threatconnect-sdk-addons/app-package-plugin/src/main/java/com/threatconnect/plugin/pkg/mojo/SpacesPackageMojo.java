package com.threatconnect.plugin.pkg.mojo;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

import java.io.File;
import java.io.IOException;

@Mojo(name = "spaces-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class SpacesPackageMojo extends AbstractAppPackageMojo
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
			// check to see if this child is a directory
			if (child.isDirectory())
			{
				// make sure that this file is not hidden and that it is not the output folder
				if (!child.isHidden() && !child.equals(outputDirectory))
				{
					// copy this directory to the target folder
					File target = new File(targetDirectory.getAbsolutePath() + File.separator + child.getName());
					copyFileToDirectoryIfExists(child, target);
				}
			}
		}
	}
}
