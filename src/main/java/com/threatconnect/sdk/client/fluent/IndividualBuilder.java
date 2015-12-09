package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Individual;

public class IndividualBuilder
{
    private Integer id;
    private String name;
    private String type;

    public IndividualBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public IndividualBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public IndividualBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public Individual createIndividual()
    {
        return new Individual(id, name, type);
    }
}