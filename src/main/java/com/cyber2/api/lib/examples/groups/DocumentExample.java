package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.DocumentReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.*;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.Document;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Created by cblades on 4/20/2015.
 */
public class DocumentExample
{
    public static void main(String[] args) {
        System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
        try (Connection conn = new Connection()){
            doDownload(conn);

            doUpload(conn);

            doGet(conn);

            doCreate(conn);

            doUpdate(conn);

            doDelete(conn);

            doAddAttribute(conn);

            doAssociateIndicator(conn);

            doAssociateGroup(conn);

            doAssociateTag(conn);

            doAssociateVictim(conn);

            doDissociateTag(conn);
        } catch (IOException ex ) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doDownload(Connection conn)
    {
        DocumentReaderAdapter reader = ReaderAdapterFactory.createDocumentReaderAdapter(conn);
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Documents
            // -----------------------------------------------------------------------------------------------------------
            List<Document> data = reader.getAll();

            if (data.size() > 0) {
                reader.downloadFile(data.get(0).getId(), null, Paths.get("./testDownload.txt"));
                try (Scanner in = new Scanner(new FileInputStream("./testDownload.txt")))
                {
                    while (in.hasNextLine())
                    {
                        System.out.println(in.nextLine());
                    }
                }
            }

        } catch (FailedResponseException | IOException e)
        {
            e.printStackTrace();
        }
    }

    private static void doUpload(Connection conn) {
        DocumentWriterAdapter writer = WriterAdapterFactory.createDocumentWriterAdapter(conn);

        try
        {

            // -----------------------------------------------------------------------------------------------------------
            // Create a Document
            // -----------------------------------------------------------------------------------------------------------
            Document document = mockDocument();

            ApiEntitySingleResponse<Document, ?> response = writer.create(document);

            if (response.isSuccess())
            {
                //Get file from resources folder
                ClassLoader classLoader = DocumentExample.class.getClassLoader();
                File file = new File(classLoader.getResource("testUpload.txt").getFile());

                writer.uploadFile(response.getItem().getId(), file);
            }
        } catch (FailedResponseException | IOException e)
        {
            System.err.println("Error: " + e);
        }
    }

    private static void doGet(Connection conn) throws IOException {

        DocumentReaderAdapter reader = ReaderAdapterFactory.createDocumentReaderAdapter(conn);
        List<Document> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Documents
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Document d : data) {
                System.out.println("Document: " + d.getId());

            }

            // -----------------------------------------------------------------------------------------------------------
            // Get a Document
            // -----------------------------------------------------------------------------------------------------------
            if (data.size() > 0) {
               Document d = reader.getById(data.get(0).getId());
                System.out.println("Document: " + d.getFileName());
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn)
    {
        AbstractGroupWriterAdapter<Document> writer = WriterAdapterFactory.createDocumentWriterAdapter(conn);

        try
        {

            // -----------------------------------------------------------------------------------------------------------
            // Create a Document
            // -----------------------------------------------------------------------------------------------------------
            Document document = mockDocument();

            ApiEntitySingleResponse<Document, ?> response = writer.create(document);

            if (response.isSuccess())
            {
                System.out.println("Created document: " + document.getFileName());
            }
        } catch (FailedResponseException | IOException e)
        {
            System.err.println("Error: " + e);
        }
    }

