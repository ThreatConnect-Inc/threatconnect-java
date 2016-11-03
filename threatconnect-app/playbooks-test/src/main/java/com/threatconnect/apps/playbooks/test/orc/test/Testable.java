package com.threatconnect.apps.playbooks.test.orc.test;

import com.threatconnect.sdk.playbooks.app.PlaybooksApp;

/**
 * @author Greg Marut
 */
public interface Testable
{
	void run(final PlaybooksApp playbooksApp) throws Exception;
}
