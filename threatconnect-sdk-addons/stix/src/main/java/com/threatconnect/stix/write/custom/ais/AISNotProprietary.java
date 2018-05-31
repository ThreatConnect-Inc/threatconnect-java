package com.threatconnect.stix.write.custom.ais;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class AISNotProprietary
{
	@XmlElement(name="AISConsent", namespace="http://www.us-cert.gov/STIXMarkingStructure#AISConsentMarking-2")
	private AISConsent aisConsent;
	@XmlElement(name="TLPMarking ", namespace="http://www.us-cert.gov/STIXMarkingStructure#AISConsentMarking-2")
	private AISTLPMarking aistlpMarking;
	
	public AISConsent getAisConsent()
	{
		return aisConsent;
	}
	
	public void setAisConsent(final AISConsent aisConsent)
	{
		this.aisConsent = aisConsent;
	}
	
	public AISTLPMarking getAistlpMarking()
	{
		return aistlpMarking;
	}
	
	public void setAistlpMarking(final AISTLPMarking aistlpMarking)
	{
		this.aistlpMarking = aistlpMarking;
	}
}
