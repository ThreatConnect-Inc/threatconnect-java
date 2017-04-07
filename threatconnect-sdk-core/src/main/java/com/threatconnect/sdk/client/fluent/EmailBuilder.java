package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Email;

public class EmailBuilder extends AbstractGroupBuilder<EmailBuilder>
{
    private String to;
    private String from;
    private String subject;
    private Integer score;
    private String header;
    private String body;

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