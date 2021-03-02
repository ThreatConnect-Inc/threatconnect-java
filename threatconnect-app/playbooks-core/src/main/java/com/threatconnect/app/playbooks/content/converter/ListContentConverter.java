package com.threatconnect.app.playbooks.content.converter;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

import java.util.List;

public class ListContentConverter<T> extends ContentConverter<List<T>>
{
	private final Class<T> genericClass;

	public ListContentConverter(final Class<T> genericClass, final StandardPlaybookType standardPlaybookType)
	{
		super(standardPlaybookType);
		
		this.genericClass = genericClass;
	}

	@Override
	protected JavaType constructType(TypeFactory typeFactory)
	{
		return typeFactory.constructCollectionType(List.class, genericClass);
	}
}
