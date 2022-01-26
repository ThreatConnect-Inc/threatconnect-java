package com.threatconnect.app.addons.util.config.im;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IntegrationManifest
{
	private String note;
	private String productName;
	private String svgIcon;
	private ConnectionTest connectionTest;
	
	private final Set<String> programNames;
	private final List<Section> sections;
	private final List<Metric> metrics;
	
	public IntegrationManifest()
	{
		programNames = new HashSet<>();
		sections = new ArrayList<>();
		metrics = new ArrayList<>();
	}
	
	public String getNote()
	{
		return note;
	}
	
	public void setNote(final String note)
	{
		this.note = note;
	}
	
	public String getProductName()
	{
		return productName;
	}
	
	public void setProductName(final String productName)
	{
		this.productName = productName;
	}
	
	public String getSvgIcon()
	{
		return svgIcon;
	}
	
	public void setSvgIcon(final String svgIcon)
	{
		this.svgIcon = svgIcon;
	}
	
	public ConnectionTest getConnectionTest()
	{
		return connectionTest;
	}
	
	public void setConnectionTest(final ConnectionTest connectionTest)
	{
		this.connectionTest = connectionTest;
	}
	
	public Set<String> getProgramNames()
	{
		return programNames;
	}
	
	public List<Section> getSections()
	{
		return sections;
	}
	
	public List<Metric> getMetrics()
	{
		return metrics;
	}
}
