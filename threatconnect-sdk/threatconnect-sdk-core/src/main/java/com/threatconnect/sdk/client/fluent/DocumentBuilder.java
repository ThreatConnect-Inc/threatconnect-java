package com.threatconnect.sdk.client.fluent;

import com.threatconnect.sdk.server.entity.Document;

public class DocumentBuilder extends AbstractGroupBuilder<DocumentBuilder>
{
    private String fileName;
    private Long fileSize;
    private String status;
    private boolean malware;
    private String password;

    public DocumentBuilder withFileName(String fileName)
    {
        this.fileName = fileName;
        return this;
    }

    public DocumentBuilder withFileSize(Long fileSize)
    {
        this.fileSize = fileSize;
        return this;
    }

    public DocumentBuilder withStatus(String status)
    {
        this.status = status;
        return this;
    }

    public DocumentBuilder isMalware() {
        this.malware = true;
        return this;
    }

    public DocumentBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public Document createDocument()
    {
        return new Document(id, name, type, owner, ownerName, dateAdded, webLink, fileName, fileSize, status, malware, password);
    }
}