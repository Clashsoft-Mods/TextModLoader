package com.chaosdev.textmodloader.advanced;


public abstract class ClassReader
{
	private static ClassReader current;
	
	public String source;
	
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
	
	public ClassData getClass(String name)
	{
		return ClassData.classes.get(name);
	}
}
