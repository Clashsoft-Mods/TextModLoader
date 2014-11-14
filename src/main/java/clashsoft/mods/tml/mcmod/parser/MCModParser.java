package clashsoft.mods.tml.mcmod.parser;

import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;
import clashsoft.mods.tml.mcmod.MCMod;
import clashsoft.mods.tml.mcmod.ast.ModSection;

public class MCModParser extends Parser
{
	private MCMod mod;
	
	private String name;
	
	public MCModParser(MCMod mod)
	{
		this.mod = mod;
	}
	
	@Override
	public boolean parse(ParserManager pm, String value, IToken token) throws SyntaxException
	{
		if ("{".equals(value))
		{
			this.mod.name = this.name;
			this.mod.theSection = new ModSection(this.mod);
			pm.pushParser(new ModSectionParser(this.mod.theSection));
			return true;
		}
		else
		{
			this.name = value;
			return true;
		}
	}
}
