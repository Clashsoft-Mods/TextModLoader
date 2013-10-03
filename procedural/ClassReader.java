package com.chaosdev.textmodloader.procedural;

import java.util.ArrayList;
import java.util.List;

public class ClassReader
{
	private String file;
	
	public ClassReader(String file) 
	{
		this.file = file;
	}
	
	public void execute()
	{
		List<Token> tokens = tokenize(file);
	}

	private List<Token> tokenize(String text)
	{
		ArrayList list = new ArrayList(text.length() / 6);
		
		for (int i = 0; i < text.length(); i++)
		{
			char c = text.charAt(i);
			
			
		}
		
		return list;
	}
}
