package com.chaosdev.textmodloader.util;

import java.lang.reflect.Array;
import java.util.regex.Pattern;

import clashsoft.clashsoftapi.util.CSUtil;

import com.chaosdev.textmodloader.util.codeblock.CodeBlock;
import com.chaosdev.textmodloader.util.types.Type;

import net.minecraft.item.ItemStack;

public class Parser implements TextModConstants
{
	private CodeBlock	codeblock;
	
	public Parser(CodeBlock codeblock)
	{
		this.codeblock = codeblock;
	}
	
	public void update(CodeBlock codeblock)
	{
		this.codeblock = codeblock;
	}
	
	public Object[] parse(String[] par)
	{
		Object[] obj = new Object[par.length];
		for (int i = 0; i < par.length; i++)
		{
			obj[i] = parse(par[i]);
		}
		return obj;
	}
	
	public Object parse(String par1)
	{
		par1 = par1.trim();
		String normalCase = par1;
		String lowerCase = par1.toLowerCase();
		
		if (par1.startsWith("new ")) // New-Instance-Directives
			return parseInstance(par1);
		
		else if (codeblock.getVariables().get(normalCase) != null) // Indicates a variable
			return codeblock.getVariables().get(normalCase).value;
		
		else if (codeblock.isMethod(normalCase)) // Indicates a method
			return codeblock.executeMethod(codeblock.getMethod(par1));
		
		else if (lowerCase.equals("true") || lowerCase.equals("false")) // Boolean
			return (boolean) (lowerCase.equals("true") ? true : false);
		
		else if (par1.startsWith(STRING_START_CHAR) && par1.endsWith(STRING_END_CHAR)) // String
			return par1.substring(1, par1.length() - 1);
		
		else if (par1.startsWith(CHAR_START_CHAR) && par1.endsWith(CHAR_END_CHAR) && par1.length() <= 3) // Character
			return (char) par1.substring(1, par1.length() - 1).charAt(0);
		
		else if (lowerCase.matches("-?\\d+(\\.\\d+)?")) // Integer
			return (int)parseNumber(par1);
		
		else if (lowerCase.endsWith(FLOAT_CHAR) && lowerCase.matches("-?\\d+(\\.\\d+)?")) // Float
			return (float)parseNumber(par1);
		
		else if (lowerCase.endsWith(DOUBLE_CHAR) && lowerCase.matches("-?\\d+(\\.\\d+)?")) // Double
			return (double)parseNumber(par1);
		
		else if (par1.startsWith("new ") && par1.contains(ARRAY_START_CHAR) && par1.endsWith(ARRAY_END_CHAR)) // Arrays
			return parseArray(par1);
		
		return par1; // Everything else is parsed by the textmod.
	}
	
	public double parseNumber(String par1)
	{
		return CSUtil.calculateFromString(normalize(par1));
	}
	
	public boolean parseBoolean(String par1)
	{
		return CSUtil.createBoolean(normalize(par1));
	}
	
	public String normalize(String par1)
	{
		String[] split = TextModHelper.createParameterList(par1, ' ');
		for (int i = 0; i < split.length; i++)
		{
			split[i] = split[i].replace(INTEGER_CHAR, "").replace(FLOAT_CHAR, "").replace(DOUBLE_CHAR, "").trim(); //Replaces indicator chars
			if (codeblock.isMethod(split[i]) || codeblock.isVariable(split[i])) //Replaced methods and variables with their values
				split[i] = parse(split[i]).toString();
		}
		StringBuilder sb = new StringBuilder();
		for (String s : split)
			sb.append(s);
		return sb.toString().trim();
	}
	
	/**
	 * This will return an array of the specified type instead of an Object[]
	 * that needs to be converted.
	 * 
	 * @param par1
	 * @return
	 */
	public Object parseArray(String par1)
	{
		par1.replaceFirst(Pattern.quote("new "), "");
		int brace1Pos = par1.indexOf("{");
		int brace2Pos = par1.indexOf("}");
		if (brace1Pos == -1 || brace2Pos == -1)
			return null;
		String type = par1.substring(0, brace1Pos).trim();
		String parameters = par1.substring(brace1Pos + 1, brace2Pos).trim();
		String[] aparameters = TextModHelper.createParameterList(parameters, TextModConstants.ARRAY_SPLIT_CHAR.charAt(0));
		Object[] aparameters2 = parse(aparameters);
		return arrayWithType(type, aparameters2);
	}
	
	public Object arrayWithType(String type, Object[] values)
	{
		type = type.trim().toLowerCase();
		Type type1 = Type.getTypeFromName(type);
		
		Object[] array = (Object[]) Array.newInstance(type1.getClass(), values.length);
		System.arraycopy(values, 0, array, 0, values.length);
		
		return array;
	}
	
	public Object parseInstance(String par1)
	{
		String nonew = par1.trim().replaceFirst("new ", "");
		int brace1Pos = nonew.indexOf(TextModConstants.NEW_INSTANCE_START_CHAR);
		int brace2Pos = nonew.indexOf(TextModConstants.NEW_INSTANCE_END_CHAR);
		String type = nonew.substring(0, brace1Pos);
		String par = nonew.substring(brace1Pos + 1, brace2Pos);
		String[] par2 = TextModHelper.createParameterList(par, TextModConstants.PARAMETER_SPLIT_CHAR.charAt(0));
		return createInstance(type, parse(par2));
	}
	
	public Object createInstance(String type, Object[] parameters)
	{
		if (Type.getTypeFromName(type).getClass().equals(ItemStack.class))
		{
			int id = (Integer) parameters[0];
			int amount = 1;
			int damage = 0;
			if (parameters.length >= 2 && parameters[1] instanceof Integer)
			{
				amount = (Integer) parameters[1];
			}
			if (parameters.length >= 3 && parameters[2] instanceof Integer)
			{
				damage = (Integer) parameters[2];
			}
			return new ItemStack(id, amount, damage);
		}
		return null;
	}
}
