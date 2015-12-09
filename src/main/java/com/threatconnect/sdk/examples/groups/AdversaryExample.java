package com.threatconnect.sdk.examples.groups;

import com.threatconnect.sdk.client.fluent.AdversaryBuilder;
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
import com.threatconnect.sdk.server.entity.Adversary;
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

public class AdversaryExample {

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

        AbstractGroupReaderAdapter<Adversary> reader = ReaderAdapterFactory.createAdversaryGroupReader(conn);
        IterableResponse<Adversary> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Adversarys
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Group g : data) {
                System.out.println("Adversary: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = createTestAdversary();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + adversary.toString());
            ApiEntitySingleResponse<Adversary, ?> response = writer.create(adversary);

            if (response.isSuccess()) {
                Adversary savedAdversary = response.getItem();
                System.out.println("Saved: " + savedAdversary.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = createTestAdversary();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Adversary
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponse = writer.create(adversary);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Adversary
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Adversary, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = createTestAdversary();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponse = writer.create(adversary);
            if (createResponse.isSuccess()) {
                System.out.println("Created Adversary: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update Adversary
                // -----------------------------------------------------------------------------------------------------------
                Adversary updatedAdversary = createResponse.getItem();
                updatedAdversary.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Adversary: " + updatedAdversary);

                ApiEntitySingleResponse<Adversary, ?> updateResponse = writer.update(updatedAdversary);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Adversary: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Adversary: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Adversary: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Adversary createTestAdversary() {
        Adversary adversary = new AdversaryBuilder().createAdversary();
        adversary.setName("Test Adversary");
        adversary.setOwnerName("System");

        return adversary;
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
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = createTestAdversary();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponse = writer.create(adversary);
            if (createResponse.isSuccess()) {
                System.out.println("Created Adversary: " + createResponse.getItem());

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
                System.err.println("Failed to Create Adversary: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> gWriter= WriterAdapterFactory.createAdversaryGroupWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Adversary adversary = createTestAdversary();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponseAdversary = gWriter.create(adversary);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseAdversary.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Adversary: " + createResponseAdversary.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseAdversary.getItem().getId(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAdversary.isSuccess() ) System.err.println("Failed to Create Adversary: " + createResponseAdversary.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> gWriter= WriterAdapterFactory.createAdversaryGroupWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        Adversary adversary = createTestAdversary();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponseAdversary = gWriter.create(adversary);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseAdversary.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created Adversary: " + createResponseAdversary.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseAdversary.getItem().getId(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAdversary.isSuccess() ) System.err.println("Failed to Create Adversary: " + createResponseAdversary.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> gWriter= WriterAdapterFactory.createAdversaryGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Adversary adversary = createTestAdversary();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponseAdversary = gWriter.create(adversary);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseAdversary.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Adversary: " + createResponseAdversary.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseAdversary.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAdversary.isSuccess() ) System.err.println("Failed to Create Adversary: " + createResponseAdversary.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractGroupWriterAdapter<Adversary> gWriter= WriterAdapterFactory.createAdversaryGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Adversary adversary = createTestAdversary();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponseAdversary = gWriter.create(adversary);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseAdversary.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Adversary: " + createResponseAdversary.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseAdversary.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.dissociateTag(createResponseAdversary.getItem().getId(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAdversary.isSuccess() ) System.err.println("Failed to Create Adversary: " + createResponseAdversary.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> gWriter= WriterAdapterFactory.createAdversaryGroupWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Adversary adversary = createTestAdversary();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Adversary and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Adversary, ?> createResponseAdversary = gWriter.create(adversary);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseAdversary.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Adversary: " + createResponseAdversary.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseAdversary.getItem().getId(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAdversary.isSuccess() ) System.err.println("Failed to Create Adversary: " + createResponseAdversary.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
