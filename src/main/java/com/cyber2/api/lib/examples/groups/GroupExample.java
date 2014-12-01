package com.cyber2.api.lib.examples.groups;

import com.cyber2.api.lib.client.reader.AbstractGroupReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractGroupWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Adversary;
import com.cyber2.api.lib.server.entity.Email;
import com.cyber2.api.lib.server.entity.Group;
import com.cyber2.api.lib.server.entity.Incident;
import com.cyber2.api.lib.server.entity.Signature;
import com.cyber2.api.lib.server.entity.Threat;
import com.cyber2.api.lib.server.response.entity.ApiEntitySingleResponse;
import java.io.IOException;
import java.util.List;

public class GroupExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            //doGet(conn);

            //doGetById(conn);

            //doCreate(conn);

            doUpdate(conn);

            //doDelete(conn);

        } catch (IOException /*| FailedResponseException*/ ex) {
            System.err.println("Error: " + ex);
        } finally {
            if ( conn != null )     conn.disconnect();
        }
    }

    private static void doGet(Connection conn) throws IOException, FailedResponseException {
        AbstractGroupReaderAdapter<Adversary> reader = ReaderAdapterFactory.createAdversaryGroupReader(conn);
        List<Group> data = reader.getGroups();
        for (Group g : data ) {
            System.out.println("Group: " + g);
        }
    }

    private static void doGetById(Connection conn) throws IOException, FailedResponseException {

        AbstractGroupReaderAdapter reader = ReaderAdapterFactory.createAdversaryGroupReader(conn);
        List<Group> data = reader.getGroups();
        for (Group group : data) {
            System.err.println("Checking group.class=" + group.getClass() + ", type=" + group.getType());
            Group result = null;
            switch( Group.Type.valueOf(group.getType()) ) {
                case Adversary:
                    AbstractGroupReaderAdapter<Adversary> adversaryReader 
                        = ReaderAdapterFactory.createAdversaryGroupReader(conn);
                    // "result" is assigned an Adversary object
                    result = adversaryReader.getById(group.getId(), group.getOwnerName());  
                    break;
                case Email:
                    AbstractGroupReaderAdapter<Email> emailReader 
                        = ReaderAdapterFactory.createEmailGroupReader(conn);
                    // "result" is assigned an Email object
                    result = emailReader.getById(group.getId(), group.getOwnerName());  
                    break;
                case Incident:
                    AbstractGroupReaderAdapter<Incident> incidentReader 
                        = ReaderAdapterFactory.createIncidentGroupReader(conn);
                    // "result" is assigned an Incident object
                    result = incidentReader.getById(group.getId(), group.getOwnerName() ); 
                    break;
                case Signature:
                    AbstractGroupReaderAdapter<Signature> sigReader 
                        = ReaderAdapterFactory.createSignatureGroupReader(conn);
                    // "result" is assigned a Signature object
                    result = sigReader.getById(group.getId(), group.getOwnerName() ); 
                    break;
                case Threat:
                    AbstractGroupReaderAdapter<Threat> threatReader 
                        = ReaderAdapterFactory.createThreatGroupReader(conn);
                    // "result" is assigned a Threat object
                    result = threatReader.getById(group.getId(), group.getOwnerName() ); 
                    break;
                default: 
                    System.err.println("Unknown Group Type: " + group.getType() );
                    break;
            }

            assert result.getId().equals(group.getId());
        }

    }

    private static void doCreate(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = new Adversary();
        adversary.setName("Test Adversary");
        adversary.setOwnerName("System");

        try {
            System.out.println("Before: " + adversary.toString() );
            ApiEntitySingleResponse<Adversary,?> response = writer.create(adversary);
            if ( response.isSuccess() ) {
                Adversary savedAdversary = response.getItem();
                System.out.println("Saved: " + savedAdversary.toString() );
            } else {
                System.err.println("Error: " + response.getMessage() );

            }
            
        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doDelete(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = new Adversary();
        adversary.setName("Test Adversary");
        adversary.setOwnerName("System");

        try {
            ApiEntitySingleResponse<Adversary,?> createResponse = writer.create(adversary);
            if ( createResponse.isSuccess() ) {
                System.out.println("Saved: " + createResponse.getItem() );
                ApiEntitySingleResponse<Adversary,?> deleteResponse = writer.delete( createResponse.getItem().getId() );
                if ( deleteResponse.isSuccess() ) {
                    System.out.println("Deleted: " + createResponse.getItem() );
                } else {
                    System.err.println("Delete Failed. Cause: " + deleteResponse.getMessage() );
                }
            } else {
                System.err.println("Create Failed. Cause: " + createResponse.getMessage() );
            }
            
        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static void doUpdate(Connection conn) {
        AbstractGroupWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryGroupWriter(conn);

        Adversary adversary = new Adversary();
        adversary.setName("Test Adversary");
        adversary.setOwnerName("System");

        try {
            ApiEntitySingleResponse<Adversary,?> createResponse = writer.create(adversary);
            if ( createResponse.isSuccess() ) {
                System.out.println("Created Adversary: " + createResponse.getItem() );

                Adversary updatedAdversary = createResponse.getItem();
                updatedAdversary.setName("UPDATED: " + createResponse.getItem().getName() );
                System.out.println("Saving Updated Adversary: " + updatedAdversary );

                ApiEntitySingleResponse<Adversary,?> updateResponse = writer.update( updatedAdversary );
                if ( updateResponse.isSuccess() ) {
                    System.out.println("Updated Adversary: " + updateResponse.getItem() );
                } else {
                    System.err.println("Failed to Update Adversary: " + updateResponse.getMessage() );
                }
            } else {
                System.err.println("Failed to Create Adversary: " + createResponse.getMessage() );
            }
            
        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

}
