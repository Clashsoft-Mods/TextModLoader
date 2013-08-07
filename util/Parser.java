package com.chaosdev.textmodloader.util;

import net.minecraft.item.ItemStack;

import com.chaosdev.textmodloader.util.codeblock.CodeBlock;
import com.chaosdev.textmodloader.util.types.*;

public class Parser
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
		
		else if (codeblock.getVariables().get(normalCase) != null) // Indicates
																	// a
																	// variable
			return codeblock.getVariables().get(normalCase).value;
		
		else if (codeblock.isMethod(normalCase)) // Indicates a method
			return codeblock.executeMethod(codeblock.readMethod(par1));
		
		else if (lowerCase.equals("true") || lowerCase.equals("false")) // Boolean
			return (boolean) (lowerCase.equals("true") ? true : false);
		
		else if (par1.startsWith(TextModConstants.STRING_START_CHAR) && par1.endsWith(TextModConstants.STRING_END_CHAR)) // String
			return par1.substring(1, par1.length() - 1);
		
		else if (par1.startsWith(TextModConstants.CHAR_START_CHAR) && par1.endsWith(TextModConstants.CHAR_END_CHAR) && par1.length() <= 3) // Character
			return (char) par1.substring(1, par1.length() - 1).charAt(0);
		
		else if (lowerCase.matches("-?\\d+(\\.\\d+)?")) // Integer
			return Integer.parseInt(lowerCase.replace(TextModConstants.INTEGER_CHAR, ""));
		
		else if (lowerCase.endsWith(TextModConstants.FLOAT_CHAR) && lowerCase.matches("-?\\d+(\\.\\d+)?")) // Float
			return Float.parseFloat(lowerCase.replace(TextModConstants.FLOAT_CHAR, ""));
		
		else if (lowerCase.endsWith(TextModConstants.DOUBLE_CHAR) && lowerCase.matches("-?\\d+(\\.\\d+)?")) // Double
			return Double.parseDouble(lowerCase.replace(TextModConstants.DOUBLE_CHAR, ""));
		
		else if (par1.contains(TextModConstants.ARRAY_START_CHAR) && par1.endsWith(TextModConstants.ARRAY_END_CHAR)) // Arrays
			return parseArray(par1);
		
		return par1; // Everything else is parsed by the textmod.
	}
	
	public String store(Object par1)
	{
		if (par1 instanceof String)
			return TextModConstants.STRING_START_CHAR + (String) par1 + TextModConstants.STRING_END_CHAR;
		else if (par1 instanceof Character)
			return TextModConstants.CHAR_START_CHAR + par1 + TextModConstants.CHAR_END_CHAR;
		else if (par1 instanceof Integer)
			return ((Integer) par1).toString() + TextModConstants.INTEGER_CHAR;
		else if (par1 instanceof Float)
			return ((Float) par1).toString() + TextModConstants.FLOAT_CHAR;
		else if (par1 instanceof Double)
			return ((Double) par1).toString() + TextModConstants.DOUBLE_CHAR;
		else if (par1 instanceof Boolean)
			return ((Boolean) par1) ? "true" : "false";
		else if (par1 != null && par1.getClass().isArray())
			return storeArray(par1);
		return "";
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
		int brace1Pos = par1.indexOf("{");
		int brace2Pos = par1.indexOf("}");
		String type = par1.substring(0, brace1Pos).trim();
		String parameters = par1.substring(brace1Pos + 1, brace2Pos).trim();
		String[] aparameters = TextModHelper.createParameterList(parameters, TextModConstants.ARRAY_SPLIT_CHAR.charAt(0));
		return arrayWithType(type, aparameters);
	}
	
	public String storeArray(Object par1)
	{
		String type = "";
		if (par1 instanceof int[])
			type = "int";
		else if (par1 instanceof float[])
			type = "float";
		else if (par1 instanceof double[])
			type = "double";
		else if (par1 instanceof String[])
			type = "string";
		else if (par1 instanceof char[])
			type = "char";
		else if (par1 instanceof boolean[])
			type = "boolean";
		String ret = type + "{";
		for (int i = 0; i < ((Object[]) par1).length; i++)
		{
			Object o = ((Object[]) par1)[i];
			ret += store(o) + (i == ((Object[]) par1).length - 1 ? "" : ", ");
		}
		return ret;
	}
	
	public Object arrayWithType(String type, String[] values)
	{
		String type1 = type.trim().toLowerCase();
		Object o = new Object[values.length];
		if (type1.equals("integer") || type1.equals("int"))
		{
			int[] array = new int[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = (Integer) parse(values[i]);
			}
			o = array;
		}
		if (type1.equals("float"))
		{
			float[] array = new float[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = (Float) parse(values[i]);
			}
			o = array;
		}
		if (type1.equals("double"))
		{
			double[] array = new double[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = (Double) parse(values[i]);
			}
			o = array;
		}
		if (type1.equals("char") || type1.equals("character"))
		{
			char[] array = new char[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = (Character) parse(values[i]);
			}
			o = array;
		}
		if (type1.equals("string"))
		{
			String[] array = new String[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = (String) parse(values[i]);
			}
			o = array;
		}
		if (type1.equals("bool") || type1.equals("boolean"))
		{
			boolean[] array = new boolean[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = (Boolean) parse(values[i]);
			}
			o = array;
		}
		if (type1.equals("") || type1.equals("object"))
		{
			Object[] array = new Object[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = parse(values[i]);
			}
			o = array;
		}
		if (type1.endsWith("[]"))
		{
			Object[] array = new Object[values.length];
			for (int i = 0; i < values.length; i++)
			{
				array[i] = arrayWithType(type1.replace("[]", ""), values);
			}
			o = array;
		}
		return o;
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
