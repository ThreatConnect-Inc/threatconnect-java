package com.threatconnect.sdk.server.response.entity.data;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.threatconnect.sdk.server.entity.FalsePositive;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;


/**
 * 
 * @author mangelo
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class FalsePositiveResponseData extends ApiEntitySingleResponseData<FalsePositive>
{
    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name="FalsePositive", required = false)
    private FalsePositive falsePositive;

    public FalsePositiveResponseData(FalsePositive falsePositive)
    {
        super(falsePositive);
        this.falsePositive = falsePositive;
    }
    
    public FalsePositive getFalsePositive()
    {
        return falsePositive;
    }

    public void setFalsePositive(FalsePositive falsePositive)
    {
        this.falsePositive = falsePositive;
    }

    @Override
    public FalsePositive getData()
    {
        return getFalsePositive();
    }

    @Override
    public void setData(FalsePositive data)
    {
        setFalsePositive(data);        
    }

}
