package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cblades on 6/10/2015.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BulkStatus")
public class BatchConfig
{
    @XmlElement(name = "haltOnError", required = true)
    private boolean haltOnError;

    @XmlElement(name = "attributeWriteType", required = true)
    private AttributeWriteType attributeWriteType;

    @XmlElement(name = "action", required = true)
    private Action action;

    public enum AttributeWriteType {
        Append,
        Replace,
        Static
    }

    public enum Action {
        Create,
        Delete
    }


    public BatchConfig() {}

    public BatchConfig(boolean haltOnError, AttributeWriteType attributeWriteType, Action action)
    {
        this.haltOnError = haltOnError;
        this.attributeWriteType = attributeWriteType;
        this.action = action;
    }

    public boolean isHaltOnError()
    {
        return haltOnError;
    }

    public void setHaltOnError(boolean haltOnError)
    {
        this.haltOnError = haltOnError;
    }

    public AttributeWriteType getAttributeWriteType()
    {
        return attributeWriteType;
    }

    public void setAttributeWriteType(AttributeWriteType attributeWriteType)
    {
        this.attributeWriteType = attributeWriteType;
    }

    public Action getAction()
    {
        return action;
    }

    public void setAction(Action action)
    {
        this.action = action;
    }
}
