package com.threatconnect.stix.read.parser;

import com.threatconnect.sdk.model.Group;
import com.threatconnect.sdk.model.Incident;
import com.threatconnect.sdk.model.Indicator;
import com.threatconnect.sdk.model.Item;
import com.threatconnect.sdk.model.SecurityLabel;
import com.threatconnect.sdk.model.Threat;
import com.threatconnect.sdk.model.util.ItemUtil;
import com.threatconnect.sdk.model.util.merge.MergeAttributeStrategy;
import com.threatconnect.sdk.parser.ParserException;
import com.threatconnect.sdk.parser.source.DataSource;
import com.threatconnect.sdk.parser.source.FileDataSource;
import com.threatconnect.stix.read.parser.exception.InvalidObservableException;
import com.threatconnect.stix.read.parser.exception.UnsupportedObservableTypeException;
import com.threatconnect.stix.read.parser.map.MappingContainer;
import com.threatconnect.stix.read.parser.map.cybox.object.CyboxObjectMapping;
import com.threatconnect.stix.read.parser.map.stix.IncidentMapping;
import com.threatconnect.stix.read.parser.map.stix.IndicatorMapping;
import com.threatconnect.stix.read.parser.map.stix.ThreatActorMapping;
import com.threatconnect.stix.read.parser.observer.ItemObserver;
import com.threatconnect.stix.read.parser.resolver.NodeResolver;
import com.threatconnect.stix.read.parser.resolver.ObservableNodeResolver;
import com.threatconnect.stix.read.parser.resolver.Resolver;
import com.threatconnect.stix.read.parser.util.NamespaceUtil;
import com.threatconnect.stix.read.parser.util.SecurityLabelUtil;
import com.threatconnect.stix.read.parser.util.StixNodeUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Greg Marut
 */
public class STIXStreamParser extends AbstractXMLStreamParser<Item>
{
	// holds the source of the data
	private final MappingContainer mappingContainer;
	
	// holds the map of indicators that have been loaded from the xml document
	private final Map<String, List<? extends Item>> observableMap;
	private final NodeResolver nodeResolver;
	private final Resolver<List<? extends Item>, ItemObserver> cyboxObjectResolver;
	
	private final java.io.File documentFile;
	private final String documentName;
	private final boolean associateItems;
	
	public STIXStreamParser(final DataSource dataSource)
	{
		this(dataSource, new MappingContainer());
	}
	
	public STIXStreamParser(final DataSource dataSource, final MappingContainer mappingContainer)
	{
		super(dataSource);
		
		this.observableMap = new HashMap<String, List<? extends Item>>();
		this.cyboxObjectResolver = new Resolver<List<? extends Item>, ItemObserver>();
		this.nodeResolver = new ObservableNodeResolver();
		this.mappingContainer = mappingContainer;
		this.documentFile = null;
		this.documentName = null;
		this.associateItems = false;
	}
	
	public STIXStreamParser(final java.io.File documentFile, final MappingContainer mappingContainer, final String documentName,
		final boolean associateItemsWithDocument)
	{
		super(new FileDataSource(documentFile));
		
		this.observableMap = new HashMap<String, List<? extends Item>>();
		this.cyboxObjectResolver = new Resolver<List<? extends Item>, ItemObserver>();
		this.nodeResolver = new ObservableNodeResolver();
		this.mappingContainer = mappingContainer;
		this.documentFile = documentFile;
		this.documentName = documentName;
		this.associateItems = associateItemsWithDocument;
	}
	
	@Override
	public List<Item> parseData() throws ParserException
	{
		// holds the list of items that were parsed
		List<Item> items = super.parseData();
		
		//merge the indicators attributes
		ItemUtil.mergeIndicators(items, new MergeAttributeStrategy());
		
		// make sure that the document should be associated and that there were items found. If the
		// document is empty or does not contain any usable data, we do not want to store it.
		if (null != documentFile && null != documentName && !items.isEmpty())
		{
			// create a new document
			com.threatconnect.sdk.model.Document document =
				new com.threatconnect.sdk.model.Document();
			document.setName(documentName);
			document.setFileName(documentName + ".xml");
			document.setFile(documentFile);
			document.setFileSize(documentFile.length());
			
			//check to see if all of the items should be associated with this document
			if (associateItems)
			{
				// break the list of items into sets of groups and indicators
				Set<Group> groups = new HashSet<Group>();
				Set<Indicator> indicators = new HashSet<Indicator>();
				ItemUtil.separateGroupsAndIndicators(items, groups, indicators);
				
				// associate all of the indicators with this document
				document.getAssociatedItems().addAll(indicators);
			}
			
			// add this document to the list of items
			items.add(document);
		}
		
		return items;
	}
	
