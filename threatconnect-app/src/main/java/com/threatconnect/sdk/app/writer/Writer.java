package com.threatconnect.sdk.app.writer;

import java.io.IOException;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.threatconnect.sdk.app.model.GroupType;
import com.threatconnect.sdk.app.model.Indicator;
import com.threatconnect.sdk.app.service.save.AssociateFailedException;
import com.threatconnect.sdk.app.util.MapperUtil;
import com.threatconnect.sdk.conn.Connection;

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
	
	/**
	 * Associates a group with the saved object of this writer class
	 * 
	 * @param groupType
	 * @param savedID
	 * @param writer
	 * @throws AssociateFailedException
	 * @throws IOException
	 */
	public abstract void associateGroup(final GroupType groupType, final Integer savedID)
		throws AssociateFailedException, IOException;
		
	/**
	 * Associates an indicator with the saved object of this writer class
	 * 
	 * @param indicator
	 * @param writer
	 * @throws AssociateFailedException
	 * @throws IOException
	 */
	public abstract void associateIndicator(final Indicator indicator)
		throws AssociateFailedException, IOException;
}
