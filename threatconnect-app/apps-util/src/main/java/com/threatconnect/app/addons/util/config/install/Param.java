package com.threatconnect.app.addons.util.config.install;

import com.google.gson.annotations.SerializedName;

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
	@SerializedName("default")
	private String defaultValue;
	private boolean allowMultiple;
	private boolean encrypt;
	private boolean required;
	private boolean hidden;
	private boolean setup;
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
		
		//set the default data type for this param
		this.type = ParamDataType.String;
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
	
	public boolean isAllowMultiple()
	{
		return allowMultiple;
	}
	
	public void setAllowMultiple(final boolean allowMultiple)
	{
		this.allowMultiple = allowMultiple;
	}
	
	public boolean isEncrypt()
	{
		return encrypt;
	}
	
	public void setEncrypt(final boolean encrypt)
	{
		this.encrypt = encrypt;
	}
	
	public boolean isRequired()
	{
		return required;
	}
	
	public void setRequired(final boolean required)
	{
		this.required = required;
	}
	
	public boolean isHidden()
	{
		return hidden;
	}
	
	public void setHidden(final boolean hidden)
	{
		this.hidden = hidden;
	}
	
	public boolean isSetup()
	{
		return setup;
	}
	
	public void setSetup(final boolean setup)
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