	@Override
	protected List<Item> parseXml(final XMLStreamReader xmlStreamReader) throws ParserException
	{
		//holds the list of items found
		Set<Item> items = new HashSet<Item>();
		
		//build the item observer which will be notified everytime a list of items are found
		ItemObserver itemObserver = items::addAll;
		
		//holds the list strings containing the xml for each stix package
		logger.info("Reading STIX Packages");
		List<String> stixPackageXmlList = new STIXPackageParser().parsePackages(xmlStreamReader);
		
		try
		{
			DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(false);
			DocumentBuilder builder = domFactory.newDocumentBuilder();
			
			//for each of the stix packages
			for (String xml : stixPackageXmlList)
			{
				InputSource inputSource = new InputSource(new StringReader(xml));
				Document document = builder.parse(inputSource);
				
				parseStixPackage(document, itemObserver);
			}
		}
		catch (IOException | SAXException | XPathExpressionException | ParserConfigurationException e)
		{
			throw new ParserException(e);
		}
		
		//since we are using the observer pattern, we now need to notify all of the observers which observables have been found
		logger.info("Flushing Observers");
		nodeResolver.flushAll();
		cyboxObjectResolver.flushAll();
		
		return new ArrayList<Item>(items);
	}
	
	protected void parseStixPackage(final Document document, final ItemObserver itemObserver)
		throws XPathExpressionException
	{
		//parse the stix package node from the document
		Node stixPackageNode = Constants.XPATH_UTIL.getNode("/" + Constants.ELEMENT_STIX_PACAKGE, document);
		parseStixPackage(stixPackageNode, document, itemObserver);
		
		NodeList relatedPackageNodeList = findRelatedPackages(stixPackageNode);
		logger.trace("Found {} Related Packages", relatedPackageNodeList.getLength());
		
		// for each of the nodes in the list
		for (int i = 0; i < relatedPackageNodeList.getLength(); i++)
		{
			// retrieve the current package node
			Node packageNode = relatedPackageNodeList.item(i);
			
			// detach this node from the parent
			packageNode.getParentNode().removeChild(packageNode);
			
			parseStixPackage(packageNode, document, itemObserver);
		}
	}
	
	protected void parseStixPackage(final Node stixPackageNode, final Document document,
		final ItemObserver itemObserver)
		throws XPathExpressionException
	{
		// retrieve the current stix package node
		final String stixPackageID = StixNodeUtil.getID(stixPackageNode);
		logger.trace("Parsing Stix Package {}", stixPackageID);
		
		// extract the namespaces from the stix package node
		NamespaceUtil.extractNamespaces(stixPackageNode);
		
		//extract the security labels
		List<SecurityLabel> securityLabels = extractSecurityLabels(stixPackageNode);
		
		// parse the observables for this stix package
		parseObservables(document, stixPackageNode, itemObserver, securityLabels);
		
		//parse the stix indicators
		parseIndicators(document, stixPackageNode, itemObserver, securityLabels);
		parseRelatedIndicators(document, stixPackageNode, itemObserver, securityLabels);
		
		// parse all of the incidents for this stix package
		parseIncidents(document, stixPackageNode, itemObserver, securityLabels);
		
		//parse all of the threat actors for this stix package
		parseThreatActors(document, stixPackageNode, itemObserver, securityLabels);
	}
	
	protected List<SecurityLabel> extractSecurityLabels(final Node parentNode)
		throws XPathExpressionException
	{
		//find the marking structures in the package
		NodeList markingStructureNodeList = findMarkingStructures(parentNode);
		return SecurityLabelUtil.extractSecurityLabels(markingStructureNodeList);
	}
	
