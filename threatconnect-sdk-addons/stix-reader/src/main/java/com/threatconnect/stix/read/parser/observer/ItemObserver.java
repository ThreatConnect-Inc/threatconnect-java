package com.threatconnect.stix.read.parser.observer;

import com.threatconnect.sdk.model.Item;

import java.util.List;

/**
 * @author Greg Marut
 */
public interface ItemObserver extends Observer<List<? extends Item>>
{
	
}
