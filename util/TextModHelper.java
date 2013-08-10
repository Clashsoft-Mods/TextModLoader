package com.chaosdev.textmodloader.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.chaosdev.textmodloader.methods.MethodExecuter;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;

public class TextModHelper implements TextModConstants
{
	public static Map<String, MethodExecuter>	methods	= new HashMap<String, MethodExecuter>();
	
	public static void registerMethodExecuter(MethodExecuter executer)
	{
		if (executer == null)
			throw new IllegalArgumentException("Method Executer cant be null!");
		if (executer.getName().contains("|"))
		{
			String[] names = executer.getName().split("|");
			for (String name : names)
			{
				registerMethodExecuter(name, executer);
			}
		}
		else
		{
			registerMethodExecuter(executer.getName(), executer);
		}
	}
	
	private static void registerMethodExecuter(String name, MethodExecuter executer)
	{
		if (!name.startsWith(TML_CLASS_NAME + "."))
			name = TML_CLASS_NAME + ".";
		methods.put(name, executer);
	}
	
	public static MethodExecuter getMethodExecuterFromName(String name)
	{
		String changedName = name.replaceFirst(">", "");
		return methods.get(changedName);
	}
	
	public static boolean isLineValid(String line)
	{
		return line != null && !line.equals("") && !line.equals("\n") && !(new CodeBlock(null)).isComment(line);
	}
	
	public static String[] createParameterList(String par1, char splitChar)
	{
		List<String> strings = new LinkedList<String>();
		
		String curString = "";
		char block = ' ';
		int length = par1.toCharArray().length;
		
		for (int i = 0; i < length; i++)
		{
			char c = par1.charAt(i);
			if (block == ' ')
			{
				if (isBlockStartChar(c))
					block = c;
				else if (isValidBlock(block, c))
					block = ' ';
			}
			else
			{
				if (isValidBlock(block, c))
					block = ' ';
			}
			
			if (block == ' ' && (c == splitChar || i == par1.length() - 1))
			{
				if (i == par1.length() - 1)
					curString += c;
				curString = curString.trim();
				strings.add(curString);
				curString = "";
			}
			else
				curString += c;
		}
		String[] ret = new String[strings.size()];
		for (int i = 0; i < strings.size(); i++)
		{
			ret[i] = strings.get(i).trim();
		}
		return ret;
	}
	
	public static boolean isBlockStartChar(char c)
	{
		return c == CHAR_START_CHAR.charAt(0) || c == STRING_START_CHAR.charAt(0) || c == METHOD_INVOCATION_START_CHAR.charAt(0) || c == ARRAY_START_CHAR.charAt(0) || c == NEW_INSTANCE_START_CHAR.charAt(0);
	}
	
	public static boolean isValidBlock(char s, char e)
	{
		return (s == CHAR_START_CHAR.charAt(0) && e == CHAR_END_CHAR.charAt(0)) || (s == STRING_START_CHAR.charAt(0) && e == STRING_END_CHAR.charAt(0)) || (s == METHOD_INVOCATION_START_CHAR.charAt(0) && e == METHOD_INVOCATION_END_CHAR.charAt(0)) || (s == ARRAY_START_CHAR.charAt(0) && e == ARRAY_END_CHAR.charAt(0)) || (s == NEW_INSTANCE_START_CHAR.charAt(0) && e == NEW_INSTANCE_END_CHAR.charAt(0));
	}
}
