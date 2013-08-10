package com.chaosdev.textmodloader.util.codeblock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.chaosdev.textmodloader.util.method.Method;

public class ClassCodeBlock extends CodeBlock
{
	public String				author			= "";
	
	public Map<String, Method>	customMethods	= new HashMap<String, Method>();
	
	public ClassCodeBlock()
	{
		this(new LinkedList<String>());
	}
	
	public ClassCodeBlock(List<String> lines)
	{
		this(null, lines);
	}
	
	public ClassCodeBlock(CodeBlock superBlock, List<String> lines)
	{
		super(superBlock, lines);
	}
	
	@Override
	public ClassCodeBlock getCodeBlockClass()
	{
		return this;
	}
	
	@Override
	public Method getCustomMethod(String name)
	{
		return customMethods.get(name);
	}
	
	public void registerMethod(Method method)
	{
		this.customMethods.put(method.name, method);
		this.parser.update(this);
		System.out.println("  Method \'" + method.name + "\' added.");
	}
}
