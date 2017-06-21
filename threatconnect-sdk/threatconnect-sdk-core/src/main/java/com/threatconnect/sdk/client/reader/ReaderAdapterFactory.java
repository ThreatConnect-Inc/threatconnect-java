/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.threatconnect.sdk.client.reader;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.server.entity.Address;
import com.threatconnect.sdk.server.entity.Adversary;
import com.threatconnect.sdk.server.entity.Campaign;
import com.threatconnect.sdk.server.entity.CustomIndicator;
import com.threatconnect.sdk.server.entity.Email;
import com.threatconnect.sdk.server.entity.EmailAddress;
import com.threatconnect.sdk.server.entity.Group;
import com.threatconnect.sdk.server.entity.Host;
import com.threatconnect.sdk.server.entity.Incident;
import com.threatconnect.sdk.server.entity.Indicator;
import com.threatconnect.sdk.server.entity.Signature;
import com.threatconnect.sdk.server.entity.Threat;
import com.threatconnect.sdk.server.entity.Url;
import com.threatconnect.sdk.server.response.entity.AddressListResponse;
import com.threatconnect.sdk.server.response.entity.AddressResponse;
import com.threatconnect.sdk.server.response.entity.AdversaryListResponse;
import com.threatconnect.sdk.server.response.entity.AdversaryResponse;
import com.threatconnect.sdk.server.response.entity.CampaignListResponse;
import com.threatconnect.sdk.server.response.entity.CampaignResponse;
import com.threatconnect.sdk.server.response.entity.CustomIndicatorListResponse;
import com.threatconnect.sdk.server.response.entity.CustomIndicatorResponse;
import com.threatconnect.sdk.server.response.entity.EmailAddressListResponse;
import com.threatconnect.sdk.server.response.entity.EmailAddressResponse;
import com.threatconnect.sdk.server.response.entity.EmailListResponse;
import com.threatconnect.sdk.server.response.entity.EmailResponse;
import com.threatconnect.sdk.server.response.entity.HostListResponse;
import com.threatconnect.sdk.server.response.entity.HostResponse;
import com.threatconnect.sdk.server.response.entity.IncidentListResponse;
import com.threatconnect.sdk.server.response.entity.IncidentResponse;
import com.threatconnect.sdk.server.response.entity.SignatureListResponse;
import com.threatconnect.sdk.server.response.entity.SignatureResponse;
import com.threatconnect.sdk.server.response.entity.ThreatListResponse;
import com.threatconnect.sdk.server.response.entity.ThreatResponse;
import com.threatconnect.sdk.server.response.entity.UrlListResponse;
import com.threatconnect.sdk.server.response.entity.UrlResponse;

/**
 * @author dtineo
 */
public class ReaderAdapterFactory
{
	
