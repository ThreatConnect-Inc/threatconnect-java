package com.threatconnect.plugin.pkg;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author Greg Marut
 */
public class ZipUtilTest
{
	@Test
	public void zipFilesTest() throws IOException
	{
		File file1 = new File("src/test/resources/file1.txt");
		File file2 = new File("src/test/resources/file2.txt");
		
		ZipUtil.zipFiles(Arrays.asList(file1, file2), "target/bundle.zip");
	}
}
