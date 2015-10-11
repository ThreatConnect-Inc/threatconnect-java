package com.threatconnect.sdk.server.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by dtineo
 */
@XmlRootElement(name = "SpaceState")
@XmlAccessorType(XmlAccessType.FIELD)
public class SpaceState
{
    @XmlElement(name = "spaceId", required = true)
    private Integer spaceId;

    /*
    @XmlElement(name = "parameters", required = false)
    private List<JobParameter> parameters;
    */

    @XmlElement(name = "stateText", required = false)
    private String stateText;

    public SpaceState()
    {
    }

    public Integer getSpaceId()
    {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId)
    {
        this.spaceId = spaceId;
    }

    /*
    public List<JobParameter> getParameters()
    {
        return parameters;
    }

    public void setParameters(List<JobParameter> parameters)
    {
        this.parameters = parameters;
    }
    */

    public String getStateText()
    {
        return stateText;
    }

    public void setStateText(String stateText)
    {
        this.stateText = stateText;
    }
}
