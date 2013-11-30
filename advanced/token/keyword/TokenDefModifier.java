package com.chaosdev.textmodloader.advanced.token.keyword;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenDefModifier extends Token
{
	public static final long	CLASS		= 0b1L;
	public static final long	ENUM		= 0b10L;
	public static final long	EXTENDS		= 0b100L;
	public static final long	IMPLEMENTS	= 0b1000L;
	public static final long	INTERFACE	= 0b10000L;
	public static final long	PACKAGE		= 0b100000L;
	public static final long	THROWS		= 0b1000000L;
	
	public TokenDefModifier(String string)
	{
		super(Token.TYPE_DEFMODIFIER, getTypeFromString(string), string);
	}
	
	public static long getTypeFromString(String string)
	{
		switch (string)
		{
		case "class":
			return CLASS;
		case "enum":
			return ENUM;
		case "extends":
			return EXTENDS;
		case "implements":
			return IMPLEMENTS;
		case "interface":
			return INTERFACE;
		case "package":
			return PACKAGE;
		case "throws":
			return THROWS;
		default:
			return -1;
		}
	}
}
