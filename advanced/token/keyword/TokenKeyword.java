package com.chaosdev.textmodloader.advanced.token.keyword;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenKeyword extends Token
{
	public static final long	ASSERT		= 0b1L;
	public static final long	BREAK		= 0b10L;
	public static final long	CASE		= 0b100L;
	public static final long	CATCH		= 0b1000L;
	public static final long	CONTINUE	= 0b10000L;
	public static final long	DEFAULT		= 0b100000L;
	public static final long	DO			= 0b1000000L;
	public static final long	ELSE		= 0b10000000L;
	public static final long	FINALLY		= 0b100000000L;
	public static final long	FOR			= 0b1000000000L;
	public static final long	GOTO		= 0b10000000000L;
	public static final long	IF			= 0b100000000000L;
	public static final long	IMPORT		= 0b1000000000000L;
	public static final long	INSTANCEOF	= 0b10000000000000L;
	public static final long	NEW			= 0b100000000000000L;
	public static final long	RETURN		= 0b1000000000000000L;
	public static final long	SUPER		= 0b10000000000000000L;
	public static final long	SWITCH		= 0b100000000000000000L;
	public static final long	THIS		= 0b1000000000000000000L;
	public static final long	THROW		= 0b10000000000000000000L;
	public static final long	TRY			= 0b100000000000000000000L;
	public static final long	WHILE		= 0b1000000000000000000000L;
	
	public TokenKeyword(String string)
	{
		super(Token.TYPE_KEYWORD, getTypeFromString(string), string);
	}
	
	public static long getTypeFromString(String string)
	{
		switch (string)
		{
		case "assert":
			return ASSERT;
		case "break":
			return BREAK;
		case "case":
			return CASE;
		case "catch":
			return CATCH;
		case "continue":
			return CONTINUE;
		case "default":
			return DEFAULT;
		case "do":
			return DO;
		case "else":
			return ELSE;
		case "finally":
			return FINALLY;
		case "for":
			return FOR;
		case "goto":
			return GOTO;
		case "if":
			return IF;
		case "import":
			return IMPORT;
		case "instanceof":
			return INSTANCEOF;
		case "new":
			return NEW;
		case "return":
			return RETURN;
		case "super":
			return SUPER;
		case "switch":
			return SWITCH;
		case "this":
			return THIS;
		case "throw":
			return THROW;
		case "try":
			return TRY;
		case "while":
			return WHILE;
			
		default:
			return -1;
		}
	}
}
