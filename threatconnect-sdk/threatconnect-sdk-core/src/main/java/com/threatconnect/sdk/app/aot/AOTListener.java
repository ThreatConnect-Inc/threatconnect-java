package com.threatconnect.sdk.app.aot;

public interface AOTListener
{
	void execute(AOTHandler aotHandler);
	
	void terminate(boolean timeout);
}
