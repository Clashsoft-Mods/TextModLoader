package com.chaosdev.textmodloader.util.method;

import com.chaosdev.textmodloader.methods.MethodExecutor;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;

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
