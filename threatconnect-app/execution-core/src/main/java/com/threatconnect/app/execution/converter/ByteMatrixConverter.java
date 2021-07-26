package com.threatconnect.app.execution.converter;

import com.threatconnect.app.addons.util.config.install.StandardPlaybookType;

/**
 * @author Greg Marut
 */
public class ByteMatrixConverter extends DefaultContentConverter<byte[][]>
{
	public ByteMatrixConverter()
	{
		super(byte[][].class, StandardPlaybookType.BinaryArray);
	}
}
