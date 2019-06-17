package com.threatconnect.sdk.app.service;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.apps.service.Service;
import com.threatconnect.sdk.app.SDKAppLauncher;
import com.threatconnect.sdk.app.service.launcher.DefaultServiceLauncher;
import com.threatconnect.sdk.app.service.launcher.ServiceLauncher;
import com.threatconnect.sdk.log.ServerLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceMain extends SDKAppLauncher<Service>
{
	private static final Logger logger = LoggerFactory.getLogger(ServiceMain.class);
	
	public ServiceMain()
	{
		this(findAppConfig());
	}
	
	public ServiceMain(final AppConfig appConfig)
	{
		super(Service.class, appConfig);
	}
	
	@Override
	public void launch() throws Exception
	{
		logger.info("Retrieving class to execute...");
		final Class<? extends Service> serviceClass = getAppClassToExecute();
		
		// set whether or not api logging is enabled
		ServerLogger.getInstance(getAppConfig()).setEnabled(getAppConfig().isTcLogToApi());
		
		//create a new service executor and start the service
		ServiceLauncher serviceLauncher = new DefaultServiceLauncher(getAppConfig(), serviceClass);
		serviceLauncher.start();
	}
	
	public static void main(String[] args) throws Exception
	{
		new ServiceMain().launch();
	}
}
