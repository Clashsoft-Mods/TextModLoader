package clashsoft.mods.tml.mcmod.parser;

import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;
import clashsoft.mods.tml.mcmod.ast.ConstantSection;
import clashsoft.mods.tml.mcmod.ast.ModSection;

public class ModSectionParser extends Parser
{
	private ModSection section;
	
	public ModSectionParser(ModSection section)
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
		else if ("const".equals(value))
		{
			ConstantSection section = new ConstantSection(this.section);
			this.section.setConstantSection(section);
			pm.pushParser(new ConstantSectionParser(section));
			return true;
		}
		return false;
	}	
}
