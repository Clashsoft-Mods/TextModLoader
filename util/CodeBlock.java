package com.chaosdev.textmodloader.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class CodeBlock
{
	public List<CodeBlock> codeBlocks;
	public List<String> lines;
	public Map<String, Variable> variables = new HashMap<String, Variable>();
	
	public CodeBlock()
	{
		lines = new LinkedList<String>();
		codeBlocks = new LinkedList<CodeBlock>();
	}
	
	public CodeBlock(List<String> lines)
	{
		this.lines = lines;
	}
}