	/**
	 * Parses all of the observable objects for a given node and returns the list of items objects that were created
	 *
	 * @param parentNode
	 * @return
	 * @throws XPathExpressionException
	 */
	protected void parseObservables(final Document document, final Node parentNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels)
		throws XPathExpressionException
	{
		NodeList observablesNodeList = findObservables(parentNode);
		logger.trace("Found {} Observables", observablesNodeList.getLength());
		
		// for each of the nodes in the list
		for (int i = 0; i < observablesNodeList.getLength(); i++)
		{
			// retrieve the current stix package node
			Node observableNode = observablesNodeList.item(i);
			
			// detach this node from the parent
			observableNode.getParentNode().removeChild(observableNode);
			
			//parse this observable and add all of the results to the list
			parseObservable(document, observableNode, itemObserver, securityLabels);
		}
	}
	
	/**
	 * Parses the observable object for a given node and returns the list of items objects that were created
	 *
	 * @param observableNode
	 * @return
	 * @throws XPathExpressionException
	 */
	protected void parseObservable(final Document document, final Node observableNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels)
		
		throws XPathExpressionException
	{
		// just in case this is a pointer to another observable, we need to resolve it
		nodeResolver.resolveNode(document, observableNode, resolvedObservableNode ->
		{
			try
			{
				// parse this node and retrieve the parent signature group
				List<? extends Item> observableItems =
					parseResolvedObservable(document, resolvedObservableNode, securityLabels);
				List<Item> items = new ArrayList<Item>();
				items.addAll(observableItems);
				itemObserver.found(items);
			}
			catch (XPathExpressionException | UnsupportedObservableTypeException | InvalidObservableException e)
			{
				logger.warn(e.getMessage(), e);
			}
		});
	}
	
	/**
	 * Parses an observable node. It is important that the observable has already been resolved to a concrete object and
	 * not just a pointer
	 *
	 * @param resolvedObservableNode the node that has already been resolved to a concrete object
	 * @return the parent Signature object that contains the parsed data and child associated objects
	 * @throws XPathExpressionException
	 * @throws UnsupportedObservableTypeException
	 * @throws InvalidObservableException
	 */
	protected List<? extends Item> parseResolvedObservable(final Document document, final Node resolvedObservableNode,
		final List<SecurityLabel> securityLabels)
		throws XPathExpressionException, UnsupportedObservableTypeException, InvalidObservableException
	{
		final String observableNodeID = StixNodeUtil.getID(resolvedObservableNode);
		logger.trace("Parsing Observable {}", observableNodeID);
		
		// check to see if this observable has already been parsed
		if (observableMap.containsKey(observableNodeID))
		{
			return observableMap.get(observableNodeID);
		}
		else
		{
			// retrieve the mapping object for parsing this observable
			final Node objectNode = Constants.XPATH_UTIL.getNode("Object", resolvedObservableNode);
			final String objectNodeID = StixNodeUtil.getID(objectNode);
			CyboxObjectMapping cyboxObjectMapping = determineCyboxMapping(objectNode);
			
			// parse the list of items from this observable
			List<? extends Item> observableItems =
				cyboxObjectMapping.map(objectNode, observableNodeID, document, securityLabels, nodeResolver, cyboxObjectResolver);
			
			//parse the related objects from the resolved observable node
			List<? extends Item> relatedObjects = parseRelatedObjects(document, resolvedObservableNode, securityLabels);
			
			//build one list to capture all of the items
			List<Item> items = new ArrayList<Item>();
			items.addAll(observableItems);
			items.addAll(relatedObjects);
			
			//index the results in the maps
			observableMap.put(observableNodeID, items);
			cyboxObjectResolver.addResolvedObject(objectNodeID, items);
			
			return items;
		}
	}
	
