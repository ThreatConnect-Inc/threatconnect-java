package com.threatconnect.app.playbooks.util;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.variable.PlaybooksVariable;
import com.threatconnect.app.playbooks.variable.PlaybooksVariableNamespace;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Greg Marut
 */
public class PlaybooksVariableUtilTest
{
	private String variableTestData;
	private PlaybooksVariable playbooksVariableTestData;
	
	public PlaybooksVariableUtilTest()
	{
		variableTestData = "#Global:0:gbl.test!String";
		playbooksVariableTestData = new PlaybooksVariable(PlaybooksVariableNamespace.App, 1, "app.name.var",
			StandardPlaybookType.String);
	}
	
	@Test
	public void isVariableTest()
	{
		Assert.assertTrue(PlaybooksVariableUtil.isVariable(variableTestData));
		Assert.assertTrue(PlaybooksVariableUtil.isVariable(playbooksVariableTestData.toString()));
		Assert.assertFalse(PlaybooksVariableUtil.isVariable("not a variable"));
	}
	
	@Test
	public void containsVariableTest()
	{
		final String testData = "Today is " + variableTestData;
		Assert.assertFalse(PlaybooksVariableUtil.isVariable(testData));
		Assert.assertTrue(PlaybooksVariableUtil.containsVariable(testData));
	}
	
	@Test
	public void dataTypeText()
	{
		Assert.assertTrue(PlaybooksVariableUtil.isStringType(variableTestData));
		Assert.assertFalse(PlaybooksVariableUtil.isStringArrayType(variableTestData));
		Assert.assertEquals(StandardPlaybookType.String.toString(),
			PlaybooksVariableUtil.extractVariableType(variableTestData));
	}
	
	@Test
	public void embeddedVariableTest()
	{
		Assert.assertEquals("String",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!String"));
		Assert.assertEquals("StringArray",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!StringArray"));
		Assert.assertEquals("CustomVariable",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!CustomVariable"));
		Assert.assertEquals("Binary",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!Binary"));
		Assert.assertEquals("BinaryArray",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!BinaryArray"));
		
		Assert.assertEquals("String",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!String!"));
		Assert.assertEquals("StringArray",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!StringArray!"));
		Assert.assertEquals("StringCustom",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!StringCustom!"));
		Assert.assertEquals("Binary",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!Binary!"));
		Assert.assertEquals("BinaryArray",
			PlaybooksVariableUtil.extractVariableType("#Global:0:gbl.test!BinaryArray!"));
	}
}
