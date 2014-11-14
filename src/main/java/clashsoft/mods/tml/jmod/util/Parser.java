package clashsoft.mods.tml.jmod.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import clashsoft.mods.tml.TextModConstants;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;
import clashsoft.mods.tml.jmod.util.exceptions.SyntaxException;
import clashsoft.mods.tml.jmod.util.operator.Operator;
import clashsoft.mods.tml.jmod.util.types.Type;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * The Class Parser.
 */
public class Parser implements TextModConstants
{
	/** The codeblock. */
	private CodeBlock	codeblock;
	
	/**
	 * Instantiates a new parser.
	 *
	 * @param codeblock the codeblock
	 */
	public Parser(CodeBlock codeblock)
	{
		this.codeblock = codeblock;
	}
	
	/**
	 * Helper method to update the sync the codeblock variables.
	 *
	 * @param codeblock the codeblock
	 */
	public void setCodeBlock(CodeBlock codeblock)
	{
		this.codeblock = codeblock;
	}
	
	/**
	 * Parses a list of parsable objects.
	 *
	 * @param par the list to parse
	 * @return the parsed list
	 * @throws SyntaxException the parser exception
	 */
	public Object[] parse(CodeLine line, String... par) throws SyntaxException
	{
		Object[] obj = new Object[par.length];
		for (int i = 0; i < par.length; i++)
		{
			obj[i] = this.parse(line, par[i]);
		}
		return obj;
	}
	
	/**
	 * Parses a String, even if it has operators in it
	 * <p>
	 * Example:
	 * <p>
	 * <code>
	 * Object o = parse("\"Hello \" + 1")
	 * </code>
	 * <p>
	 * would return the string
	 * <p>
	 * <code>
	 * "Hello"1
	 * </code>
	 *
	 * @param par1 the string to parse
	 * @return the parsed object
	 * @throws SyntaxException the parser exception
	 */
	public Object parse(CodeLine line, String par1) throws SyntaxException
	{	
		String[] split = TextModHelper.createCharList(par1);
		
		ArrayList<Object> list = new ArrayList<Object>();
		
		String op = "";
		String string = "";
		for (int i = 0; i < split.length; i++)
		{
			String s = split[i];
			
			if (Operator.fromStart(s) == null)
			{
				if (!op.isEmpty() && Operator.fromString(op) != null)
				{
					string = string.trim();
					if (!string.isEmpty())
					{
						list.add(string);
						string = "";
					}
					list.add(Operator.fromString(op));
					op = "";
				}
				else if (!op.isEmpty())
				{
					string += op;
					op = "";
				}
				string += s;
				
				if (i == split.length - 1 && !string.isEmpty())
				{
					list.add(string.trim());
				}
			}
			else
			{
				op += s;
			}
		}
		return this.parseSplitOperatorList(line, list);
	}
	
	public Object parseSplitOperatorList(CodeLine line, List<Object> objects) throws SyntaxException
	{
		Object value = null;
		
		Operator op = null;
		Operator preOp = null;
		Operator postOp = null;
		
		for (int i = 0; i < objects.size(); i++)
		{
			Object object = objects.get(i);
			boolean first = i == 0;
			boolean last = i == objects.size() - 1;
			
			if (object instanceof String)
			{	
				if (op != null && (value != null || op.isPrefixOperator()))
				{
					Object value2 = this.directParse(line, (String)object);
					boolean flag = false;
					
					if (preOp != null && preOp.canOperate(Type.VOID, Type.getTypeFromObject(value2)))
					{
						value2 = preOp.operate(null, value2);
						flag = true;
					}
					
					if (op.canOperate(Type.getTypeFromObject(value), Type.getTypeFromObject(value2)))
					{
						value = op.operate(value, value2);
						flag = true;
					}
					
					if (postOp != null && postOp.canOperate(Type.getTypeFromObject(value), Type.VOID))
					{
						value = postOp.operate(value, null);
						flag = true;
					}
					
					if (!flag && value != null)
					{
						String message = "Invalid operator " + op + " for operating the types " + value.getClass().getSimpleName() + " and " + value2.getClass().getSimpleName();
						throw new SyntaxException(message, line, op.operator);
					}
				}
				else
				{
					value = this.directParse(line, (String)object);
				}
			}
			else if (object instanceof Operator)
			{	
				if (((Operator)object).isPrefixOperator())
				{
					preOp = (Operator)object;
					postOp = null;
				}
				else if (((Operator)object).isPostfixOperator())
				{
					preOp = null;
					postOp = (Operator)object;
				}
				else
				{
					preOp = null;
					postOp = null;
					op = (Operator)object;
				}
				
				if (op != null && (first && !op.isPrefixOperator() || last && !op.isPostfixOperator()))
				{
					throw new SyntaxException("Invalid operator " + op, line, op.operator);
				}
			}	
		}
		
		return value;
	}
	
