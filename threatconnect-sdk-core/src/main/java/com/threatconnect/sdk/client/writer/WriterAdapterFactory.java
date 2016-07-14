/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.writer;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.*;
import com.threatconnect.sdk.server.response.entity.*;

/**
 *
 * @author dtineo
 */
public class WriterAdapterFactory {

    public static AbstractGroupWriterAdapter<Adversary> createAdversaryGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter<Adversary>(conn, AdversaryResponse.class) {
            @Override
            public String getUrlType() {
                return "adversaries";
            }
        };
    }

    public static AbstractGroupWriterAdapter<Email> createEmailGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter<Email>(conn, EmailResponse.class) {
            @Override
            public String getUrlType() {
                return "emails";
            }
        };
    }

    public static AbstractGroupWriterAdapter<Incident> createIncidentGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter<Incident>(conn, IncidentResponse.class) {
            @Override
            public String getUrlType() {
                return "incidents";
            }
        };
    }

    public static AbstractGroupWriterAdapter<Signature> createSignatureGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter<Signature>(conn, SignatureResponse.class) {
            @Override
            public String getUrlType() {
                return "signatures";
            }
        };
    }
    
    public static AbstractGroupWriterAdapter<Threat> createThreatGroupWriter(Connection conn) {
        return new AbstractGroupWriterAdapter<Threat>(conn, ThreatResponse.class) {
            @Override
            public String getUrlType() {
                return "threats";
            }
        };
    }

    public static AbstractIndicatorWriterAdapter<Address> createAddressIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<Address>(conn, AddressResponse.class) {
            @Override
            public String getUrlType() {
                return "addresses";
            }

            @Override
            public String getId(Address indicator) {
                return indicator.getIp();
            }
        };
    }

    public static AbstractIndicatorWriterAdapter<EmailAddress> createEmailAddressIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<EmailAddress>(conn, EmailAddressResponse.class) {
            @Override
            public String getUrlType() {
                return "emailAddresses";
            }

            @Override
            public String getId(EmailAddress indicator) {
                return indicator.getAddress();
            }
        };
    }
    
    public static FileIndicatorWriterAdapter createFileIndicatorWriter(Connection conn) {
        return new FileIndicatorWriterAdapter(conn);
 
    }

    public static AbstractIndicatorWriterAdapter<Host> createHostIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<Host>(conn, HostResponse.class) {
            @Override
            public String getUrlType() {
                return "hosts";
            }
            @Override
            public String getId(Host indicator) {
                return indicator.getHostName();
            }
        };
    }

    public static AbstractIndicatorWriterAdapter<Url> createUrlIndicatorWriter(Connection conn) {
        return new AbstractIndicatorWriterAdapter<Url>(conn, UrlResponse.class) {
            @Override
            public String getUrlType() {
                return "urls";
            }

            @Override
            public String getId(Url indicator) {
                return indicator.getText();
            }
        };
    }

    public static SecurityLabelWriterAdapter createSecurityLabelWriter(Connection conn) {
        return new SecurityLabelWriterAdapter(conn);
    }

    public static TagWriterAdapter createTagWriter(Connection conn) {
        return new TagWriterAdapter(conn);
    }

    public static VictimWriterAdapter createVictimWriter(Connection conn) {
        return new VictimWriterAdapter(conn);
    }


    public static AbstractIndicatorWriterAdapter createIndicatorWriter(String type, Connection conn) {
        if (Character.isLowerCase(type.charAt(0))) {
            String indType = Character.toLowerCase(type.charAt(0)) + type.substring(1);
            return createIndicatorWriter(Indicator.Type.valueOf(indType), conn);
        } else {
            return createIndicatorWriter(Indicator.Type.valueOf(type), conn);
        }

    }

    public static AbstractIndicatorWriterAdapter createIndicatorWriter(Indicator.Type type, Connection conn) {

        switch (type) {
            case Address:
                return createAddressIndicatorWriter(conn);
            case EmailAddress:
                return createEmailAddressIndicatorWriter(conn);
            case File:
                return createFileIndicatorWriter(conn);
            case Host:
                return createHostIndicatorWriter(conn);
            case Url:
                return createUrlIndicatorWriter(conn);
            default:
                return null;
        }
    }

    public static AbstractGroupWriterAdapter createGroupWriter(Group.Type type, Connection conn) {

        switch (type) {
            case Adversary:
                return createAdversaryGroupWriter(conn);
            case Document:
            	return createDocumentWriter(conn);
            case Email:
                return createEmailGroupWriter(conn);
            case Incident:
                return createIncidentGroupWriter(conn);
            case Signature:
                return createSignatureGroupWriter(conn);
            case Threat:
                return createThreatGroupWriter(conn);
            case Task:
                return createTaskWriter(conn);
            default:
                return null;
        }
    }

    public static DocumentWriterAdapter createDocumentWriter(Connection conn) {
        return new DocumentWriterAdapter(conn);
    }

    public static AbstractBatchWriterAdapter<Indicator> createBatchIndicatorWriter(Connection conn) {
        return new AbstractBatchWriterAdapter<Indicator>(conn)
        {
            @Override
            public String getUrlType()
            {
                return "indicators";
            }
        };
    }

    public static ExchangeWriterAdapter createExchangeWriter(Connection conn) {
        return new ExchangeWriterAdapter(conn);
    }

    public static TaskWriterAdapter createTaskWriter(Connection conn) {
        return new TaskWriterAdapter(conn);
    }

    public static DataStoreWriterAdapater createDataStoreWriterAdapter(Connection conn)
    {
        return new DataStoreWriterAdapater(conn);
    }
}
