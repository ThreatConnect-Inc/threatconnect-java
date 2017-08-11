package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.apps.App;
import com.threatconnect.app.playbooks.app.PlaybooksApp;
import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.orc.test.OutputTest;
import com.threatconnect.apps.playbooks.test.orc.test.Testable;
import com.threatconnect.apps.playbooks.test.util.ContentServiceUtil;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.List;

/**
 * @author Greg Marut
 */
public class AssertOutput extends AbstractThen<POResult>
{
	private static final Logger logger = LoggerFactory.getLogger(AssertOutput.class);
	
	AssertOutput(final POResult poResult)
	{
		super(poResult);
	}
	
	public AssertOutput fail()
	{
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("fail()");
				Assert.fail();
			}
		});
		
		return this;
	}
	
	public AssertOutput assertNull(final String outputParam, final StandardPlaybookType type)
	{
		return assertNull(outputParam, type.toString());
	}
	
	public AssertOutput assertNull(final String outputParam, final String type)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("assertNull Output Param \"{}\" of type \"{}\"", outputParam, type);
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				Assert.assertNull(ContentServiceUtil.read(variable, playbooksApp.getContentService()));
			}
		});
		
		return this;
	}
	
	public AssertOutput assertNotNull(final String outputParam, final StandardPlaybookType type)
	{
		return assertNotNull(outputParam, type.toString());
	}
	
	public AssertOutput assertNotNull(final String outputParam, final String type)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("assertNotNull Output Param \"{}\" of type \"{}\"", outputParam, type);
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				Assert.assertNotNull(ContentServiceUtil.read(variable, playbooksApp.getContentService()));
			}
		});
		
		return this;
	}
	
	public AssertOutput assertEquals(final String outputParam, final StandardPlaybookType type, final Object expected)
	{
		return assertEquals(outputParam, type.toString(), expected);
	}
	
	public AssertOutput assertEquals(final String outputParam, final String type, final Object expected)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("assertEquals Output Param \"{}\" of type \"{}\" = \"{}\"", outputParam, type,
					expected);
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				Assert.assertEquals(expected, ContentServiceUtil.read(variable, playbooksApp.getContentService()));
			}
		});
		
		return this;
	}
	
	public AssertOutput assertStringArrayEquals(final String outputParam, final StandardPlaybookType type,
		final List<String> expected)
	{
		return assertStringArrayEquals(outputParam, type.toString(), expected);
	}
	
	public AssertOutput assertStringArrayEquals(final String outputParam, final String type,
		final List<String> expected)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("assertStringArrayEquals Output Param \"{}\" of type \"{}\" = \"{}\"", outputParam,
					type, expected);
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				List<String> result = playbooksApp.getContentService().readStringList(variable);
				Assert.assertArrayEquals(expected.toArray(new String[] {}), result.toArray(new String[] {}));
			}
		});
		
		return this;
	}
	
	public AssertOutput assertStringArraySize(final String outputParam, final StandardPlaybookType type,
		final int expected)
	{
		return assertStringArraySize(outputParam, type.toString(), expected);
	}
	
	public AssertOutput assertStringArraySize(final String outputParam, final String type,
		final int expected)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("assertStringArraySize Output Param \"{}\" of type \"{}\" = \"{}\"", outputParam,
					type, expected);
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				List<String> result = playbooksApp.getContentService().readStringList(variable);
				Assert.assertEquals(expected, result.size());
			}
		});
		
		return this;
	}
	
	public AssertOutput assertMessageTcContains(final String text)
	{
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				//read the message tc file
				File file = playbooksApp.getMessageLogFile();
				
				try (FileInputStream fileInputStream = new FileInputStream(file))
				{
					ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
					IOUtils.copy(fileInputStream, byteArrayOutputStream);
					String messageTcText = new String(byteArrayOutputStream.toByteArray());
					
					logger.debug("assertMessageTcContains \"{}\" contains \"{}\"", messageTcText, text);
					
					//check to see if the messageTcText does not contain the text
					if (!messageTcText.contains(text))
					{
						throw new AssertionError(
							App.MESSAGE_TC + " does not contain the following text: \"" + text + "\"");
					}
				}
			}
		});
		
		return this;
	}
	
	public AssertOutput runTest(final String outputParam, final StandardPlaybookType type, final OutputTest outputTest)
	{
		return runTest(outputParam, type.toString(), outputTest);
	}
	
	public AssertOutput runTest(final String outputParam, final String type, final OutputTest outputTest)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("runTest Output Param \"{}\" of type \"{}\"", outputParam, type);
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				Assert.assertTrue("runTest Output Param \"{}\" of type \"{}\" failed.",
					outputTest.test(ContentServiceUtil.read(variable, playbooksApp.getContentService())));
			}
		});
		
		return this;
	}
	
	private PlaybookConfig getPlaybookConfig()
	{
		return getThen().getPlaybooksOrchestration().getPlaybookConfig();
	}
	
	private void add(final Testable testable)
	{
		getThen().getTests().add(testable);
	}
}