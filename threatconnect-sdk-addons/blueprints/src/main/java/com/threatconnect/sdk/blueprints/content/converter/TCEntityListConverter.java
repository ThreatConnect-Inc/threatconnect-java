package com.threatconnect.sdk.blueprints.content.converter;

import com.threatconnect.sdk.blueprints.content.entity.TCEntity;

public class TCEntityListConverter extends ListContentConverter<TCEntity>
{
	public TCEntityListConverter()
	{
		super(TCEntity.class);
	}
}
