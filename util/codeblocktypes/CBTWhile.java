package com.chaosdev.textmodloader.util.codeblocktypes;

import com.chaosdev.textmodloader.util.CodeLine;
import com.chaosdev.textmodloader.util.codeblock.Break;
import com.chaosdev.textmodloader.util.codeblock.Breakable;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;
import com.chaosdev.textmodloader.util.exceptions.SyntaxException;
import com.chaosdev.textmodloader.util.method.Method;

public class CBTWhile extends CodeBlockType
{
	private CodeBlock superCodeBlock = null;
	private CodeLine header = null;
	private boolean	value	= false;
	
	public CBTWhile(String initializer)
	{
		super(initializer, Boolean.class);
	}
	
	@Override
	public Object execute(CodeBlock codeblock)
	{
		System.out.println("  Processing WHILE code block, boolean value is " + String.valueOf(value).toUpperCase());
		while (value)
		{
			try
			{
				((Breakable)codeblock).execute();
				updateValue();
			}
			catch (Break b)
			{
				break;
			}
		}
		
		return value;
	}
	
	@Override
	public void setup(CodeBlock codeblock, CodeLine line) throws SyntaxException
	{
		superCodeBlock = codeblock;
		header = line;
	}
	
	public void updateValue()
	{
		try
		{
		Method m = superCodeBlock.readMethod(header);
		value = (Boolean) m.parameters[0];
		}
		catch (SyntaxException ex)
		{
			System.err.println("  Syntax error while executing line " + (header.lineNumber + 1) + ": ");
			System.err.println(ex.getMessage());
		}
	}
	
	@Override
	public boolean isBreakable()
	{
		return true;
	}
}