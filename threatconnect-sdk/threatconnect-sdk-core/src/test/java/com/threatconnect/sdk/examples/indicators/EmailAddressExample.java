package com.threatconnect.sdk.examples.indicators;

import com.threatconnect.sdk.app.SdkAppConfig;
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
import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.client.fluent.EmailAddressBuilder;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.client.fluent.HostBuilder;
import com.threatconnect.sdk.server.entity.Indicator;
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

public class EmailAddressExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            //System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            Configuration config = new Configuration(SdkAppConfig.getInstance(),"https://127.0.0.1:8443/api", "53597229568569709386", "DL6okSIkRFovChG0js9gAC^l6l36G^q6ulZ7wmIlPRlT9FP%IISbrU1uBnJg%uFu",  "System");
            config.setResultLimit(1);
            conn = new Connection(config);
            /*
    		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
    		System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
    		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.wire", "DEBUG");
    		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.conn", "DEBUG");
    		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.impl.client", "DEBUG");
    		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http.client", "DEBUG");
    		System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.http", "DEBUG");    
    		*/        
            doAssociateIndicator(conn);
            //StackTraceElement[] stacks= Thread.currentThread().getStackTrace();
            //new Throwable().getStackTrace();
            //for (StackTraceElement ste : Thread.currentThread().getStackTrace()) {
             //   System.out.println(ste);
           // }
            
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
*/
        } catch (IOException ex ) {
            //System.err.println("Error: " + ex);
            ex.printStackTrace();
        }
    }

    private static void doGet(Connection conn) throws IOException {

        AbstractIndicatorReaderAdapter<EmailAddress> reader = ReaderAdapterFactory.createEmailAddressIndicatorReader(conn);
        IterableResponse<EmailAddress> data;
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
        EmailAddress emailAddress = new EmailAddressBuilder().createEmailAddress();
        emailAddress.setAddress("test2@test.com");
        emailAddress.setDescription("Test EmailAddress");
        emailAddress.setOwnerName("System");

        return emailAddress;
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
        tag.setName("Information Security");
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

    private static void doDissociateTag(Connection conn) {

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
                        = gWriter.dissociateTag(createResponseEmailAddress.getItem().getAddress(), createResponseTag.getItem().getName() );

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
                
                //retrieve email address associated victims
                /*
                AbstractIndicatorReaderAdapter<EmailAddress> reader = ReaderAdapterFactory.createEmailAddressIndicatorReader(conn);
                IterableResponse<Victim> victims = reader.getAssociatedVictims(createResponseEmailAddress.getItem().getAddress());
                for(Victim v : victims) {
                	System.out.println(v.getDescription()+","+v.getName());
                }
                */
                

            } else {
                if ( !createResponseEmailAddress.isSuccess() ) System.err.println("Failed to Create EmailAddress: " + createResponseEmailAddress.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
            ex.printStackTrace();
        }
        
    }

}
