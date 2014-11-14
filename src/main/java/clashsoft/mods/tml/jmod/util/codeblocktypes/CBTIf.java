package clashsoft.mods.tml.jmod.util.codeblocktypes;

import clashsoft.mods.tml.jmod.util.CodeLine;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;
import clashsoft.mods.tml.jmod.util.exceptions.SyntaxException;
import clashsoft.mods.tml.jmod.util.method.Method;

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
		System.out.println("  Processing IF code block, boolean value is " + String.valueOf(this.value).toUpperCase());
		if (this.value)
		{
			codeblock.execute();
		}
		
		return this.value;
	}
	
	@Override
	public void setup(CodeBlock codeblock, CodeLine line) throws SyntaxException
	{
		Method m = codeblock.readMethod(line);
		this.value = (Boolean) m.parameters[0];
	}
}
