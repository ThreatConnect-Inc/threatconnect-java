package com.threatconnect.app.addons.util.config.validation.json;

import com.threatconnect.app.addons.util.config.attribute.json.Attribute;
import com.threatconnect.app.addons.util.config.attribute.json.AttributeType;
import com.threatconnect.app.addons.util.config.attribute.json.AttributeValidationRule;
import com.threatconnect.app.addons.util.config.validation.ValidationException;
import com.threatconnect.app.addons.util.config.validation.Validator;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Greg Marut
 */
public class AttributeValidator extends Validator<Attribute>
{
	private final Validator<AttributeValidationRule> attributeValidationRuleValidator;
	
	public AttributeValidator()
	{
		this.attributeValidationRuleValidator = new AttributeValidationRuleValidator();
	}
	
	@Override
	public void validate(final Attribute object) throws ValidationException
	{
		//make sure the list of attribute types is not empty
		if (object.getTypes().isEmpty())
		{
			throw new ValidationException("Attribute types cannot be empty.");
		}
		
		//for each of the validation rules
		for (AttributeValidationRule rule : object.getValidationRules())
		{
			attributeValidationRuleValidator.validate(rule);
		}
		
		//check to see if there are any duplicate rules defined
		Set<AttributeValidationRule> duplicates = findDuplicates(object.getValidationRules());
		if (!duplicates.isEmpty())
		{
			throw new ValidationException(
				"Validation Rule \"" + duplicates.iterator().next().getName() + "\" cannot be defined multiple times.");
		}
		
		//load the map of attribute validation rules by name
		Map<String, AttributeValidationRule> validationRuleMap = object.getValidationRules().stream().collect(
			Collectors.toMap(AttributeValidationRule::getName, a -> a));
		
		//create the attribute type validator with the validation rule map
		AttributeTypeValidator attributeTypeValidator = new AttributeTypeValidator(validationRuleMap);
		
		//for each of the attribute types
		for (AttributeType attributeType : object.getTypes())
		{
			attributeTypeValidator.validate(attributeType);
		}
	}
	
	private <T> Set<T> findDuplicates(Collection<T> list)
	{
		Set<T> duplicates = new LinkedHashSet<T>();
		Set<T> uniques = new HashSet<T>();
		
		for (T t : list)
		{
			if (!uniques.add(t))
			{
				duplicates.add(t);
			}
		}
		
		return duplicates;
	}
}
