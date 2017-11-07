package com.threatconnect.sdk.examples.groups;

import com.threatconnect.sdk.client.fluent.AttributeBuilder;
import com.threatconnect.sdk.client.reader.AbstractGroupReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.TagWriterAdapter;
import com.threatconnect.sdk.client.writer.VictimWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.client.fluent.EmailBuilder;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.client.fluent.HostBuilder;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.client.fluent.SecurityLabelBuilder;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.client.fluent.TagBuilder;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.client.fluent.ThreatBuilder;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.client.fluent.VictimBuilder;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

import java.io.IOException;

public class EmailExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

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

    private static void doGet(Connection conn) throws IOException {

        AbstractGroupReaderAdapter<Email> reader = ReaderAdapterFactory.createEmailGroupReader(conn);
        IterableResponse<Email> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Emails
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Group g : data) {
                System.out.println("Email: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractGroupWriterAdapter<Email> writer = WriterAdapterFactory.createEmailGroupWriter(conn);

        Email email = createTestEmail();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Email
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + email.toString());
            ApiEntitySingleResponse<Email, ?> response = writer.create(email);

            if (response.isSuccess()) {
                Email savedEmail = response.getItem();
                System.out.println("Saved: " + savedEmail.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Email> writer = WriterAdapterFactory.createEmailGroupWriter(conn);

        Email email = createTestEmail();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Email
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponse = writer.create(email);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Email
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Email, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
                if (deleteResponse.isSuccess()) {
                    System.out.println("Deleted: " + createResponse.getItem());
                } else {
                    System.err.println("Delete Failed. Cause: " + deleteResponse.getMessage());
                }
            } else {
                System.err.println("Create Failed. Cause: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doUpdate(Connection conn) {
        AbstractGroupWriterAdapter<Email> writer = WriterAdapterFactory.createEmailGroupWriter(conn);

        Email email = createTestEmail();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Email
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponse = writer.create(email);
            if (createResponse.isSuccess()) {
                System.out.println("Created Email: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update Email
                // -----------------------------------------------------------------------------------------------------------
                Email updatedEmail = createResponse.getItem();
                updatedEmail.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Email: " + updatedEmail);

                ApiEntitySingleResponse<Email, ?> updateResponse = writer.update(updatedEmail);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Email: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Email: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Email: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Email createTestEmail() {
        Email email = new EmailBuilder().createEmail();
        email.setName("Test Email");
        email.setOwnerName("System");
        email.setFrom("admin@test.com");
        email.setTo("test@test.com");
        email.setSubject("Test Subject");
        email.setBody("Test Body");
        email.setHeader("Test Header");

        return email;
    }

    private static Attribute createTestAttribute() {
        Attribute attribute = new AttributeBuilder().createAttribute();
        attribute.setSource("Test Source");
        attribute.setDisplayed(true);
        attribute.setType("Description");
        attribute.setValue("Test Attribute Description");

        return attribute;
    }

    private static Host createTestHost() {
        Host host = new HostBuilder().createHost();
        host.setOwnerName("System");
        host.setDescription("Test Host");
        host.setHostName("www.bad-hostname.com");
        host.setRating( 5.0 );
        host.setConfidence(98.0);

        return host;
    }

    private static Threat createTestThreat() {
        Threat threat = new ThreatBuilder().createThreat();
        threat.setOwnerName("System");
        threat.setName("Test Threat");

        return threat;
    }

    private static Tag createTestTag() {
        Tag tag = new TagBuilder().createTag();
        tag.setName("Test-Tag");
        tag.setDescription("Test Tag Description");

        return tag;
    }

    private static SecurityLabel createTestSecurityLabel() {
        SecurityLabel securityLabel = new SecurityLabelBuilder().createSecurityLabel();
        securityLabel.setName("Test-SecurityLabel");
        securityLabel.setDescription("Test SecurityLabel Description");

        return securityLabel;
    }

    private static Victim createTestVictim() {
        Victim victim = new VictimBuilder().createVictim();
        victim.setOrg("System");
        victim.setName("Test API Victim");
        victim.setDescription("Test API Victim Description");

        return victim;
    }
 
    private static void doAddAttribute(Connection conn) {
        AbstractGroupWriterAdapter<Email> writer = WriterAdapterFactory.createEmailGroupWriter(conn);

        Email email = createTestEmail();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Email
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponse = writer.create(email);
            if (createResponse.isSuccess()) {
                System.out.println("Created Email: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Add Attribute
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Attribute, ?> attribResponse
                    = writer.addAttribute( createResponse.getItem().getId(), attribute );

                if ( attribResponse.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + attribResponse.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + attribResponse.getMessage());
                }

            } else {
                System.err.println("Failed to Create Email: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractGroupWriterAdapter<Email> gWriter= WriterAdapterFactory.createEmailGroupWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Email email = createTestEmail();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Email and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponseEmail = gWriter.create(email);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseEmail.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Email: " + createResponseEmail.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseEmail.getItem().getId(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmail.isSuccess() ) System.err.println("Failed to Create Email: " + createResponseEmail.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractGroupWriterAdapter<Email> gWriter= WriterAdapterFactory.createEmailGroupWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        Email email = createTestEmail();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Email and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponseEmail = gWriter.create(email);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseEmail.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created Email: " + createResponseEmail.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseEmail.getItem().getId(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmail.isSuccess() ) System.err.println("Failed to Create Email: " + createResponseEmail.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractGroupWriterAdapter<Email> gWriter= WriterAdapterFactory.createEmailGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Email email = createTestEmail();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Email and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponseEmail = gWriter.create(email);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseEmail.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Email: " + createResponseEmail.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseEmail.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmail.isSuccess() ) System.err.println("Failed to Create Email: " + createResponseEmail.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractGroupWriterAdapter<Email> gWriter= WriterAdapterFactory.createEmailGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Email email = createTestEmail();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Email and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponseEmail = gWriter.create(email);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseEmail.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Email: " + createResponseEmail.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseEmail.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.dissociateTag(createResponseEmail.getItem().getId(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmail.isSuccess() ) System.err.println("Failed to Create Email: " + createResponseEmail.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractGroupWriterAdapter<Email> gWriter= WriterAdapterFactory.createEmailGroupWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Email email = createTestEmail();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Email and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Email, ?> createResponseEmail = gWriter.create(email);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseEmail.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Email: " + createResponseEmail.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseEmail.getItem().getId(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmail.isSuccess() ) System.err.println("Failed to Create Email: " + createResponseEmail.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
