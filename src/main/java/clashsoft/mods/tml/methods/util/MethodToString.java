package clashsoft.mods.tml.methods.util;

import clashsoft.mods.tml.methods.MethodExecutor;

public class MethodToString extends MethodExecutor
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
