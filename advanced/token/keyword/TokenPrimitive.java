package com.chaosdev.textmodloader.advanced.token.keyword;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenPrimitive extends Token
{
	public static final long	BOOLEAN	= 0b1L;
	public static final long	BYTE	= 0b10L;
	public static final long	CHAR	= 0b100L;
	public static final long	DOUBLE	= 0b1000L;
	public static final long	FLOAT	= 0b10000L;
	public static final long	INT		= 0b100000L;
	public static final long	LONG	= 0b1000000L;
	public static final long	SHORT	= 0b10000000L;
	public static final long	VOID	= 0b100000000L;
	
	public TokenPrimitive(String string)
	{
		super(Token.TYPE_PRIMITIVE, getTypeFromString(string), string);
	}
	
	public static long getTypeFromString(String string)
	{
		switch (string)
		{
		case "boolean":
			return BOOLEAN;
		case "byte":
			return BYTE;
		case "char":
			return CHAR;
		case "double":
			return DOUBLE;
		case "float":
			return FLOAT;
		case "int":
			return INT;
		case "long":
			return LONG;
		case "short":
			return SHORT;
		case "void":
			return VOID;
		default:
			return -1;
		}
	}
}
