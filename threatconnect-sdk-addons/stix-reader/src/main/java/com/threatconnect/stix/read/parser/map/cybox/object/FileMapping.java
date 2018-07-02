package com.threatconnect.stix.read.parser.map.cybox.object;

import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.FileOccurrence;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.parser.util.AttributeHelper;
import com.threatconnect.stix.read.parser.Constants;
import com.threatconnect.stix.read.parser.exception.InvalidObservableException;
import com.threatconnect.stix.read.parser.util.DebugUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class FileMapping extends CyboxObjectMapping
{
	private static final Logger logger = LoggerFactory.getLogger(FileMapping.class);
	
	private static final String ATTR_IS_PACKED = "Is Packed";
	private static final String ATTR_IS_MASQUERADED = "Is Masqueraded";
	private static final String ATTR_FILE_EXTENSION = "File Extension";
	private static final String ATTR_MAGIC_NUMBER = "Magic Number";
	private static final String ATTR_FILE_FORMAT = "File Format";
	private static final String ATTR_DIGITAL_SIGNATURES = "Digital Signatures";
	private static final String ATTR_DATE_LAST_MODIFIED = "Date Last Modified";
	private static final String ATTR_DATE_LAST_ACCESSED = "Date Last Accessed";
	private static final String ATTR_DATE_CREATED = "Date Created";
	private static final String ATTR_FILE_ATTRIBUTES = "File Attributes";
	
	public FileMapping(final Double defaultRating, final Double defaultConfidence)
	{
		super(defaultRating, defaultConfidence);
	}
	
	@Override
	public List<? extends Item> map(Node objectNode, final String observableNodeID, final Document document, final List<SecurityLabel> securityLabels)
		throws XPathExpressionException
	{
		File file = new File();
		file.getSecurityLabels().addAll(securityLabels);
		setDefaultRatingConfidence(file);
		addStixObservableIDAttribute(file, observableNodeID);
		
		// get the properties node for this observable
		Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
		
		// extract the file occurrence information
		final String fileName = Constants.XPATH_UTIL.getString("File_Name", propertiesNode);
		final String fullPath = Constants.XPATH_UTIL.getString("Full_Path", propertiesNode);
		final String devicePath = Constants.XPATH_UTIL.getString("Device_Path", propertiesNode);
		final String filePath = Constants.XPATH_UTIL.getString("File_Path", propertiesNode);
		
		if (StringUtils.isNotBlank(fileName))
		{
			String path;
			
			//check to see if the fullPath should be used
			if (StringUtils.isNotBlank(fullPath))
			{
				path = fullPath;
			}
			//check to see if the file path should be used
			else if (StringUtils.isNotBlank(filePath))
			{
				path = filePath;
				
				//check to see if there is a device path as well
				if (StringUtils.isNotBlank(devicePath))
				{
					//prepend the device path
					path = devicePath + path;
				}
			}
			else
			{
				//no path was found
				path = null;
			}
			
			FileOccurrence fileOccurrence = new FileOccurrence();
			fileOccurrence.setFileName(fileName.trim());
			fileOccurrence.setPath(path);
			file.getFileOccurrences().add(fileOccurrence);
		}
		
		final String fileSizeInBytes = Constants.XPATH_UTIL.getString("Size_In_Bytes", propertiesNode);
		if (null != fileSizeInBytes && !fileSizeInBytes.isEmpty())
		{
			file.setSize(Integer.parseInt(fileSizeInBytes));
		}
		
		// for each of the hash nodes
		NodeList hashNodes = Constants.XPATH_UTIL.getNodes("Hashes/Hash", propertiesNode);
		for (int i = 0; i < hashNodes.getLength(); i++)
		{
			// get the type and hash values of this hash node
			Node hashNode = hashNodes.item(i);
			final String type = Constants.XPATH_UTIL.getString("Type", hashNode);
			final String hash = Constants.XPATH_UTIL.getString("Simple_Hash_Value", hashNode);
			
			switch (type)
			{
				case "MD5":
					file.setMd5(hash);
					break;
				case "SHA1":
					file.setSha1(hash);
					break;
				case "SHA256":
					file.setSha256(hash);
					break;
			}
		}
		
		// for each of the digital signature nodes
		NodeList digitalSignatureNodes =
			Constants.XPATH_UTIL.getNodes("Digital_Signatures/Digital_Signature", propertiesNode);
		for (int i = 0; i < digitalSignatureNodes.getLength(); i++)
		{
			Node digitalSignatureNode = digitalSignatureNodes.item(i);
			
			StringBuilder sb = new StringBuilder();
			sb.append("**Certificate Issuer:** ");
			sb.append(Constants.XPATH_UTIL.getString("Certificate_Issuer", digitalSignatureNode));
			sb.append("\n");
			sb.append("**Certificate Subject:** ");
			sb.append(Constants.XPATH_UTIL.getString("Certificate_Subject", digitalSignatureNode));
			sb.append("\n");
			sb.append("**Signature Description:** ");
			sb.append(Constants.XPATH_UTIL.getString("Signature_Description", digitalSignatureNode));
			sb.append("\n");
			
			AttributeHelper.addAttributeIfExists(file, ATTR_DIGITAL_SIGNATURES, sb.toString());
		}
		
		// add the date field attributes
		final String modifiedTime = Constants.XPATH_UTIL.getString("Modified_Time", propertiesNode);
		final String accessedTime = Constants.XPATH_UTIL.getString("Accessed_Time", propertiesNode);
		final String createdTime = Constants.XPATH_UTIL.getString("Created_Time", propertiesNode);
		addDateAttribute(file, ATTR_DATE_LAST_MODIFIED, modifiedTime);
		addDateAttribute(file, ATTR_DATE_LAST_ACCESSED, accessedTime);
		addDateAttribute(file, ATTR_DATE_CREATED, createdTime);
		
		// add all of the attributes for this object if they exist
		AttributeHelper.addAttributeIfExists(file, ATTR_IS_PACKED,
			Constants.XPATH_UTIL.getString("@is_packed", propertiesNode));
		AttributeHelper.addAttributeIfExists(file, ATTR_IS_MASQUERADED,
			Constants.XPATH_UTIL.getString("@is_masqueraded", propertiesNode));
		AttributeHelper.addAttributeIfExists(file, ATTR_FILE_EXTENSION,
			Constants.XPATH_UTIL.getString("File_Extension", propertiesNode));
		AttributeHelper.addAttributeIfExists(file, ATTR_MAGIC_NUMBER,
			Constants.XPATH_UTIL.getString("Magic_Number", propertiesNode));
		AttributeHelper.addAttributeIfExists(file, ATTR_FILE_FORMAT,
			Constants.XPATH_UTIL.getString("File_Format", propertiesNode));
		
		// :FIXME: handle the unknown mappings
		DebugUtil.logUnknownMapping("File_Attributes_List", propertiesNode);
		
		return Collections.singletonList(file);
	}
	
	private void addDateAttribute(final File file, final String attributeName, final String time)
	{
		// make sure the time is not null
		if (null != time && !time.isEmpty())
		{
			try
			{
				Date date = Constants.DEFAULT_DATE_FORMATTER.parse(time);
				SimpleDateFormat dateFormat = new SimpleDateFormat(AttributeHelper.SOURCE_DATE_TIME_FORMAT);
				AttributeHelper.addAttribute(file, attributeName, dateFormat.format(date));
			}
			catch (ParseException e)
			{
				logger.warn(e.getMessage(), e);
			}
		}
	}
}
