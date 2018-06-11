/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Event;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class EventResponseData extends ApiEntitySingleResponseData<Event>
{
	@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
	@XmlElement(name = "Event", required = false)
	private Event event;
	
	public Event getEvent()
	{
		return event;
	}
	
	public void setEvent(Event event)
	{
		this.event = event;
	}
	
	@Override
	@JsonIgnore
	public Event getData()
	{
		return getEvent();
	}
	
	@Override
	public void setData(Event data)
	{
		setEvent(data);
	}
}
