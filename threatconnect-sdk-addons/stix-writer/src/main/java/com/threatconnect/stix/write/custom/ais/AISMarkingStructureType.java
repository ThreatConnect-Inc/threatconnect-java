package com.threatconnect.stix.write.custom.ais;

import org.mitre.data_marking.marking_1.MarkingStructureType;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AIS:AISMarkingStructureType")
public class AISMarkingStructureType extends MarkingStructureType
{
	@XmlElement(name = "Not_Proprietary", namespace="http://www.us-cert.gov/STIXMarkingStructure#AISConsentMarking-2")
	protected AISNotProprietary aisNotProprietary;
	
	public AISNotProprietary getAisNotProprietary()
	{
		return aisNotProprietary;
	}
	
	public void setAisNotProprietary(final AISNotProprietary aisNotProprietary)
	{
		this.aisNotProprietary = aisNotProprietary;
	}
}
