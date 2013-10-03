package com.chaosdev.textmodloader.procedural;

public class Token
{
	public EnumToken type;
	
	public String value;
	
	public Token(EnumToken type, String value)
	{
		this.type = type;
		this.value = value;
	}
}
