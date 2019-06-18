package com.threatconnect.app.addons.util.config.validation.service;

import com.threatconnect.app.addons.util.config.install.Install;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;
import com.threatconnect.app.apps.service.api.ApiService;
import com.threatconnect.app.apps.service.api.mapping.ApiMapper;
import com.threatconnect.app.apps.service.api.mapping.ApiMapping;

public class ApiServiceValidator extends Validator<Install>
{
	@Override
	public void validate(final Install object) throws ValidationException
	{
		//validate the program language
		if (null == object.getMainAppClass())
		{
			throw new ValidationException("mainAppClass is not defined.");
		}
		
		try
		{
			//retrieve the
			final String mainAppClass = object.getMainAppClass();
			final Class<? extends ApiService> apiServiceClass = (Class<? extends ApiService>) Class.forName(mainAppClass);
			
			//retrieve all of the service items from the main class
			ApiMapper apiMapper = new ApiMapper(apiServiceClass);
			if (apiMapper.getServiceItems().isEmpty())
			{
				throw new ValidationException(
					mainAppClass + " does not define any api endpoints. Please add at least one @" + ApiMapping.class.getName()
						+ " annotated method.");
			}
		}
		catch (ClassCastException | ClassNotFoundException e)
		{
			throw new ValidationException("mainAppClass must extend " + ApiService.class.getName());
		}
	}
}
