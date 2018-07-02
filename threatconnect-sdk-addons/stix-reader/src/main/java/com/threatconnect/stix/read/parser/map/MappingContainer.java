package com.threatconnect.stix.read.parser.map;

import com.threatconnect.stix.read.parser.map.cybox.object.CidrIP4Mapping;
import com.threatconnect.stix.read.parser.map.cybox.object.DNSRecordMapping;
import com.threatconnect.stix.read.parser.map.cybox.object.DomainNameMapping;
import com.threatconnect.stix.read.parser.map.cybox.object.EmailMapping;
import com.threatconnect.stix.read.parser.map.cybox.object.FileMapping;
import com.threatconnect.stix.read.parser.map.cybox.object.IPv4Mapping;
import com.threatconnect.stix.read.parser.map.cybox.object.UrlMapping;
import com.threatconnect.stix.read.parser.map.stix.IncidentMapping;
import com.threatconnect.stix.read.parser.map.stix.IndicatorMapping;
import com.threatconnect.stix.read.parser.map.stix.ThreatActorMapping;

/**
 * A simple bean class that holds all of the mapping objects that are used for parsing
 *
 * @author Greg Marut
 */
public class MappingContainer
{
	// cybox mappings
	private IPv4Mapping ipv4Mapping;
	private EmailMapping emailMapping;
	private UrlMapping urlMapping;
	private DomainNameMapping domainNameMapping;
	private DNSRecordMapping dnsRecordMapping;
	private FileMapping fileMapping;
	private CidrIP4Mapping cidrIP4Mapping;
	
	// stix mappings
	private IndicatorMapping indicatorMapping;
	private IncidentMapping incidentMapping;
	private ThreatActorMapping threatActorMapping;
	
	public MappingContainer()
	{
		this(null, null);
	}
	
	public MappingContainer(final Double defaultRating, final Double defaultConfidence)
	{
		setIPv4Mapping(new IPv4Mapping(defaultRating, defaultConfidence));
		setEmailMapping(new EmailMapping(defaultRating, defaultConfidence));
		setUrlMapping(new UrlMapping(defaultRating, defaultConfidence));
		setDomainNameMapping(new DomainNameMapping(defaultRating, defaultConfidence));
		setDnsRecordMapping(new DNSRecordMapping(defaultRating, defaultConfidence));
		setFileMapping(new FileMapping(defaultRating, defaultConfidence));
		setCidrIP4Mapping(new CidrIP4Mapping(defaultRating, defaultConfidence));
		
		setIndicatorMapping(new IndicatorMapping());
		setIncidentMapping(new IncidentMapping());
		setThreatActorMapping(new ThreatActorMapping());
	}
	
	public IPv4Mapping getIPv4Mapping()
	{
		return ipv4Mapping;
	}
	
	public void setIPv4Mapping(IPv4Mapping ipv4Mapping)
	{
		if (null == ipv4Mapping)
		{
			throw new IllegalArgumentException("ipv4Mapping cannot be null");
		}
		
		this.ipv4Mapping = ipv4Mapping;
	}
	
	public EmailMapping getEmailMapping()
	{
		return emailMapping;
	}
	
	public void setEmailMapping(EmailMapping emailMapping)
	{
		if (null == emailMapping)
		{
			throw new IllegalArgumentException("emailMapping cannot be null");
		}
		
		this.emailMapping = emailMapping;
	}
	
	public UrlMapping getUrlMapping()
	{
		return urlMapping;
	}
	
	public void setUrlMapping(UrlMapping urlMapping)
	{
		if (null == urlMapping)
		{
			throw new IllegalArgumentException("urlMapping cannot be null");
		}
		
		this.urlMapping = urlMapping;
	}
	
	public DomainNameMapping getDomainNameMapping()
	{
		return domainNameMapping;
	}
	
	public void setDomainNameMapping(DomainNameMapping domainNameMapping)
	{
		if (null == domainNameMapping)
		{
			throw new IllegalArgumentException("domainNameMapping cannot be null");
		}
		
		this.domainNameMapping = domainNameMapping;
	}
	
	public FileMapping getFileMapping()
	{
		return fileMapping;
	}
	
	public void setFileMapping(FileMapping fileMapping)
	{
		if (null == fileMapping)
		{
			throw new IllegalArgumentException("fileMapping cannot be null");
		}
		
		this.fileMapping = fileMapping;
	}
	
	public CidrIP4Mapping getCidrIP4Mapping()
	{
		return cidrIP4Mapping;
	}
	
	public void setCidrIP4Mapping(final CidrIP4Mapping cidrIP4Mapping)
	{
		if (null == cidrIP4Mapping)
		{
			throw new IllegalArgumentException("cidrIP4Mapping cannot be null");
		}
		
		this.cidrIP4Mapping = cidrIP4Mapping;
	}
	
	public DNSRecordMapping getDnsRecordMapping()
	{
		return dnsRecordMapping;
	}
	
	public void setDnsRecordMapping(DNSRecordMapping dnsRecordMapping)
	{
		if (null == dnsRecordMapping)
		{
			throw new IllegalArgumentException("dnsRecordMapping cannot be null");
		}
		
		this.dnsRecordMapping = dnsRecordMapping;
	}
	
	public IndicatorMapping getIndicatorMapping()
	{
		return indicatorMapping;
	}
	
	public void setIndicatorMapping(IndicatorMapping indicatorMapping)
	{
		if (null == indicatorMapping)
		{
			throw new IllegalArgumentException("indicatorMapping cannot be null");
		}
		
		this.indicatorMapping = indicatorMapping;
	}
	
	public IncidentMapping getIncidentMapping()
	{
		return incidentMapping;
	}
	
	public void setIncidentMapping(IncidentMapping incidentMapping)
	{
		if (null == incidentMapping)
		{
			throw new IllegalArgumentException("incidentMapping cannot be null");
		}
		
		this.incidentMapping = incidentMapping;
	}
	
	public ThreatActorMapping getThreatActorMapping()
	{
		return threatActorMapping;
	}
	
	public void setThreatActorMapping(ThreatActorMapping threatActorMapping)
	{
		if (null == threatActorMapping)
		{
			throw new IllegalArgumentException("threatActorMapping cannot be null");
		}
		
		this.threatActorMapping = threatActorMapping;
	}
}
