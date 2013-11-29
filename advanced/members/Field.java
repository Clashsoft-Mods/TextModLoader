package com.chaosdev.textmodloader.advanced.members;

public class Field<T> extends Member<T>
{
	
	public Field(String name, Class type, T value)
	{
		super(name, type, value);
	}
	
	public Field(String name, Class type)
	{
		super(name, type);
	}
	
	public Field(String name, T value)
	{
		super(name, value);
	}
	
}
