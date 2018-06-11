package com.threatconnect.app.playbooks.content.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;

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
