package com.chaosdev.textmodloader.advanced.token.literal;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.token.Token;

public abstract class TokenLiteralNumber extends Token
{
	public TokenLiteralNumber(long type, String string)
	{
		super(type, 0, string);
	}
	
	@Override
	public Number parse(ClassData data)
	{
		if (!isValid(string))
			return Integer.valueOf(0);
		
		int radix = 10;
		int mult = 1;
		
		if (string.startsWith("+"))
		{
			string = string.substring(1);
			mult = -1;
		}
		else if (string.startsWith("-"))
		{
			string = string.substring(1);
			mult = -1;
		}
		
		if (string.startsWith("0b"))
		{
			string = string.substring(2);
			radix = 2;
		}
		else if (string.startsWith("0x"))
		{
			string = string.substring(2);
			radix = 16;
		}
		else if (string.startsWith("0"))
		{
			string = string.substring(1);
			radix = 8;
		}
		
		return parse(string, radix);
	}
	
	public abstract Number parse(String string, int radix);
	public abstract boolean isValid(String string);
}
