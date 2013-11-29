package com.chaosdev.textmodloader.advanced.token.literal;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.token.Token;

import clashsoft.cslib.util.CSSource;

public class TokenLiteralString extends Token
{
	public TokenLiteralString(String string)
	{
		super(Token.TYPE_STRING, 0, string);
	}
	
	@Override
	public Object parse(ClassData data)
	{
		String quoted = CSSource.replaceLiterals(string);
		return quoted.substring(1, quoted.length() - 1);
	}
}
