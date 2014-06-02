package clashsoft.mods.tml.mcmod;

import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;

public class MCModParser extends Parser
{
	private MCMod mod;
	
	public MCModParser(MCMod mod)
	{
		this.mod = mod;
	}
	
	@Override
	public void parse(ParserManager pm, String value, IToken token) throws SyntaxException
	{
	}
}
