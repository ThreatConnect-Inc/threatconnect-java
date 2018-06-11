/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Event;
import com.threatconnect.sdk.server.response.entity.data.EventResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "eventResponse")
@XmlSeeAlso(Event.class)
public class EventResponse extends ApiEntitySingleResponse<Event, EventResponseData>
{
	public void setData(EventResponseData data)
	{
		super.setData(data);
	}
}
