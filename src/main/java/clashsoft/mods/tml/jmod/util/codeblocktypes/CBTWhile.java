package clashsoft.mods.tml.jmod.util.codeblocktypes;

import clashsoft.mods.tml.jmod.util.CodeLine;
import clashsoft.mods.tml.jmod.util.codeblock.Break;
import clashsoft.mods.tml.jmod.util.codeblock.Breakable;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;
import clashsoft.mods.tml.jmod.util.exceptions.SyntaxException;
import clashsoft.mods.tml.jmod.util.method.Method;

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
		System.out.println("  Processing WHILE code block, boolean value is " + String.valueOf(this.value).toUpperCase());
		while (this.value)
		{
			try
			{
				((Breakable)codeblock).execute();
				this.updateValue();
			}
			catch (Break b)
			{
				break;
			}
		}
		
		return this.value;
	}
	
	@Override
	public void setup(CodeBlock codeblock, CodeLine line) throws SyntaxException
	{
		this.superCodeBlock = codeblock;
		this.header = line;
	}
	
	public void updateValue()
	{
		try
		{
		Method m = this.superCodeBlock.readMethod(this.header);
		this.value = (Boolean) m.parameters[0];
		}
		catch (SyntaxException ex)
		{
			System.err.println("  Syntax error while executing line " + (this.header.lineNumber + 1) + ": ");
			System.err.println(ex.getMessage());
		}
	}
	
	@Override
	public boolean isBreakable()
	{
		return true;
	}
}