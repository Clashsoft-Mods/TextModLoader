package com.chaosdev.textmodloader.advanced.members;

public class Method<T> extends Member<T>
{
	
	public Method(String name, Class type, T value)
	{
		super(name, type, value);
	}
	
	public Method(String name, Class type)
	{
		super(name, type);
	}
	
	public Method(String name, T value)
	{
		super(name, value);
	}
	
}
