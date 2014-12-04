package com.cyber2.api.lib.examples.indicators;

import com.cyber2.api.lib.client.reader.AbstractIndicatorReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.AbstractIndicatorWriterAdapter;
import com.cyber2.api.lib.client.writer.TagWriterAdapter;
import com.cyber2.api.lib.client.writer.VictimWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Attribute;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class AddressExample {

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

            doRemoveAssociatedTag(conn);

        } catch (IOException ex ) {
            System.err.println("Error: " + ex);
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    private static void doGet(Connection conn) throws IOException {

        AbstractIndicatorReaderAdapter<Address> reader = ReaderAdapterFactory.createAddressIndicatorReader(conn);
        List<Address> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Addresss
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Indicator g : data) {
                System.out.println("Address: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Address address = createTestAddress();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + address.toString());
            ApiEntitySingleResponse<Address, ?> response = writer.create(address);

            if (response.isSuccess()) {
                Address savedAddress = response.getItem();
                System.out.println("Saved: " + savedAddress.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Address address = createTestAddress();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Address
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponse = writer.create(address);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Address
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Address, ?> deleteResponse = writer.delete(createResponse.getItem().getIp());
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
        AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Address address = createTestAddress();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponse = writer.create(address);
            if (createResponse.isSuccess()) {
                System.out.println("Created Address: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update Address
                // -----------------------------------------------------------------------------------------------------------
                Address updatedAddress = createResponse.getItem();
                updatedAddress.setDescription("UPDATED: " + createResponse.getItem().getDescription());
                System.out.println("Saving Updated Address: " + updatedAddress);

                ApiEntitySingleResponse<Address, ?> updateResponse = writer.update(updatedAddress);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Address: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Address: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Address: " + createResponse.getMessage());
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
        AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Address address = createTestAddress();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponse = writer.create(address);
            if (createResponse.isSuccess()) {
                System.out.println("Created Address: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Add Attribute
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Attribute, ?> attribResponse
                    = writer.addAttribute( createResponse.getItem().getIp(), attribute );

                if ( attribResponse.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + attribResponse.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + attribResponse.getMessage());
                }

            } else {
                System.err.println("Failed to Create Address: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> gWriter= WriterAdapterFactory.createAddressIndicatorWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Address address = createTestAddress();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Address and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponseAddress = gWriter.create(address);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseAddress.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Address: " + createResponseAddress.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseAddress.getItem().getIp(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAddress.isSuccess() ) System.err.println("Failed to Create Address: " + createResponseAddress.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> gWriter= WriterAdapterFactory.createAddressIndicatorWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        Address address = createTestAddress();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponseAddress = gWriter.create(address);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseAddress.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created Address: " + createResponseAddress.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseAddress.getItem().getIp(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAddress.isSuccess() ) System.err.println("Failed to Create Address: " + createResponseAddress.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> gWriter= WriterAdapterFactory.createAddressIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Address address = createTestAddress();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponseAddress = gWriter.create(address);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseAddress.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Address: " + createResponseAddress.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseAddress.getItem().getIp()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAddress.isSuccess() ) System.err.println("Failed to Create Address: " + createResponseAddress.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doRemoveAssociatedTag(Connection conn) {

        AbstractIndicatorWriterAdapter<Address> gWriter= WriterAdapterFactory.createAddressIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Address address = createTestAddress();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponseAddress = gWriter.create(address);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseAddress.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Address: " + createResponseAddress.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseAddress.getItem().getIp()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.deleteAssociatedTag(createResponseAddress.getItem().getIp(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAddress.isSuccess() ) System.err.println("Failed to Create Address: " + createResponseAddress.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> gWriter= WriterAdapterFactory.createAddressIndicatorWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Address address = createTestAddress();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponseAddress = gWriter.create(address);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseAddress.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Address: " + createResponseAddress.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseAddress.getItem().getIp(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseAddress.isSuccess() ) System.err.println("Failed to Create Address: " + createResponseAddress.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
