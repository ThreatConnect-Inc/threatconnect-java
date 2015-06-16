package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Attribute;

import java.util.Date;

public class AttributeBuilder
{
    private Integer id;
    private String type;
    private String value;
    private String source;
    private Date dateAdded;
    private Date lastModified;
    private Boolean displayed;

    public AttributeBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public AttributeBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public AttributeBuilder withValue(String value)
    {
        this.value = value;
        return this;
    }

    public AttributeBuilder withSource(String source)
    {
        this.source = source;
        return this;
    }

    public AttributeBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public AttributeBuilder withLastModified(Date lastModified)
    {
        this.lastModified = lastModified;
        return this;
    }

    public AttributeBuilder withDisplayed(Boolean displayed)
    {
        this.displayed = displayed;
        return this;
    }

    public Attribute createAttribute()
    {
        return new Attribute(id, type, value, source, dateAdded, lastModified, displayed);
    }
}