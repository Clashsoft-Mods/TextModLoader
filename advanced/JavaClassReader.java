package com.chaosdev.textmodloader.advanced;

import java.util.ArrayList;
import java.util.List;

import clashsoft.cslib.util.CSSource;

import com.chaosdev.textmodloader.advanced.token.Token;

public class JavaClassReader extends ClassReader
{
	public List<Token> tokens;
	public List<String> primitives;
	
	public ClassData theClassData;
	
	public JavaClassReader(String source)
	{
		super(source);
		
		int length = this.source.length() / 10;
		this.tokens = new ArrayList<Token>(length);
		this.primitives = new ArrayList<String>(length);
	}

	@Override
	public void read()
	{
		Token.language = "Java";
		
		// We do not need comments at all
		this.source = CSSource.stripComments(this.source);
		
		for (int i = 0; i < primitives.size(); i++)
		{
			String primitive = primitives.get(i);
			
			Token token = Token.get(primitive, tokens);
			tokens.add(token);
		}
	}
	
	public void readPrimitives()
	{
		StringBuilder current = new StringBuilder(20);
		
		for (int i = 0; i < this.source.length(); i++)
		{
			 char c = this.source.charAt(i);
			 
			 if (Character.isWhitespace(c) && current.length() > 0)
			 {
				 primitives.add(current.toString());
				 current.delete(0, current.length());
			 }
			 else
				 current.append(c);
		}
	}

	@Override
	public void run()
	{
	}
}
