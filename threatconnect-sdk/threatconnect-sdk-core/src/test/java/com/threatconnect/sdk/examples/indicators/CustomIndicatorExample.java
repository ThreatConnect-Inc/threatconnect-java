package com.threatconnect.sdk.examples.indicators;

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
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.entity.CustomIndicatorIdFinder;
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
import java.util.HashMap;

public class CustomIndicatorExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            //System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            Configuration config = new Configuration("https://127.0.0.1:8443/api", "53597229568569709386", "DL6okSIkRFovChG0js9gAC^l6l36G^q6ulZ7wmIlPRlT9FP%IISbrU1uBnJg%uFu",  "System");
            conn = new Connection(config);
           
           
            doGet(conn); //success

            doCreate(conn);//success

            doUpdate(conn);//success

            doDelete(conn);//success

            doAddAttribute(conn);//success

            doAssociateGroup(conn);//success

            doAssociateTag(conn);//success

            doDissociateTag(conn);//success

 			doCustomAssociateIndicator(conn);//success

 /*			
			//old code: doesn't work
            doAssociateIndicator(conn);///POST : api/v2/indicators/registrationkey/7ywx9-w3c2v-d46gw-p722p-9cp4daa/indicators/hosts/www.bad-hostname.com?owner=System: Resource not found, check the requested path. Please review your request and try again. If the problem persists, contact technical support.

			//the function is not implemented according to Morris
            doAssociateVictim(conn);// GET : /api/v2/indicators/registrationkey/7ywx9-w3c2v-d46gw-p722p-9cp4daa/victims/1?owner=System : Resource not found, check the requested path. Please review your request and try again. If the problem persists, contact technical support

*/
        } catch (IOException ex ) {
            //System.err.println("Error: " + ex);
            ex.printStackTrace();
        }
    }

    private static void doGet(Connection conn) throws IOException {

        AbstractIndicatorReaderAdapter<CustomIndicator> reader = ReaderAdapterFactory.createCustomIndicatorReader(conn,"registrationkey");
        IterableResponse<CustomIndicator> data;
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Get CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
        	
            data = reader.getAll();
            for (Indicator g : data) {
                System.out.println("CustomIndicator: " + g.getId());
            }
        } catch (FailedResponseException ex) {
            System.err.println("Error: " + ex);
        }
    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,null);

        CustomIndicator indicator = createTestCustomIndicator();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + indicator.toString());
            ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);

            if (response.isSuccess()) {
            	CustomIndicator savedIndicator = response.getItem();
                System.out.println("Saved: " + savedIndicator.toString()+",id="+savedIndicator.getId());

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
    	AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

    	CustomIndicator indicator = createTestCustomIndicator();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Update CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
            ApiEntitySingleResponse<CustomIndicator, ?> createResponse = writer.create(indicator);
            if (createResponse.isSuccess()) {
                System.out.println("Saved: " + createResponse.getItem());

                // -----------------------------------------------------------------------------------------------------------
                // Delete CustomIndicator
                // -----------------------------------------------------------------------------------------------------------
                createResponse.getItem().setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
                
                ApiEntitySingleResponse<CustomIndicator, ?> deleteResponse = writer.delete(createResponse.getItem().getUniqueId());
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
    	
        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,null);

        CustomIndicator indicator = createTestCustomIndicator();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + indicator.toString());
            ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);

            if (response.isSuccess()) {
            	CustomIndicator savedIndicator = response.getItem();
                System.out.println("Saved: " + savedIndicator.toString()+",id="+savedIndicator.getId());
                // -----------------------------------------------------------------------------------------------------------
                // Update CustomIndicator
                // -----------------------------------------------------------------------------------------------------------
                
                savedIndicator.setDescription("UPDATED: " + response.getItem().getDescription());
                System.out.println("Saving Updated CustomIndicator: " + savedIndicator);

                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
                ApiEntitySingleResponse<CustomIndicator, ?> updateResponse = writer.update(savedIndicator);
                if (updateResponse.isSuccess()) {
                    System.out.println("Updated CustomIndicator: " + updateResponse.getItem());
                } else {
                    System.err.println("Failed to Update CustomIndicator: " + updateResponse.getMessage());
                }
                
            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

        
      
    }

    private static CustomIndicator createTestCustomIndicator() {
    	CustomIndicator indicator = new  CustomIndicator();
    	indicator.setIndicatorType("registrationkey");
    	HashMap<String, String> map = new HashMap<String, String>();
    	map.put("keyentry", "7YWX9-W3C2V-D46GW-P722P-9CP4DAA");
    	indicator.setMap(map);
    	indicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

			@Override
			public String getUniqueId(CustomIndicator indicator) {
				return indicator.getMap().get("keyentry");
			}
    		
    	});
    	return indicator;
    }

    private static EmailAddress createTestEmailAddress() {
        EmailAddress emailAddress = new EmailAddressBuilder().createEmailAddress();
        emailAddress.setAddress("test@test.com");
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
        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

        CustomIndicator indicator = createTestCustomIndicator();
        
        Attribute attribute = createTestAttribute();
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + indicator.toString());
            ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);

            if (response.isSuccess()) {
            	CustomIndicator savedIndicator = response.getItem();
                System.out.println("Saved: " + savedIndicator.toString()+",id="+savedIndicator.getId());
                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
             // -----------------------------------------------------------------------------------------------------------
                // Add Attribute
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse<Attribute, ?> attribResponse
                    = writer.addAttribute( savedIndicator.getUniqueId(), attribute );

                if ( attribResponse.isSuccess() ) {
                    System.out.println("\tAdded Attribute: " + attribResponse.getItem() );
                } else {
                    System.err.println("Failed to Add Attribute: " + attribResponse.getMessage());
                }
                

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }    
        

    }

    private static void doCustomAssociateIndicator(Connection conn) {
    	
        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

        CustomIndicator indicator = createTestCustomIndicator();
        
        Attribute attribute = createTestAttribute();
        
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);
        Host host = createTestHost();
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + indicator.toString());
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);

            if (response.isSuccess() && createResponseHost.isSuccess()) {
            	CustomIndicator savedIndicator = response.getItem();
                System.out.println("Saved: " + savedIndicator.toString()+",id="+savedIndicator.getId());
                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
                
                 System.out.println("unique id ="+savedIndicator.getUniqueId());
             // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = writer.associateCustomIndicatorToIndicator(savedIndicator.getUniqueId(), createResponseHost.getItem().getHostName(),"rkassociation","hosts" );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to associate with host: " + assocResponse.getMessage());
                }
                
                AbstractIndicatorReaderAdapter<CustomIndicator> reader = ReaderAdapterFactory.createCustomIndicatorReader(conn,"registrationkey");
                IterableResponse<? extends Indicator> indicators = reader.getAssociatedIndicatorsForCustomIndicators(savedIndicator.getUniqueId(), "rkassociation");
                for (Indicator g : indicators) {
                    System.out.println("indicator: " + g.getId());
                }
                indicators = reader.getAssociatedIndicatorsForCustomIndicators(savedIndicator.getUniqueId(), "rkassociation","hosts");
                for (Indicator g : indicators) {
                    System.out.println("indicator: " + g.getId());
                }
                
                

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }    
        
      
    }
    
    private static void doAssociateIndicator(Connection conn) {
    	
        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

        CustomIndicator indicator = createTestCustomIndicator();
        
        Attribute attribute = createTestAttribute();
        
        AbstractIndicatorWriterAdapter<Host> hWriter = WriterAdapterFactory.createHostIndicatorWriter(conn);
        Host host = createTestHost();
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
            System.out.println("Before: " + indicator.toString());
            ApiEntitySingleResponse<Host, ?> createResponseHost = hWriter.create(host);
            ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);

            if (response.isSuccess() && createResponseHost.isSuccess()) {
            	CustomIndicator savedIndicator = response.getItem();
                System.out.println("Saved: " + savedIndicator.toString()+",id="+savedIndicator.getId());
                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
                
                savedIndicator.setIndicatorType("registrationkey");
                
             // -----------------------------------------------------------------------------------------------------------
                // Associate Host
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = writer.associateIndicatorHost(savedIndicator.getUniqueId(), createResponseHost.getItem().getHostName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Host: " + createResponseHost.getItem().getHostName() );
                } else {
                    System.err.println("Failed to Add Attribute: " + assocResponse.getMessage());
                }
                

            } else {
                System.err.println("Error: " + response.getMessage());

            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }    
        
      
    }

    private static void doAssociateGroup(Connection conn) {
        AbstractGroupWriterAdapter<Threat> tWriter = WriterAdapterFactory.createThreatGroupWriter(conn);
        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

        CustomIndicator indicator = createTestCustomIndicator();

        Threat threat = createTestThreat();
        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator
            // -----------------------------------------------------------------------------------------------------------
        	ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);
            ApiEntitySingleResponse<Threat, ?> createResponseThreat = tWriter.create(threat);
            if (response.isSuccess() && createResponseThreat.isSuccess() ) {
                System.out.println("Created registration key: " + response.getItem());
                System.out.println("Created Threat: " + createResponseThreat.getItem());
                CustomIndicator savedIndicator = response.getItem();
                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
                
               
                // -----------------------------------------------------------------------------------------------------------
                // Associate Threat
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = writer.associateGroupThreat(savedIndicator.getUniqueId(), createResponseThreat.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Threat: " + createResponseThreat.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Threat: " + assocResponse.getMessage());
                }

            } else {
                if ( !response.isSuccess() ) System.err.println("Failed to Create registration key: " + response.getMessage());
                if ( !createResponseThreat.isSuccess() ) System.err.println("Failed to Create Threat: " + createResponseThreat.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doAssociateTag(Connection conn) {
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Tag tag = createTestTag();

        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

        CustomIndicator indicator = createTestCustomIndicator();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator and Tag 
            // -----------------------------------------------------------------------------------------------------------
        	ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (response.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created registration key: " + response.getItem());
                System.out.println("Created Threat: " + createResponseTag.getItem());
                CustomIndicator savedIndicator = response.getItem();
                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    =writer.associateTag(savedIndicator.getUniqueId(), createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
            	if ( !response.isSuccess() ) System.err.println("Failed to Create registration key: " + response.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
    }

    private static void doDissociateTag(Connection conn) {
        TagWriterAdapter tWriter = WriterAdapterFactory.createTagWriter(conn);

        Tag tag = createTestTag();

        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

        CustomIndicator indicator = createTestCustomIndicator();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create CustomIndicator and Tag 
            // -----------------------------------------------------------------------------------------------------------
        	ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);
            tWriter.delete(tag.getName()); // delete if it exists
            ApiEntitySingleResponse<Tag, ?> createResponseTag = tWriter.create(tag);

            if (response.isSuccess() && createResponseTag.isSuccess() ) {
                System.out.println("Created registration key: " + response.getItem());
                System.out.println("Created Threat: " + createResponseTag.getItem());
                CustomIndicator savedIndicator = response.getItem();
                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
               
                // -----------------------------------------------------------------------------------------------------------
                // Associate Tag
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    =writer.associateTag(savedIndicator.getUniqueId(), createResponseTag.getItem().getName() );

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Tag: " + createResponseTag.getItem().getName() );
                 // -----------------------------------------------------------------------------------------------------------
                    // Delete Association
                    // -----------------------------------------------------------------------------------------------------------
                    ApiEntitySingleResponse deleteAssocResponse
                        = writer.dissociateTag(savedIndicator.getUniqueId(), createResponseTag.getItem().getName() );

                    if ( deleteAssocResponse.isSuccess() ) {
                        System.out.println("\tDeleted Associated Tag: " + createResponseTag.getItem().getName() );
                    } else {
                        System.err.println("Failed to delete Associated Tag: " + deleteAssocResponse.getMessage());
                    }
                } else {
                    System.err.println("Failed to Associate Tag: " + assocResponse.getMessage());
                }

            } else {
            	if ( !response.isSuccess() ) System.err.println("Failed to Create registration key: " + response.getMessage());
                if ( !createResponseTag.isSuccess() ) System.err.println("Failed to Create Tag: " + createResponseTag.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }
        
   
    }

    private static void doAssociateVictim(Connection conn) {
        VictimWriterAdapter vWriter = WriterAdapterFactory.createVictimWriter(conn);
        Victim victim = createTestVictim();
        AbstractIndicatorWriterAdapter<CustomIndicator> writer = WriterAdapterFactory.createCustomIndicatorWriter(conn,"registrationkey");

        CustomIndicator indicator = createTestCustomIndicator();

        try {
            // -----------------------------------------------------------------------------------------------------------
            // Create EmailAddress and Victim
            // -----------------------------------------------------------------------------------------------------------
        	ApiEntitySingleResponse<CustomIndicator, ?> response = writer.create(indicator);
            ApiEntitySingleResponse<Victim, ?> createResponseVictim = vWriter.create(victim);
            if (response.isSuccess() && createResponseVictim.isSuccess() ) {
                System.out.println("Created Victim: " + createResponseVictim.getItem());
                System.out.println("Created registration key: " + response.getItem());
                CustomIndicator savedIndicator = response.getItem();
                savedIndicator.setUniqueIdFinder(new CustomIndicatorIdFinder() {

        			@Override
        			public String getUniqueId(CustomIndicator indicator) {
        				return indicator.getMap().get("keyentry");
        			}
            		
            	});
                
                
                // -----------------------------------------------------------------------------------------------------------
                // Associate Victim
                // -----------------------------------------------------------------------------------------------------------
                ApiEntitySingleResponse assocResponse
                    = writer.associateVictim(savedIndicator.getUniqueId(), createResponseVictim.getItem().getId());

                if ( assocResponse.isSuccess() ) {
                    System.out.println("\tAssociated Victim: " + createResponseVictim.getItem().getId() );
                } else {
                    System.err.println("Failed to Associate Victim: " + assocResponse.getMessage());
                }
                
               
                

            } else {
            	if ( !response.isSuccess() ) System.err.println("Failed to Create registration key: " + response.getMessage());
                if ( !createResponseVictim.isSuccess() ) System.err.println("Failed to Create Victim: " + createResponseVictim.getMessage());
            }

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
            ex.printStackTrace();
        }
        
    }

}
