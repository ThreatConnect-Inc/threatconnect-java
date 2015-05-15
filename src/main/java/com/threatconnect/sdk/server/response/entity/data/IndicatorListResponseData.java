/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.server.response.entity.data;

import com.threatconnect.sdk.server.entity.Indicator;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author James
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class IndicatorListResponseData extends ApiEntityListResponseData<Indicator>
{
    @JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
    @XmlElement(name = "Indicator", required = false)
    private List<Indicator> indicator;
    
    public List<Indicator> getIndicator()
    {
        return indicator;
    }

    public void setIndicator(List<Indicator> indicator)
    {
        this.indicator = indicator;
    }

    @Override
    @JsonIgnore
    public List<Indicator> getData()
    {
        return getIndicator();
    }

    @Override
    public void setData(List<Indicator> data)
    {
        setIndicator(data);
    }
}
