package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.execution.converter.ContentConverter;
import com.threatconnect.app.playbooks.db.DBService;
import com.threatconnect.app.playbooks.variable.PlaybooksVariable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypedContentAccumulator<T> extends ContentAccumulator<T>
{
	private static final Logger logger = LoggerFactory.getLogger(TypedContentAccumulator.class.getName());
	
	private final StandardPlaybookType standardPlaybookType;
	
	public TypedContentAccumulator(final DBService dbService, final StandardPlaybookType standardPlaybookType,
		final ContentConverter<T> contentConverter)
	{
		super(dbService, contentConverter);
		
		//make sure the standard type is not null
		if (null == standardPlaybookType)
		{
			throw new IllegalArgumentException("standardPlaybookType cannot be null or empty");
		}
		
		this.standardPlaybookType = standardPlaybookType;
	}
	
	@Override
	protected PlaybooksVariable verifyKey(final String key)
	{
		//extract the type from this key
		PlaybooksVariable playbooksVariable = super.verifyKey(key);
		
		//check to see if the actual type and the expected type do not match
		if (!standardPlaybookType.toString().equalsIgnoreCase(playbooksVariable.getPlaybookVariableType()))
		{
			throw new IllegalArgumentException(
				"key \"" + key + "\" is of type " + playbooksVariable.getPlaybookVariableType() + ", expected "
					+ standardPlaybookType.toString());
		}
		
		return playbooksVariable;
	}
}
