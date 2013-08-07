package com.chaosdev.textmodloader.util.codeblocktypes;

import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;

public class CBTFor extends CodeBlockType
{
	private Variable var;
	private boolean end;
	private int step;
	
	public CBTFor(String initializer)
	{
		super(initializer, new Class[] {});
	}

	@Override
	public void setup(CodeBlock codeblock, String line)
	{
		String[] parts = TextModHelper.createParameterList(line, ';');
		
		var = codeblock.getVariable(parts[0]);
		end = (Boolean) codeblock.parser.parse(parts[1]);
		step = (Integer) codeblock.parser.parse(parts[2]);
	}
	
	@Override
	public Object execute(CodeBlock codeblock)
	{
		int var1 = (Integer) var.value;
		for (int i = var1; end; i += step) //TODO
		{
			codeblock.variables.put(var.name, var);
			codeblock.execute();
		}
		codeblock.variables.remove(var.name);
		return null;
	}
	
	public boolean isBreakable()
	{
		return true;
	}
}
