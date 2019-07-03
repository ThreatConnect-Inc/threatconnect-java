package com.threatconnect.app.apps.service.message;

import java.util.List;

public class ServiceItem
{
    private String name;
    private String path;
    private String description;
    private List<String> methods;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<String> getMethods()
    {
        return methods;
    }

    public void setMethods(List<String> methods)
    {
        this.methods = methods;
    }
}
