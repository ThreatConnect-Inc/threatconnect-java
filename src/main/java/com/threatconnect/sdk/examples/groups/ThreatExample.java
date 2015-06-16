package com.threatconnect.sdk.examples.groups;

import com.threatconnect.sdk.client.fluent.AttributeBuilder;
import com.threatconnect.sdk.client.reader.AbstractGroupReaderAdapter;
import com.threatconnect.sdk.client.reader.IterableResponse;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.TagWriterAdapter;
import com.threatconnect.sdk.client.writer.VictimWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.client.fluent.HostBuilder;
import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.client.fluent.IncidentBuilder;
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

public class ThreatExample {

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

        AbstractGroupReaderAdapter<Threat> reader = ReaderAdapterFactory.createThreatGroupReader(conn);
        IterableResponse<Threat> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Threats
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Group g : data) {
                System.out.println("Threat: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractGroupWriterAdapter<Threat> writer = WriterAdapterFactory.createThreatGroupWriter(conn);

        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + threat.toString());
            ApiEntitySingleResponse<Threat, ?> response = writer.create(threat);

            if (response.isSuccess()) {
                Threat savedThreat = response.getItem();
                System.out.println("Saved: " + savedThreat.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Threat> writer = WriterAdapterFactory.createThreatGroupWriter(conn);

        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponse = writer.create(threat);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Threat, ?> deleteResponse = writer.delete(createResponse.getItem().getId());
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
        AbstractGroupWriterAdapter<Threat> writer = WriterAdapterFactory.createThreatGroupWriter(conn);

        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponse = writer.create(threat);
            if (createResponse.isSuccess()) {
                System.out.println("Created Threat: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update Threat
                // -----------------------------------------------------------------------------------------------------------
                Threat updatedThreat = createResponse.getItem();
                updatedThreat.setName("UPDATED: " + createResponse.getItem().getName());
                System.out.println("Saving Updated Threat: " + updatedThreat);

                ApiEntitySingleResponse<Threat, ?> updateResponse = writer.update(updatedThreat);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Threat: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Threat: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Threat: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Threat createTestThreat() {
        Threat threat = new ThreatBuilder().createThreat();
        threat.setName("Test Threat");
        threat.setOwnerName("System");

        return threat;
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

    private static Incident createTestIncident() {
        Incident incident = new IncidentBuilder().createIncident();
        incident.setOwnerName("System");
        incident.setName("Test Incident");

        return incident;
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
        AbstractGroupWriterAdapter<Threat> writer = WriterAdapterFactory.createThreatGroupWriter(conn);

        Threat threat = createTestThreat();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponse = writer.create(threat);
            if (createResponse.isSuccess()) {
                System.out.println("Created Threat: " + createResponse.getItem());

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
                System.err.println("Failed to Create Threat: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractGroupWriterAdapter<Threat> gWriter= WriterAdapterFactory.createThreatGroupWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Threat threat = createTestThreat();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = gWriter.create(threat);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseThreat.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Threat: " + createResponseThreat.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseThreat.getItem().getId(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractGroupWriterAdapter<Threat> gWriter= WriterAdapterFactory.createThreatGroupWriter(conn);
        AbstractGroupWriterAdapter<Incident> tWriter = WriterAdapterFactory.createIncidentGroupWriter(conn);

        Threat threat = createTestThreat();
        Incident incident = createTestIncident();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = gWriter.create(threat);
            ApiEntitySingleResponse<Incident, ?> createResponseIncident = tWriter.create(incident);
            if (createResponseThreat.isSuccess() && createResponseIncident.isSuccess() ) {
                System.out.println("Created Threat: " + createResponseThreat.getItem());
                System.out.println("Created Incident: " + createResponseIncident.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupIncident(createResponseThreat.getItem().getId(), createResponseIncident.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Incident: " + createResponseIncident.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Incident: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
                if ( !createResponseIncident.isSuccess() ) System.err.println("Failed to Create Incident: " + createResponseIncident.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractGroupWriterAdapter<Threat> gWriter= WriterAdapterFactory.createThreatGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Threat threat = createTestThreat();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = gWriter.create(threat);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseThreat.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Threat: " + createResponseThreat.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseThreat.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractGroupWriterAdapter<Threat> gWriter= WriterAdapterFactory.createThreatGroupWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Threat threat = createTestThreat();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = gWriter.create(threat);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseThreat.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Threat: " + createResponseThreat.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseThreat.getItem().getId()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.dissociateTag(createResponseThreat.getItem().getId(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractGroupWriterAdapter<Threat> gWriter= WriterAdapterFactory.createThreatGroupWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Threat threat = createTestThreat();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Threat and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = gWriter.create(threat);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseThreat.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Threat: " + createResponseThreat.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseThreat.getItem().getId(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
