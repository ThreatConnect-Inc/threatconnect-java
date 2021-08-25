package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;
import com.threatconnect.app.playbooks.content.entity.TCEntity;

public class TCEntityConverter extends DefaultContentConverter<TCEntity>
{
	public TCEntityConverter()
	{
		super(TCEntity.class, StandardPlaybookType.TCEntity);
	}
}
