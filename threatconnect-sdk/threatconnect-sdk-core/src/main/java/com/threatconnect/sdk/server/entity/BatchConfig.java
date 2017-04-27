package com.threatconnect.sdk.server.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by cblades on 6/10/2015.
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "BulkStatus")
public class BatchConfig
{
	@XmlElement(name = "haltOnError", required = true)
	private boolean haltOnError;
	
	@XmlElement(name = "attributeWriteType", required = true)
	private AttributeWriteType attributeWriteType;
	
	@XmlElement(name = "action", required = true)
	private Action action;
	
	@XmlElement(name = "owner", required = true)
	private String owner;
	
	@XmlElement(name = "version", required = false)
	private Version version;
	
	@XmlElement(name = "removeOrphanIndicators")
	private boolean removeOrphanIndicators;
	
	public enum AttributeWriteType
	{
		Append,
		Replace,
		Static
	}
	
	public enum Action
	{
		Create,
		Delete
	}
	
	public enum Version
	{
		V1,
		V2
	}
	
	public BatchConfig()
	{
	}
	
	public BatchConfig(boolean haltOnError, AttributeWriteType attributeWriteType, Action action, String owner)
	{
		this(haltOnError, attributeWriteType, action, owner, Version.V1);
	}
	
	public BatchConfig(boolean haltOnError, AttributeWriteType attributeWriteType, Action action, String owner,
		Version version)
	{
		this(haltOnError, attributeWriteType, action, owner, version, false);
	}
	
	public BatchConfig(boolean haltOnError, AttributeWriteType attributeWriteType, Action action, String owner,
		Version version, boolean removeOrphanIndicators)
	{
		this.haltOnError = haltOnError;
		this.attributeWriteType = attributeWriteType;
		this.action = action;
		this.owner = owner;
		this.version = version;
		this.removeOrphanIndicators = removeOrphanIndicators;
	}
	
	public boolean isHaltOnError()
	{
		return haltOnError;
	}
	
	public void setHaltOnError(boolean haltOnError)
	{
		this.haltOnError = haltOnError;
	}
	
	public AttributeWriteType getAttributeWriteType()
	{
		return attributeWriteType;
	}
	
	public void setAttributeWriteType(AttributeWriteType attributeWriteType)
	{
		this.attributeWriteType = attributeWriteType;
	}
	
	public Action getAction()
	{
		return action;
	}
	
	public void setAction(Action action)
	{
		this.action = action;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	
	public Version getVersion()
	{
		return version;
	}
	
	public void setVersion(final Version version)
	{
		this.version = version;
	}
	
	public boolean isRemoveOrphanIndicators()
	{
		return removeOrphanIndicators;
	}
	
	public void setRemoveOrphanIndicators(final boolean removeOrphanIndicators)
	{
		this.removeOrphanIndicators = removeOrphanIndicators;
	}
}
