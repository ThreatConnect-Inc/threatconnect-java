package com.threatconnect.apps.playbooks.test.orc.test;

import com.threatconnect.app.playbooks.app.PlaybooksApp;

/**
 * @author Greg Marut
 */
public interface Testable
{
	void run(final PlaybooksApp playbooksApp) throws Exception;
}
