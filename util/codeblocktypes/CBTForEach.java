package clashsoft.mods.tml.util.codeblocktypes;

import clashsoft.mods.tml.util.CodeLine;
import clashsoft.mods.tml.util.TextModHelper;
import clashsoft.mods.tml.util.Variable;
import clashsoft.mods.tml.util.codeblock.CodeBlock;
import clashsoft.mods.tml.util.exceptions.SyntaxException;
import clashsoft.mods.tml.util.types.Type;

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
		if (field.getClass().isArray())
		{
			for (Object o : (Object[]) field)
			{
				codeblock.variables.put(name, new Variable(type, name, o));
				codeblock.execute();
			}
			codeblock.variables.remove(name);
		}
		else if (field instanceof Iterable)
		{
			for (Object o : (Iterable) field)
			{
				codeblock.variables.put(name, new Variable(type, name, o));
				codeblock.execute();
			}
			codeblock.variables.remove(name);
		}
		return null;
	}
	
	@Override
	public void setup(CodeBlock codeblock, CodeLine line) throws SyntaxException
	{
		String[] parts = TextModHelper.createParameterList(line.line, ' ');
		
		type = Type.getTypeFromName(parts[0]);
		name = parts[1];
		field = codeblock.parser.directParse(line, parts[2]);
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