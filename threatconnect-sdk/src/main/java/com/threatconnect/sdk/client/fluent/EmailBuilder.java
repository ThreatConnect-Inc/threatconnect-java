package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.Owner;

import java.util.Date;

public class EmailBuilder
{
    private Integer id;
    private String name;
    private String type;
    private Owner owner;
    private String ownerName;
    private Date dateAdded;
    private String webLink;
    private String to;
    private String from;
    private String subject;
    private Integer score;
    private String header;
    private String body;

    public EmailBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public EmailBuilder withName(String name)
    {
        this.name = name;
        return this;
    }

    public EmailBuilder withType(String type)
    {
        this.type = type;
        return this;
    }

    public EmailBuilder withOwner(Owner owner)
    {
        this.owner = owner;
        return this;
    }

    public EmailBuilder withOwnerName(String ownerName)
    {
        this.ownerName = ownerName;
        return this;
    }

    public EmailBuilder withDateAdded(Date dateAdded)
    {
        this.dateAdded = dateAdded;
        return this;
    }

    public EmailBuilder withWebLink(String webLink)
    {
        this.webLink = webLink;
        return this;
    }

    public EmailBuilder withTo(String to)
    {
        this.to = to;
        return this;
    }

    public EmailBuilder withFrom(String from)
    {
        this.from = from;
        return this;
    }

    public EmailBuilder withSubject(String subject)
    {
        this.subject = subject;
        return this;
    }

    public EmailBuilder withScore(Integer score)
    {
        this.score = score;
        return this;
    }

    public EmailBuilder withHeader(String header)
    {
        this.header = header;
        return this;
    }

    public EmailBuilder withBody(String body)
    {
        this.body = body;
        return this;
    }

    public Email createEmail()
    {
        return new Email(id, name, type, owner, ownerName, dateAdded, webLink, to, from, subject, score, header, body);
    }
}