package com.threatconnect.sdk.examples.indicators;

import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.TagWriterAdapter;
import com.threatconnect.sdk.client.writer.VictimWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.SecurityLabel;
import com.threatconnect.sdk.server.entity.Tag;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.entity.Victim;
import com.threatconnect.sdk.server.response.entity.ApiEntitySingleResponse;

import java.io.IOException;
import java.util.List;

public class HostExample {

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

        AbstractIndicatorReaderAdapter<Host> reader = ReaderAdapterFactory.createHostIndicatorReader(conn);
        List<Host> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Host
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Indicator g : data) {
                System.out.println("Host: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<Host> writer = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Host host = createTestHost();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Host
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + host.toString());
            ApiEntitySingleResponse<Host, ?> response = writer.create(host);

            if (response.isSuccess()) {
                Host savedHost = response.getItem();
                System.out.println("Saved: " + savedHost.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractIndicatorWriterAdapter<Host> writer = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Host host = createTestHost();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponse = writer.create(host);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Host, ?> deleteResponse = writer.delete(createResponse.getItem().getHostName());
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
        AbstractIndicatorWriterAdapter<Host> writer = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Host host = createTestHost();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponse = writer.create(host);
            if (createResponse.isSuccess()) {
                System.out.println("Created Host: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update Host
                // -----------------------------------------------------------------------------------------------------------
                Host updatedHost = createResponse.getItem();
                updatedHost.setDescription("UPDATED: " + createResponse.getItem().getDescription());
                System.out.println("Saving Updated Host: " + updatedHost);

                ApiEntitySingleResponse<Host, ?> updateResponse = writer.update(updatedHost);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Host: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Host: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Host: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Attribute createTestAttribute() {
        Attribute attribute = new Attribute();
        attribute.setSource("Test Source");
        attribute.setDisplayed(true);
        attribute.setType("Description");
        attribute.setValue("Test Attribute Description");

        return attribute;
    }

    private static Host createTestHost() {
        Host host = new Host();
        host.setOwnerName("System");
        host.setDescription("Test Host");
        host.setHostName("www.bad-hostname.com");
        host.setRating( 5.0 );
        host.setConfidence(98.0);

        return host;
    }

    private static Threat createTestThreat() {
        Threat threat = new Threat();
        threat.setOwnerName("System");
        threat.setName("Test Threat");

        return threat;
    }

    private static Tag createTestTag() {
        Tag tag = new Tag();
        tag.setName("Test-Tag");
        tag.setDescription("Test Tag Description");

        return tag;
    }

    private static SecurityLabel createTestSecurityLabel() {
        SecurityLabel securityLabel = new SecurityLabel();
        securityLabel.setName("Test-SecurityLabel");
        securityLabel.setDescription("Test SecurityLabel Description");

        return securityLabel;
    }

    private static Victim createTestVictim() {
        Victim victim = new Victim();
        victim.setOrg("System");
        victim.setName("Test API Victim");
        victim.setDescription("Test API Victim Description");

        return victim;
    }
 
    private static void doAddAttribute(Connection conn) {
        AbstractIndicatorWriterAdapter<Host> writer = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Host host = createTestHost();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponse = writer.create(host);
            if (createResponse.isSuccess()) {
                System.out.println("Created Host: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Add Attribute
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Attribute, ?> attribResponse
                    = writer.addAttribute( createResponse.getItem().getHostName(), attribute );

                if ( attribResponse.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + attribResponse.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + attribResponse.getMessage());
                }

            } else {
                System.err.println("Failed to Create Host: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Address createTestAddress() {
        Address address = new Address();
        address.setIp("127.0.0.1");
        address.setDescription("Test Address");
        address.setOwnerName("System");

        return address;
    }


    private static void doAssociateIndicator(Connection conn) {
        AbstractIndicatorWriterAdapter<Host> gWriter= WriterAdapterFactory.createHostIndicatorWriter(conn);
        AbstractIndicatorWriterAdapter<Address> hWriter = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Host host = createTestHost();
        Address address = createTestAddress();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Host and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponseHost = gWriter.create(host);
            ApiEntitySingleResponse<Address, ?> createResponseAddress = hWriter.create(address);
            if (createResponseHost.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Host: " + createResponseHost.getItem());
                System.out.println("Created Address: " + createResponseAddress.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseHost.getItem().getHostName(), createResponseAddress.getItem().getIp() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Address: " + createResponseAddress.getItem().getIp() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
                if ( !createResponseAddress.isSuccess() ) System.err.println("Failed to Create Address: " + createResponseAddress.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractIndicatorWriterAdapter<Host> gWriter= WriterAdapterFactory.createHostIndicatorWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        Host host = createTestHost();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Host and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponseHost = gWriter.create(host);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseHost.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created Host: " + createResponseHost.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseHost.getItem().getHostName(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractIndicatorWriterAdapter<Host> gWriter= WriterAdapterFactory.createHostIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Host host = createTestHost();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Host and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponseHost = gWriter.create(host);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseHost.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Host: " + createResponseHost.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseHost.getItem().getHostName()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractIndicatorWriterAdapter<Host> gWriter= WriterAdapterFactory.createHostIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Host host = createTestHost();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Host and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponseHost = gWriter.create(host);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseHost.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Host: " + createResponseHost.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseHost.getItem().getHostName()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.dissociateTag(createResponseHost.getItem().getHostName(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractIndicatorWriterAdapter<Host> gWriter= WriterAdapterFactory.createHostIndicatorWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Host host = createTestHost();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Host and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Host, ?> createResponseHost = gWriter.create(host);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseHost.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Host: " + createResponseHost.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseHost.getItem().getHostName(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
