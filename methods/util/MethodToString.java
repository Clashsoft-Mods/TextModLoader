package com.chaosdev.textmodloader.methods.util;

import com.chaosdev.textmodloader.methods.MethodExecuter;

public class MethodToString extends MethodExecuter
{
	@Override
	public Object execute(Object... parameters)
	{
		return parameters[0].toString();
	}
	
	@Override
	public String getName()
	{
		return "toString";
	}
	
	@Override
	public String getUsage()
	{
		return "toString(...)";
	}
	
}
