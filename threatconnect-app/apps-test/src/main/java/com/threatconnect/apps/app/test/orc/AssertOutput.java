package com.threatconnect.apps.app.test.orc;

import com.threatconnect.app.apps.App;
import com.threatconnect.apps.app.test.config.AppConfiguration;
import com.threatconnect.apps.app.test.orc.test.Testable;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;

/**
 * @author Greg Marut
 */
public class AssertOutput<A extends App> implements Then<POResult<A>>
{
	private static final Logger logger = LoggerFactory.getLogger(AssertOutput.class);
	
	private final POResult<A> poResult;
	
	AssertOutput(final POResult<A> poResult)
	{
		this.poResult = poResult;
	}
	
	public AssertOutput<A> fail()
	{
		add(new Testable<A>()
		{
			@Override
			public void run(final A app) throws Exception
			{
				logger.debug("fail()");
				Assert.fail();
			}
		});
		
		return this;
	}
	
	public AssertOutput<A> assertMessageTcContains(final String text)
	{
		add(new Testable<A>()
		{
			@Override
			public void run(final A app) throws Exception
			{
				//read the message tc file
				File file = app.getMessageLogFile();
				
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
	
	private AppConfiguration getAppConfiguration()
	{
		return then().getAppOrchestration().getAppConfiguration();
	}
	
	private void add(final Testable<A> testable)
	{
		then().getTests().add(testable);
	}
	
	@Override
	public POResult<A> then()
	{
		return poResult;
	}
}