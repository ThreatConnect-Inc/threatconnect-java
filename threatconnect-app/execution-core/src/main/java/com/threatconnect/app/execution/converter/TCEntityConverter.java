package com.threatconnect.app.execution.converter;

import com.threatconnect.app.execution.entity.TCEntity;

public class TCEntityConverter extends DefaultContentConverter<TCEntity>
{
	public TCEntityConverter()
	{
		super(TCEntity.class);
	}
}
