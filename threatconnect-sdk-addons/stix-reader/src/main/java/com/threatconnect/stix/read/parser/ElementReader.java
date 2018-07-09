package com.threatconnect.stix.read.parser;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayOutputStream;
import java.util.Stack;

/**
 * @author Greg Marut
 */
public class ElementReader
{
	private static XMLOutputFactory outputFactory = XMLOutputFactory.newInstance();
	
	public String writeToString(XMLStreamReader xmlStreamReader)
		throws XMLStreamException
	{
		//make sure this is a start element event
		if (XMLEvent.START_ELEMENT != xmlStreamReader.getEventType())
		{
			throw new IllegalArgumentException("xmlStreamReader must be a start element");
		}
		
		//holds the byte array to write to
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		XMLStreamWriter writer = outputFactory.createXMLStreamWriter(baos);
		
		//holds the stack of element names to trace where we are in the xml file
		Stack<String> elementStack = new Stack<String>();
		
		do
		{
			switch (xmlStreamReader.getEventType())
			{
				case XMLEvent.START_ELEMENT:
					final QName elementQName = xmlStreamReader.getName();
					writer.writeStartElement(elementQName.getPrefix(), elementQName.getLocalPart(), elementQName.getNamespaceURI());
					
					//for every namespace
					for (int i = 0; i < xmlStreamReader.getNamespaceCount(); i++)
					{
						writer
							.writeNamespace(xmlStreamReader.getNamespacePrefix(i), xmlStreamReader.getNamespaceURI(i));
					}
					
					//for every attribute
					for (int i = 0; i < xmlStreamReader.getAttributeCount(); i++)
					{
						final QName attributeQName = xmlStreamReader.getAttributeName(i);
						writer.writeAttribute(attributeQName.getPrefix(), attributeQName.getNamespaceURI(), attributeQName.getLocalPart(),
							xmlStreamReader.getAttributeValue(i));
					}
					
					//add this element name to the stack
					elementStack.push(xmlStreamReader.getLocalName());
					break;
				case XMLEvent.END_ELEMENT:
					writer.writeEndElement();
					
					//pop this element from the stack
					String elementName = elementStack.pop();
					
					//verify that the elements are the same
					if (!elementName.equals(xmlStreamReader.getLocalName()))
					{
						throw new IllegalStateException(
							"Expected " + elementName + ", found " + xmlStreamReader.getLocalName());
					}
					break;
				case XMLEvent.SPACE:
					break;
				case XMLEvent.CHARACTERS:
					writer.writeCharacters(xmlStreamReader.getTextCharacters(), xmlStreamReader.getTextStart(),
						xmlStreamReader.getTextLength());
					break;
				case XMLEvent.PROCESSING_INSTRUCTION:
					writer.writeProcessingInstruction(xmlStreamReader.getPITarget(), xmlStreamReader.getPIData());
					break;
				case XMLEvent.CDATA:
					writer.writeCData(xmlStreamReader.getText());
					break;
				case XMLEvent.COMMENT:
					writer.writeComment(xmlStreamReader.getText());
					break;
				case XMLEvent.ENTITY_REFERENCE:
					writer.writeEntityRef(xmlStreamReader.getLocalName());
					break;
				case XMLEvent.ATTRIBUTE:
					xmlStreamReader.getAttributeCount();
					break;
				case XMLEvent.START_DOCUMENT:
					String encoding = xmlStreamReader.getCharacterEncodingScheme();
					String version = xmlStreamReader.getVersion();
					if (encoding != null && version != null)
					{
						writer.writeStartDocument(encoding, version);
					}
					else if (version != null)
					{
						writer.writeStartDocument(xmlStreamReader.getVersion());
					}
					break;
				case XMLEvent.END_DOCUMENT:
					writer.writeEndDocument();
					break;
				case XMLEvent.DTD:
					writer.writeDTD(xmlStreamReader.getText());
					break;
			}
			
			//read the next element
			xmlStreamReader.next();
		} while (!elementStack.isEmpty());
		
		writer.flush();
		writer.close();
		
		return baos.toString();
	}
}
