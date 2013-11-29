package com.chaosdev.textmodloader.advanced.members;


public class Member<T>
{
	public String name;
	public Class type;
	public T value;
	
	public long modifiers;
	
	public Member(String name, Class type)
	{
		this(name, type, null);
	}
	
	public Member(String name, T value)
	{
		this(name, getClass(value), value);
	}
	
	public Member(String name, Class type, T value)
	{
		this.name = name;
		this.type = type;
		this.value = value;
	}
	
	public void setModifier(long modifier, boolean flag)
	{
		if (flag)
			modifiers |= modifier;
		else
			modifiers = (modifiers | modifier) ^ modifier;
	}
	
	public static Class getClass(Object object)
	{
		return object != null ? object.getClass() : Object.class;
	}
}
