package com.threatconnect.sdk.app.aot;

public interface AOTListener
{
	void execute();
	
	void terminate(boolean timeout);
}
