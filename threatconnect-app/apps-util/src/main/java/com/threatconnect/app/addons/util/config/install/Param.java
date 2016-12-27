package com.threatconnect.app.addons.util.config.install;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Greg Marut
 */
public class Param
{
	private String name;
	private ParamDataType type;
	private String label;
	private String defaultValue;
	private String allowMultiple;
	private Boolean encrypt;
	private Boolean required;
	private Boolean hidden;
	private Boolean setup;
	private Integer viewRows;
	private String note;
	private String viewType;
	private Integer sequence;
	
	private final List<String> validValues;
	private final List<PlaybookVariableType> playbookDataType;
	
	public Param()
	{
		this.validValues = new ArrayList<String>();
		this.playbookDataType = new ArrayList<PlaybookVariableType>();
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(final String name)
	{
		this.name = name;
	}
	
	public ParamDataType getType()
	{
		return type;
	}
	
	public void setType(final ParamDataType type)
	{
		this.type = type;
	}
	
	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(final String label)
	{
		this.label = label;
	}
	
	public String getDefaultValue()
	{
		return defaultValue;
	}
	
	public void setDefaultValue(final String defaultValue)
	{
		this.defaultValue = defaultValue;
	}
	
	public String getAllowMultiple()
	{
		return allowMultiple;
	}
	
	public void setAllowMultiple(final String allowMultiple)
	{
		this.allowMultiple = allowMultiple;
	}
	
	public Boolean getEncrypt()
	{
		return encrypt;
	}
	
	public void setEncrypt(final Boolean encrypt)
	{
		this.encrypt = encrypt;
	}
	
	public Boolean getRequired()
	{
		return required;
	}
	
	public void setRequired(final Boolean required)
	{
		this.required = required;
	}
	
	public Boolean getHidden()
	{
		return hidden;
	}
	
	public void setHidden(final Boolean hidden)
	{
		this.hidden = hidden;
	}
	
	public Boolean getSetup()
	{
		return setup;
	}
	
	public void setSetup(final Boolean setup)
	{
		this.setup = setup;
	}
	
	public Integer getViewRows()
	{
		return viewRows;
	}
	
	public void setViewRows(final Integer viewRows)
	{
		this.viewRows = viewRows;
	}
	
	public String getNote()
	{
		return note;
	}
	
	public void setNote(final String note)
	{
		this.note = note;
	}
	
	public String getViewType()
	{
		return viewType;
	}
	
	public void setViewType(final String viewType)
	{
		this.viewType = viewType;
	}
	
	public Integer getSequence()
	{
		return sequence;
	}
	
	public void setSequence(final Integer sequence)
	{
		this.sequence = sequence;
	}
	
	public List<String> getValidValues()
	{
		return validValues;
	}
	
	public List<PlaybookVariableType> getPlaybookDataType()
	{
		return playbookDataType;
	}
}
