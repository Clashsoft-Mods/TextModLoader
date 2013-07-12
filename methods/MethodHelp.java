package com.chaosdev.textmodloader.methods;

import com.chaosdev.textmodloader.TextModHelper;

public class MethodHelp implements MethodExecuter
{
	@Override
	public void execute(Object... parameters)
	{
		String methodname = (String) parameters[0];
		System.out.println("Help called: Usage of \'" + methodname + "\': " + TextModHelper.getMethodExecuterFromName(methodname).getUsage());
	}

	@Override
	public String getName()
	{
		return "help";
	}

	@Override
	public String getUsage()
	{
		return "help(\"methodname\")";
	}

}
