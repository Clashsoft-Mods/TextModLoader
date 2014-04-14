package clashsoft.mods.tml.util.method;

import clashsoft.mods.tml.methods.MethodExecutor;
import clashsoft.mods.tml.util.TextModHelper;
import clashsoft.mods.tml.util.codeblock.CodeBlock;

public class PredefinedMethod extends Method
{
	public PredefinedMethod(String name, Object[] parameters)
	{
		super(name, parameters);
	}
	
	public MethodExecutor getExecuter()
	{
		return TextModHelper.getMethodExecutorFromName(name);
	}
	
	@Override
	public Object execute(CodeBlock superCodeBlock, Object... parameters)
	{
		return getExecuter().execute(parameters);
	}
	
	@Override
	public boolean isValid()
	{
		return getExecuter() != null;
	}
}
