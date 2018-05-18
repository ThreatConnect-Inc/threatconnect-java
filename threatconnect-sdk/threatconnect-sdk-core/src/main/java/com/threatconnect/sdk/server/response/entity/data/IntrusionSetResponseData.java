/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.IntrusionSet;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class IntrusionSetResponseData extends ApiEntitySingleResponseData<IntrusionSet>
{
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	@XmlElement(name = "IntrusionSet", required = false)
	private IntrusionSet intrusionSet;
	
	public IntrusionSet getIntrusionSet()
	{
		return intrusionSet;
	}
	
	public void setIntrusionSet(IntrusionSet intrusionSet)
	{
		this.intrusionSet = intrusionSet;
	}
	
	@Override
	@JsonIgnore
	public IntrusionSet getData()
	{
		return getIntrusionSet();
	}
	
	@Override
	public void setData(IntrusionSet data)
	{
		setIntrusionSet(data);
	}
}
