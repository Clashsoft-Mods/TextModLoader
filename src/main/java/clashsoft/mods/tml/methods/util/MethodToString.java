package clashsoft.mods.tml.methods.util;

import clashsoft.mods.tml.methods.TMLMethod;

public class MethodToString extends TMLMethod
{
	@Override
	public Object call(Object... args)
	{
		return args[0].toString();
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
