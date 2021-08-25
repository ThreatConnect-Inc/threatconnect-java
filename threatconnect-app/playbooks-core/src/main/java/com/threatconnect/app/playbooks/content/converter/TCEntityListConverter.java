package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.entity.TCEntity;

public class TCEntityListConverter extends ListContentConverter<TCEntity>
{
	public TCEntityListConverter()
	{
		super(TCEntity.class, StandardPlaybookType.TCEntityArray);
	}
}
