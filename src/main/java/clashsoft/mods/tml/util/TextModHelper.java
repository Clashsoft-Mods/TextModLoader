package clashsoft.mods.tml.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import clashsoft.mods.tml.TextModConstants;
import clashsoft.mods.tml.methods.MethodExecutor;

public class TextModHelper implements TextModConstants
{
	public static Map<String, MethodExecutor>	methods	= new HashMap<String, MethodExecutor>();
	
	public static void registerMethodExecuter(MethodExecutor executor)
	{
		if (executor == null)
			throw new IllegalArgumentException("Method Executor cant be null!");
		if (executor.getName().contains("|"))
		{
			String[] names = executor.getName().split("|");
			for (String name : names)
			{
				registerMethodExecutor(name, executor);
			}
		}
		else
		{
			registerMethodExecutor(executor.getName(), executor);
		}
	}
	
	private static void registerMethodExecutor(String name, MethodExecutor executor)
	{
		if (!name.contains("."))
			name = TML_CLASS_NAME + "." + name;
		methods.put(name, executor);
	}
	
	public static MethodExecutor getMethodExecutorFromName(String name)
	{
		return methods.get(name);
	}
	
	public static String[] createParameterList(String par1, char splitChar)
	{
		List<String> strings = new ArrayList<String>();
		
		String curString = "";
		char block = 0;
		int length = par1.toCharArray().length;
		
		for (int i = 0; i < length; i++)
		{
			char c = par1.charAt(i);
			if (block == 0)
			{
				if (isBlockStartChar(c))
					block = c;
				else if (isValidBlock(block, c))
					block = 0;
			}
			else
			{
				if (isValidBlock(block, c))
					block = 0;
			}
			
			if (block == 0 && (c == splitChar || i == par1.length() - 1))
			{
				if (i == par1.length() - 1)
					curString += c;
				strings.add(curString.trim());
				curString = "";
			}
			else
				curString += c;
		}
		return strings.<String>toArray(new String[strings.size()]);
	}
	
	public static String[] createCharList(String par1)
	{
		ArrayList<String> strings = new ArrayList<String>();
		
		char[] chars = par1.toCharArray();
		String curString = "";
		char block = 0;
		for (int i = 0; i < chars.length; i++)
		{
			char c = chars[i];
			
			if (block == 0)
			{
				if (TextModHelper.isBlockStartChar(c))
					block = c;
				else if (TextModHelper.isValidBlock(block, c))
					block = 0;
			}
			else
			{
				if (TextModHelper.isValidBlock(block, c))
					block = 0;
			}
			
			if (block == 0 || (i == par1.length() - 1))
			{
				curString += c;
				strings.add(curString);
				curString = "";
			}
			else
				curString += c;
		}
		
		return strings.<String>toArray(new String[strings.size()]);
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
