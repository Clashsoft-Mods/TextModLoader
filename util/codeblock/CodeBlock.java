package com.chaosdev.textmodloader.util.codeblock;

import java.util.*;
import java.util.regex.Pattern;

import com.chaosdev.textmodloader.TextModConstants;
import com.chaosdev.textmodloader.methods.MethodExecuter;
import com.chaosdev.textmodloader.util.Parser;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.annotations.Annotation;
import com.chaosdev.textmodloader.util.annotations.Annotation.AnnotationType;
import com.chaosdev.textmodloader.util.annotations.IAnnotable;
import com.chaosdev.textmodloader.util.codeblocktypes.CodeBlockType;
import com.chaosdev.textmodloader.util.exceptions.ParserException;
import com.chaosdev.textmodloader.util.method.Method;
import com.chaosdev.textmodloader.util.method.PredefinedMethod;
import com.chaosdev.textmodloader.util.operator.Operator;
import com.chaosdev.textmodloader.util.types.Type;

/**
 * The Class CodeBlock.
 */
public class CodeBlock implements IAnnotable, TextModConstants
{
	/** The block comment flag. */
	public boolean					blockComment	= false;
	
	/** The super code block. */
	public CodeBlock				superCodeBlock;
	
	/** The lines. */
	public List<String>				lines;
	
	/** The variables. */
	public Map<String, Variable>	variables		= new HashMap<String, Variable>();
	
	/** The parser. */
	public Parser					parser;
	
	/**
	 * Instantiates a new code block.
	 * 
	 * @param superBlock
	 *            the super block
	 */
	public CodeBlock(CodeBlock superBlock)
	{
		this(superBlock, new LinkedList<String>());
	}
	
	/**
	 * Instantiates a new code block.
	 * 
	 * @param superBlock
	 *            the super block
	 * @param lines
	 *            the lines
	 */
	public CodeBlock(CodeBlock superBlock, List<String> lines)
	{
		this.superCodeBlock = superBlock;
		this.lines = lines;
		this.parser = new Parser(this);
	}
	
	/**
	 * Gets the code block super class.
	 * 
	 * @return the code block class
	 */
	public ClassCodeBlock getCodeBlockClass()
	{
		return superCodeBlock != null ? superCodeBlock.getCodeBlockClass() : null;
	}
	
	/**
	 * Gets the variables.
	 * 
	 * @return the variables
	 */
	public Map<String, Variable> getVariables()
	{
		Map<String, Variable> var = new HashMap<String, Variable>();
		var.putAll(variables);
		if (superCodeBlock != null)
			var.putAll(superCodeBlock.getVariables());
		return var;
	}
	
	/**
	 * Checks if the line can start a new code block
	 * 
	 * @param line
	 *            the line
	 * @return true, if is block start
	 * @throws ParserException
	 *             the parser exception
	 */
	public boolean isBlockStart(String line) throws ParserException
	{
		return !line.isEmpty() && !line.equals("\n") && !isComment(line) && !isMethod(line) && !isVariable(line) && CodeBlockType.getCodeBlockType(this, line) != null;
	}
	
	/**
	 * Checks if the line can end a new code block
	 * 
	 * @param line
	 *            the line
	 * @return true, if is block end
	 */
	public boolean isBlockEnd(String line)
	{
		return line.endsWith("}");
	}
	
	/**
	 * Checks if the line is a comment
	 * 
	 * @param line
	 *            the line
	 * @return true, if is comment
	 */
	public boolean isComment(String line)
	{
		return blockComment || line.startsWith("//");
	}
	
