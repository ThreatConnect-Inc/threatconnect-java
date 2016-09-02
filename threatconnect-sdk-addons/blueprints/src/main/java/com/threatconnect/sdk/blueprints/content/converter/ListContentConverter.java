package com.threatconnect.sdk.blueprints.content.converter;

import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import java.util.List;

public class ListContentConverter<T> extends ContentConverter<T>
{
	public ListContentConverter(final Class<T> genericClass)
	{
		super(genericClass);
	}

	@Override
	protected JavaType constructType(TypeFactory typeFactory, Class<T> genericClass)
	{
		return typeFactory.constructCollectionType(List.class, genericClass);
	}
}