    private static void doUpdate(Connection conn)
    {
        AbstractGroupWriterAdapter<Document> writer = WriterAdapterFactory.createDocumentWriterAdapter(conn);

        try
        {
            // -----------------------------------------------------------------------------------------------------------
            // Create a Document
            // -----------------------------------------------------------------------------------------------------------
            Document document = mockDocument();

            ApiEntitySingleResponse<Document, ?> response = writer.create(document);

            if (response.isSuccess())
            {
                System.out.println("Created document: " + response.getItem().getFileName());
                // -----------------------------------------------------------------------------------------------------------
                // Update a Document
                // -----------------------------------------------------------------------------------------------------------
                response.getItem().setFileName("UpdatedDocument");
                response = writer.update(response.getItem());

                if (response.isSuccess())
                {
                    System.out.println("Updated document: " + response.getItem().getFileName());
                }
            }
        } catch (FailedResponseException | IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static void doDelete(Connection conn)
    {
        AbstractGroupWriterAdapter<Document> writer = WriterAdapterFactory.createDocumentWriterAdapter(conn);

        try
        {
            // -----------------------------------------------------------------------------------------------------------
            // Create a Document
            // -----------------------------------------------------------------------------------------------------------
            Document document = mockDocument();

            ApiEntitySingleResponse<Document, ?> response = writer.create(document);

            if (response.isSuccess())
            {
                System.out.println("Created document: " + response.getItem().getFileName());
                // -----------------------------------------------------------------------------------------------------------
                // Update a Document
                // -----------------------------------------------------------------------------------------------------------
                response = writer.delete(response.getItem().getId());

                if (response.isSuccess())
                {
                    System.out.println("Deleted document");
                }
            }
        } catch (FailedResponseException | IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static void doAddAttribute(Connection conn)
    {
        AbstractGroupWriterAdapter<Document> writer = WriterAdapterFactory.createDocumentWriterAdapter(conn);

        try
        {
            // -----------------------------------------------------------------------------------------------------------
            // Create a Document
            // -----------------------------------------------------------------------------------------------------------
            Document document = mockDocument();
            Attribute attribute = mockAttribute();

            ApiEntitySingleResponse<Document, ?> response = writer.create(document);

            if (response.isSuccess())
            {
                System.out.println("Created document: " + response.getItem().getFileName());
                // -----------------------------------------------------------------------------------------------------------
                // Add attribute to Document
                // -----------------------------------------------------------------------------------------------------------
                response = writer.addAttribute(response.getItem().getId(), attribute);

                if (response.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + response.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + response.getMessage());
                }
            }
        } catch (FailedResponseException | IOException e)
        {
            System.err.println(e.getMessage());
        }
    }

    private static void doAssociateGroup(Connection conn) {
        AbstractGroupWriterAdapter<Document> gWriter = WriterAdapterFactory.createDocumentWriterAdapter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        Document document = mockDocument();
        Threat threat = mockThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Document, ?> createDocumentResponse = gWriter.create(document);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createDocumentResponse.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created Document: " + createDocumentResponse.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                        = gWriter.associateGroupThreat(createDocumentResponse.getItem().getId(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createDocumentResponse.isSuccess() ) System.err.println("Failed to Create Address: " + createDocumentResponse.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }


    private static void doAssociateTag(Connection conn) {
        AbstractGroupWriterAdapter<Document> gWriter= WriterAdapterFactory.createDocumentWriterAdapter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Document document = mockDocument();
        Tag tag = mockTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Tag
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Document, ?> createResponseDocument = gWriter.create(document);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseDocument.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Threat: " + createResponseDocument.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                        = gWriter.associateTag(createResponseDocument.getItem().getId()
                        , createResponseTag.getItem().getName());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseDocument.isSuccess() ) System.err.println("Failed to Create Document: " + createResponseDocument.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractGroupWriterAdapter<Document> gWriter= WriterAdapterFactory.createDocumentWriterAdapter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Document document = mockDocument();
        Tag tag = mockTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Tag
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Document, ?> createResponseDocument = gWriter.create(document);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseDocument.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Document: " + createResponseDocument.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                        = gWriter.associateTag(createResponseDocument.getItem().getId()
                        , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                            = gWriter.dissociateTag(createResponseDocument.getItem().getId(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseDocument.isSuccess() ) System.err.println("Failed to Create Document: " + createResponseDocument.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractGroupWriterAdapter<Document> gWriter= WriterAdapterFactory.createDocumentWriterAdapter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Document document = mockDocument();
        Host host = mockHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Document, ?> createResponseDocument = gWriter.create(document);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseDocument.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Document: " + createResponseDocument.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                        = gWriter.associateIndicatorHost(createResponseDocument.getItem().getId(), createResponseHost.getItem().getHostName());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseDocument.isSuccess() ) System.err.println("Failed to Create Document: " + createResponseDocument.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }


    private static void doAssociateVictim(Connection conn) {
        AbstractGroupWriterAdapter<Document> gWriter= WriterAdapterFactory.createDocumentWriterAdapter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Document document = mockDocument();
        Victim victim = mockVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Document, ?> createResponseDocument = gWriter.create(document);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseDocument.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Document: " + createResponseDocument.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                        = gWriter.associateVictim(createResponseDocument.getItem().getId(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseDocument.isSuccess() ) System.err.println("Failed to Create Document: " + createResponseDocument.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Document mockDocument()
    {
        Document document = new Document();
        document.setFileName("testFile");
        document.setName("TestName");
        return document;
    }

    private static Attribute mockAttribute() {
        Attribute attribute = new Attribute();
        attribute.setSource("Test Source");
        attribute.setDisplayed(true);
        attribute.setType("Description");
        attribute.setValue("Test Attribute Description");

        return attribute;
    }

    private static Threat mockThreat() {
        Threat threat = new Threat();
        threat.setOwnerName("System");
        threat.setName("Test Threat");

        return threat;
    }


    private static Tag mockTag() {
        Tag tag = new Tag();
        tag.setName("Test-Tag");
        tag.setDescription("Test Tag Description");

        return tag;
    }

    private static Host mockHost() {
        Host host = new Host();
        host.setOwnerName("System");
        host.setDescription("Test Host");
        host.setHostName("www.bad-hostname.com");
        host.setRating(5.0);
        host.setConfidence(98.0);

        return host;
    }


    private static Victim mockVictim() {
        Victim victim = new Victim();
        victim.setOrg("System");
        victim.setName("Test API Victim");
        victim.setDescription("Test API Victim Description");

        return victim;
    }
}
