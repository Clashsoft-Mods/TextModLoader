package com.chaosdev.textmodloader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.minecraft.item.ItemStack;
import clashsoft.clashsoftapi.util.CSArray;

import com.chaosdev.textmodloader.util.CodeBlock;
import com.chaosdev.textmodloader.util.Parser;
import com.chaosdev.textmodloader.util.Variable;

public class TextMod extends CodeBlock
{
	public static final String PARAMETER_SPLIT_CHAR = ",";
	public static final String ARRAY_SPLIT_CHAR = ",";
	
	public static final String ARRAY_START_CHAR = "{";
	public static final String ARRAY_END_CHAR = "}";
	
	public static final String METHOD_PARAMETERS_START_CHAR = "(";
	public static final String METHOD_PARAMETERS_END_CHAR = ")";
	public static final String METHOD_INVOCATION_START_CHAR = ">";
	public static final String METHOD_INVOCATION_END_CHAR = "<";
	
	public static final String NEW_INSTANCE_START_CHAR = "(";
	public static final String NEW_INSTANCE_END_CHAR = ")";
	
	public static final String VARIABLE_INITIALIZATION_CHAR = "+";
	public static final String VARIABLE_USAGE_CHAR = "%";
	
	public static final String INTEGER_CHAR = "i";
	public static final String FLOAT_CHAR = "f";
	public static final String DOUBLE_CHAR = "d";
	
	public static final String STRING_START_CHAR = "\"";
	public static final String STRING_END_CHAR = "\"";
	public static final String CHAR_START_CHAR = "\'";
	public static final String CHAR_END_CHAR = "\'";
	
	public long loadTime = -1L;
	public long executedTime = -1L;
	
	public TextMod()
	{
		super(null);
		loadTime = System.currentTimeMillis();
	}
	
	@Override
	public void execute()
	{		
		super.execute();
	}
	
	public long getTotalLoadingTime()
	{
		return executedTime - loadTime;
	}

	public static TextMod fromFile(File modClass) throws IOException
	{
		TextMod tm = new TextMod();
		BufferedReader br = new BufferedReader(new FileReader(modClass));
		String line;
		while ((line = br.readLine()) != null)
		{
			tm.lines.add(line);
		}
		br.close();
		return tm;
	}
	
	public static void load(File modClass)
	{
		System.out.println(" Loading TextMod: " + modClass);
		try
		{
			TextMod tm = fromFile(modClass);
			tm.execute();
			tm.executedTime = System.currentTimeMillis();
			System.out.println(" TextMod " + modClass + " loaded. (" + tm.getTotalLoadingTime() + " Milliseconds)");
			TextModLoader.loadedTextMods.add(tm);
		}
		catch (Exception ex)
		{
			System.out.println(" Unable to load TextMod: " + ex.getMessage());
		}
	}
}
