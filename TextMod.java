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

import com.chaosdev.textmodloader.methods.IMethodExecuter;
import com.chaosdev.textmodloader.util.Method;
import com.chaosdev.textmodloader.util.Parser;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;

public class TextMod
{	
	public static List<String> lines = new LinkedList<String>();
	
	public static Map<String, String> variables = new HashMap<String, String>();
	
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
	
	public static TextMod fromFile(File modClass) throws IOException
	{
		TextMod tm = new TextMod();
		BufferedReader br = new BufferedReader(new FileReader(modClass));
		String line;
		while ((line = br.readLine()) != null)
		{
			lines.add(line);
		}
		br.close();
		return tm;
	}
	
	public void init()
	{
		for (String line : lines)
		{
			try
			{
				this.executeLine(line);
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
		}
	}

	public void executeLine(String line)
	{
		if (TextModHelper.isLineValid(line))
		{
			System.out.println("  Reading line: " + line);
			if (line.startsWith(METHOD_INVOCATION_START_CHAR)) //Methods
			{
				executeMethod(getMethod(line));
			}
			else if (line.startsWith("+")) //Variables
			{
				line.replace(";", "");
				Variable v = getVariable(line);
				this.variables.put(v.name, v.value);
				System.out.println("  Variable \'" + v.name + "\' added with value \'" + v.value + "\'.");
			}
		}
	}
	
	public Object executeMethod(Method method)
	{
		IMethodExecuter executer = TextModHelper.getMethodExecuterFromName(method.name);
		if (executer != null)
			return executer.execute(method.parameters);
		else
			System.out.println("  No valid executer found for method name " + method.name);
		return null;
	}

	public Method getMethod(String line)
	{
		//Replaces the method identifier
		line = line.replaceFirst("[>]", "").trim();
		int i = line.indexOf(METHOD_PARAMETERS_START_CHAR);
		int j = line.lastIndexOf(METHOD_PARAMETERS_END_CHAR);
		if (i == -1 || j == -1)
			return null;
		String methodName = line.substring(0, i);
		String parameters = line.substring(i + 1, j);
		String[] aparameters = TextModHelper.createParameterList(parameters, PARAMETER_SPLIT_CHAR.charAt(0));
		for (int m = 0; m < aparameters.length; m++)
		{
			aparameters[m] = aparameters[m].trim();
		}
		Object[] aparameters2 = Parser.parse(aparameters);
		for (int m = 0; m < aparameters2.length; m++)
		{
			if (((String)aparameters2[m]).startsWith(VARIABLE_USAGE_CHAR)) //Indicates a variable	
			{
				//Replace variables with their values
				String variableName = aparameters[m].substring(1);
				aparameters2[m] = Parser.parse(variables.get(variableName));
			}
			if (((String)aparameters2[m]).startsWith(METHOD_INVOCATION_START_CHAR) && aparameters[m].endsWith(METHOD_INVOCATION_END_CHAR)) //Indicates a method
			{
				Method method = getMethod(aparameters[m].substring(1));
				aparameters2[m] = executeMethod(method);
			}
		}
		return new Method(methodName, aparameters2);
	}
	
	public Variable getVariable(String line)
	{
		line = line.substring(1).trim();
		int equalPos = line.indexOf('=');
		String name = line.substring(0, equalPos).trim();
		String value = line.substring(equalPos + 1, line.indexOf(";")).trim();
		if (value.startsWith(METHOD_INVOCATION_START_CHAR) && value.endsWith(METHOD_INVOCATION_END_CHAR))
		{
			Method method = getMethod(value.substring(1));
			value = Parser.store(executeMethod(method));
		}
		return new Variable(name, value);
	}
}
