package clashsoft.mods.tml.methods.util;

import clashsoft.mods.tml.methods.MethodExecutor;
import clashsoft.mods.tml.util.TextModHelper;

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
