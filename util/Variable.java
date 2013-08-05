package com.chaosdev.textmodloader.util;

import com.chaosdev.textmodloader.util.types.Type;

public class Variable
{
	public Type		type;
	public String	name;
	public Object	value;
	
	public Variable(Type type, String name, Object value)
	{
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	@Override
	public String toString()
	{
		return "Variable[type=" + type + ", name=" + name + ", value=" + value + "]";
	}
}
