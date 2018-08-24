package com.threatconnect.sdk.model.util.merge;

import com.threatconnect.sdk.model.Indicator;

public interface MergeStrategy
{
	/**
	 * Merges the two indicators together
	 * @param i1
	 * @param i2
	 */
	void merge(Indicator i1, Indicator i2);
}
