package clashsoft.mods.tml.mcmod;

import clashsoft.cslib.src.SyntaxException;
import clashsoft.cslib.src.parser.IToken;
import clashsoft.cslib.src.parser.Parser;
import clashsoft.cslib.src.parser.ParserManager;

public class JModParserManager extends ParserManager
{
	private boolean comment;
	private boolean blockComment;
	
	public JModParserManager(Parser parser)
	{
		super(parser);
	}

	@Override
	public void parse(String value, IToken token) throws SyntaxException
	{
		if ("#".equals(value) || "//".equals(value))
		{
			this.comment = true;
			return;
		}
		else if ("/*".equals(value))
		{
			this.blockComment = true;
			return;
		}
		else if ("*/".equals(value))
		{
			this.blockComment = false;
			return;
		}
		else if (value.startsWith("\n"))
		{
			this.comment = false;
			return;
		}
		
		if (!this.comment && !this.blockComment)
		{
			super.parse(value, token);
		}
	}
}
