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

import com.chaosdev.textmodloader.methods.MethodExecuter;
import com.chaosdev.textmodloader.util.CodeBlock;
import com.chaosdev.textmodloader.util.Method;
import com.chaosdev.textmodloader.util.Parser;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.types.Type;

public class TextMod extends CodeBlock
{	
	public static List<String> lines = new LinkedList<String>();

	public static Map<String, Variable> variables = new HashMap<String, Variable>();

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

	public Parser parser;

	public TextMod()
	{
		this.parser = new Parser(this);
	}

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

	private boolean isBlockStart(String line)
	{
		return false;
	}

	public void executeLine(String line)
	{
		if (TextModHelper.isLineValid(line))
		{
			System.out.println("  Reading line: " + line);
			if (isMethod(line)) //Methods
			{
				Method method = getMethod(line);
				executeMethod(method);
			}
			else if (isVariable(line)) //Variables
			{
				Variable v = getVariable(line);
				this.variables.put(v.name, v);
				this.parser.update(this.variables);
				System.out.println("  Variable \'" + v.name + "\' of type \'" + v.type.toString() + "\' added with value \'" + v.value.toString() + "\'.");
			}
		}
	}

	public Object executeMethod(Method method)
	{
		MethodExecuter executer = TextModHelper.getMethodExecuterFromName(method.name);
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
		Object[] aparameters2 = parser.parse(aparameters);
		return new Method(methodName, aparameters2);
	}

	public Variable getVariable(String line)
	{
		String[] split = TextModHelper.createParameterList(line.replace(";", ""), ' ');
		Variable var = null;
		if (isType(split[0])) //First part is a type declaration
		{
			Type type = parser.getType(split[0]);
			String name = split[1];
			Object value = parser.parse(split[3]);
			var = new Variable(type, name, value);
		}
		else //First part is an existing variable name
		{
			Variable var1 = variables.get(split[0]);
			String operator = split[1];
			Object value = parser.parse(split[2]);
			var = operate(var1, operator, value);
		}
		return var;
	}

	public boolean isVariable(String par1)
	{
		String first = par1;
		if (par1.contains(" "))
			first = par1.substring(0, par1.indexOf(" "));
		if (variables.get(TextModHelper.changeName(first)) != null) //Already existing Variable
			return true;
		else if (isType(first))
			return true;
		return false;
	}

	public boolean isMethod(String par1)
	{
		par1 = par1.replace(";", "");
		if (par1.contains(METHOD_PARAMETERS_START_CHAR) && par1.endsWith(METHOD_PARAMETERS_END_CHAR))
		{
			MethodExecuter executer = TextModHelper.getMethodExecuterFromName(par1.substring(0, par1.indexOf(METHOD_PARAMETERS_START_CHAR)));
			return executer != null;
		}
		return false;
	}

	public boolean isType(String par1)
	{
		return parser.getType(par1) != null;
	}

	public Variable operate(Variable var1, String operator, Object value)
	{
		if (operator.equals("="))
		{
			var1.value = value;
			return var1;
		}
		else if (operator.equals("+="))
		{
			if (var1.value instanceof String && value instanceof String)
			{
				var1.value = (String)var1.value + (String)value;
				return var1;
			}
			try
			{
				double i = ((Double)var1.value);
				double j = ((Double)value);
				i += j;
				var1.value = i;
				return var1;
			}
			catch (ClassCastException cce)
			{
				return var1;
			}
		}
		else if (operator.equals("-="))
		{
			try
			{
				double i = ((Double)var1.value);
				double j = ((Double)value);
				i -= j;
				var1.value = i;
				return var1;
			}
			catch (ClassCastException cce)
			{
				return var1;
			}
		}
		else if (operator.equals("*="))
		{
			try
			{
				double i = ((Double)var1.value);
				double j = ((Double)value);
				i *= j;
				var1.value = i;
				return var1;
			}
			catch (ClassCastException cce)
			{
				return var1;
			}
		}
		else if (operator.equals("/="))
		{
			try
			{
				double i = ((Double)var1.value);
				double j = ((Double)value);
				i /= j;
				var1.value = i;
				return var1;
			}
			catch (ClassCastException cce)
			{
				return var1;
			}
		}
		else if (operator.equals("%="))
		{
			try
			{
				double i = ((Double)var1.value);
				double j = ((Double)value);
				i %= j;
				var1.value = i;
				return var1;
			}
			catch (ClassCastException cce)
			{
				return var1;
			}
		}
		else if (operator.equals("&="))
		{
			if (var1.value instanceof Boolean && value instanceof Boolean)
			{
				var1.value = (Boolean)var1.value & (Boolean)value;
				return var1;
			}
			try
			{
				int i = ((Integer)var1.value);
				int j = ((Integer)value);
				i &= j;
				var1.value = i;
				return var1;
			}
			catch (ClassCastException cce)
			{
				return var1;
			}
		}
		else if (operator.equals("|="))
		{
			if (var1.value instanceof Boolean && value instanceof Boolean)
			{
				var1.value = (Boolean)var1.value | (Boolean)value;
				return var1;
			}
			try
			{
				int i = ((Integer)var1.value);
				int j = ((Integer)value);
				i |= j;
				var1.value = i;
				return var1;
			}
			catch (ClassCastException cce)
			{
				return var1;
			}
		}
		return var1;
	}
}