	public static AbstractGroupReaderAdapter<Adversary> createAdversaryGroupReader(Connection conn)
	{
		return new AbstractGroupReaderAdapter<Adversary>(conn, AdversaryResponse.class, Adversary.class,
			AdversaryListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "adversaries";
			}
		};
	}
	
	public static AbstractGroupReaderAdapter<Campaign> createCampaignGroupReader(Connection conn)
	{
		return new AbstractGroupReaderAdapter<Campaign>(conn, CampaignResponse.class, Campaign.class,
			CampaignListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "campaigns";
			}
		};
	}
	
	public static AbstractGroupReaderAdapter<Email> createEmailGroupReader(Connection conn)
	{
		return new AbstractGroupReaderAdapter<Email>(conn, EmailResponse.class, Email.class, EmailListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "emails";
			}
		};
	}
	
	public static AbstractGroupReaderAdapter<Incident> createIncidentGroupReader(Connection conn)
	{
		return new AbstractGroupReaderAdapter<Incident>(conn, IncidentResponse.class, Incident.class,
			IncidentListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "incidents";
			}
		};
	}
	
	public static AbstractGroupReaderAdapter<Signature> createSignatureGroupReader(Connection conn)
	{
		return new AbstractGroupReaderAdapter<Signature>(conn, SignatureResponse.class, Signature.class,
			SignatureListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "signatures";
			}
		};
	}
	
	public static AbstractGroupReaderAdapter<Threat> createThreatGroupReader(Connection conn)
	{
		return new AbstractGroupReaderAdapter<Threat>(conn, ThreatResponse.class, Threat.class,
			ThreatListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "threats";
			}
		};
	}
	
	public static AbstractIndicatorReaderAdapter<Address> createAddressIndicatorReader(Connection conn)
	{
		return new AbstractIndicatorReaderAdapter<Address>(conn, AddressResponse.class, Address.class,
			AddressListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "addresses";
			}
		};
	}
	
	public static AbstractIndicatorReaderAdapter<EmailAddress> createEmailAddressIndicatorReader(Connection conn)
	{
		return new AbstractIndicatorReaderAdapter<EmailAddress>(conn, EmailAddressResponse.class, EmailAddress.class,
			EmailAddressListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "emailAddresses";
			}
		};
	}

	public static AbstractIndicatorReaderAdapter<CustomIndicator> createCustomIndicatorReader(Connection conn, final String type)
	{
		return new AbstractIndicatorReaderAdapter<CustomIndicator>(conn, CustomIndicatorResponse.class, CustomIndicator.class,
				CustomIndicatorListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return type;
			}
		};
	}

	public static FileIndicatorReaderAdapter createFileIndicatorReader(Connection conn)
	{
		return new FileIndicatorReaderAdapter(conn);
	}
	
	public static AbstractIndicatorReaderAdapter<Host> createHostIndicatorReader(Connection conn)
	{
		return new AbstractIndicatorReaderAdapter<Host>(conn, HostResponse.class, Host.class, HostListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "hosts";
			}
		};
	}
	
	public static AbstractIndicatorReaderAdapter<Url> createUrlIndicatorReader(Connection conn)
	{
		return new AbstractIndicatorReaderAdapter<Url>(conn, UrlResponse.class, Url.class, UrlListResponse.class)
		{
			@Override
			public String getUrlType()
			{
				return "urls";
			}
		};
	}
	
	public static OwnerReaderAdapter createOwnerReader(Connection conn)
	{
		return new OwnerReaderAdapter(conn);
	}
	
	public static IndicatorTypeReaderAdapter createIndicatorTypeReaderAdapter(Connection conn)
	{
		return new IndicatorTypeReaderAdapter(conn);
	}
	
	public static SecurityLabelReaderAdapter createSecurityLabelReader(Connection conn)
	{
		return new SecurityLabelReaderAdapter(conn);
	}
	
	public static TagReaderAdapter createTagReader(Connection conn)
	{
		return new TagReaderAdapter(conn);
	}
	
	public static VictimReaderAdapter createVictimReader(Connection conn)
	{
		return new VictimReaderAdapter(conn);
	}
	
	public static AbstractGroupReaderAdapter createGroupReader(String type, Connection conn)
	{
		return createGroupReader(Group.Type.valueOf(type), conn);
	}
	
	public static AbstractGroupReaderAdapter createGroupReader(Group.Type type, Connection conn)
	{
		switch (type)
		{
			case Adversary:
				return createAdversaryGroupReader(conn);
			case Campaign:
				return createCampaignGroupReader(conn);
			case Document:
				return createDocumentReader(conn);
			case Email:
				return createEmailGroupReader(conn);
			case Incident:
				return createIncidentGroupReader(conn);
			case Signature:
				return createSignatureGroupReader(conn);
			case Threat:
				return createThreatGroupReader(conn);
			case Task:
				return createTaskReader(conn);
			default:
				throw new IllegalArgumentException(type + " is not a vaild GroupType");
		}
		
	}
	
	public static AbstractIndicatorReaderAdapter createIndicatorReader(String type, Connection conn)
	{
		if (Character.isLowerCase(type.charAt(0)))
		{
			String indType = Character.toLowerCase(type.charAt(0)) + type.substring(1);
			return createIndicatorReader(Indicator.Type.valueOf(indType), conn);
		}
		else
		{
			return createIndicatorReader(Indicator.Type.valueOf(type), conn);
		}
		
	}
	
	public static AbstractIndicatorReaderAdapter createIndicatorReader(Indicator.Type type, Connection conn)
	{
		
		if (type == null)
		{
			return null;
		}
		
		switch (type)
		{
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
	 * @param conn
	 * connection defining api urls
	 * @return and instance of DocumentReaderAdapter
	 */
	public static DocumentReaderAdapter createDocumentReader(Connection conn)
	{
		return new DocumentReaderAdapter(conn);
	}
	
	public static BatchReaderAdapter<Indicator> createIndicatorBatchReader(Connection conn)
	{
		return new BatchReaderAdapter<>(conn);
	}
	
	public static ExchangeReaderAdapter createExchangeReader(Connection conn)
	{
		return new ExchangeReaderAdapter(conn);
	}
	
	public static TaskReaderAdapter createTaskReader(Connection conn)
	{
		return new TaskReaderAdapter(conn);
	}

	public static DataStoreReaderAdapter createDataStoreReaderAdapter(Connection conn)
	{
		return new DataStoreReaderAdapter(conn);
	}
}
