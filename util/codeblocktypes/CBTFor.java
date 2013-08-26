package com.chaosdev.textmodloader.util.codeblocktypes;

import com.chaosdev.textmodloader.util.CodeLine;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;
import com.chaosdev.textmodloader.util.exceptions.SyntaxException;

public class CBTFor extends CodeBlockType
{
	private Variable	var;
	private boolean		end;
	private int			step;
	
	public CBTFor(String initializer)
	{
		super(initializer, new Class[] {});
	}
	
	@Override
	public void setup(CodeBlock codeblock, CodeLine line) throws SyntaxException
	{
		String[] parts = TextModHelper.createParameterList(line.line, ';');
		
		var = codeblock.readVariable(new CodeLine(line.lineNumber, parts[0]));
		end = (Boolean) codeblock.parser.directParse(line, parts[1]);
		step = (Integer) codeblock.parser.directParse(line, parts[2]);
	}
	
	@Override
	public Object execute(CodeBlock codeblock)
	{
		int var1 = (Integer) var.value;
		for (int i = var1; end; i += step)
		{
			codeblock.variables.put(var.name, var);
			codeblock.execute();
		}
		codeblock.variables.remove(var.name);
		return null;
	}
	
	@Override
	public boolean isBreakable()
	{
		return true;
	}
}
