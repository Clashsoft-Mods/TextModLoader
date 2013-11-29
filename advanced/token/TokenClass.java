package com.chaosdev.textmodloader.advanced.token;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.ClassReader;

public class TokenClass extends Token
{
	public TokenClass(String name)
	{
		super(Token.TYPE_CLASS, 0, name);
	}
	
	@Override
	public ClassData parse(ClassData data)
	{
		return ClassReader.getCurrent().getClass(data.getFullClassName(string));
	}
}
