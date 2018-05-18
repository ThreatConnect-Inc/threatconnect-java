/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.IntrusionSet;
import com.threatconnect.sdk.server.response.entity.data.IntrusionSetResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "intrusionSetResponse")
@XmlSeeAlso(IntrusionSet.class)
public class IntrusionSetResponse extends ApiEntitySingleResponse<IntrusionSet, IntrusionSetResponseData>
{
	public void setData(IntrusionSetResponseData data)
	{
		super.setData(data);
	}
}
