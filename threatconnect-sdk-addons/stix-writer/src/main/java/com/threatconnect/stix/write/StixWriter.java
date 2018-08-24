package com.threatconnect.stix.write;

import com.threatconnect.sdk.model.Address;
import com.threatconnect.sdk.model.EmailAddress;
import com.threatconnect.sdk.model.File;
import com.threatconnect.sdk.model.FileOccurrence;
import com.threatconnect.sdk.model.Host;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.ItemType;
import com.threatconnect.sdk.model.Url;
import com.threatconnect.stix.write.custom.ais.AISConsent;
import com.threatconnect.stix.write.custom.ais.AISMarkingStructureType;
import com.threatconnect.stix.write.custom.ais.AISNotProprietary;
import com.threatconnect.stix.write.custom.ais.AISTLPMarking;
import org.mitre.cybox.common_2.AnyURIObjectPropertyType;
import org.mitre.cybox.common_2.DateTimeWithPrecisionType;
import org.mitre.cybox.common_2.HashListType;
import org.mitre.cybox.common_2.HashType;
import org.mitre.cybox.common_2.SimpleHashValueType;
import org.mitre.cybox.common_2.StringObjectPropertyType;
import org.mitre.cybox.common_2.TimeType;
import org.mitre.cybox.common_2.UnsignedLongObjectPropertyType;
import org.mitre.cybox.cybox_2.ObjectType;
import org.mitre.cybox.cybox_2.Observable;
import org.mitre.cybox.default_vocabularies_2.HashNameVocab10;
import org.mitre.cybox.objects.CategoryTypeEnum;
import org.mitre.data_marking.marking_1.MarkingSpecificationType;
import org.mitre.data_marking.marking_1.MarkingType;
import org.mitre.stix.common_1.ConfidenceType;
import org.mitre.stix.common_1.ControlledVocabularyStringType;
import org.mitre.stix.common_1.InformationSourceType;
import org.mitre.stix.common_1.ReferencesType;
import org.mitre.stix.common_1.StructuredTextType;
import org.mitre.stix.stix_1.IndicatorsType;
import org.mitre.stix.stix_1.STIXHeaderType;
import org.mitre.stix.stix_1.STIXPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

public class StixWriter
{
	private static final Logger logger = LoggerFactory.getLogger(StixWriter.class);
	
	private static final String PACKAGE_ID_PREFIX = "threatconnect";
	private static final String PACKAGE_ID_LOCALPART = "Package-";
	private static final String PACKAGE_NAMESPACE_URL = "http://www.threatconnect.com";
	
	private static final String INDICATOR_ID_PREFIX = "threatconnect";
	private static final String INDICATOR_ID_LOCALPART = "Indicator-";
	private static final String INDICATOR_NAMESPACE_URL = "http://www.threatconnect.com";
	
	private static final String OBSERVABLE_ID_PREFIX = "threatconnect";
	private static final String OBSERVABLE_ID_LOCALPART = "Observable-";
	private static final String OBSERVABLE_NAMESPACE_URL = "http://www.threatconnect.com";
	
	private static final String CYBOX_ID_PREFIX = "threatconnect";
	private static final String CYBOX_ID_LOCALPART = "Object-";
	private static final String CYBOX_NAMESPACE_URL = "http://www.threatconnect.com";
	
	private static final String AIS_PREFIX = "AIS";
	private static final String AIS_MARKING_STRUCTURE = "AISMarkingStructure";
	private static final String AIS_NAMESPACE_URL = "http://www.us-cert.gov/STIXMarkingStructure#AISConsentMarking-2";
	
	public static final String STIX_VERSION = "1.1.1";
	
	/**
	 * Takes a list of items and writes out stix xml
	 *
	 * @param items the list of items to write
	 * @return
	 */
	public String writeStix(final Item... items)
	{
		return writeStix(true, true, items);
	}
	
	/**
	 * Takes a list of items and writes out stix xml
	 *
	 * @param includeWeblink whether or not indicators should include the weblink in the xml
	 * @param formatXml      whether or not to format the xml
	 * @param items          the list of items to write
	 * @return
	 */
	public String writeStix(final boolean includeWeblink, final boolean formatXml, final Item... items)
	{
		return writeStix(includeWeblink, formatXml, Arrays.asList(items));
	}
	
