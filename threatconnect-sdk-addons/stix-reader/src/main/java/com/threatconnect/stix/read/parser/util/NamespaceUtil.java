package com.threatconnect.stix.read.parser.util;

import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class NamespaceUtil
{
	private static final String XML_NAMESPACE = "xmlns";
	
	private static final Map<String, String> prefixMap = new HashMap<String, String>();
	
	public static void extractNamespaces(Node node)
	{
		NamedNodeMap attrs = node.getAttributes();
		for (int i = 0; i < attrs.getLength(); i++)
		{
			Node attr = attrs.item(i);
			String name = attr.getNodeName();
			if (name != null && name.startsWith(XML_NAMESPACE + ":"))
			{
				final String localName = name.split(Pattern.quote(":"))[1];
				prefixMap.put(localName, attr.getNodeValue());
			}
		}
	}
	
	public static void addNamespacesToElement(final Element element)
	{
		// for each entry in the map
		for (Map.Entry<String, String> entry : prefixMap.entrySet())
		{
			final String attr = XML_NAMESPACE + ":" + entry.getKey();
			element.setAttribute(attr, entry.getValue());
		}
	}
}
