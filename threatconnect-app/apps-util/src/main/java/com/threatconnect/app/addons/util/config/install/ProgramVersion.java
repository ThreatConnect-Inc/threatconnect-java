package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.validation.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class ProgramVersion implements Comparable<ProgramVersion>
{
	public static final String VERSION_REGEX = "^([0-9]+)\\.([0-9]+)\\.([0-9]+)$";
	public static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);
	
	private static final String ERROR_MESSAGE =
		"Invalid programVersion. Must be in <MAJOR>.<MINOR>.<PATCH> format (e.g. 1.0.0)";
	
	private final int major;
	private final int minor;
	private final int patch;
	
	public ProgramVersion(final int major, final int minor, final int patch)
	{
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	public ProgramVersion(final String version) throws ValidationException
	{
		//check to see if the program versions do not match
		Matcher matcher = ProgramVersion.VERSION_PATTERN.matcher(version);
		if (matcher.find())
		{
			this.major = Integer.parseInt(matcher.group(1));
			this.minor = Integer.parseInt(matcher.group(2));
			this.patch = Integer.parseInt(matcher.group(3));
		}
		else
		{
			throw new ValidationException(ERROR_MESSAGE);
		}
	}
	
	public int getMajor()
	{
		return major;
	}
	
	public int getMinor()
	{
		return minor;
	}
	
	public int getPatch()
	{
		return patch;
	}
	
	@Override
	public int compareTo(final ProgramVersion o)
	{
		//compare major versions
		if (getMajor() == o.getMajor())
		{
			//compare the minor versions
			if (getMinor() == o.getMinor())
			{
				//compare the patch versions
				if (getPatch() == getPatch())
				{
					return 0;
				}
				else
				{
					return Integer.compare(getPatch(), o.getPatch());
				}
			}
			else
			{
				return Integer.compare(getMinor(), o.getMinor());
			}
		}
		else
		{
			return Integer.compare(getMajor(), o.getMajor());
		}
	}
	
	/**
	 * Validates that the program version follows the correct format. If not, an exception is thrown
	 *
	 * @param version
	 * @throws ValidationException if the version does not follow the expected format
	 */
	public static void validate(final String version) throws ValidationException
	{
		if (!ProgramVersion.VERSION_PATTERN.matcher(version).matches())
		{
			throw new ValidationException(ERROR_MESSAGE);
		}
	}
}