	/**
	 * Takes a list of items and writes out stix xml
	 *
	 * @param includeWeblink whether or not indicators should include the weblink in the xml
	 * @param formatXml      whether or not to format the xml
	 * @param items          the list of items to write
	 * @return
	 */
	public String writeStix(final boolean includeWeblink, final boolean formatXml, final List<Item> items)
	{
		STIXPackage stixPackage = new STIXPackage();
		IndicatorsType stixIndicators = new IndicatorsType();
		
		//for each of the items in the list
		for (Item item : items)
		{
			//check to see if this item is an indicator
			if (ItemType.INDICATOR == item.getItemType())
			{
				try
				{
					stixIndicators.getIndicators().add(toStixIndicator((Indicator) item, includeWeblink));
				}
				catch (DatatypeConfigurationException | UnsupportedIndicatorTypeException e)
				{
					logger.info(e.getMessage());
				}
			}
		}
		
		stixPackage.withIndicators(stixIndicators);
		
		try
		{
			//create the stix header
			STIXHeaderType stixHeader = new STIXHeaderType();
			stixHeader.withPackageIntents(new ControlledVocabularyStringType().withValue("Indicators - Watchlist"));
			
			AISNotProprietary aisNotProprietary = new AISNotProprietary();
			aisNotProprietary.setAisConsent(new AISConsent("EVERYONE"));
			aisNotProprietary.setAistlpMarking(new AISTLPMarking("GREEN"));
			AISMarkingStructureType markingStructureType = new AISMarkingStructureType();
			markingStructureType.setAisNotProprietary(aisNotProprietary);
			
			MarkingSpecificationType markingSpecificationType = new MarkingSpecificationType();
			markingSpecificationType.withControlledStructure("//node() | //@*");
			markingSpecificationType.withMarkingStructures(markingStructureType);
			
			stixHeader.withHandling(new MarkingType().withMarkings(markingSpecificationType));
			
			//add the components to the stix package
			stixPackage.withTimestamp(now())
				.withVersion(STIX_VERSION)
				.withSTIXHeader(stixHeader)
				.withIndicators(stixIndicators)
				.withId(new QName(PACKAGE_NAMESPACE_URL, PACKAGE_ID_LOCALPART + UUID.randomUUID().toString(),
					PACKAGE_ID_PREFIX));
			
			JAXBContext jaxbContext = JAXBContext.newInstance(STIXPackage.class, AISMarkingStructureType.class);
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, formatXml);
			marshaller.marshal(stixPackage.toJAXBElement(), byteStream);
			return byteStream.toString();
		}
		catch (JAXBException | DatatypeConfigurationException e)
		{
			throw new RuntimeException(e);
		}
	}
	
	protected org.mitre.stix.indicator_2.Indicator toStixIndicator(final Indicator indicator, final boolean includeWeblink)
		throws UnsupportedIndicatorTypeException, DatatypeConfigurationException
	{
		double rating = null != indicator.getRating() ? indicator.getRating() : 0;
		double confidence = null != indicator.getConfidence() ? indicator.getConfidence() : 0;
		
		//create a new stix indicator object
		org.mitre.stix.indicator_2.Indicator stixIndicator = new org.mitre.stix.indicator_2.Indicator();
		stixIndicator.setTimestamp(DatatypeFactory.newInstance().newXMLGregorianCalendar(new GregorianCalendar()));
		stixIndicator.setTitle(indicator.getIndicatorType().toLowerCase() + ": " + indicator.toString());
		stixIndicator.setObservable(toObservable(indicator));
		stixIndicator.withId(new QName(INDICATOR_NAMESPACE_URL, INDICATOR_ID_LOCALPART + UUID.randomUUID().toString(),
			INDICATOR_ID_PREFIX));
		
		//build the description
		StringBuilder description = new StringBuilder();
		description.append(indicator.getIndicatorType().toLowerCase());
		description.append(": ");
		description.append(indicator.toString());
		
		//check to see if there is a description on this indicator
		if (null != indicator.getDescription())
		{
			description.append("|");
			description.append("desc: ");
			description.append(indicator.getDescription());
		}
		
		//check to see if there is a source for this indicator
		if (null != indicator.getSource())
		{
			description.append("|");
			description.append("src: ");
			description.append(indicator.getSource());
		}
		
		description.append("|");
		description.append("rating: ");
		description.append(rating);
		
		//set the description for this indicator
		stixIndicator.withDescription(new StructuredTextType().withValue(description.toString()));
		
		//set the confidence
		stixIndicator.setConfidence(new ConfidenceType().withValue(
			new ControlledVocabularyStringType().withVocabName("Percentage")
				.withValue(Double.toString(confidence))));
		
		//create a producer for this indicator
		InformationSourceType producer = new InformationSourceType();
		stixIndicator.setProducer(producer);
		
		//make sure the indicator date added is not null
		if (null != indicator.getDateAdded())
		{
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(indicator.getDateAdded());
			XMLGregorianCalendar dateAdded = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			producer.setTime(new TimeType().withProducedTime(new DateTimeWithPrecisionType().withValue(dateAdded)));
		}
		
		//check to see if the weblink is not null
		if (includeWeblink && null != indicator.getWebLink())
		{
			producer.setReferences(new ReferencesType().withReferences(indicator.getWebLink()));
		}
		
		return stixIndicator;
	}
	
	protected Observable toObservable(final Indicator indicator) throws UnsupportedIndicatorTypeException
	{
		ObjectType obj;
		
		switch (indicator.getIndicatorType())
		{
			case Address.INDICATOR_TYPE:
				obj = buildAddressObject((Address) indicator);
				break;
			case EmailAddress.INDICATOR_TYPE:
				obj = buildEmailAddressObject((EmailAddress) indicator);
				break;
			case File.INDICATOR_TYPE:
				obj = buildFileObject((File) indicator);
				break;
			case Host.INDICATOR_TYPE:
				obj = buildDomainObject((Host) indicator);
				break;
			case Url.INDICATOR_TYPE:
				obj = buildUriObject((Url) indicator);
				break;
			default:
				throw new UnsupportedIndicatorTypeException(indicator.getIndicatorType() + " is not supported");
		}
		
		Observable observable = new Observable()
			.withId(new QName(OBSERVABLE_NAMESPACE_URL, OBSERVABLE_ID_LOCALPART + UUID.randomUUID().toString(),
				OBSERVABLE_ID_PREFIX));
		observable.setObject(obj);
		return observable;
	}
	
	protected ObjectType buildAddressObject(final Address address)
	{
		org.mitre.cybox.objects.Address addressObject =
			new org.mitre.cybox.objects.Address().withCategory(CategoryTypeEnum.IPV_4_ADDR)
				.withAddressValue(new StringObjectPropertyType().withValue(address.getIp()));
		return new ObjectType().withProperties(addressObject)
			.withId(
				new QName(CYBOX_NAMESPACE_URL, CYBOX_ID_LOCALPART + UUID.randomUUID().toString(),
					CYBOX_ID_PREFIX));
	}
	
	protected ObjectType buildEmailAddressObject(final EmailAddress emailAddress)
	{
		org.mitre.cybox.objects.Address emailAddressObject =
			new org.mitre.cybox.objects.Address().withCategory(CategoryTypeEnum.E_MAIL)
				.withAddressValue(new StringObjectPropertyType().withValue(emailAddress.getAddress()));
		return new ObjectType().withProperties(emailAddressObject)
			.withId(
				new QName(CYBOX_NAMESPACE_URL, CYBOX_ID_LOCALPART + UUID.randomUUID().toString(),
					CYBOX_ID_PREFIX));
	}
	
	protected ObjectType buildFileObject(final File file)
	{
		org.mitre.cybox.objects.FileObjectType fileObject =
			new org.mitre.cybox.objects.FileObjectType().withHashes(new HashListType(createFileHashTypes(file)));
		
		//check to see if the file size is set
		if (null != file.getSize())
		{
			fileObject.setSizeInBytes(new UnsignedLongObjectPropertyType().withValue(file.getSize()));
		}
		
		//check to see if there are file occurrences
		if (!file.getFileOccurrences().isEmpty())
		{
			//:TODO: what to do with multiple?
			FileOccurrence fileOccurrence = file.getFileOccurrences().get(0);
			
			fileObject.setFileName(new StringObjectPropertyType().withValue(fileOccurrence.getFileName()));
			fileObject.setFullPath(new StringObjectPropertyType().withValue(fileOccurrence.getPath()));
		}
		
		return new ObjectType().withProperties(fileObject)
			.withId(
				new QName(CYBOX_NAMESPACE_URL, CYBOX_ID_LOCALPART + UUID.randomUUID().toString(),
					CYBOX_ID_PREFIX));
	}
	
	protected ObjectType buildDomainObject(final Host host)
	{
		org.mitre.cybox.objects.DomainName domainObject =
			new org.mitre.cybox.objects.DomainName()
				.withValue(new StringObjectPropertyType().withValue(host.getHostName()));
		return new ObjectType().withProperties(domainObject)
			.withId(
				new QName(CYBOX_NAMESPACE_URL, CYBOX_ID_LOCALPART + UUID.randomUUID().toString(),
					CYBOX_ID_PREFIX));
	}
	
	protected ObjectType buildUriObject(final Url url)
	{
		org.mitre.cybox.objects.URIObjectType uriObjectType =
			new org.mitre.cybox.objects.URIObjectType()
				.withValue(new AnyURIObjectPropertyType().withValue(url.getText()));
		return new ObjectType().withProperties(uriObjectType)
			.withId(new QName(CYBOX_NAMESPACE_URL, CYBOX_ID_LOCALPART + UUID.randomUUID().toString(),
				CYBOX_ID_PREFIX));
	}
	
	protected List<HashType> createFileHashTypes(final File file)
	{
		List<HashType> hashTypes = new ArrayList<HashType>();
		
		if (null != file.getMd5() && !file.getMd5().isEmpty())
		{
			hashTypes.add(new HashType()
				.withType(new HashNameVocab10().withValue("MD5"))
				.withSimpleHashValue(new SimpleHashValueType().withValue(file.getMd5())));
		}
		
		if (null != file.getSha1() && !file.getSha1().isEmpty())
		{
			hashTypes.add(new HashType()
				.withType(new HashNameVocab10().withValue("SHA1"))
				.withSimpleHashValue(new SimpleHashValueType().withValue(file.getSha1())));
		}
		
		if (null != file.getSha256() && !file.getSha256().isEmpty())
		{
			hashTypes.add(new HashType()
				.withType(new HashNameVocab10().withValue("SHA256"))
				.withSimpleHashValue(new SimpleHashValueType().withValue(file.getSha256())));
		}
		
		return hashTypes;
	}
	
	private XMLGregorianCalendar now() throws DatatypeConfigurationException
	{
		return DatatypeFactory.newInstance()
			.newXMLGregorianCalendar(new GregorianCalendar(TimeZone.getTimeZone("UTC")));
	}
}
