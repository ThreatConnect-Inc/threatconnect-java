package com.threatconnect.app.execution.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.execution.entity.TCEntity;

public class TCEntityListConverter extends ListContentConverter<TCEntity>
{
	public TCEntityListConverter()
	{
		super(TCEntity.class, StandardPlaybookType.TCEntityArray);
	}
}