	protected List<? extends Item> parseRelatedObjects(final Document document, final Node resolvedObservableNode,
		final List<SecurityLabel> securityLabels)
		throws XPathExpressionException
	{
		final String observableNodeID = StixNodeUtil.getID(resolvedObservableNode);
		
		//holds the list of items to return
		List<Item> items = new ArrayList<Item>();
		
		//look for any related objects on this observable's Object
		NodeList relatedObjectNodes =
			Constants.XPATH_UTIL.getNodes("Object/Related_Objects/Related_Object", resolvedObservableNode);
		logger.trace("Found {} Related_Objects", relatedObjectNodes.getLength());
		
		if (relatedObjectNodes.getLength() > 0)
		{
			// for each of the nodes in the list
			for (int i = 0; i < relatedObjectNodes.getLength(); i++)
			{
				// retrieve the current related object node
				Node relatedObjectNode = relatedObjectNodes.item(i);
				
				try
				{
					// retrieve the mapping object for parsing this observable
					CyboxObjectMapping cyboxObjectMapping = determineCyboxMapping(relatedObjectNode);
					items.addAll(
						cyboxObjectMapping.map(relatedObjectNode, observableNodeID, document, securityLabels, nodeResolver, cyboxObjectResolver));
				}
				catch (InvalidObservableException | UnsupportedObservableTypeException e)
				{
					logger.info(e.getMessage(), e);
				}
			}
		}
		
		return items;
	}
	
	/**
	 * Parses all of the indicator objects for a given node
	 *
	 * @param parentNode
	 * @throws XPathExpressionException
	 */
	protected void parseIndicators(final Document document, final Node parentNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels)
		throws XPathExpressionException
	{
		NodeList indicatorNodeList = findIndicators(parentNode);
		logger.trace("Found {} Indicators", indicatorNodeList.getLength());
		
		// for each of the nodes in the list
		for (int i = 0; i < indicatorNodeList.getLength(); i++)
		{
			// retrieve the current stix package node
			Node indicatorNode = indicatorNodeList.item(i);
			
			// detach this node from the parent
			indicatorNode.getParentNode().removeChild(indicatorNode);
			
			parseIndicator(document, indicatorNode, itemObserver, securityLabels);
		}
	}
	
	/**
	 * Parses all of the related indicator objects for a given node
	 *
	 * @param parentNode
	 * @throws XPathExpressionException
	 */
	protected void parseRelatedIndicators(final Document document, final Node parentNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels)
		throws XPathExpressionException
	{
		NodeList relatedIndicatorNodeList = findRelatedIndicators(parentNode);
		logger.trace("Found {} Related Indicators", relatedIndicatorNodeList.getLength());
		
		// for each of the nodes in the list
		for (int i = 0; i < relatedIndicatorNodeList.getLength(); i++)
		{
			// retrieve the current stix package node
			Node indicatorNode = relatedIndicatorNodeList.item(i);
			
			// detach this node from the parent
			indicatorNode.getParentNode().removeChild(indicatorNode);
			
			parseIndicator(document, indicatorNode, itemObserver, securityLabels);
		}
	}
	
	protected void parseIndicator(final Document document, final Node indicatorNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		final String indicatorNodeID = StixNodeUtil.getID(indicatorNode);
		logger.trace("Parsing Indicator {}", indicatorNodeID);
		
		// find the observable node
		Node observableNode = findObservable(indicatorNode);
		
		//make sure the observable node is not null
		if (null != observableNode)
		{
			//create a new item observer for the observable node
			ItemObserver observableObserver = object ->
			{
				try
				{
					// retrieve the indicator mapping object
					IndicatorMapping indicatorMapping = getMappingContainer().getIndicatorMapping();
					
					//apply the indicator mapping logic directly on the observables that were given to it.
					indicatorMapping.map(indicatorNode, document, object);
					
					//notify the original item observer that this indicator has been mapped
					itemObserver.found(object);
				}
				catch (XPathExpressionException e)
				{
					logger.warn(e.getMessage(), e);
				}
			};
			
			parseObservable(document, observableNode, observableObserver, securityLabels);
		}
	}
	
	/**
	 * Parses all of the incident objects for a given node
	 *
	 * @param parentNode
	 * @throws XPathExpressionException
	 */
	protected void parseIncidents(final Document document, final Node parentNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		NodeList incidentNodeList = findIncidents(parentNode);
		logger.trace("Found {} Incidents", incidentNodeList.getLength());
		
		// for each of the nodes in the list
		for (int i = 0; i < incidentNodeList.getLength(); i++)
		{
			// retrieve the current stix package node
			Node incidentNode = incidentNodeList.item(i);
			
			// detach this node from the parent
			incidentNode.getParentNode().removeChild(incidentNode);
			
			parseIncident(document, incidentNode, itemObserver, securityLabels);
		}
	}
	
