package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.validation.ValidationException;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author Greg Marut
 */
public class JobTest
{
	@Test
	public void loadJobJson() throws IOException, ValidationException
	{
		File file = new File("src/test/resources/job.json");
		Job job = JobUtil.load(file);
		
		Assert.assertEquals("ThreatConnect", job.getJobName());
		Assert.assertEquals("1.0.0", job.getProgramVersion());
		Assert.assertEquals("TC - CrossIntelSharing v1.0", job.getProgramName());
		
		Assert.assertEquals(6, job.getParams().size());
	}
}
