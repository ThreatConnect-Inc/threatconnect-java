package com.threatconnect.sdk.examples.indicators;

import com.threatconnect.sdk.app.SdkAppConfig;
import com.threatconnect.sdk.client.fluent.AddressBuilder;
import com.threatconnect.sdk.client.fluent.AttributeBuilder;
import com.threatconnect.sdk.client.reader.AbstractIndicatorReaderAdapter;
import com.threatconnect.sdk.client.response.IterableResponse;
import com.threatconnect.sdk.client.reader.ReaderAdapterFactory;
import com.threatconnect.sdk.client.writer.AbstractGroupWriterAdapter;
import com.threatconnect.sdk.client.writer.AbstractIndicatorWriterAdapter;
import com.threatconnect.sdk.client.writer.TagWriterAdapter;
import com.threatconnect.sdk.client.writer.VictimWriterAdapter;
import com.threatconnect.sdk.client.writer.WriterAdapterFactory;
import com.threatconnect.sdk.config.Configuration;
import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.exception.FailedResponseException;
import com.threatconnect.sdk.server.entity.Attribute;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.FalsePositive;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.client.fluent.HostBuilder;
import com.threatconnect.sdk.server.entity.Observation;
import com.threatconnect.sdk.server.entity.ObservationCount;
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

public class AddressExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            Configuration config = new Configuration(SdkAppConfig.getInstance(), "https://127.0.0.1:8443/api", "53597229568569709386", "DL6okSIkRFovChG0js9gAC^l6l36G^q6ulZ7wmIlPRlT9FP%IISbrU1uBnJg%uFu",  "System");
            config.setResultLimit(1);
            conn = new Connection(config);

            /*
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
             doObservationCountAndFalsePositive(conn);
            */

            doAssociateIndicator(conn);

        } catch (IOException ex ) {
            System.err.println("Error: " + ex);
        }
    }


    private static void doGet(Connection conn) throws IOException {

        AbstractIndicatorReaderAdapter<Address> reader = ReaderAdapterFactory.createAddressIndicatorReader(conn);
        IterableResponse<Address> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Addresss
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Address g : data) {
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
        Address address = new AddressBuilder().createAddress();
        address.setIp("127.0.0.1");
        address.setDescription("Test Address");
        address.setOwnerName("System");

        return address;
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

    private static void doDissociateTag(Connection conn) {

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
                        = gWriter.dissociateTag(createResponseAddress.getItem().getIp(), createResponseTag.getItem().getName() );

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

    private static void doObservationCountAndFalsePositive(Connection conn)
    {
        AbstractIndicatorReaderAdapter<Address> iReader = ReaderAdapterFactory.createAddressIndicatorReader(conn);
        AbstractIndicatorWriterAdapter<Address> iWriter = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Address address = createTestAddress();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Address
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Address, ?> createResponseAddress = iWriter.create(address);
            if ( createResponseAddress.isSuccess() )
            {
                System.out.println("Created Address: " + createResponseAddress.getItem());

                if ( iWriter.updateFalsePositive(address.getIp()).isSuccess() )
                {
                    System.err.println("Created False Positive");
                }

                FalsePositive falsePositive = iReader.getFalsePositive(address.getIp());
                System.err.println("Read False Positive: " + falsePositive.toString() );

                if ( iWriter.createObservation( address.getIp() ).isSuccess() )
                {
                    System.err.println("Created Observation");
                }

                for (Observation observation : iReader.getObservations(address.getIp()) )
                {
                    System.err.println("Read Observation: " + observation.toString());
                }

                ObservationCount observationCount = iReader.getObservationCount(address.getIp());
                System.err.println("Read ObservationCount: " + observationCount.toString() );

            }


    } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }
}
