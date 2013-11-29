package com.chaosdev.textmodloader.advanced;

import java.util.Map;

public abstract class ClassReader
{
	private static ClassReader current;
	
	public String source;
	public Map<String, Class> classes;
	
	public ClassReader(String source)
	{
		this.source = source;
	}
	
	public static ClassReader getCurrent()
	{
		return current;
	}
	
	public final void readAndRun()
	{
		current = this;
		this.read();
		this.run();
	}
	
	public abstract void read();
	public abstract void run();
	
	public Class getClass(String name)
	{
		return classes.get(name);
	}
}
