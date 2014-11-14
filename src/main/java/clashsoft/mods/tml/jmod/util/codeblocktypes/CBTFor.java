package clashsoft.mods.tml.jmod.util.codeblocktypes;

import clashsoft.mods.tml.jmod.util.CodeLine;
import clashsoft.mods.tml.jmod.util.TextModHelper;
import clashsoft.mods.tml.jmod.util.Variable;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;
import clashsoft.mods.tml.jmod.util.exceptions.SyntaxException;

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
		
		this.var = codeblock.readVariable(new CodeLine(line.lineNumber, parts[0]));
		this.end = (Boolean) codeblock.parser.directParse(line, parts[1]);
		this.step = (Integer) codeblock.parser.directParse(line, parts[2]);
	}
	
	@Override
	public Object execute(CodeBlock codeblock)
	{
		int var1 = (Integer) this.var.value;
		for (int i = var1; this.end; i += this.step)
		{
			codeblock.variables.put(this.var.name, this.var);
			codeblock.execute();
		}
		codeblock.variables.remove(this.var.name);
		return null;
	}
	
	@Override
	public boolean isBreakable()
	{
		return true;
	}
}
