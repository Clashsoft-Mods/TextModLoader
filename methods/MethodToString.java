package com.chaosdev.textmodloader.methods;

public class MethodToString extends MethodExecuter
{
	@Override
	public Object execute(Object... parameters)
	{
		return parameters[0];
	}

	@Override
	public String getName()
	{
		return "tostring";
	}

	@Override
	public String getUsage()
	{
		return ">toString(...)";
	}
	
}
