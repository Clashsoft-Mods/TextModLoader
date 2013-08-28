package com.chaosdev.textmodloader.methods.util;

import com.chaosdev.textmodloader.methods.MethodExecutor;
import com.chaosdev.textmodloader.util.TextModHelper;

public class MethodHelp extends MethodExecutor
{
	@Override
	public Object execute(Object... parameters)
	{
		String methodname = (String) parameters[0];
		String usage = TextModHelper.getMethodExecutorFromName(methodname).getUsage();
		System.out.println("Help called: Usage of \'" + methodname + "\': " + usage);
		return usage;
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
