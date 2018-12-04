package com.threatconnect.app.addons.util.config.layout.validation;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.layout.Layout;
import com.threatconnect.app.addons.util.config.layout.LayoutGroup;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

public class LayoutValidator extends Validator<Layout>
{
	private final Install install;
	
	public LayoutValidator(final Install install)
	{
		this.install = install;
	}
	
	@Override
	public void validate(final Layout object) throws ValidationException
	{
		//for each of the groups
		LayoutGroupValidator layoutGroupValidator = new LayoutGroupValidator(install);
		for(LayoutGroup layoutGroup : object.getGroups())
		{
			layoutGroupValidator.validate(layoutGroup);
		}
	}
}
