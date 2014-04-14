package clashsoft.mods.tml.methods.util;

import clashsoft.mods.tml.methods.MethodExecutor;

public class MethodGetID extends MethodExecutor
{
	@Override
	public Object execute(Object... parameters)
	{
		return -1;
	}

	@Override
	public String getName()
	{
		return "getID|getId";
	}

	@Override
	public String getUsage()
	{
		return "getID(\"[block]\") OR getID(\"[item]\")";
	}

}
