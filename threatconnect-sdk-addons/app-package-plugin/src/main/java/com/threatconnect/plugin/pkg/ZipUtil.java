package com.threatconnect.plugin.pkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{
	public static File zipFolder(File srcFolder, final String fileExtension) throws IOException
	{
		File destZipFile = new File(srcFolder.getAbsolutePath() + "." + fileExtension);
		zipFolder(srcFolder, destZipFile);
		return destZipFile;
	}
	
	public static void zipFolder(File srcFolder, File destZipFile) throws IOException
	{
		try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(destZipFile)))
		{
			addFolderToZip(null, srcFolder, zip);
		}
	}
	
	public static File zipFiles(final List<File> sourceFiles, final String destZipFile) throws IOException
	{
		File destFile = new File(destZipFile);
		
		try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(destFile)))
		{
			//for each of the source files
			for (File file : sourceFiles)
			{
				//add this file to the zip archive
				addFileToZip(null, file, zip);
			}
			zip.flush();
		}
		
		return destFile;
	}
	
	private static void addFileToZip(String path, File srcFile, ZipOutputStream zip) throws IOException
	{
		if (srcFile.isDirectory())
		{
			addFolderToZip(path, srcFile, zip);
		}
		else
		{
			byte[] buf = new byte[1024];
			int len;
			
			try (FileInputStream in = new FileInputStream(srcFile))
			{
				//determine the path of this zip entry
				final String zipPath = (null == path) ? srcFile.getName() : path + "/" + srcFile.getName();
				zip.putNextEntry(new ZipEntry(zipPath));
				
				//write the contents of the file to this zip entry
				while ((len = in.read(buf)) > 0)
				{
					zip.write(buf, 0, len);
				}
			}
		}
	}
	
	private static void addFolderToZip(String path, File srcFolder, ZipOutputStream zip) throws IOException
	{
		//for each of the child files
		for (File file : srcFolder.listFiles())
		{
			//determine the path of this zip entry and add this file to the zip archive
			final String zipPath = (null == path) ? srcFolder.getName() : path + "/" + srcFolder.getName();
			addFileToZip(zipPath, file, zip);
		}
	}
}
