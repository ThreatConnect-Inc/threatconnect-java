package com.threatconnect.app.services.message;

import java.util.List;

public class ListServiceAcknowledgeMessage extends AcknowledgeMessage
{
	private List<ServiceItem> data;
	
	public ListServiceAcknowledgeMessage()
	{
		super(CommandType.ListServices);
	}
	
	public List<ServiceItem> getData()
	{
		return data;
	}
	
	public void setData(List<ServiceItem> data)
	{
		this.data = data;
	}
}
