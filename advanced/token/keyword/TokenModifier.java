package com.chaosdev.textmodloader.advanced.token.keyword;

import com.chaosdev.textmodloader.advanced.token.Token;

public class TokenModifier extends Token
{
	public static final long	ABSTRACT	= 0b1L;
	public static final long	CONST		= 0b10L;
	public static final long	DEFAULT		= 0b100L;
	public static final long	FINAL		= 0b1000L;
	public static final long	NATIVE		= 0b10000L;
	public static final long	PRIVATE		= 0b100000L;
	public static final long	PROTECTED	= 0b1000000L;
	public static final long	PUBLIC		= 0b10000000L;
	public static final long	STATIC		= 0b100000000L;
	public static final long	STRICTFP	= 0b1000000000L;
	public static final long	SYNCHRNIZED	= 0b10000000000L;
	public static final long	TRANSIENT	= 0b100000000000L;
	public static final long	VOLATILE	= 0b1000000000000L;
	
	public TokenModifier(String string)
	{
		super(Token.TYPE_MODIFIER, getTypeFromString(string), string);
	}
	
	public static long getTypeFromString(String string)
	{
		switch (string)
		{
		case "abstract":
			return ABSTRACT;
		case "const":
			return CONST;
		case "default":
			return DEFAULT;
		case "final":
			return FINAL;
		case "native":
			return NATIVE;
		case "private":
			return PRIVATE;
		case "protected":
			return PROTECTED;
		case "public":
			return PUBLIC;
		case "static":
			return STATIC;
		case "strictfp":
			return STRICTFP;
		case "synchronized":
			return SYNCHRNIZED;
		case "transient":
			return TRANSIENT;
		case "volatile":
			return VOLATILE;
		default:
			return -1;
		}
	}
}
