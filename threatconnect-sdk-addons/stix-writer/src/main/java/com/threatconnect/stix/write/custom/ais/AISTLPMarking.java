package com.threatconnect.stix.write.custom.ais;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AISTLPMarking
{
	@XmlAttribute(name="color")
	protected String color;
	
	public AISTLPMarking(final String color)
	{
		this.color = color;
	}
	
	public String getColor()
	{
		return color;
	}
	
	public void setColor(final String color)
	{
		this.color = color;
	}
}