	/**
	 * Directly parses a string, ignores operators.
	 * <p>
	 * Example:
	 * <p>
	 * <code>
	 * Object o = parser.directParse("\"Hello \"" + 1")
	 * </code>
	 * <p>
	 * would cause a ParserException, because operators are not supported by
	 * this method.
	 * <p>
	 * Use parse(String) instead.
	 *
	 * @param par1 String to parse
	 * @return Parsed object
	 * @throws SyntaxException the parser exception
	 */
	public Object directParse(CodeLine line, String par1) throws SyntaxException
	{
		par1 = par1.trim();
		String normalCase = par1;
		String lowerCase = par1.toLowerCase();
		CodeLine cl = new CodeLine(line.lineNumber, normalCase);
		
		if (par1.startsWith("(") && par1.endsWith(")"))
		{
			return this.parse(line, par1.substring(par1.indexOf("(") + 1, par1.lastIndexOf(")")));
		}
		else if (Type.getTypeFromName(par1) != Type.VOID)
		{
			return Type.getTypeFromName(par1);
		}
		else if (par1.startsWith("new ") && par1.contains(ARRAY_START_CHAR) && par1.endsWith(ARRAY_END_CHAR))
		{
			return this.parseArray(line, par1);
		}
		else if (par1.startsWith("new "))
		{
			return this.parseInstance(line, par1);
		}
		else if (lowerCase.equals("true") || lowerCase.equals("false"))
		{
			return (boolean) (lowerCase.equals("true") ? true : false);
		}
		else if (par1.startsWith(STRING_START_CHAR) && par1.endsWith(STRING_END_CHAR))
		{
			return par1.substring(1, par1.length() - 1);
		}
		else if (par1.startsWith(CHAR_START_CHAR) && par1.endsWith(CHAR_END_CHAR) && par1.length() <= 3)
		{
			return (char) par1.substring(1, par1.length() - 1).charAt(0);
		}
		else if (lowerCase.matches("-?\\d+(\\.\\d+)?"))
		{
			return (int) this.parseNumber(line, par1);
		}
		else if (lowerCase.matches("-?\\d+(\\.\\d+)?f"))
		{
			return (float) this.parseNumber(line, par1);
		}
		else if (lowerCase.matches("-?\\d+(\\.\\d+)?d"))
		{
			return (double) this.parseNumber(line, par1);
		}
		else if (lowerCase.matches("-?\\d+(\\.\\d+)?l"))
		{
			return (long) this.parseNumber(line, par1);
		}
		else if (normalCase.equals("null"))
		{
			return null;
		}
		else if (normalCase.contains(METHOD_PARAMETERS_START_CHAR) && normalCase.endsWith(METHOD_PARAMETERS_END_CHAR) && this.codeblock.isMethod(cl))
		{
			return this.codeblock.executeMethod(this.codeblock.getMethod(cl));
		}
		else if (this.codeblock.getVariable(line, normalCase) != null)
		{
			return this.codeblock.getVariable(line, normalCase).value;
		}
		
		
		throw new SyntaxException("Unable to parse: " + par1, line, par1);
	}
	
	/**
	 * Parses a number
	 *
	 * @param par1 the par1
	 * @return the double
	 * @throws SyntaxException the parser exception
	 */
	public double parseNumber(CodeLine line, String par1) throws SyntaxException
	{
		return Double.parseDouble(this.normalize(line, par1));
	}
	
