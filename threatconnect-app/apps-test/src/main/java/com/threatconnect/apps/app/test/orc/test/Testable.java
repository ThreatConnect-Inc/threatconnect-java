package com.threatconnect.apps.app.test.orc.test;

import com.threatconnect.app.apps.App;

/**
 * @author Greg Marut
 */
public interface Testable<A extends App>
{
	void run(final A playbooksApp) throws Exception;
}
