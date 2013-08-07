package com.chaosdev.textmodloader.util.method;

import com.chaosdev.textmodloader.util.codeblock.CodeBlock;

public class CustomMethod extends Method
{
	public CustomMethod(String name, Object[] parameters)
	{
		super(name, parameters);
	}

	@Override
	public Object execute(CodeBlock superCodeBlock, Object... parameters)
	{
		return null;
	}
	
}
