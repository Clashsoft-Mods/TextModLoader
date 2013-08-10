package com.chaosdev.textmodloader.util.method;

import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;

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
			Variable var = new Variable(variables[i].type, variables[i].name, parameters[i]);
			codeBlock.variables.put(var.name, var);
		}
		return codeBlock.execute();
	}
}
