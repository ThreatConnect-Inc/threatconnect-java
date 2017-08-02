package com.threatconnect.app.addons.util.config.install;

import com.threatconnect.app.addons.util.config.validation.ValidationException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Greg Marut
 */
public class MajorMinorPatchVersion implements Comparable<MajorMinorPatchVersion>
{
	public static final String VERSION_REGEX = "^([0-9]+)\\.([0-9]+)\\.([0-9]+)$";
	public static final Pattern VERSION_PATTERN = Pattern.compile(VERSION_REGEX);
	
	private final int major;
	private final int minor;
	private final int patch;
	
	public MajorMinorPatchVersion(final int major, final int minor, final int patch)
	{
		this.major = major;
		this.minor = minor;
		this.patch = patch;
	}
	
	protected MajorMinorPatchVersion(final String version, final String errorMessage) throws ValidationException
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
			throw new ValidationException(errorMessage);
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
	public int compareTo(final MajorMinorPatchVersion o)
	{
		//compare major versions
		if (getMajor() == o.getMajor())
		{
			//compare the minor versions
			if (getMinor() == o.getMinor())
			{
				//compare the patch versions
				if (getPatch() == o.getPatch())
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
}
