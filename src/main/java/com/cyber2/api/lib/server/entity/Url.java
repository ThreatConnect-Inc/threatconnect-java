package com.cyber2.api.lib.server.entity;

import com.cyber2.api.lib.server.entity.init.ApiEntityInit;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *
 * @author eric
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlRootElement(name = "Url")
@XmlAccessorType(XmlAccessType.FIELD)
public class Url extends Indicator
{
    @XmlElement(name = "Text", required = true)
    private String text;
    
    public Url()
    {
        super();
    }
    
    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
    
}
