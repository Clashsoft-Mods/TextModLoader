package com.chaosdev.textmodloader.util;

import java.util.*;

import com.chaosdev.textmodloader.TextModConstants;
import com.chaosdev.textmodloader.methods.MethodExecuter;

/**
 * The Class TextModHelper.
 */
public class TextModHelper implements TextModConstants
{
	/** The methods. */
	public static Map<String, MethodExecuter>	methods	= new HashMap<String, MethodExecuter>();
	
	/**
	 * Register method executer.
	 *
	 * @param executer the executer
	 */
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
	
	/**
	 * Register method executer.
	 *
	 * @param name the name
	 * @param executer the executer
	 */
	private static void registerMethodExecuter(String name, MethodExecuter executer)
	{
		if (!name.contains("."))
			name = TML_CLASS_NAME + "." + name;
		methods.put(name, executer);
	}
	
	/**
	 * Gets the method executer from name.
	 *
	 * @param name the name
	 * @return the method executer from name
	 */
	public static MethodExecuter getMethodExecuterFromName(String name)
	{
		return methods.get(name);
	}
	
	/**
	 * Creates the parameter list.
	 *
	 * @param par1 the par1
	 * @param splitChar the split char
	 * @return the string[]
	 */
	public static String[] createParameterList(String par1, char splitChar)
	{
		List<String> strings = new LinkedList<String>();
		
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
	
	public static String[] createCharList(String par1)
	{
		ArrayList<String> ret = new ArrayList<String>();
		
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
				ret.add(curString);
				curString = "";
			}
			else
				curString += c;
		}
		
		return ret.<String>toArray(new String[ret.size()]);
	}
	
	/**
	 * Checks if the char starts a block
	 *
	 * @param c the c
	 * @return true, if is block start char
	 */
	public static boolean isBlockStartChar(char c)
	{
		return c == CHAR_START_CHAR.charAt(0) || c == STRING_START_CHAR.charAt(0) || c == METHOD_INVOCATION_START_CHAR.charAt(0) || c == ARRAY_START_CHAR.charAt(0) || c == NEW_INSTANCE_START_CHAR.charAt(0);
	}
	
	/**
	 * Checks if the two chars start and end a block
	 *
	 * @param s the s
	 * @param e the e
	 * @return true, if is valid block
	 */
	public static boolean isValidBlock(char s, char e)
	{
		return (s == CHAR_START_CHAR.charAt(0) && e == CHAR_END_CHAR.charAt(0)) || (s == STRING_START_CHAR.charAt(0) && e == STRING_END_CHAR.charAt(0)) || (s == METHOD_INVOCATION_START_CHAR.charAt(0) && e == METHOD_INVOCATION_END_CHAR.charAt(0)) || (s == ARRAY_START_CHAR.charAt(0) && e == ARRAY_END_CHAR.charAt(0)) || (s == NEW_INSTANCE_START_CHAR.charAt(0) && e == NEW_INSTANCE_END_CHAR.charAt(0));
	}
}
