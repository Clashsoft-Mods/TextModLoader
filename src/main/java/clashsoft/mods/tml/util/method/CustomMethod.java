package clashsoft.mods.tml.util.method;

import clashsoft.mods.tml.util.Variable;
import clashsoft.mods.tml.util.codeblock.CodeBlock;

public class CustomMethod extends Method
{
	private Variable[]	variables;
	private CodeBlock	codeBlock;
	
	public CustomMethod(String name, Object[] parameters, Variable[] variables, CodeBlock codeblock)
	{
		super(name, parameters);
		this.variables = variables;
		this.codeBlock = codeblock;
	}
	
	@Override
	public Object execute(CodeBlock superCodeBlock, Object... parameters)
	{
		for (int i = 0; i < parameters.length; i++)
		{
			Variable var = this.variables[i];
			var.value = parameters[i];
			this.codeBlock.variables.put(var.name, var);
		}
		return codeBlock.execute();
	}
}
