package com.chaosdev.textmodloader.advanced.token;

import com.chaosdev.textmodloader.advanced.ClassData;

public class TokenPackage extends Token
{
	public TokenPackage(String string)
	{
		super(Token.TYPE_PACKAGE, string.endsWith("*") ? 1 : 0, string);
	}
	
	@Override
	public String parse(ClassData data)
	{
		return string;
	}
}
