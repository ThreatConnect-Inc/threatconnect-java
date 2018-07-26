package com.threatconnect.sdk.model.util.merge;

import com.threatconnect.sdk.model.Indicator;

public class MergeAttributeStrategy implements MergeStrategy
{
	@Override
	public void merge(final Indicator i1, final Indicator i2)
	{
		//add all the attributes together
		i1.getAttributes().addAll(i2.getAttributes());
		i2.getAttributes().addAll(i1.getAttributes());
	}
}
