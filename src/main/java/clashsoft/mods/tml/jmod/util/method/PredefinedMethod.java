package clashsoft.mods.tml.jmod.util.method;

import clashsoft.mods.tml.jmod.methods.TMLMethod;
import clashsoft.mods.tml.jmod.util.TextModHelper;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;

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
		return this.getExecuter() != null;
	}
}
