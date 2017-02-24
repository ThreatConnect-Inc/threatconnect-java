package com.threatconnect.app.playbooks.content.entity;

/**
 * A generic class for holding a key value pair
 *
 * @param <K> Key type
 * @param <V> Value type
 * @author Greg Marut
 */
public class KeyValue<K, V>
{
	private K key;
	private V value;
	
	public KeyValue()
	{
	}
	
	public KeyValue(K key, V value)
	{
		this.key = key;
		this.value = value;
	}
	
	public void setKey(K key)
	{
		this.key = key;
	}
	
	public void setValue(V value)
	{
		this.value = value;
	}
	
	public K getKey()
	{
		return key;
	}
	
	public V getValue()
	{
		return value;
	}
	
	@Override
	public String toString()
	{
		return "KeyValue{" +
			"key=" + key +
			", value=" + value +
			'}';
	}
}