	/**
	 * Parses a boolean value
	 *
	 * @param par1 the par1
	 * @return true, if successful
	 * @throws SyntaxException the parser exception
	 */
	public boolean parseBoolean(String par1) throws SyntaxException
	{
		return par1.equals("true") ? true : false;
	}
	
	/**
	 * Normalizes a string for use in JavaScript evaluation.
	 *
	 * @param par1 the par1
	 * @return the string
	 * @throws SyntaxException the parser exception
	 */
	public String normalize(CodeLine line, String par1) throws SyntaxException
	{
		String[] split = TextModHelper.createParameterList(par1, ' ');
		for (int i = 0; i < split.length; i++)
		{
			split[i] = split[i].replace(INTEGER_CHAR, "").replace(FLOAT_CHAR, "").replace(DOUBLE_CHAR, "").replace(LONG_CHAR, ""); // Replaces indicator chars
			CodeLine cl = new CodeLine(line.lineNumber, split[i]);
			if (this.codeblock.isMethod(cl) || this.codeblock.isVariable(cl))
			{
				split[i] = String.valueOf(this.directParse(line, split[i]));
			}
		}
		StringBuilder sb = new StringBuilder();
		for (String s : split)
		{
			sb.append(s);
		}
		return sb.toString();
	}
	
	/**
	 * This will return an array of the specified type instead of an Object[]
	 * that needs to be converted.
	 *
	 * @param par1 the par1
	 * @return the object
	 * @throws SyntaxException the parser exception
	 */
	public Object parseArray(CodeLine line, String par1) throws SyntaxException
	{
		par1 = par1.replaceFirst(Pattern.quote("new "), "");
		int brace1Pos = par1.indexOf("{");
		int brace2Pos = par1.indexOf("}");
		if (brace1Pos == -1 || brace2Pos == -1)
		{
			return null;
		}
		String type = par1.substring(0, brace1Pos);
		String parameters = par1.substring(brace1Pos + 1, brace2Pos);
		String[] aparameters = TextModHelper.createParameterList(parameters, TextModConstants.ARRAY_SPLIT_CHAR.charAt(0));
		Object[] aparameters2 = this.parse(line, aparameters);
		return this.arrayWithType(type, aparameters2);
	}
	
	/**
	 * Creates an array of the type.
	 *
	 * @param type the type
	 * @param values the values
	 * @return the object
	 */
	public Object arrayWithType(String type, Object... values)
	{
		type = type.replaceAll("\\[\\]", "");
		Type type1 = Type.getTypeFromName(type);
		
		Object[] array = (Object[]) Array.newInstance(type1.type, values.length);
		for (int i = 0; i < values.length; i++)
		{
			array[i] = values[i];
		}
		
		return array;
	}
	
	/**
	 * Parses a new-instance-directive.
	 *
	 * @param par1 the par1
	 * @return the object
	 * @throws SyntaxException the parser exception
	 */
	public Object parseInstance(CodeLine line, String par1) throws SyntaxException
	{
		String nonew = par1.replaceFirst(Pattern.quote("new "), "");
		int brace1Pos = nonew.indexOf(NEW_INSTANCE_START_CHAR);
		int brace2Pos = nonew.indexOf(NEW_INSTANCE_END_CHAR);
		if (brace1Pos == -1)
		{
			brace1Pos = nonew.indexOf(ARRAY_INITIALIZER_START_CHAR);
		}
		if (brace2Pos == -1)
		{
			brace2Pos = nonew.indexOf(ARRAY_INITIALIZER_END_CHAR);
		}
		String type = nonew.substring(0, brace1Pos);
		String par = nonew.substring(brace1Pos + 1, brace2Pos);
		String[] par2 = TextModHelper.createParameterList(par, TextModConstants.PARAMETER_SPLIT_CHAR.charAt(0));
		return this.createInstance(type, this.parse(line, par2));
	}
	
	/**
	 * Creates a new Object of type <i> type </i> using the parameters <i>
	 * parameters </i>.
	 *
	 * @param type the type
	 * @param parameters the parameters
	 * @return the object
	 */
	public Object createInstance(String type, Object... parameters)
	{
		Type type1 = Type.getTypeFromName(type);
		if (type1.type.equals(ItemStack.class))
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
			return new ItemStack(Item.getItemById(id), amount, damage);
		}
		return null;
	}
}
