package com.chaosdev.textmodloader.util.codeblocktypes;

import com.chaosdev.textmodloader.util.CodeBlock;
import com.chaosdev.textmodloader.util.Method;

public class CodeBlockTypeIf extends CodeBlockType
{
	private boolean value = false;
	
	public CodeBlockTypeIf(String initializer)
	{
		super(initializer, Boolean.class);
	}

	@Override
	public Object execute(CodeBlock codeblock)
	{
		System.out.println("  Processing IF code block, boolean value is " + String.valueOf(value).toUpperCase());
		if (value)
			codeblock.execute();
		
		return value;
	}

	@Override
	public void setup(CodeBlock codeblock, String line)
	{
		Method m = codeblock.getMethod(line);
		value = (Boolean) m.parameters[0];
	}
	
}
