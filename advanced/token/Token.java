package com.chaosdev.textmodloader.advanced.token;

import com.chaosdev.textmodloader.advanced.ClassData;

public class Token
{
	public static final long	TYPE_BRACKET		= 0b1L;
	public static final long	TYPE_PUNCTUATION	= 0b10L;
	
	public static final long	TYPE_KEYWORD		= 0b100L;
	public static final long	TYPE_MODIFIER		= 0b1000L;
	public static final long	TYPE_PRIMITVE		= 0b10000L;
	public static final long	TYPE_DEF			= 0b100000L;
	
	public static final long	TYPE_CLASS			= 0b1000000L;
	public static final long	TYPE_PACKAGE		= 0b10000000L;
	public static final long	TYPE_METHOD			= 0b100000000L;
	public static final long	TYPE_CONSTRUCTOR	= 0b1000000000L;
	public static final long	TYPE_ENUMDEF		= 0b10000000000L;
	public static final long	TYPE_VARIABLE		= 0b100000000000L;
	
	public static final long	TYPE_STRING			= 0b1000000000000L;
	public static final long	TYPE_CHAR			= 0b10000000000000L;
	
	public static final long	TYPE_INT			= 0b100000000000000L;
	public static final long	TYPE_FLOAT			= 0b1000000000000000L;
	public static final long	TYPE_DOUBLE			= 0b10000000000000000L;
	public static final long	TYPE_LONG			= 0b100000000000000000L;
	
	public static final long	TYPE_OPERATOR		= 0b1000000000000000000L;
	
	public final long			type;
	public final long			data;
	public String				string;
	
	public Token(long type, long data, String string)
	{
		this.type = type;
		this.data = data;
		this.string = string;
	}
	
	public Object parse(ClassData data)
	{
		return null;
	}
	
	public static Token get(String string)
	{
		if (TokenBracket.getTypeFromString(string) != -1)
			return new TokenBracket(string);
		else
			return null;
	}
}
