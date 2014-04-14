package clashsoft.mods.tml.util.codeblocktypes;

import clashsoft.mods.tml.util.CodeLine;
import clashsoft.mods.tml.util.codeblock.CodeBlock;
import clashsoft.mods.tml.util.exceptions.SyntaxException;
import clashsoft.mods.tml.util.method.Method;

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
