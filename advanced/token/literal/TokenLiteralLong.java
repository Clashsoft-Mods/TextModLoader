package com.chaosdev.textmodloader.advanced.token.literal;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenLiteralLong extends TokenLiteralNumber
{
	public TokenLiteralLong(String string)
	{
		super(Token.TYPE_LONG, string);
	}

	@Override
	public Number parse(String string, int radix)
	{
		return Long.parseLong(string, radix);
	}

	@Override
	public boolean isValid(String string)
	{
		return string.endsWith("l") || string.endsWith("L");
	}
}
