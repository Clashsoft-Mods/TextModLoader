package com.chaosdev.textmodloader.advanced.token;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clashsoft.cslib.reflect.CSReflection;

import com.chaosdev.textmodloader.advanced.ClassData;
import com.chaosdev.textmodloader.advanced.token.keyword.TokenDefModifier;
import com.chaosdev.textmodloader.advanced.token.keyword.TokenKeyword;
import com.chaosdev.textmodloader.advanced.token.keyword.TokenModifier;
import com.chaosdev.textmodloader.advanced.token.keyword.TokenPrimitive;
import com.chaosdev.textmodloader.advanced.token.literal.*;

public class Token
{
	public static String			language			= "";
	
	public static Map<Long, Class>	classMap			= new HashMap();
	
	public static final long		TYPE_ANY			= 0x7fffffffffffffffL;
	
	public static final long		TYPE_BRACKET		= 0b1L;
	public static final long		TYPE_PUNCTUATION	= 0b10L;
	
	public static final long		TYPE_KEYWORD		= 0b100L;
	public static final long		TYPE_MODIFIER		= 0b1000L;
	public static final long		TYPE_PRIMITIVE		= 0b10000L;
	public static final long		TYPE_DEFMODIFIER	= 0b100000L;
	
	public static final long		TYPE_CLASS			= 0b1000000L;
	public static final long		TYPE_PACKAGE		= 0b10000000L;
	public static final long		TYPE_METHOD			= 0b100000000L;
	public static final long		TYPE_CONSTRUCTOR	= 0b1000000000L;
	public static final long		TYPE_ENUMDEF		= 0b10000000000L;
	public static final long		TYPE_FIELD			= 0b100000000000L;
	public static final long		TYPE_ANNOTATION		= 0b1000000000000L;
	
	public static final long		TYPE_STRING			= 0b10000000000000L;
	public static final long		TYPE_CHAR			= 0b100000000000000L;
	
	public static final long		TYPE_INT			= 0b1000000000000000L;
	public static final long		TYPE_FLOAT			= 0b10000000000000000L;
	public static final long		TYPE_DOUBLE			= 0b100000000000000000L;
	public static final long		TYPE_LONG			= 0b1000000000000000000L;
	
	public static final long		TYPE_OPERATOR		= 0b10000000000000000000L;
	
	static
	{
		classMap.put(TYPE_BRACKET, TokenBracket.class);
		classMap.put(TYPE_PUNCTUATION, TokenPunctuation.class);
		
		classMap.put(TYPE_KEYWORD, TokenKeyword.class);
		classMap.put(TYPE_MODIFIER, TokenModifier.class);
		classMap.put(TYPE_PRIMITIVE, TokenPrimitive.class);
		classMap.put(TYPE_DEFMODIFIER, TokenDefModifier.class);
		
		classMap.put(TYPE_CLASS, TokenClass.class);
		classMap.put(TYPE_PACKAGE, TokenPackage.class);
		classMap.put(TYPE_METHOD, TokenMethod.class);
		classMap.put(TYPE_CONSTRUCTOR, TokenConstructor.class);
		classMap.put(TYPE_ENUMDEF, TokenEnumDef.class);
		classMap.put(TYPE_FIELD, TokenField.class);
		classMap.put(TYPE_ANNOTATION, TokenAnnotation.class);
		
		classMap.put(TYPE_STRING, TokenLiteralString.class);
		classMap.put(TYPE_CHAR, TokenLiteralChar.class);
		
		classMap.put(TYPE_INT, TokenLiteralInt.class);
		classMap.put(TYPE_FLOAT, TokenLiteralFloat.class);
		classMap.put(TYPE_DOUBLE, TokenLiteralDouble.class);
		classMap.put(TYPE_LONG, TokenLiteralLong.class);
		
		classMap.put(TYPE_OPERATOR, TokenOperator.class);
	}
	
	public final long				type;
	public final long				data;
	public String					string;
	
	public Token(long type, long data, String string)
	{
		this.type = type;
		this.data = data;
		this.string = string.intern();
	}
	
	public Object parse(ClassData data)
	{
		return null;
	}
	
	public ExpectedToken expected(List<Token> metadata)
	{
		return ExpectedToken.ANY;
	}
	
	protected static boolean contains(long l1, long l2)
	{
		return (l1 & l2) != 0;
	}
	
	protected static <T> T prev(List<T> list)
	{
		return list.size() > 1 ? list.get(list.size() - 2) : null;
	}
	
	public static Token get(String string, List<Token> metadata)
	{
		Token prev = metadata.get(metadata.size() - 1);
		ExpectedToken expected = prev.expected(metadata);
		return construct(expected, string);
	}
	
	public static Token construct(ExpectedToken expected, String string)
	{
		Class expectedClass = expected.getExpectedClass();
		Token token = CSReflection.createInstance(expectedClass, string);
		
		if (!expected.dataMatches(token.data))
			return null;
		else if (!expected.typeMatches(token.type))
			return null;
		else
			return token;
	}
}