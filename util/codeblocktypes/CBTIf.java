package com.chaosdev.textmodloader.util.codeblocktypes;

import com.chaosdev.textmodloader.util.CodeLine;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;
import com.chaosdev.textmodloader.util.exceptions.SyntaxException;
import com.chaosdev.textmodloader.util.method.Method;

public class CBTIf extends CodeBlockType
{
	private boolean	value	= false;
	
	public CBTIf(String initializer)
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
	public void setup(CodeBlock codeblock, CodeLine line) throws SyntaxException
	{
		Method m = codeblock.readMethod(line);
		value = (Boolean) m.parameters[0];
	}
}
