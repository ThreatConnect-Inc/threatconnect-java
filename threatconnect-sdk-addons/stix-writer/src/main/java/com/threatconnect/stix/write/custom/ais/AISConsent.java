package com.threatconnect.stix.write.custom.ais;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@XmlAccessorType(XmlAccessType.FIELD)
public class AISConsent
{
	@XmlAttribute(name="consent")
	protected String consent;
	
	public AISConsent(final String consent)
	{
		this.consent = consent;
	}
	
	public String getConsent()
	{
		return consent;
	}
	
	public void setConsent(final String consent)
	{
		this.consent = consent;
	}
}
