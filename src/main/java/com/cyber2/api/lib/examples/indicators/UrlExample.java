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
import com.cyber2.api.lib.server.entity.Url;
import com.cyber2.api.lib.server.entity.Host;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.SecurityLabel;
import com.cyber2.api.lib.server.entity.Tag;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.entity.Victim;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class UrlExample {

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

        AbstractIndicatorReaderAdapter<Url> reader = ReaderAdapterFactory.createUrlIndicatorReader(conn);
        List<Url> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get Url
            // -----------------------------------------------------------------------------------------------------------
            data = reader.getAll();
            for (Indicator g : data) {
                System.out.println("Url: " + g);
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<Url> writer = WriterAdapterFactory.createUrlIndicatorWriter(conn);

        Url url = createTestUrl();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Url
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + url.toString());
            ApiEntitySingleResponse<Url, ?> response = writer.create(url);

            if (response.isSuccess()) {
                Url savedUrl = response.getItem();
                System.out.println("Saved: " + savedUrl.toString());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractIndicatorWriterAdapter<Url> writer = WriterAdapterFactory.createUrlIndicatorWriter(conn);

        Url url = createTestUrl();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update Url
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponse = writer.create(url);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete Url
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Url, ?> deleteResponse = writer.delete(createResponse.getItem().getText());
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
        AbstractIndicatorWriterAdapter<Url> writer = WriterAdapterFactory.createUrlIndicatorWriter(conn);

        Url url = createTestUrl();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Url
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponse = writer.create(url);
            if (createResponse.isSuccess()) {
                System.out.println("Created Url: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Update Url
                // -----------------------------------------------------------------------------------------------------------
                Url updatedUrl = createResponse.getItem();
                updatedUrl.setDescription("UPDATED: " + createResponse.getItem().getDescription());
                System.out.println("Saving Updated Url: " + updatedUrl);

                ApiEntitySingleResponse<Url, ?> updateResponse = writer.update(updatedUrl);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated Url: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update Url: " + updateResponse.getMessage());
                }
            } else {
                System.err.println("Failed to Create Url: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Url createTestUrl() {
        Url url = new Url();
        url.setText("http://www.badurl.com");
        url.setDescription("Test Url");
        url.setOwnerName("System");
        url.setRating(4d);
        url.setThreatAssessConfidence(58d);
        url.setThreatAssessRating(5d);

        return url;
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
        host.setHostName("bad-hostname.com");
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
        AbstractIndicatorWriterAdapter<Url> writer = WriterAdapterFactory.createUrlIndicatorWriter(conn);

        Url url = createTestUrl();
        Attribute attribute = createTestAttribute();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Url
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponse = writer.create(url);
            if (createResponse.isSuccess()) {
                System.out.println("Created Url: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Add Attribute
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Attribute, ?> attribResponse
                    = writer.addAttribute( createResponse.getItem().getText(), attribute );

                if ( attribResponse.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + attribResponse.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + attribResponse.getMessage());
                }

            } else {
                System.err.println("Failed to Create Url: " + createResponse.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateIndicator(Connection conn) {
        AbstractIndicatorWriterAdapter<Url> gWriter= WriterAdapterFactory.createUrlIndicatorWriter(conn);
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);

        Url url = createTestUrl();
        Host host = createTestHost();

        try {

            // -----------------------------------------------------------------------------------------------------------
            // Create Url and Host
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponseUrl = gWriter.create(url);
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            if (createResponseUrl.isSuccess() && createResponseHost.isSuccess() ) {
                System.out.println("Created Url: " + createResponseUrl.getItem());
                System.out.println("Created Host: " + createResponseHost.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateIndicatorHost(createResponseUrl.getItem().getText(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseUrl.isSuccess() ) System.err.println("Failed to Create Url: " + createResponseUrl.getMessage());
                if ( !createResponseHost.isSuccess() ) System.err.println("Failed to Create Host: " + createResponseHost.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateGroup(Connection conn) {
        AbstractIndicatorWriterAdapter<Url> gWriter= WriterAdapterFactory.createUrlIndicatorWriter(conn);
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);

        Url url = createTestUrl();
        Threat threat = createTestThreat();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Url and Threat
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponseUrl = gWriter.create(url);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (createResponseUrl.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created Url: " + createResponseUrl.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateGroupThreat(createResponseUrl.getItem().getText(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseUrl.isSuccess() ) System.err.println("Failed to Create Url: " + createResponseUrl.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        AbstractIndicatorWriterAdapter<Url> gWriter= WriterAdapterFactory.createUrlIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Url url = createTestUrl();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Url and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponseUrl = gWriter.create(url);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseUrl.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Url: " + createResponseUrl.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseUrl.getItem().getText()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseUrl.isSuccess() ) System.err.println("Failed to Create Url: " + createResponseUrl.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {

        AbstractIndicatorWriterAdapter<Url> gWriter= WriterAdapterFactory.createUrlIndicatorWriter(conn);
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Url url = createTestUrl();
        Tag tag = createTestTag();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Url and Tag 
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponseUrl = gWriter.create(url);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (createResponseUrl.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created Url: " + createResponseUrl.getItem());
                System.out.println("Created Tag: " + createResponseTag.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateTag(createResponseUrl.getItem().getText()
                                         , createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );

                    // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = gWriter.dissociateTag(createResponseUrl.getItem().getText(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }

                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseUrl.isSuccess() ) System.err.println("Failed to Create Url: " + createResponseUrl.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

    private static void doAssociateVictim(Connection conn) {
        AbstractIndicatorWriterAdapter<Url> gWriter= WriterAdapterFactory.createUrlIndicatorWriter(conn);
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);

        Url url = createTestUrl();
        Victim victim = createTestVictim();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create Url and Victim
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<Url, ?> createResponseUrl = gWriter.create(url);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (createResponseUrl.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Url: " + createResponseUrl.getItem());
                System.out.println("Created Victim: " + createResponseVictim.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = gWriter.associateVictim(createResponseUrl.getItem().getText(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }

            } else {
                if ( !createResponseUrl.isSuccess() ) System.err.println("Failed to Create Url: " + createResponseUrl.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
    }

}
