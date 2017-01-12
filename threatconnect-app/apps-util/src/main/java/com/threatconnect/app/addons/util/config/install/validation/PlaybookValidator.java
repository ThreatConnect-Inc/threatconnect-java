package com.threatconnect.app.addons.util.config.install.validation;

import com.threatconnect.app.addons.util.config.install.Playbook;

/**
 * @author Greg Marut
 */
public class PlaybookValidator extends Validator<Playbook>
{
	public static final int PLAYBOOK_TYPE_MAX_LENGTH = 35;
	
	@Override
	public void validate(final Playbook object) throws ValidationException
	{
		//check to see if the playbook type is missing
		if (null == object.getType())
		{
			throw new ValidationException("'playbook.type' must be defined for a playbook app.");
		}
		
		//check the length of the playbook type
		if (object.getType().trim().length() > PLAYBOOK_TYPE_MAX_LENGTH)
		{
			throw new ValidationException(
				"'playbook.type' must be " + PLAYBOOK_TYPE_MAX_LENGTH + " characters or less.");
		}
	}
}
