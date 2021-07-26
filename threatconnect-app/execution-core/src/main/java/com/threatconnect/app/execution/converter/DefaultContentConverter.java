package com.threatconnect.app.execution.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

public class DefaultContentConverter<T> extends ContentConverter<T>
{
	private final Class<T> genericClass;
	
	public DefaultContentConverter(final Class<T> genericClass, final StandardPlaybookType standardPlaybookType)
	{
		super(standardPlaybookType);
		
		this.genericClass = genericClass;
	}
	
	@Override
	protected JavaType constructType(TypeFactory typeFactory)
	{
		return typeFactory.constructType(this.genericClass);
	}
}
