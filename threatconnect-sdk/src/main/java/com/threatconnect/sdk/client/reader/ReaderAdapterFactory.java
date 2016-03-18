/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.*;
import com.threatconnect.sdk.server.response.entity.*;

/**
 *
 * @author dtineo
 */
public class ReaderAdapterFactory {

    public static AbstractGroupReaderAdapter<Task> createTaskGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Task>(conn, TaskResponse.class, Task.class, TaskListResponse.class) {
            @Override
            public String getUrlType() { return "tasks"; }
        };
    }

    public static AbstractGroupReaderAdapter<Adversary> createAdversaryGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Adversary>(conn, AdversaryResponse.class, Adversary.class, AdversaryListResponse.class) {
            @Override
            public String getUrlType() {
                return "adversaries";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Email> createEmailGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Email>(conn, EmailResponse.class, Email.class, EmailListResponse.class) {
            @Override
            public String getUrlType() {
                return "emails";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Incident> createIncidentGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Incident>(conn, IncidentResponse.class, Incident.class, IncidentListResponse.class) {
            @Override
            public String getUrlType() {
                return "incidents";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Signature> createSignatureGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Signature>(conn, SignatureResponse.class, Signature.class, SignatureListResponse.class) {
            @Override
            public String getUrlType() {
                return "signatures";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Threat> createThreatGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Threat>(conn, ThreatResponse.class, Threat.class, ThreatListResponse.class) {
            @Override
            public String getUrlType() {
                return "threats";
            }
        };
    }

    public static AbstractIndicatorReaderAdapter<Address> createAddressIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<Address>(conn, AddressResponse.class, Address.class, AddressListResponse.class) {
            @Override
            public String getUrlType() {
                return "addresses";
            }
        };
    }

    public static AbstractIndicatorReaderAdapter<EmailAddress> createEmailAddressIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<EmailAddress>(conn, EmailAddressResponse.class, EmailAddress.class, EmailAddressListResponse.class) {
            @Override
            public String getUrlType() {
                return "emailAddresses";
            }
        };
    }

    public static FileIndicatorReaderAdapter createFileIndicatorReader(Connection conn) {
        return new FileIndicatorReaderAdapter(conn);
    }

    public static AbstractIndicatorReaderAdapter<Host> createHostIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<Host>(conn, HostResponse.class, Host.class, HostListResponse.class) {
            @Override
            public String getUrlType() {
                return "hosts";
            }
        };
    }

    public static AbstractIndicatorReaderAdapter<Url> createUrlIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<Url>(conn, UrlResponse.class, Url.class, UrlListResponse.class) {
            @Override
            public String getUrlType() {
                return "urls";
            }
        };
    }

    public static OwnerReaderAdapter createOwnerReader(Connection conn) {
        return new OwnerReaderAdapter(conn) {
        };
    }

    public static SecurityLabelReaderAdapter createSecurityLabelReader(Connection conn) {
        return new SecurityLabelReaderAdapter(conn);
    }

    public static TagReaderAdapter createTagReader(Connection conn) {
        return new TagReaderAdapter(conn);
    }

    public static VictimReaderAdapter createVictimReader(Connection conn) {
        return new VictimReaderAdapter(conn);
    }

    public static AbstractGroupReaderAdapter createGroupReader(String type, Connection conn) {
        return createGroupReader(Group.Type.valueOf(type), conn);
    }

    public static AbstractGroupReaderAdapter createGroupReader(Group.Type type, Connection conn) {

        switch (type) {
            case Adversary:
                return createAdversaryGroupReader(conn);
            case Email:
                return createEmailGroupReader(conn);
            case Incident:
                return createIncidentGroupReader(conn);
            case Signature:
                return createSignatureGroupReader(conn);
            case Threat:
                return createThreatGroupReader(conn);
            case Task:
                return createTaskGroupReader(conn);
            default:
                return null;
        }

    }

    public static AbstractIndicatorReaderAdapter createIndicatorReader(String type, Connection conn) {
        if (Character.isLowerCase(type.charAt(0))) {
            String indType = Character.toLowerCase(type.charAt(0)) + type.substring(1);
            return createIndicatorReader(Indicator.Type.valueOf(indType), conn);
        } else {
            return createIndicatorReader(Indicator.Type.valueOf(type), conn);
        }

    }

    public static AbstractIndicatorReaderAdapter createIndicatorReader(Indicator.Type type, Connection conn) {

        if ( type == null )
        {
            return null;
        }

        switch (type) {
            case Address:
                return createAddressIndicatorReader(conn);
            case EmailAddress:
                return createEmailAddressIndicatorReader(conn);
            case File:
                return createFileIndicatorReader(conn);
            case Host:
                return createHostIndicatorReader(conn);
            case Url:
                return createUrlIndicatorReader(conn);
            default:
                return null;
        }
    }

    /**
     * Creates an instance of {@link DocumentReaderAdapter}.
     *
     * @param conn connection defining api urls
     * @return and instance of DocumentReaderAdapter
     */
    public static DocumentReaderAdapter createDocumentReader(Connection conn) {
        return new DocumentReaderAdapter(conn);
    }

    public static BatchReaderAdapter<Indicator> createIndicatorBatchReader(Connection conn) {
        return new BatchReaderAdapter<>(conn);
    }

    public static ExchangeReaderAdapter createExchangeReader(Connection conn) {
        return new ExchangeReaderAdapter(conn);
    }
}
