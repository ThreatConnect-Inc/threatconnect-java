package com.threatconnect.opendxl.config;

import java.io.File;
import java.util.UUID;

/**
 * @author Greg Marut
 */
public abstract class OpenDXLConfigIO
{
	protected static File createTempFile(final String fileName)
	{
		final String filePath =
			System.getProperty("java.io.tmpdir") + File.separator + UUID.randomUUID().toString() + "_" + fileName;
		return new File(filePath);
	}
}
