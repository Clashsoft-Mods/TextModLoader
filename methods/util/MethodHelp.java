package com.chaosdev.textmodloader.methods.util;

import com.chaosdev.textmodloader.methods.MethodExecuter;
import com.chaosdev.textmodloader.util.TextModHelper;

public class MethodHelp extends MethodExecuter
{
	@Override
	public Object execute(Object... parameters)
	{
		String methodname = (String) parameters[0];
		String usage = TextModHelper.getMethodExecuterFromName(methodname).getUsage();
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
		return ">help(\"methodname\")";
	}
	
}