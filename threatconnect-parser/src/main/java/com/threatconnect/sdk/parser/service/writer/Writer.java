package com.threatconnect.sdk.parser.service.writer;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.conn.Connection;
import com.threatconnect.sdk.parser.util.MapperUtil;

public abstract class Writer
{
	protected final Connection connection;
	protected final Mapper mapper;
	
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public Writer(final Connection connection)
	{
		this.connection = connection;
		this.mapper = MapperUtil.createMapper();
	}
}
