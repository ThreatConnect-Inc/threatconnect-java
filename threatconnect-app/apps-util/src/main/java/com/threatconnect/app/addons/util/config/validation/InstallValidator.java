package com.threatconnect.app.addons.util.config.validation;

import com.threatconnect.app.addons.util.config.Feature;
import com.threatconnect.app.addons.util.config.install.Feed;
import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.install.Param;
import com.threatconnect.app.addons.util.config.install.Playbook;
import com.threatconnect.app.addons.util.config.install.ProgramLanguageType;
import com.threatconnect.app.addons.util.config.install.ProgramVersion;
import com.threatconnect.app.addons.util.config.install.RunLevelType;
import com.threatconnect.app.addons.util.config.install.ServerVersion;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Greg Marut
 */
public class InstallValidator extends Validator<Install>
{
	private final Validator<Param> paramValidator;
	private final Validator<Playbook> playbookValidator;
	private final Validator<Feed> feedValidator;
	
	public InstallValidator()
	{
		this.paramValidator = new ParamValidator();
		this.playbookValidator = new PlaybookValidator();
		this.feedValidator = new FeedValidator();
	}
	
	@Override
	public void validate(final Install object) throws ValidationException
	{
		//validate the program version
		if (null == object.getProgramVersion())
		{
			throw new ValidationException("programVersion is not defined.");
		}
		else
		{
			//validate the format of the program version
			ProgramVersion.validate(object.getProgramVersion());
		}
		
		//validate the program language
		if (null == object.getProgramLanguage())
		{
			throw new ValidationException("programLanguage is not defined.");
		}
		
		//check to see if min server version is set
		if (null != object.getMinServerVersion())
		{
			//validate the format of the server version
			ServerVersion.validate(object.getMinServerVersion());
		}
		
		//validate the runtime levels
		if (null == object.getRuntimeLevel())
		{
			throw new ValidationException("runtimeLevel is not defined.");
		}
		//check to see if this is a playbook app
		else if (object.getRuntimeLevel().equals(RunLevelType.Playbook))
		{
			//check to see if the playbook object is missing
			if (null == object.getPlaybook())
			{
				throw new ValidationException("'playbook' config must be defined for a playbook app.");
			}
			
			//validate the playbook
			playbookValidator.validate(object.getPlaybook());
		}
		
		//check to see if this is a third party app
		if (object.getRuntimeLevel().equals(RunLevelType.ThirdParty))
		{
			//validate the display name
			if (null == object.getDisplayName())
			{
				throw new ValidationException("displayName is not defined.");
			}
			
			//make sure the program languate type is NONE
			if (!ProgramLanguageType.NONE.equals(object.getProgramLanguage()))
			{
				throw new ValidationException("programLanguage is expected to be NONE for Third Party Apps.");
			}
		}
		else
		{
			//check to see if the programming language is JAVA, PLAYBOOK or PYTHON
			if (ProgramLanguageType.JAVA.equals(object.getProgramLanguage()) ||
				ProgramLanguageType.PLAYBOOK.equals(object.getProgramLanguage()) ||
				ProgramLanguageType.PYTHON.equals(object.getProgramLanguage()))
			{
				//validate the program main
				if (isNullOrEmpty(object.getProgramMain()))
				{
					throw new ValidationException("programMain is not defined.");
				}
			}
		}
		
		//check to see if this app supports the smtp settings feature
		if (object.getFeatures().contains(Feature.SMTP_SETTINGS))
		{
			//any app that supports smtp settings must also support secure params
			if (!object.getFeatures().contains(Feature.SECURE_PARAMS))
			{
				throw new ValidationException(
					"Any app that supports the \"" + Feature.SMTP_SETTINGS + "\" feature must also specify the \""
						+ Feature.SECURE_PARAMS + "\" feature.");
			}
		}
		
		//for each of the params
		for (Param param : object.getParams())
		{
			//validate this param
			paramValidator.validate(param);
		}
		
		//holds the set of source names for the feeds
		Set<String> feedSourceNames = new HashSet<String>();
		
		//for each of the feeds
		for (Feed feed : object.getFeeds())
		{
			//make sure this feed source name does not already exist in the set
			if (!feedSourceNames.contains(feed.getSourceName()))
			{
				//validate this feed
				feedValidator.validate(feed);
				
				//add this feed source name to the map
				feedSourceNames.add(feed.getSourceName());
			}
			else
			{
				throw new ValidationException("Multiple feeds with sourceName \"" + feed.getSourceName()
					+ "\" were found. Please make sure sourceName is unique.");
			}
		}
	}
}
