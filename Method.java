package com.chaosdev.textmodloader;

public class Method
{
	public String name;
	public Object[] parameters;

	public Method(String name, Object... parameters)
	{
		this.name = name;
		this.parameters = parameters;
	}
}
