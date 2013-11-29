package com.chaosdev.textmodloader.advanced.token.literal;

import clashsoft.cslib.util.CSSource;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenLiteralString extends Token
{
	public TokenLiteralString(String string)
	{
		super(Token.TYPE_STRING, 0, string);
	}
	
	@Override
	public Object parse(ClassData data)
	{
		return CSSource.replaceLiterals(string.substring(1, string.length() - 1));
	}
	
	
}
