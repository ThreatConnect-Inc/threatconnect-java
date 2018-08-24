package com.threatconnect.stix.read.parser.util;

import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.stix.read.parser.Constants;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathExpressionException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SecurityLabelUtil
{
	private static final String TLP_RED = "TLP:Red";
	private static final String TLP_AMBER = "TLP:Amber";
	private static final String TLP_GREEN = "TLP:Green";
	private static final String TLP_WHITE = "TLP:White";
	
	public static List<SecurityLabel> extractSecurityLabels(final NodeList markingStructureNodeList)
		throws XPathExpressionException
	{
		List<SecurityLabel> securityLabels = new ArrayList<SecurityLabel>();
		
		// for each of the nodes in the list
		for (int i = 0; i < markingStructureNodeList.getLength(); i++)
		{
			// retrieve the current node
			Node markingStructureNode = markingStructureNodeList.item(i);
			
			final String color = Constants.XPATH_UTIL.getString("@color", markingStructureNode);
			final String name = convertSecurityColorToName(color);
			if (null != name)
			{
				SecurityLabel securityLabel = new SecurityLabel();
				securityLabel.setName(name);
				securityLabels.add(securityLabel);
			}
		}
		
		return securityLabels;
	}
	
	public static void keepHighestSecurityLabelOnly(final List<SecurityLabel> securityLabels)
	{
		//find the highest security label in the list
		SecurityLabel highest = findHighestSecurityLabel(securityLabels);
		
		//make sure one was found
		if (null != highest)
		{
			//create a new temp list that will hold allof the values to remove
			List<SecurityLabel> toRemove = new ArrayList<SecurityLabel>(securityLabels);
			toRemove.remove(highest);
			
			//remove all of these values from the original list, leaving only the highest one we wanted
			securityLabels.removeAll(toRemove);
		}
	}
	
	public static SecurityLabel findHighestSecurityLabel(final List<SecurityLabel> securityLabels)
	{
		return securityLabels.stream().min(Comparator.comparingInt(s -> getOrder(s.getName()))).orElse(null);
	}
	
	/**
	 * Converts a security color to a name suitable for a security label
	 *
	 * @param color
	 * @return
	 */
	private static String convertSecurityColorToName(final String color)
	{
		if (null != color && !color.isEmpty())
		{
			switch (color.trim().toUpperCase())
			{
				case "RED":
					return TLP_RED;
				case "AMBER":
					return TLP_AMBER;
				case "GREEN":
					return TLP_GREEN;
				case "WHITE":
					return TLP_WHITE;
				default:
					return null;
			}
		}
		else
		{
			return null;
		}
	}
	
	private static int getOrder(final String securityLabel)
	{
		if (null != securityLabel)
		{
			switch (securityLabel)
			{
				case TLP_RED:
					return 0;
				case TLP_AMBER:
					return 1;
				case TLP_GREEN:
					return 2;
				case TLP_WHITE:
					return 3;
				default:
					return 100;
			}
		}
		else
		{
			return 100;
		}
	}
}
