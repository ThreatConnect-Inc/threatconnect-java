package com.threatconnect.sdk.playbooks.content.entity;

/**
 * A generic class for holding a key value pair
 *
 * @param <K>
 * @param <V>
 * @author Greg Marut
 */
public class KeyValue<K, V>
{

	private K key;
	private V value;
	private String displayValue;

	public KeyValue()
	{
	}

	public KeyValue(K key, V value)
	{
		this.key = key;
		this.value = value;
	}

	public KeyValue(K key, V value, String displayValue)
	{
		this.key = key;
		this.value = value;
		this.displayValue = displayValue;
	}

	public void setKey(K key)
	{
		this.key = key;
	}

	public void setValue(V value)
	{
		this.value = value;
	}

	public void setDisplayValue(String displayValue)
	{
		this.displayValue = displayValue;
	}

	public String getDisplayValue()
	{
		return (displayValue == null || displayValue.isEmpty()) && value != null ? value.toString() : displayValue;
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
			", displayValue=" + displayValue +
			'}';
	}
}