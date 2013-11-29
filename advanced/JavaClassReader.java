package com.chaosdev.textmodloader.advanced;

import java.util.ArrayList;

import com.chaosdev.textmodloader.advanced.token.Token;

import clashsoft.cslib.util.CSSource;

public class JavaClassReader extends ClassReader
{
	public ArrayList<Token> tokens;
	
	public ClassData theClassData;
	
	public JavaClassReader(String source)
	{
		super(source);
		tokens = new ArrayList<Token>(source.length() / 10);
	}

	@Override
	public void read()
	{
		// We do not need comments at all
		this.source = CSSource.stripComments(source);
		
		
	}

	@Override
	public void run()
	{
	}
}
