package com.threatconnect.sdk.blueprints.app;

import com.threatconnect.sdk.app.App;
import com.threatconnect.sdk.app.AppConfig;
import com.threatconnect.sdk.app.ExitStatus;
import com.threatconnect.sdk.blueprints.content.ContentService;
import com.threatconnect.sdk.blueprints.db.DBService;
import com.threatconnect.sdk.blueprints.db.DBServiceFactory;

/**
 * @author Greg Marut
 */
public abstract class BlueprintsApp extends App
{
	//holds the content service object for reading and writing content
	private final ContentService contentService;

	public BlueprintsApp()
	{
		this(DBServiceFactory.buildFromAppConfig());
	}

	public BlueprintsApp(final DBService dbService)
	{
		this.contentService = new ContentService(dbService);
	}

	@Override
	public ExitStatus execute(AppConfig appConfig) throws Exception
	{
		return execute(BlueprintsAppConfig.getInstance());
	}

	public ContentService getContentService()
	{
		return contentService;
	}

	/**
	 * Executes the blueprint app
	 *
	 * @param blueprintsAppConfig
	 * @return
	 * @throws Exception
	 */
	protected abstract ExitStatus execute(BlueprintsAppConfig blueprintsAppConfig) throws Exception;
}
