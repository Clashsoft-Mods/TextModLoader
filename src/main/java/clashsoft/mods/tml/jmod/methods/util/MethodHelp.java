package clashsoft.mods.tml.jmod.methods.util;

import clashsoft.mods.tml.jmod.methods.TMLMethod;
import clashsoft.mods.tml.jmod.util.TextModHelper;

public class MethodHelp extends TMLMethod
{
	@Override
	public Object call(Object... parameters)
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
