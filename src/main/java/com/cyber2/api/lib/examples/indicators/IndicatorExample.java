package com.cyber2.api.lib.examples.indicators;

import com.cyber2.api.lib.client.reader.AbstractIndicatorReaderAdapter;
import com.cyber2.api.lib.client.reader.ReaderAdapterFactory;
import com.cyber2.api.lib.client.writer.AbstractIndicatorWriterAdapter;
import com.cyber2.api.lib.client.writer.TagWriterAdapter;
import com.cyber2.api.lib.client.writer.WriterAdapterFactory;
import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.exception.FailedResponseException;
import com.cyber2.api.lib.server.entity.Address;
import com.cyber2.api.lib.server.entity.Indicator;
import com.cyber2.api.lib.server.entity.Tag;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class IndicatorExample {

    public static void main(String[] args) {

        Connection conn = null;

        try {

            System.getProperties().setProperty("threatconnect.api.config", "/config.properties");
            conn = new Connection();

            //doGet(conn);

            //doGetById(conn);
            doGetAssociatedTags(conn);

            //doCreate(conn);
            //doAssociateTag(conn);

            //doUpdate(conn);

            //doDelete(conn);

        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex);
        } finally {
            if ( conn != null )     conn.disconnect();
        }
    }

    private static void doGet(Connection conn) throws IOException, FailedResponseException {
        AbstractIndicatorReaderAdapter<Address> reader = ReaderAdapterFactory.createAddressIndicatorReader(conn);
        List<Address> data = reader.getAll();
        for (Address g : data ) {
            System.out.println("Address: " + g);
        }
    }

    private static void doGetAssociatedTags(Connection conn) throws IOException, FailedResponseException {
        AbstractIndicatorReaderAdapter reader = ReaderAdapterFactory.createAddressIndicatorReader(conn);
        List<Address> data = reader.getAll();
        for (Address address : data) {
            System.out.printf("IP Address: %20s", address.getIp() ); 

            List<Tag> associatedTags = reader.getAssociatedTags( address.getIp() );
             System.out.printf("\tAssociated Tag:"); 
            for(Tag tag : associatedTags) {
                System.out.printf("%20s", tag.getName() );
            }
            System.out.println();
        }
    }

    private static void doGetById(Connection conn) throws IOException, FailedResponseException {

        AbstractIndicatorReaderAdapter reader = ReaderAdapterFactory.createAddressIndicatorReader(conn);
        List<Indicator> data = reader.getIndicators();
        for (Indicator indicator : data) {
            Indicator result = null;
            switch( Indicator.Type.valueOf(indicator.getType()) ) {
                case Address:
                    AbstractIndicatorReaderAdapter<Address> addressReader 
                        = ReaderAdapterFactory.createAddressIndicatorReader(conn);
                    Address address = (Address)indicator;
                    // "result" is assigned an Address object
                    result = addressReader.getById(address.getIp(), indicator.getOwnerName());  
                    break;
                    /*
                case EmailAddress:
                    AbstractIndicatorReaderAdapter<Email> emailReader 
                        = ReaderAdapterFactory.createEmailIndicatorReader(conn);
                    // "result" is assigned an Email object
                    result = emailReader.getById(group.getId(), group.getOwnerName());  
                    break;
                case File:
                    AbstractIndicatorReaderAdapter<Incident> incidentReader 
                        = ReaderAdapterFactory.createIncidentIndicatorReader(conn);
                    // "result" is assigned an Incident object
                    result = incidentReader.getById(group.getId(), group.getOwnerName() ); 
                    break;
                case Host:
                    AbstractIndicatorReaderAdapter<Signature> sigReader 
                        = ReaderAdapterFactory.createSignatureIndicatorReader(conn);
                    // "result" is assigned a Signature object
                    result = sigReader.getById(group.getId(), group.getOwnerName() ); 
                    break;
                case Url:
                    AbstractIndicatorReaderAdapter<Threat> threatReader 
                        = ReaderAdapterFactory.createThreatIndicatorReader(conn);
                    // "result" is assigned a Threat object
                    result = threatReader.getById(group.getId(), group.getOwnerName() ); 
                    break;
                    */
                default: 
                    System.err.println("Unknown Indicator Type: " + indicator.getType() );
                    break;
            }

            assert result.getId().equals(indicator.getId());
        }

    }

    private static void doAssociateTag(Connection conn) throws IOException, FailedResponseException {

        TagWriterAdapter tagWriter = WriterAdapterFactory.createTagWriter(conn);

        Tag t = new Tag();
        t.setName("testTag1");
        t.setDescription("testTag1 Description");
        //tagWriter.create(t);

        AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);
        //writer.associateTag("127.0.0.1", t.getName());

        t = new Tag();
        t.setName("testTag2");
        t.setDescription("testTag2 Description");
        //tagWriter.create(t);
        //writer.associateTag("127.0.0.2", t.getName());

        t = new Tag();
        t.setName("testTag3");
        t.setDescription("testTag3 Description");
        tagWriter.create(t);
        writer.associateTag("127.0.0.3", t.getName());
        
    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Address address = createAddress("127.0.0.3");

        try {
            Address saved = writer.create(address, "System");
            System.out.println("Saved: " + saved.toString() );
            
        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Address createAddress(String ipAddress) {
        Address address = new Address();
        address.setConfidence( (short)45 );
        address.setDescription("This is a test of IP Address: " + ipAddress );
        address.setIp(ipAddress);
        address.setRating(BigDecimal.valueOf(99));
        address.setOwnerName("System");

        return address;
    }

    /*
    private static void doDelete(Connection conn) {
        AbstractIndicatorWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryIndicatorWriter(conn);

        Adversary adversary = new Adversary();
        adversary.setName("Test Adversary");
        adversary.setOwnerName("System");


    }

    private static void doCreate(Connection conn) {
        AbstractIndicatorWriterAdapter<Address> writer = WriterAdapterFactory.createAddressIndicatorWriter(conn);

        Address address = createAddress("127.0.0.3");

        try {
            Address saved = writer.create(address, "System");
            System.out.println("Saved: " + saved.toString() );
            
        } catch (IOException | FailedResponseException ex) {
            System.err.println("Error: " + ex.toString());
        }

    }

    private static Address createAddress(String ipAddress) {
        Address address = new Address();
        address.setConfidence( (short)45 );
        address.setDescription("This is a test of IP Address: " + ipAddress );
        address.setIp(ipAddress);
        address.setRating(BigDecimal.valueOf(99));
        address.setOwnerName("System");

        return address;
    }

    /*
    private static void doDelete(Connection conn) {
        AbstractIndicatorWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryIndicatorWriter(conn);

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
    */

    /*
    private static void doUpdate(Connection conn) {
        AbstractIndicatorWriterAdapter<Adversary> writer = WriterAdapterFactory.createAdversaryIndicatorWriter(conn);

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
    */

}
