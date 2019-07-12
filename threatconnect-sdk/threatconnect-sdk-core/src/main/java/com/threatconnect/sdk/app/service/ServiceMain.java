package com.threatconnect.sdk.app.service;

import com.threatconnect.app.apps.AppConfig;
import com.threatconnect.app.services.Service;
import com.threatconnect.app.services.api.ApiService;
import com.threatconnect.playbooks.service.PlaybookService;
import com.threatconnect.playbooks.service.webhook.WebhookService;
import com.threatconnect.sdk.app.LoggerUtil;
import com.threatconnect.sdk.app.SDKAppLauncher;
import com.threatconnect.sdk.app.exception.AppInitializationException;
import com.threatconnect.sdk.app.service.launcher.ApiServiceLauncher;
import com.threatconnect.sdk.app.service.launcher.PlaybookServiceLauncher;
import com.threatconnect.sdk.app.service.launcher.ServiceLauncher;
import com.threatconnect.sdk.app.service.launcher.WebhookServiceLauncher;

import com.threatconnect.sdk.log.ServerLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public final class ServiceMain extends SDKAppLauncher<Service>
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
		// set whether or not api logging is enabled
		ServerLogger.getInstance(getAppConfig()).setEnabled(getAppConfig().isTcLogToApi());
		
		logger.info("Retrieving class to execute...");
		final Class<? extends Service> serviceClass = getAppClassToExecute();
		final Service service;
		
		// reconfigure the log file for this app
		logger.info("Configuring App Logging...");
		File logFile = new File(getAppConfig().getTcLogPath() + File.separator + serviceClass.getSimpleName() + ".log");
		LoggerUtil.reconfigureGlobalLogger(logFile, getAppConfig());
		
		try
		{
			// instantiate a new service class
			logger.trace("Instantiating service class: " + serviceClass.getName());
			service = serviceClass.newInstance();
		}
		catch (InstantiationException | IllegalAccessException e)
		{
			throw new AppInitializationException(e);
		}
		
		//initialize the service
		service.init(getAppConfig());
		
		//create a new service executor and start the service
		ServiceLauncher<?> serviceLauncher;
		
		if (ApiService.class.isAssignableFrom(serviceClass))
		{
			serviceLauncher = new ApiServiceLauncher(getAppConfig(), (ApiService) service);
		}
		else if (WebhookService.class.isAssignableFrom(serviceClass))
		{
			serviceLauncher = new WebhookServiceLauncher(getAppConfig(), (WebhookService) service);
		}
		else if (PlaybookService.class.isAssignableFrom(serviceClass))
		{
			serviceLauncher = new PlaybookServiceLauncher<PlaybookService>(getAppConfig(), (PlaybookService) service);
		}
		else
		{
			throw new AppInitializationException("Unsupported service: " + serviceClass.getName());
		}
		
		serviceLauncher.start();
	}
	
	public static void main(String[] args) throws Exception
	{
		new ServiceMain().launch();
	}
}
