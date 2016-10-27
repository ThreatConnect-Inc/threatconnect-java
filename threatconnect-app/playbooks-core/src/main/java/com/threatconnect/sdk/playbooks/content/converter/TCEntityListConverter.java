package com.threatconnect.sdk.playbooks.content.converter;

import com.threatconnect.sdk.playbooks.content.entity.TCEntity;

public class TCEntityListConverter extends ListContentConverter<TCEntity>
{
	public TCEntityListConverter()
	{
		super(TCEntity.class);
	}
}
