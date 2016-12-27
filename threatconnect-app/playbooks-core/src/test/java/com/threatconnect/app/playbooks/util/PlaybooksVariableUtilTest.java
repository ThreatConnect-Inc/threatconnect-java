package com.threatconnect.app.playbooks.util;

import com.threatconnect.app.addons.util.config.install.type.PlaybookVariableType;
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
			PlaybookVariableType.String);
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
		Assert.assertEquals(PlaybookVariableType.String, PlaybooksVariableUtil.extractVariableType(variableTestData));
	}
}
