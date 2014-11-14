package clashsoft.mods.tml.jmod.util.codeblocktypes;

import clashsoft.mods.tml.jmod.util.CodeLine;
import clashsoft.mods.tml.jmod.util.TextModHelper;
import clashsoft.mods.tml.jmod.util.Variable;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;
import clashsoft.mods.tml.jmod.util.exceptions.SyntaxException;
import clashsoft.mods.tml.jmod.util.types.Type;

public class CBTForEach extends CodeBlockType
{
	public Type		type	= Type.OBJECT;
	public String	name	= "";
	public Object	field	= null;
	
	public CBTForEach(String initializer)
	{
		super(initializer, new Class[] {});
	}
	
	@Override
	public Object execute(CodeBlock codeblock)
	{
		if (this.field.getClass().isArray())
		{
			for (Object o : (Object[]) this.field)
			{
				codeblock.variables.put(this.name, new Variable(this.type, this.name, o));
				codeblock.execute();
			}
			codeblock.variables.remove(this.name);
		}
		else if (this.field instanceof Iterable)
		{
			for (Object o : (Iterable) this.field)
			{
				codeblock.variables.put(this.name, new Variable(this.type, this.name, o));
				codeblock.execute();
			}
			codeblock.variables.remove(this.name);
		}
		return null;
	}
	
	@Override
	public void setup(CodeBlock codeblock, CodeLine line) throws SyntaxException
	{
		String[] parts = TextModHelper.createParameterList(line.line, ' ');
		
		this.type = Type.getTypeFromName(parts[0]);
		this.name = parts[1];
		this.field = codeblock.parser.directParse(line, parts[2]);
	}
	
	@Override
	public char getSplitChar()
	{
		return 0;
	}
	
	@Override
	public boolean isBreakable()
	{
		return true;
	}
}