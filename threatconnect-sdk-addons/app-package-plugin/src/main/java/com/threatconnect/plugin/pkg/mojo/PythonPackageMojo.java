package com.threatconnect.plugin.pkg.mojo;

import com.threatconnect.plugin.pkg.PackageFileFilter;
import com.threatconnect.plugin.pkg.PythonPackageFileFilter;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.ResolutionScope;

@Mojo(name = "python-package", defaultPhase = LifecyclePhase.PACKAGE, threadSafe = true, requiresDependencyResolution = ResolutionScope.RUNTIME)
public class PythonPackageMojo extends AbstractAppPackageMojo
{
	@Override
	protected PackageFileFilter createPackageFileFilter()
	{
		return new PythonPackageFileFilter(getExclude());
	}
}
