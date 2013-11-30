package com.chaosdev.textmodloader.advanced.token;

import java.util.List;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.ClassReader;
import com.chaosdev.textmodloader.advanced.token.keyword.TokenDefModifier;
import com.chaosdev.textmodloader.advanced.token.keyword.TokenKeyword;

public class TokenClass extends Token
{
	public TokenClass(String name)
	{
		super(Token.TYPE_CLASS, 0, name);
	}
	
	@Override
	public ExpectedToken expected(List<Token> metadata)
	{
		Token prev = prev(metadata);
		if (prev.type == Token.TYPE_DEFMODIFIER && prev.data == TokenDefModifier.CLASS) // class [CLASSNAME]
			return new ExpectedToken(Token.TYPE_DEFMODIFIER, TokenDefModifier.EXTENDS | TokenDefModifier.IMPLEMENTS);
		else if (prev.type == Token.TYPE_KEYWORD && prev.data == TokenKeyword.NEW) // new [CLASSNAME](...)
			return new ExpectedToken(Token.TYPE_BRACKET, TokenBracket.BRACE_OPEN);
		else if (prev.type == Token.TYPE_MODIFIER || prev.type == Token.TYPE_ANNOTATION) // public String doStuff | @Override String doStuff
			return new ExpectedToken(Token.TYPE_METHOD | Token.TYPE_FIELD, 0);
		else // Misplaced
			return null;
	}
	
	@Override
	public ClassData parse(ClassData data)
	{
		return ClassReader.getCurrent().getClass(data.getFullClassName(string));
	}
}
