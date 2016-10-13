package com.threatconnect.plugin.pkg;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil
{
	public static void zipFolder(File srcFolder, final String fileExtension) throws IOException
	{
		zipFolder(srcFolder.getAbsolutePath(), srcFolder.getAbsolutePath() + "." + fileExtension);
	}
	
	public static void zipFolder(String srcFolder, String destZipFile) throws IOException
	{
		ZipOutputStream zip = null;
		FileOutputStream fileWriter = null;
		
		fileWriter = new FileOutputStream(destZipFile);
		zip = new ZipOutputStream(fileWriter);
		
		addFolderToZip("", srcFolder, zip);
		zip.flush();
		zip.close();
	}
	
	private static void addFileToZip(String path, String srcFile, ZipOutputStream zip) throws IOException
	{
		File folder = new File(srcFile);
		if (folder.isDirectory())
		{
			addFolderToZip(path, srcFile, zip);
		}
		else
		{
			byte[] buf = new byte[1024];
			int len;
			
			try (FileInputStream in = new FileInputStream(srcFile))
			{
				zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
				while ((len = in.read(buf)) > 0)
				{
					zip.write(buf, 0, len);
				}
			}
		}
	}
	
	private static void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws IOException
	{
		File folder = new File(srcFolder);
		
		for (String fileName : folder.list())
		{
			if (path.equals(""))
			{
				addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip);
			}
			else
			{
				addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip);
			}
		}
	}
}
