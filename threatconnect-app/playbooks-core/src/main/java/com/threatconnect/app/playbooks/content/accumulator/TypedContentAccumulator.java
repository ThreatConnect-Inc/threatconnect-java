package com.threatconnect.app.playbooks.content.accumulator;

import com.threatconnect.app.addons.util.config.install.PlaybookVariableType;
import com.threatconnect.app.playbooks.content.converter.ContentConverter;
import com.threatconnect.app.playbooks.db.DBService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypedContentAccumulator<T> extends ContentAccumulator<T>
{
	private static final Logger logger = LoggerFactory.getLogger(TypedContentAccumulator.class.getName());
	
	private final PlaybookVariableType standardType;
	
	public TypedContentAccumulator(final DBService dbService, final PlaybookVariableType standardType,
		final ContentConverter<T> contentConverter)
	{
		super(dbService, contentConverter);
		
		//make sure the standard type is not null
		if (null == standardType)
		{
			throw new IllegalArgumentException("standardType cannot be null or empty");
		}
		
		this.standardType = standardType;
	}
	
	@Override
	protected PlaybookVariableType verifyKey(final String key)
	{
		//extract the type from this key
		PlaybookVariableType actualType = super.verifyKey(key);
		
		//check to see if the actual type and the expected type do not match
		if (!standardType.equals(actualType))
		{
			throw new IllegalArgumentException(
				"key is of type " + actualType.toString() + ", expected " + standardType.toString());
		}
		
		return actualType;
	}
}
