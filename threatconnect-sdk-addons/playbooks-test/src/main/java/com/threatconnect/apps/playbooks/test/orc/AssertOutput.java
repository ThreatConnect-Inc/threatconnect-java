package com.threatconnect.apps.playbooks.test.orc;

import com.threatconnect.apps.playbooks.test.config.PlaybookConfig;
import com.threatconnect.apps.playbooks.test.orc.test.Testable;
import com.threatconnect.apps.playbooks.test.util.ContentServiceUtil;
import com.threatconnect.sdk.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.sdk.playbooks.app.PlaybooksApp;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	
	public AssertOutput assertNotNull(final String outputParam, final PlaybookVariableType type)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("assertNotNull Output Param \"{}\" of type \"{}\"", outputParam, type.toString());
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				Assert.assertNotNull(ContentServiceUtil.read(variable, playbooksApp.getContentService()));
			}
		});
		
		return this;
	}
	
	public AssertOutput assertEquals(final String outputParam, final PlaybookVariableType type, final Object expected)
	{
		//since this param has been requested, make sure it is added to the out params
		getThen().getPlaybooksOrchestration().addOutputParam(outputParam, type);
		
		add(new Testable()
		{
			@Override
			public void run(final PlaybooksApp playbooksApp) throws Exception
			{
				logger.debug("assertEquals Output Param \"{}\" of type \"{}\" = \"{}\"", outputParam, type.toString(),
					expected);
				final String variable = getPlaybookConfig().createVariableForOutputVariable(outputParam, type);
				Assert.assertEquals(expected, ContentServiceUtil.read(variable, playbooksApp.getContentService()));
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