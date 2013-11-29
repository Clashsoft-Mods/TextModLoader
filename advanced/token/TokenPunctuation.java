package com.chaosdev.textmodloader.advanced.token;

public class TokenPunctuation extends Token
{
	public static final long	WHITESPACE	= 0b1L;
	public static final long	TAB			= 0b10L;
	public static final long	NEWLINE		= 0b100L;
	public static final long	DOT			= 0b1000L;
	public static final long	COMMA		= 0b10000L;
	public static final long	SEMICOLON	= 0b100000L;
	public static final long	COLON		= 0b1000000L;
	
	public TokenPunctuation(String string)
	{
		super(Token.TYPE_PUNCTUATION, getTypeFromString(string), string);
	}
	
	public static long getTypeFromString(String string)
	{
		switch (string)
		{
		case " ":
			return WHITESPACE;
		case "\t":
			return TAB;
		case "\n":
			return NEWLINE;
		case ".":
			return DOT;
		case ",":
			return COMMA;
		case ";":
			return SEMICOLON;
		case ":":
			return COLON;
		default:
			return -1;
		}
	}
}
