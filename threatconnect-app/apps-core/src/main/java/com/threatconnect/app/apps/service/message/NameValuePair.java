package com.threatconnect.app.apps.service.message;

public class NameValuePair<A, B>
{
    private A name;
    private B value;

    public A getName()
    {
        return name;
    }

    public void setName(A name)
    {
        this.name = name;
    }

    public B getValue()
    {
        return value;
    }

    public void setValue(B value)
    {
        this.value = value;
    }
}
