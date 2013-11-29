package com.chaosdev.textmodloader.advanced.token;

public class TokenBracket extends Token
{
	public static long	BRACE_OPEN				= 0b1L;
	public static long	BRACE_CLOSE				= 0b10L;
	public static long	SQUARE_BRACKET_OPEN		= 0b100L;
	public static long	SQUARE_BRACKET_CLOSE	= 0b1000L;
	public static long	CURLY_BRACKET_OPEN		= 0b10000L;
	public static long	CURLY_BRACKET_CLOSE		= 0b100000L;
	public static long	ANGLE_BRACKET_OPEN		= 0b1000000L;
	public static long	ANGLE_BRACKET_CLOSE		= 0b10000000L;
	
	public TokenBracket(String bracket)
	{
		super(Token.TYPE_BRACKET, getTypeFromString(bracket), bracket);
	}
	
	public static long getTypeFromString(String string)
	{
		switch (string.intern())
		{
		case "(":
			return BRACE_OPEN;
		case ")":
			return BRACE_CLOSE;
		case "[":
			return SQUARE_BRACKET_OPEN;
		case "]":
			return SQUARE_BRACKET_CLOSE;
		case "{":
			return CURLY_BRACKET_OPEN;
		case "}":
			return CURLY_BRACKET_CLOSE;
		case "<":
			return ANGLE_BRACKET_OPEN;
		case ">":
			return ANGLE_BRACKET_CLOSE;
		default:
			return -1;
		}
	}
}