	protected void parseIncident(final Document document, final Node incidentNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		final String incidentNodeID = StixNodeUtil.getID(incidentNode);
		logger.trace("Parsing Incident {}", incidentNodeID);
		
		// holds all of the items that have been parsed from the incidents nodes
		List<Incident> incidents = new ArrayList<Incident>();
		
		// retrieve the incident mapping object
		IncidentMapping incidentMapping = getMappingContainer().getIncidentMapping();
		incidents.addAll(incidentMapping.map(incidentNode, document, securityLabels));
		
		//notify the observer that an incident was found
		itemObserver.found(incidents);
		
		//create a new item observer for the observable node
		ItemObserver observableObserver = object ->
		{
			//for each of the incidents
			for (Incident incident : incidents)
			{
				//add all of the observable to the incident
				incident.getAssociatedItems().addAll(object);
			}
		};
		
		// parse the observables for this incident
		parseObservables(document, incidentNode, observableObserver, securityLabels);
		
		parseIndicators(document, incidentNode, observableObserver, securityLabels);
		parseRelatedIndicators(document, incidentNode, observableObserver, securityLabels);
	}
	
	/**
	 * Parses all of the threatActor objects for a given node
	 *
	 * @param parentNode
	 * @throws XPathExpressionException
	 */
	protected void parseThreatActors(final Document document, final Node parentNode, final ItemObserver itemObserver,
		final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		NodeList threatActorNodeList = findThreatActors(parentNode);
		logger.trace("Found {} ThreatActors", threatActorNodeList.getLength());
		
		// for each of the nodes in the list
		for (int i = 0; i < threatActorNodeList.getLength(); i++)
		{
			// retrieve the current stix package node
			Node threatActorNode = threatActorNodeList.item(i);
			
			// detach this node from the parent
			threatActorNode.getParentNode().removeChild(threatActorNode);
			
			parseThreatActor(document, threatActorNode, itemObserver, securityLabels);
		}
	}
	
	protected List<? extends Item> parseThreatActor(final Document document, final Node threatActorNode,
		final ItemObserver itemObserver, final List<SecurityLabel> securityLabels) throws XPathExpressionException
	{
		final String threatActorNodeID = StixNodeUtil.getID(threatActorNode);
		logger.trace("Parsing ThreatActor {}", threatActorNodeID);
		
		// holds all of the items that have been parsed from the threatActors nodes
		List<Threat> threats = new ArrayList<Threat>();
		
		// retrieve the threatActor mapping object
		ThreatActorMapping threatActorMapping = getMappingContainer().getThreatActorMapping();
		threats.addAll(threatActorMapping.map(threatActorNode, document, securityLabels));
		
		//notify the observer that threats were found
		itemObserver.found(threats);
		
		//create a new item observer for the observable node
		ItemObserver obserableObserver = object ->
		{
			//for each of the threats
			for (Threat threat : threats)
			{
				//add all of the observable to the threat
				threat.getAssociatedItems().addAll(object);
			}
		};
		
		// parse the observables for this threat
		parseObservables(document, threatActorNode, obserableObserver, securityLabels);
		
		return threats;
	}
	
