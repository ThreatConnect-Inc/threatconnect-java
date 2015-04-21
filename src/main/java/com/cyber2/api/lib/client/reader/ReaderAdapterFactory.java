/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cyber2.api.lib.client.reader;

import com.cyber2.api.lib.conn.Connection;
import com.cyber2.api.lib.conn.RequestExecutor;
import com.cyber2.api.lib.server.entity.*;
import com.cyber2.api.lib.server.response.entity.*;

/**
 *
 * @author dtineo
 */
public class ReaderAdapterFactory {

    public static AbstractGroupReaderAdapter<Adversary> createAdversaryGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Adversary>(conn, new RequestExecutor(conn), AdversaryResponse.class, AdversaryListResponse.class) {
            @Override
            public String getUrlType() {
                return "adversaries";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Email> createEmailGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Email>(conn, new RequestExecutor(conn), EmailResponse.class, EmailListResponse.class) {
            @Override
            public String getUrlType() {
                return "emails";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Incident> createIncidentGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Incident>(conn, new RequestExecutor(conn), IncidentResponse.class, IncidentListResponse.class) {
            @Override
            public String getUrlType() {
                return "incidents";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Signature> createSignatureGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Signature>(conn, new RequestExecutor(conn), SignatureResponse.class, SignatureListResponse.class) {
            @Override
            public String getUrlType() {
                return "signatures";
            }
        };
    }

    public static AbstractGroupReaderAdapter<Threat> createThreatGroupReader(Connection conn) {
        return new AbstractGroupReaderAdapter<Threat>(conn, new RequestExecutor(conn), ThreatResponse.class, ThreatListResponse.class) {
            @Override
            public String getUrlType() {
                return "threats";
            }
        };
    }

    public static AbstractIndicatorReaderAdapter<Address> createAddressIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<Address>(conn, new RequestExecutor(conn), AddressResponse.class, AddressListResponse.class) {
            @Override
            public String getUrlType() {
                return "addresses";
            }
        };
    }

    public static AbstractIndicatorReaderAdapter<EmailAddress> createEmailAddressIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<EmailAddress>(conn, new RequestExecutor(conn), EmailAddressResponse.class, EmailAddressListResponse.class) {
            @Override
            public String getUrlType() {
                return "emailAddresses";
            }
        };
    }

    public static AbstractIndicatorReaderAdapter<File> createFileIndicatorReader(Connection conn) {
        return new FileIndicatorReaderAdapter(conn, new RequestExecutor(conn));
    }

    public static AbstractIndicatorReaderAdapter<Host> createHostIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<Host>(conn, new RequestExecutor(conn), HostResponse.class, HostListResponse.class) {
            @Override
            public String getUrlType() {
                return "hosts";
            }
        };
    }

    public static AbstractIndicatorReaderAdapter<Url> createUrlIndicatorReader(Connection conn) {
        return new AbstractIndicatorReaderAdapter<Url>(conn, new RequestExecutor(conn), UrlResponse.class, UrlListResponse.class) {
            @Override
            public String getUrlType() {
                return "urls";
            }
        };
    }

    public static OwnerReaderAdapter createOwnerReader(Connection conn) {
        return new OwnerReaderAdapter(conn, new RequestExecutor(conn)) {
        };
    }

    public static SecurityLabelReaderAdapter createSecurityLabelReader(Connection conn) {
        return new SecurityLabelReaderAdapter(conn, new RequestExecutor(conn) );
    }

    public static TagReaderAdapter createTagReader(Connection conn) {
        return new TagReaderAdapter(conn, new RequestExecutor(conn) );
    }

    public static VictimReaderAdapter createVictimReader(Connection conn) {
        return new VictimReaderAdapter(conn, new RequestExecutor(conn));
    }

    public static AbstractGroupReaderAdapter getGroupReader(String type, Connection conn) {
        return getGroupReader(Group.Type.valueOf(type), conn);
    }

    public static AbstractGroupReaderAdapter getGroupReader(Group.Type type, Connection conn) {

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
            default:
                return null;
        }

    }

    public static AbstractIndicatorReaderAdapter getIndicatorReader(String type, Connection conn) {
        if (Character.isLowerCase(type.charAt(0))) {
            String indType = Character.toLowerCase(type.charAt(0)) + type.substring(1);
            return getIndicatorReader(Indicator.Type.valueOf(indType), conn);
        } else {
            return getIndicatorReader(Indicator.Type.valueOf(type), conn);
        }

    }

    public static AbstractIndicatorReaderAdapter getIndicatorReader(Indicator.Type type, Connection conn) {

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
     * Creates an instance of {@link com.cyber2.api.lib.client.reader.DocumentReaderAdapter}.
     *
     * @param conn connection defining api urls
     * @return and instance of DocumentReaderAdapter
     */
    public static DocumentReaderAdapter createDocumentReaderAdapter(Connection conn) {
        return new DocumentReaderAdapter(conn, new RequestExecutor(conn));
    }
}
