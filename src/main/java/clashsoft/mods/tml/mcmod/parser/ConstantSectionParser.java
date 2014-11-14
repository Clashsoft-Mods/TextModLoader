package clashsoft.mods.tml.mcmod.parser;

import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;
import clashsoft.mods.tml.mcmod.ast.ConstantSection;

public class ConstantSectionParser extends Parser
{
	private ConstantSection section;
	
	public ConstantSectionParser(ConstantSection section)
	{
		this.section = section;
	}
	
	@Override
	public boolean parse(ParserManager pm, String value, IToken token) throws SyntaxException
	{
		if ("}".equals(value))
		{
			pm.popParser();
			return true;
		}
		else if ("=".equals(value))
		{
			String name = token.prev().value();
			return true;
		}
		return false;
	}
}
