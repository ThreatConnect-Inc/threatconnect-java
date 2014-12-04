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
import com.cyber2.api.lib.server.entity.EmailAddress;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class EmailAddressExample {

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

        AbstractIndicatorReaderAdapter<EmailAddress> reader = ReaderAdapterFactory.createEmailAddressIndicatorReader(conn);
        List<EmailAddress> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get EmailAddress
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Indicator g : data) {
                System.out.println("EmailAddress: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<EmailAddress> writer = WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + emailAddress.toString());
            ApiEntitySingleResponse<EmailAddress, ?> response = writer.create(emailAddress);

            if (response.isSuccess()) {
                EmailAddress savedEmailAddress = response.getItem();
                System.out.println("Saved: " + savedEmailAddress.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractIndicatorWriterAdapter<EmailAddress> writer = WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update EmailAddress
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponse = writer.create(emailAddress);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete EmailAddress
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<EmailAddress, ?> deleteResponse = writer.delete(createResponse.getItem().getAddress());
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
        AbstractIndicatorWriterAdapter<EmailAddress> writer = WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponse = writer.create(emailAddress);
            if (createResponse.isSuccess()) {
                System.out.println("Created EmailAddress: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update EmailAddress
                // -----------------------------------------------------------------------------------------------------------
                EmailAddress updatedEmailAddress = createResponse.getItem();
                updatedEmailAddress.setDescription("UPDATED: " + createResponse.getItem().getDescription());
                System.out.println("Saving Updated EmailAddress: " + updatedEmailAddress);

                ApiEntitySingleResponse<EmailAddress, ?> updateResponse = writer.update(updatedEmailAddress);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated EmailAddress: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update EmailAddress: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create EmailAddress: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static EmailAddress createTestEmailAddress() {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setAddress("test@test.com");
        emailAddress.setDescription("Test EmailAddress");
        emailAddress.setOwnerName("System");

        return emailAddress;
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
        AbstractIndicatorWriterAdapter<EmailAddress> writer = WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponse = writer.create(emailAddress);
            if (createResponse.isSuccess()) {
                System.out.println("Created EmailAddress: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Add Attribute
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Attribute, ?> attribResponse
                    = writer.addAttribute( createResponse.getItem().getAddress(), attribute );

                if ( attribResponse.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + attribResponse.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + attribResponse.getMessage());
                }

            } else {
                System.err.println("Failed to Create EmailAddress: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractIndicatorWriterAdapter<EmailAddress> gWriter= WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponseEmailAddress = gWriter.create(emailAddress);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseEmailAddress.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created EmailAddress: " + createResponseEmailAddress.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseEmailAddress.getItem().getAddress(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmailAddress.isSuccess() ) System.err.println("Failed to Create EmailAddress: " + createResponseEmailAddress.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractIndicatorWriterAdapter<EmailAddress> gWriter= WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponseEmailAddress = gWriter.create(emailAddress);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseEmailAddress.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created EmailAddress: " + createResponseEmailAddress.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseEmailAddress.getItem().getAddress(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmailAddress.isSuccess() ) System.err.println("Failed to Create EmailAddress: " + createResponseEmailAddress.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractIndicatorWriterAdapter<EmailAddress> gWriter= WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponseEmailAddress = gWriter.create(emailAddress);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseEmailAddress.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created EmailAddress: " + createResponseEmailAddress.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseEmailAddress.getItem().getAddress()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmailAddress.isSuccess() ) System.err.println("Failed to Create EmailAddress: " + createResponseEmailAddress.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doRemoveAssociatedTag(Connection conn) {

        AbstractIndicatorWriterAdapter<EmailAddress> gWriter= WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponseEmailAddress = gWriter.create(emailAddress);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseEmailAddress.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created EmailAddress: " + createResponseEmailAddress.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseEmailAddress.getItem().getAddress()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.deleteAssociatedTag(createResponseEmailAddress.getItem().getAddress(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmailAddress.isSuccess() ) System.err.println("Failed to Create EmailAddress: " + createResponseEmailAddress.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractIndicatorWriterAdapter<EmailAddress> gWriter= WriterAdapterFactory.createEmailAddressIndicatorWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        EmailAddress emailAddress = createTestEmailAddress();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<EmailAddress, ?> createResponseEmailAddress = gWriter.create(emailAddress);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseEmailAddress.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created EmailAddress: " + createResponseEmailAddress.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseEmailAddress.getItem().getAddress(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseEmailAddress.isSuccess() ) System.err.println("Failed to Create EmailAddress: " + createResponseEmailAddress.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
