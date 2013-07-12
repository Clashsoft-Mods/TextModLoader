package com.chaosdev.textmodloader.methods;

public interface MethodExecuter
{
	public void execute(Object... parameters);
	public String getName();
	public String getUsage();
}
