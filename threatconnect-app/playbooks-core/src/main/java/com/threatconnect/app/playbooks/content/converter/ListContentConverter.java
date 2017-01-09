package com.threatconnect.app.playbooks.content.converter;

import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;

import java.util.List;

public class ListContentConverter<T> extends ContentConverter<List<T>>
{
	private final Class<T> genericClass;

	public ListContentConverter(final Class<T> genericClass)
	{
		this.genericClass = genericClass;
	}

	@Override
	protected JavaType constructType(TypeFactory typeFactory)
	{
		return typeFactory.constructCollectionType(List.class, genericClass);
	}
}
