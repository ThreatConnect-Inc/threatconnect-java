package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.Report;

import javax.xml.bind.annotation.XmlElement;

public class ReportResponseData extends ApiEntitySingleResponseData<Report>
{

    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Report", required = false)
    private Report report;

    public Report getReport()
    {
        return report;
    }

    public void setReport(Report report)
    {
        this.report = report;
    }

    @Override
    @JsonIgnore
    public Report getData()
    {
        return getReport();
    }

    @Override
    public void setData(Report data)
    {
        setReport(data);
    }
}
