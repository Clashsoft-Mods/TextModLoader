package com.chaosdev.textmodloader.util.codeblocktypes;

import com.chaosdev.textmodloader.TextMod;
import com.chaosdev.textmodloader.util.CodeBlock;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.types.Type;

public class CodeBlockTypeForeach extends CodeBlockType
{
	public Type type = Type.OBJECT;
	public String name = "";
	public Object field = null;
	
	public CodeBlockTypeForeach(String initializer)
	{
		super(initializer, new Class[] {});
	}
	
	@Override
	public Object execute(CodeBlock codeblock)
	{
		if (field.getClass().isArray())
		{
			for (Object o : (Object[])field)
			{
				codeblock.variables.put(name, new Variable(type, name, o));
				codeblock.execute();
			}
			codeblock.variables.remove(name);
		}
		else if (field instanceof Iterable)
		{
			for (Object o : (Iterable)field)
			{
				codeblock.variables.put(name, new Variable(type, name, o));
				codeblock.execute();
			}
			codeblock.variables.remove(name);
		}
		return null;
	}
	
	@Override
	public void setup(CodeBlock codeblock, String line)
	{
		String[] parts = TextModHelper.createParameterList(line, ' ');
		
		type = Type.getTypeFromName(parts[0]);
		name = parts[1];
		field = codeblock.parser.parse(parts[2]);
	}
	
	@Override
	public char getSplitChar()
	{
		return 0;
	}
	
	public boolean isBreakable()
	{
		return true;
	}
}