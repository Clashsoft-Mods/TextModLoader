package com.chaosdev.textmodloader.util.method;

import com.chaosdev.textmodloader.methods.MethodExecuter;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;

public class PredefinedMethod extends Method
{
	public PredefinedMethod(String name, Object[] parameters)
	{
		super(name, parameters);
	}
	
	public MethodExecuter getExecuter()
	{
		return TextModHelper.getMethodExecuterFromName(name);
	}
	
	@Override
	public Object execute(CodeBlock superCodeBlock, Object... parameters)
	{
		return getExecuter().execute(parameters);
	}
	
	public boolean isValid()
	{
		return getExecuter() != null;
	}
}
