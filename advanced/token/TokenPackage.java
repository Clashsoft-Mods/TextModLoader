package com.chaosdev.textmodloader.advanced.token;

public class TokenPackage extends Token
{
	public TokenPackage(String string)
	{
		super(Token.TYPE_PACKAGE, string.endsWith("*") ? 1 : 0, string);
	}
}
