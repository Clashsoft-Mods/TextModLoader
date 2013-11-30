package com.chaosdev.textmodloader.advanced.token;

public class ExpectedToken
{
	public static ExpectedToken ANY = new ExpectedToken(Token.TYPE_ANY, Token.TYPE_ANY);
	
	public final long expectedType;
	public final long expectedData;
	
	public ExpectedToken(long type, long data)
	{
		this.expectedType = type;
		this.expectedData = data;
	}
	
	public boolean typeMatches(long type)
	{
		return type == expectedType || (expectedType & type) != 0;
	}
	
	public boolean dataMatches(long data)
	{
		return data == expectedData || (expectedData & data) != 0;
	}
	
	public Class getExpectedClass()
	{
		return Token.classMap.get(expectedType);
	}
}