	/**
	 * Executes the Code Block
	 * 
	 * @return the return value of the execution
	 */
	public Object execute()
	{
		System.out.println();
		System.out.println("Executing Code Block: " + this.toString());
		
		Annotation nextAnnotation;
		CodeBlock cb = null;
		for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++)
		{
			String line = lines.get(lineNumber).trim();
			
			if (line.startsWith("/*"))
				blockComment = true;
			if (line.endsWith("*/"))
				blockComment = false;
			if (blockComment)
				continue;
			
			System.out.print("  Reading line " + (lineNumber + 1) + ": " + line);
			
			try
			{
				if (cb instanceof HeaderCodeBlock && ((HeaderCodeBlock) cb).getCodeBlockType().isBreakable() && line.equals("break;"))
					cb = null;
				
				if (line.startsWith("return "))
					return parser.parse(line.replaceFirst(Pattern.quote("return "), ""));
				
				if (cb != null && !isBlockStart(line) && !line.startsWith("{") && !isBlockEnd(line))
					cb.lines.add(line);
				
				if (isBlockStart(line))
					cb = new HeaderCodeBlock(line, this);
				
				else if (line.startsWith("{") && cb == null)
					cb = new CodeBlock(this);
				
				else if (isBlockEnd(line))
				{
					cb.lines.add(line.replace("}", ""));
					cb.execute();
					cb = null;
				}
				
				if (cb == null && !isBlockEnd(line))
					this.executeLine(line);
				else
					System.out.println();
			}
			catch (ParserException pex)
			{
				System.out.println("  Syntax error while executing line " + (lineNumber + 1) + ": " + pex.getMessage());
				pex.printStackTrace();
			}
			catch (Exception ex)
			{
				System.out.println("  Exception while executing line " + (lineNumber + 1) + ": " + ex.getMessage());
				ex.printStackTrace();
			}
		}
		return null;
	}
	
	/**
	 * Executes a line.
	 * 
	 * @param line
	 *            the line
	 * @throws ParserException
	 *             the parser exception
	 */
	public void executeLine(String line) throws ParserException
	{
		if (line.equals("@breakpoint"))
		{
			System.out.println("--Debugbreak");
			return;
		}
		
		if (TextModHelper.isLineValid(line))
		{
			System.out.println();
			if (isMethod(line)) // Method invocation
			{
				Method method = getMethod(line);
				executeMethod(method);
			}
			else if (isVariable(line)) // Variables
			{
				Variable v = readVariable(line);
				this.variables.put(v.name, v);
				this.parser.setCodeBlock(this);
				System.out.println("  Variable \'" + v.name + "\' of type \'" + v.type.toString() + "\' added with value \'" + toString(v.value) + "\'.");
			}
			else
			{
				throw new ParserException("Invalid line '" + line + "'");
			}
		}
		else
			System.out.println(" Invalid line, skipping");
	}
	
	private String toString(Object value)
	{
		if (value == null)
			return "<null>";
		if (value.getClass().isArray())
			return Arrays.toString((Object[])value);
		return value.toString();
	}

	/**
	 * Gets a method from a line.
	 * 
	 * @param line
	 *            the line
	 * @return the method
	 * @throws ParserException
	 *             the parser exception
	 */
	public Method getMethod(String line) throws ParserException
	{
		PredefinedMethod method = (PredefinedMethod) readMethod(line);
		if (method.isValid())
			return method;
		else
			return getCustomMethod(method.name);
	}
	
	/**
	 * Gets the custom method.
	 * 
	 * @param name
	 *            the name
	 * @return the custom method
	 */
	public Method getCustomMethod(String name)
	{
		return getCodeBlockClass().getCustomMethod(name);
	}
	
	/**
	 * Reads a method from a line
	 * 
	 * @param line
	 *            the line
	 * @return the method (with name and parameters)
	 * @throws ParserException
	 *             the parser exception
	 */
	public Method readMethod(String line) throws ParserException
	{
		// Replaces the method identifier
		int parametersStart = line.indexOf(METHOD_PARAMETERS_START_CHAR);
		int parametersEnd = line.lastIndexOf(METHOD_PARAMETERS_END_CHAR);
		if (parametersStart == -1 || parametersEnd == -1)
			return null;
		
		String methodName = line.substring(0, parametersStart);
		String parameters = line.substring(parametersStart + 1, parametersEnd);
		String[] aparameters = TextModHelper.createParameterList(parameters, PARAMETER_SPLIT_CHAR.charAt(0));
		for (int m = 0; m < aparameters.length; m++)
		{
			aparameters[m] = aparameters[m].trim();
		}
		Object[] aparameters2 = parser.parse(aparameters);
		return new PredefinedMethod(methodName, aparameters2);
	}
	
	/**
	 * Executes a method.
	 * 
	 * @param method
	 *            the method
	 * @return the object
	 */
	public Object executeMethod(Method method)
	{
		return method.execute(this, method.parameters);
	}
	
	/**
	 * Gets the variable from a line.
	 * 
	 * @param line
	 *            the line
	 * @return the variable
	 * @throws ParserException
	 *             the parser exception
	 */
	public Variable readVariable(String line) throws ParserException
	{
		line = line.replace(";", "").trim();
		String[] split = TextModHelper.createParameterList(line, ' ');
		Variable var = null;
		if (isType(split[0])) // First part is a type declaration
		{
			Type type = Type.getTypeFromName(split[0]);
			String name = split[1];
			Object value = parser.parse(line.substring(line.indexOf("=") + 1).trim());
			var = new Variable(type, name, value);
		}
		else // First part is an existing variable name
		{
			Variable var1 = getVariable(split[0]);
			String operator = split[1];
			Object value = parser.parse(line.substring(line.indexOf(operator) + operator.length()).trim());
			var = operate(var1, operator, value);
		}
		return var;
	}
	
	public Variable getVariable(String name)
	{
		Variable v = variables.get(name);
		
		while (v == null && superCodeBlock != null)
			v = superCodeBlock.variables.get(name);
		
		return v;
	}
	
	/**
	 * Checks if the line is a variable.
	 * 
	 * @param par1
	 *            the par1
	 * @return true, if is variable
	 */
	public boolean isVariable(String par1)
	{
		String first = par1;
		if (par1.contains(" "))
			first = par1.substring(0, par1.indexOf(" "));
		if (variables.get(first) != null) // Already existing Variable
			return true;
		else if (isType(first))
			return true;
		return false;
	}
	
	/**
	 * Checks if the line is a method.
	 * 
	 * @param par1
	 *            the par1
	 * @return true, if is method
	 */
	public boolean isMethod(String par1)
	{
		par1 = par1.replace(";", "");
		if (par1.contains(METHOD_PARAMETERS_START_CHAR) && par1.endsWith(METHOD_PARAMETERS_END_CHAR))
		{
			String methodName = par1.substring(0, par1.indexOf(METHOD_PARAMETERS_START_CHAR));
			
			if (getCustomMethod(methodName) != null)
				return true;
			
			MethodExecuter executer = TextModHelper.getMethodExecuterFromName(methodName);
			return executer != null;
		}
		return false;
	}
	
	/**
	 * Checks if the string is a type.
	 * 
	 * @param par1
	 *            the par1
	 * @return true, if is type
	 */
	public boolean isType(String par1)
	{
		return Type.getTypeFromName(par1) != Type.VOID;
	}
	
	/**
	 * Uses a variable, an operator and a value to calculate a new value for the variable
	 * 
	 * @param var1
	 *            the var1
	 * @param operator
	 *            the operator
	 * @param value
	 *            the value
	 * @return the variable
	 */
	public Variable operate(Variable var1, String operator, Object value)
	{
		if (operator.equals("="))
			var1.value = value;
		else
		{
			Operator op = Operator.fromString(operator);
			if (op.canOperate(var1.type, Type.getTypeFromClass(value.getClass())))
				var1.value = op.operate(var1.value, value);
		}
		return var1;
	}
	
	/*
	 * (non-Javadoc)
	 * @see
	 * com.chaosdev.textmodloader.util.annotations.IAnnotable#getAnnotationType
	 * ()
	 */
	@Override
	public AnnotationType getAnnotationType()
	{
		return AnnotationType.NOTHING;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.blockComment ? 1231 : 1237);
		result = prime * result + ((this.lines == null) ? 0 : this.lines.hashCode());
		result = prime * result + ((this.parser == null) ? 0 : this.parser.hashCode());
		result = prime * result + ((this.superCodeBlock == null) ? 0 : this.superCodeBlock.hashCode());
		result = prime * result + ((this.variables == null) ? 0 : this.variables.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (!(obj instanceof CodeBlock))
		{
			return false;
		}
		CodeBlock that = (CodeBlock) obj;
		if (this.blockComment != that.blockComment)
		{
			return false;
		}
		if (this.lines == null)
		{
			if (that.lines != null)
			{
				return false;
			}
		}
		else if (!this.lines.equals(that.lines))
		{
			return false;
		}
		if (this.parser == null)
		{
			if (that.parser != null)
			{
				return false;
			}
		}
		else if (!this.parser.equals(that.parser))
		{
			return false;
		}
		if (this.superCodeBlock == null)
		{
			if (that.superCodeBlock != null)
			{
				return false;
			}
		}
		else if (!this.superCodeBlock.equals(that.superCodeBlock))
		{
			return false;
		}
		if (this.variables == null)
		{
			if (that.variables != null)
			{
				return false;
			}
		}
		else if (!this.variables.equals(that.variables))
		{
			return false;
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("CodeBlock [");
		if (this.parser != null)
		{
			builder.append("parser=");
			builder.append(this.parser);
		}
		builder.append("]");
		return builder.toString();
	}
}
