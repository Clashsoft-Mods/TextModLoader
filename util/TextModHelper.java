package com.chaosdev.textmodloader.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.chaosdev.textmodloader.TextMod;
import com.chaosdev.textmodloader.methods.IMethodExecuter;

public class TextModHelper
{
	private static Map<String, IMethodExecuter> methods = new HashMap<String, IMethodExecuter>();

	public static void registerMethodExecuter(IMethodExecuter executer)
	{
		if (executer == null)
			throw new IllegalArgumentException("Method Executer cant be null!");
		if (executer.getName().contains("|"))
		{
			String[] names = executer.getName().split("|");
			for (String name : names)
			{
				name = changeName(name);
				methods.put(name, executer);
			}
		}
		else
		{
			methods.put(changeName(executer.getName()), executer);
		}
	}

	public static IMethodExecuter getMethodExecuterFromName(String name)
	{
		return methods.get(changeName(name.replaceFirst(">", "")));
	}

	public static String changeName(String name)
	{
		if (name != null)
			return name.replace(" ", "").toLowerCase().trim();
		return "";
	}

	public static boolean isLineValid(String line)
	{
		return line != null && line != "" && line != "\n" && !line.startsWith("#") && !line.startsWith("//") && line.endsWith(";");
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
		return c == TextMod.CHAR_START_CHAR.charAt(0) ||
				c == TextMod.STRING_START_CHAR.charAt(0) ||
				c == TextMod.METHOD_INVOCATION_START_CHAR.charAt(0) ||
				c == TextMod.ARRAY_START_CHAR.charAt(0) ||
				c == TextMod.NEW_INSTANCE_START_CHAR.charAt(0);
	}

	public static boolean isValidBlock(char s, char e)
	{
		return (s == TextMod.CHAR_START_CHAR.charAt(0) && e == TextMod.CHAR_END_CHAR.charAt(0)) ||
				(s == TextMod.STRING_START_CHAR.charAt(0) && e == TextMod.STRING_END_CHAR.charAt(0)) ||
				(s == TextMod.METHOD_INVOCATION_START_CHAR.charAt(0) && e == TextMod.METHOD_INVOCATION_END_CHAR.charAt(0)) ||
				(s == TextMod.ARRAY_START_CHAR.charAt(0) && e == TextMod.ARRAY_END_CHAR.charAt(0)) ||
				(s == TextMod.NEW_INSTANCE_START_CHAR.charAt(0) && e == TextMod.NEW_INSTANCE_END_CHAR.charAt(0));
	}
}
