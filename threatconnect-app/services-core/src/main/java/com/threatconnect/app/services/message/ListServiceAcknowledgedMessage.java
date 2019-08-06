package com.threatconnect.app.services.message;

import java.util.List;

public class ListServiceAcknowledgedMessage extends AcknowledgedMessage
{
	private List<ServiceItem> data;
	
	public ListServiceAcknowledgedMessage()
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
