package com.threatconnect.sdk.server.response.entity;

import com.threatconnect.sdk.server.entity.Report;
import com.threatconnect.sdk.server.response.entity.data.ReportResponseData;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Report")
@XmlSeeAlso(Report.class)
public class ReportResponse extends ApiEntitySingleResponse<Report, ReportResponseData>
{
	public void setData(ReportResponseData data)
	{
		super.setData(data);
	}
}
