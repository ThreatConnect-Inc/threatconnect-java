package com.threatconnect.app.playbooks.content.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

/**
 * @author Greg Marut
 */
public class ByteArrayConverter extends DefaultContentConverter<byte[]>
{
	public ByteArrayConverter()
	{
		super(byte[].class, StandardPlaybookType.Binary);
	}
}
