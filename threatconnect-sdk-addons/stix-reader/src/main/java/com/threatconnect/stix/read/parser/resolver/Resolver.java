package com.threatconnect.stix.read.parser.resolver;

import com.threatconnect.stix.read.parser.observer.Observer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Considering some nodes can either contain the data to parse or can simply be pointers to
 * another node, we may need to hunt down the node which contains the data. Using a
 * node as a start point, this method will follow the idrefs (pointers), if any, to
 * resolve the node that contains the content
 *
 * @author Greg Marut
 */
public class Resolver<E, O extends Observer<E>>
{
	private static final Logger logger = LoggerFactory.getLogger(Resolver.class);
	
	//holds the map of ids and the observers to fire when they are found
	protected final Map<String, List<E>> resolvedObjectMap;
	protected final Map<String, List<O>> observerMap;
	
	public Resolver()
	{
		this.resolvedObjectMap = new HashMap<String, List<E>>();
		this.observerMap = new HashMap<String, List<O>>();
	}
	
	public void flushAll()
	{
		//for each of the resolved nodes
		for (Map.Entry<String, List<E>> resolvedObjectEntry : resolvedObjectMap.entrySet())
		{
			//retrieve the list of observers waiting for this id
			List<O> observers = observerMap.get(resolvedObjectEntry.getKey());
			
			//make sure the list of observers is not null
			if (null != observers)
			{
				//for each of the resolved objects
				for (E node : resolvedObjectEntry.getValue())
				{
					//for each of the observers
					for (O observer : observers)
					{
						//notify the observer
						observer.found(node);
					}
				}
			}
		}
	}
	
	public void addObserver(final String id, final O nodeObserver)
	{
		//check to see if the map contains this id
		List<O> observerList = observerMap.computeIfAbsent(id, k -> new ArrayList<O>());
		
		observerList.add(nodeObserver);
	}
	
	public void addResolvedObject(final String id, final E resolvedE)
	{
		//check to see if the map contains this id
		List<E> resolvedObjectList = resolvedObjectMap.computeIfAbsent(id, k -> new ArrayList<E>());
		
		resolvedObjectList.add(resolvedE);
	}
}
