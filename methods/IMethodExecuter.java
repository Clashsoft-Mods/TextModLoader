package com.chaosdev.textmodloader.methods;

public interface IMethodExecuter
{
	public Object execute(Object... parameters);
	public String getName();
	public String getUsage();
}
