package com.threatconnect.app.playbooks.util;

import com.threatconnect.app.playbooks.content.entity.TCEntity;
import com.threatconnect.sdk.model.Indicator;

/**
 * @author Greg Marut
 */
public class TCEntityUtil
{
	public static TCEntity toTCEntity(final Indicator indicator)
	{
		TCEntity tcEntity = new TCEntity();
		tcEntity.setType(indicator.getIndicatorType());
		tcEntity.setValue(indicator.getIdentifier());
		
		if (null != indicator.getRating())
		{
			tcEntity.setRating(indicator.getRating().shortValue());
		}
		
		if (null != indicator.getConfidence())
		{
			tcEntity.setConfidence(indicator.getConfidence().intValue());
		}
		
		return tcEntity;
	}
}
