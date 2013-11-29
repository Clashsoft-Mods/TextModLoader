package com.chaosdev.textmodloader.advanced.token.literal;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenLiteralInt extends TokenLiteralNumber
{
	public TokenLiteralInt(String string)
	{
		super(Token.TYPE_INT, string);
	}

	@Override
	public Number parse(String string, int radix)
	{
		return Integer.parseInt(string, radix);
	}

	@Override
	public boolean isValid(String string)
	{
		return true;
	}
}
