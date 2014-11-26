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
import java.io.IOException;
import java.util.List;

public class GroupExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            doGet(conn);

            doGetById(conn);

            doCreate(conn);

            doUpdate(conn);

            doDelete(conn);

        } catch (IOException | FailedResponseException ex) {
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
            Adversary savedAdversary = writer.create(adversary);
            System.out.println("Saved: " + savedAdversary.toString() );
            
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
            Adversary savedAdversary = writer.create(adversary);
            writer.delete( savedAdversary.getId() );
            System.out.println("Deleted: " + savedAdversary.toString() );
            
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
            Adversary savedAdversary = writer.create(adversary);

            savedAdversary.setName("Updated: " + savedAdversary.getName() );
            writer.update(savedAdversary);
            
        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

}
