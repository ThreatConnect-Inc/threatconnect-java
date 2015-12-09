package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.FileOccurrence;

import java.util.Date;

public class FileOccurrenceBuilder
{
    private Integer id;
    private String fileName;
    private String path;
    private Date date;

    public FileOccurrenceBuilder withId(Integer id)
    {
        this.id = id;
        return this;
    }

    public FileOccurrenceBuilder withFileName(String fileName)
    {
        this.fileName = fileName;
        return this;
    }

    public FileOccurrenceBuilder withPath(String path)
    {
        this.path = path;
        return this;
    }

    public FileOccurrenceBuilder withDate(Date date)
    {
        this.date = date;
        return this;
    }

    public FileOccurrence createFileOccurrence()
    {
        return new FileOccurrence(id, fileName, path, date);
    }
}