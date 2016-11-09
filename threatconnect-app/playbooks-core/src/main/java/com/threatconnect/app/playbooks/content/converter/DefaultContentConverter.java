package com.threatconnect.app.playbooks.content.converter;

import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

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
