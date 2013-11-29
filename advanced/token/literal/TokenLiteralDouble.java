package com.chaosdev.textmodloader.advanced.token.literal;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenLiteralDouble extends TokenLiteralNumber
{
	public TokenLiteralDouble(String string)
	{
		super(Token.TYPE_DOUBLE, string);
	}

	@Override
	public Number parse(String string, int radix)
	{
		return Double.parseDouble(string);
	}

	@Override
	public boolean isValid(String string)
	{
		return string.endsWith("d") || string.endsWith("D");
	}
}
