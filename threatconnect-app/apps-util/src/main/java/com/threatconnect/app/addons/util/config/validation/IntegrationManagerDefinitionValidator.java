package com.threatconnect.app.addons.util.config.validation;

import com.threatconnect.app.addons.util.config.im.IntegrationManagerDefinition;
import com.threatconnect.app.addons.util.config.im.Section;
import com.threatconnect.app.addons.util.config.install.Param;

/**
 * @author Greg Marut
 */
public class IntegrationManagerDefinitionValidator extends Validator<IntegrationManagerDefinition>
{
	private final Validator<Param> paramValidator;
	
	public IntegrationManagerDefinitionValidator()
	{
		this.paramValidator = new ParamValidator();
	}
	
	@Override
	public void validate(final IntegrationManagerDefinition object) throws ValidationException
	{
		//validate the product name
		if (null == object.getProductName())
		{
			throw new ValidationException("productName is not defined.");
		}
		
		//validate there is at least 1 program name
		if (object.getProgramNames().isEmpty())
		{
			throw new ValidationException("programNames is empty.");
		}
		
		//for each of the sections
		for (Section section : object.getSections())
		{
			//validate that the section name is defined
			if (null == section.getSectionName())
			{
				throw new ValidationException("sectionName is not defined.");
			}
			
			//for each of the params
			for (Param param : section.getParams())
			{
				//validate this param
				paramValidator.validate(param);
			}
		}
	}
}
