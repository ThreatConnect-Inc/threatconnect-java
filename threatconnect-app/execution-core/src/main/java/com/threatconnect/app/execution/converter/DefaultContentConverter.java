package com.threatconnect.app.execution.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class DefaultContentConverter<T> extends ContentConverter<T>
{
	private final Class<T> genericClass;
	
	public DefaultContentConverter(final Class<T> genericClass)
	{
		this.genericClass = genericClass;
	}
	
	@Override
	protected JavaType constructType(TypeFactory typeFactory)
	{
		return typeFactory.constructType(this.genericClass);
	}
}