	protected CyboxObjectMapping determineCyboxMapping(final Node objectNode) throws UnsupportedObservableTypeException
	{
		try
		{
			// retrieve the properties object
			Node propertiesNode = Constants.XPATH_UTIL.getNode("Properties", objectNode);
			
			// retrieve the properties object for this
			NodeList typeNodeList = Constants.XPATH_UTIL.getNodes("@type", propertiesNode);
			
			//make sure the type is not null
			if (null == typeNodeList)
			{
				throw new UnsupportedObservableTypeException(
					"Could not identify the observable/object type. The \"type\" attribute is missing from the Properties element.");
			}
			
			List<String> types = new ArrayList<String>();
			
			// for each of the type nodes
			for (int i = 0; i < typeNodeList.getLength(); i++)
			{
				String type = typeNodeList.item(i).getTextContent();
				types.add(type);
				
				if (type.equals("AddressObj:AddressObjectType") || type.equals("AddressObject:AddressObjectType"))
				{
					// split the indicator parts
					String category = Constants.XPATH_UTIL.getString("@category", propertiesNode);
					if (category.equals("e-mail"))
					{
						return getMappingContainer().getEmailMapping();
					}
					else if (category.equals("cidr"))
					{
						return getMappingContainer().getCidrIP4Mapping();
					}
					else
					{
						//default to ipv4
						return getMappingContainer().getIPv4Mapping();
					}
				}
				else if (type.equals("URIObj:URIObjectType") || type.equals("URIObject:URIObjectType"))
				{
					return getMappingContainer().getUrlMapping();
				}
				else if (type.equals("LinkObj:LinkObjectType") || type.equals("LinkObject:LinkObjectType"))
				{
					return getMappingContainer().getUrlMapping();
				}
				else if (type.equals("FileObj:FileObjectType") || type.equals("FileObject:FileObjectType"))
				{
					return getMappingContainer().getFileMapping();
				}
				else if (type.equals("EmailMessageObj:EmailMessageObjectType") || type.equals("EmailMessageObject:EmailMessageObjectType"))
				{
					return getMappingContainer().getEmailMapping();
				}
				else if (type.equals("DomainNameObj:DomainNameObjectType") || type.equals("DomainNameObject:DomainNameObjectType"))
				{
					return getMappingContainer().getDomainNameMapping();
				}
				else if (type.equals("DNSRecordObj:DNSRecordObjectType") || type.equals("DNSRecordObject:DNSRecordObjectType"))
				{
					return getMappingContainer().getDnsRecordMapping();
				}
				else if (type.equals("MutexObj:MutexObjectType") || type.equals("MutexObject:MutexObjectType"))
				{
					return getMappingContainer().getMutexMapping();
				}
			}
			
			//the type could not be resolved
			throw new UnsupportedObservableTypeException(
				"Could not identify the observable/object type. Possible values found were: \""
					+ Arrays.toString(types.toArray(new String[] {})) + "\"");
		}
		catch (XPathExpressionException e)
		{
			throw new UnsupportedObservableTypeException(
				"Could not identify the observable/object type. There was an error evaluating the XPath expression on part of this XML.",
				e);
		}
	}
	
	protected Node findObservable(final Node node) throws XPathExpressionException
	{
		// retrieve the observable node for this node
		return Constants.XPATH_UTIL.getNode("Observable", node);
	}
	
	protected NodeList findObservables(final Node node) throws XPathExpressionException
	{
		// retrieve the observable nodes for this node
		return Constants.XPATH_UTIL.getNodes("Observables/Observable", node);
	}
	
	protected NodeList findIndicators(final Node node) throws XPathExpressionException
	{
		// retrieve the indicators nodes for this node
		return Constants.XPATH_UTIL.getNodes("Indicators/Indicator", node);
	}
	
	protected NodeList findRelatedIndicators(final Node node) throws XPathExpressionException
	{
		// retrieve the indicators nodes for this node
		return Constants.XPATH_UTIL.getNodes("Related_Indicators/Related_Indicator/Indicator", node);
	}
	
	protected NodeList findIncidents(final Node node) throws XPathExpressionException
	{
		// retrieve the indicators nodes for this node
		return Constants.XPATH_UTIL.getNodes("Incidents/Incident", node);
	}
	
	protected NodeList findThreatActors(final Node node) throws XPathExpressionException
	{
		// retrieve the threat actors nodes for this node
		return Constants.XPATH_UTIL.getNodes("Threat_Actors/Threat_Actor", node);
	}
	
	protected NodeList findMarkingStructures(final Node node) throws XPathExpressionException
	{
		// retrieve the marking structure nodes for this node
		return Constants.XPATH_UTIL.getNodes("STIX_Header/Handling/Marking/Marking_Structure", node);
	}
	
	protected NodeList findRelatedPackages(final Node node) throws XPathExpressionException
	{
		// retrieve the marking structure nodes for this node
		return Constants.XPATH_UTIL.getNodes("Related_Packages/Related_Package/Package", node);
	}
	
	public MappingContainer getMappingContainer()
	{
		return mappingContainer;
	}
}
