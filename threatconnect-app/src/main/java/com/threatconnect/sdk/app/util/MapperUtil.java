package com.threatconnect.sdk.app.util;

import java.util.Arrays;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

public class MapperUtil
{
	private static final String[] MAPPING_FILES =
		{
			"dozer-mapping.xml"
	};
	
	/**
	 * Creates and configures a bean mapper
	 * 
	 * @return
	 */
	public static Mapper createMapper()
	{
		// create a new dozer mapper
		DozerBeanMapper mapper = new DozerBeanMapper();
		
		// set the mapping files for this mapper
		mapper.setMappingFiles(Arrays.asList(MAPPING_FILES));
		
		return mapper;
	}
}
