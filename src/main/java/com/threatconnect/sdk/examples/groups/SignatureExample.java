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
import com.threatconnect.sdk.client.fluent.HostBuilder;
import com.threatconnect.sdk.client.fluent.SecurityLabelBuilder;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.client.fluent.SignatureBuilder;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.client.fluent.TagBuilder;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.client.fluent.ThreatBuilder;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.client.fluent.VictimBuilder;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

import java.io.IOException;

public class SignatureExample {

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
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static void doGet(Connection conn) throws IOException {

        AbstractGroupReaderAdapter<Signature> reader = ReaderAdapterFactory.createSignatureGroupReader(conn);
        IterableResponse<Signature> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Signatures
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Group g : data) {
                System.out.println("Signature: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractGroupWriterAdapter<Signature> writer = WriterAdapterFactory.createSignatureGroupWriter(conn);

        Signature signature = createTestSignature();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Signature
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + signature.toString());
            ApiEntitySingleResponse<Signature, ?> response = writer.create(signature);

            if (response.isSuccess()) {
                Signature savedSignature = response.getItem();
                System.out.println("Saved: " + savedSignature.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Signature> writer = WriterAdapterFactory.createSignatureGroupWriter(conn);

        Signature signature = createTestSignature();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Signature
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponse = writer.create(signature);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Signature
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Signature, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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
        AbstractGroupWriterAdapter<Signature> writer = WriterAdapterFactory.createSignatureGroupWriter(conn);

        Signature signature = createTestSignature();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Signature
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponse = writer.create(signature);
            if (createResponse.isSuccess()) {
                System.out.println("Created Signature: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update Signature
                // -----------------------------------------------------------------------------------------------------------
                Signature updatedSignature = createResponse.getItem();
                updatedSignature.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Signature: " + updatedSignature);

                ApiEntitySingleResponse<Signature, ?> updateResponse = writer.update(updatedSignature);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Signature: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Signature: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Signature: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Signature createTestSignature() {
        Signature signature = new SignatureBuilder().createSignature();
        signature.setName("Test Signature");
        signature.setOwnerName("System");
        signature.setFileName("test-file.txt");
        signature.setFileText("abcdefghijklmnopqrstuvwxyz");
        signature.setFileType("YARA");

        return signature;
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
        AbstractGroupWriterAdapter<Signature> writer = WriterAdapterFactory.createSignatureGroupWriter(conn);

        Signature signature = createTestSignature();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Signature
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponse = writer.create(signature);
            if (createResponse.isSuccess()) {
                System.out.println("Created Signature: " + createResponse.getItem());

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
                System.err.println("Failed to Create Signature: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractGroupWriterAdapter<Signature> gWriter= WriterAdapterFactory.createSignatureGroupWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Signature signature = createTestSignature();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Signature and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponseSignature = gWriter.create(signature);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseSignature.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Signature: " + createResponseSignature.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseSignature.getItem().getId(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseSignature.isSuccess() ) System.err.println("Failed to Create Signature: " + createResponseSignature.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractGroupWriterAdapter<Signature> gWriter= WriterAdapterFactory.createSignatureGroupWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        Signature signature = createTestSignature();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Signature and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponseSignature = gWriter.create(signature);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseSignature.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created Signature: " + createResponseSignature.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseSignature.getItem().getId(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseSignature.isSuccess() ) System.err.println("Failed to Create Signature: " + createResponseSignature.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractGroupWriterAdapter<Signature> gWriter= WriterAdapterFactory.createSignatureGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Signature signature = createTestSignature();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Signature and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponseSignature = gWriter.create(signature);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseSignature.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Signature: " + createResponseSignature.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseSignature.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseSignature.isSuccess() ) System.err.println("Failed to Create Signature: " + createResponseSignature.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractGroupWriterAdapter<Signature> gWriter= WriterAdapterFactory.createSignatureGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Signature signature = createTestSignature();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Signature and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponseSignature = gWriter.create(signature);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseSignature.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Signature: " + createResponseSignature.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseSignature.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.dissociateTag(createResponseSignature.getItem().getId(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseSignature.isSuccess() ) System.err.println("Failed to Create Signature: " + createResponseSignature.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractGroupWriterAdapter<Signature> gWriter= WriterAdapterFactory.createSignatureGroupWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Signature signature = createTestSignature();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Signature and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Signature, ?> createResponseSignature = gWriter.create(signature);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseSignature.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Signature: " + createResponseSignature.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseSignature.getItem().getId(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseSignature.isSuccess() ) System.err.println("Failed to Create Signature: " + createResponseSignature.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
