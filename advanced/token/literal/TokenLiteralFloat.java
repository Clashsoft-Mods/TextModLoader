package com.chaosdev.textmodloader.advanced.token.literal;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenLiteralFloat extends TokenLiteralNumber
{
	public TokenLiteralFloat(String string)
	{
		super(Token.TYPE_FLOAT, string);
	}

	@Override
	public Number parse(String string, int radix)
	{
		return Float.parseFloat(string);
	}

	@Override
	public boolean isValid(String string)
	{
		return string.endsWith("f") || string.endsWith("F");
	}
}
