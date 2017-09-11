package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.validation.ValidationException;

/**
 * @author Greg Marut
 */
public class ProgramVersion extends MajorMinorPatchVersion
{
	private static final String ERROR_MESSAGE =
		"Invalid programVersion. Must be in <MAJOR>.<MINOR>.<PATCH> format (e.g. 1.0.0)";
	
	public ProgramVersion(final int major, final int minor, final int patch)
	{
		super(major, minor, patch);
	}
	
	public ProgramVersion(final String version) throws ValidationException
	{
		super(version, ERROR_MESSAGE);
	}
	
	/**
	 * Validates that the program version follows the correct format. If not, an exception is thrown
	 *
	 * @param version
	 * @throws ValidationException if the version does not follow the expected format
	 */
	public static void validate(final String version) throws ValidationException
	{
		if (!VERSION_PATTERN.matcher(version).matches())
		{
			throw new ValidationException(ERROR_MESSAGE);
		}
	}
}
