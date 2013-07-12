package com.chaosdev.textmodloader;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.chaosdev.textmodloader.methods.MethodExecuter;

public class TextModHelper
{
	public static class Method
	{
		public String name;
		public Object[] parameters;

		public Method(String name, Object... parameters)
		{
			this.name = name;
			this.parameters = parameters;
		}
	}

	private static Map<String, MethodExecuter> methods = new HashMap<String, MethodExecuter>();
	
	public static void registerMethodExecuter(MethodExecuter executer)
	{
		if (executer == null)
			throw new IllegalArgumentException("Method Executer cant be null!");
		String name = changeName(executer.getName());
		methods.put(name, executer);
	}
	
	public static MethodExecuter getMethodExecuterFromName(String name)
	{
		return methods.get(changeName(name));
	}

	public static void executeLine(String line)
	{
		if (line != null && line != "" && !line.startsWith("#") && !line.startsWith("//")) //Comment Lines
		{
			System.out.println("  Executing line: " + line);
			Method method = splitLine(line);
			String methodname = changeName(method.name);
			MethodExecuter executer = methods.get(methodname);
			if (executer != null)
				executer.execute(method.parameters);
			else
				System.out.println("  No valid executer found for method name " + methodname);
		}
	}
	
	public static String changeName(String name)
	{
		if (name != null)
			return name.replace(" ", "").toLowerCase().trim();
		return "";
	}

	public static Method splitLine(String line)
	{
		int i = line.indexOf('(');
		int j = line.indexOf(')');
		line = line.trim();
		String methodName = line.substring(0, i);
		String parameters = line.substring(i + 1, j);
		String[] aparameters = parameters.split(", ");
		Object[] aparameters2 = parseParameters(aparameters);
		return new Method(methodName, aparameters2);
	}

	public static Object[] parseParameters(String[] par)
	{
		Object[] obj = new Object[par.length];
		for (int i = 0; i < par.length; i++)
		{
			obj[i] = parseParameter(par[i]);
		}
		return obj;
	}

	public static Object parseParameter(String par1)
	{
		String normalCase = par1;
		String lowerCase = par1.toLowerCase();

		if (par1.startsWith("\"") && par1.endsWith("\"")) //String
			return (String)par1.substring(1, par1.length() - 1);

		else if (par1.startsWith("\'") && par1.endsWith("\'") && par1.length() <= 3) //Character
			return (char)par1.substring(1, par1.length() - 1).charAt(0);

		else if (lowerCase.endsWith("i")) //Integer
			return Integer.parseInt(lowerCase.replace("i", ""));

		else if (lowerCase.endsWith("f")) //Float
			return Float.parseFloat(lowerCase.replace("f", ""));

		else if (lowerCase.endsWith("d")) //Double
			return Double.parseDouble(lowerCase.replace("d", ""));

		else if (par1 == "true" || par1 == "false") //Boolean
			return (boolean)(par1 == "true" ? true : false);

		else if (par1.startsWith("{") && par1.endsWith("}")) //Arrays
		{
			String parameters = par1.substring(1, par1.length() - 1);
			String[] aparameters = parameters.split(", ");
			return parseParameters(aparameters);
		}

		return null;
	}
}
