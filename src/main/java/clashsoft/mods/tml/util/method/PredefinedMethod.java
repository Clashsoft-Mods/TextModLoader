package clashsoft.mods.tml.util.method;

import clashsoft.mods.tml.methods.TMLMethod;
import clashsoft.mods.tml.util.TextModHelper;
import clashsoft.mods.tml.util.codeblock.CodeBlock;

public class PredefinedMethod extends Method
{
	public PredefinedMethod(String name, Object[] parameters)
	{
		super(name, parameters);
	}
	
	private final TMLMethod getExecuter()
	{
		return TextModHelper.getMethodExecutorFromName(this.name);
	}
	
	@Override
	public Object execute(CodeBlock superCodeBlock, Object... parameters)
	{
		return this.getExecuter().call(parameters);
	}
	
	@Override
	public boolean isValid()
	{
		return getExecuter() != null;
	}
}
