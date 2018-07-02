package com.threatconnect.sdk.app.aot;

import java.util.Map;

public interface AOTListener
{
	void execute(AOTHandler aotHandler, Map<String, String> parameters);
	
	void terminate(boolean timeout);
}
