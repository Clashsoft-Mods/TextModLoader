package com.chaosdev.textmodloader.util.codeblock;

import java.util.*;
import java.util.regex.Pattern;

import com.chaosdev.textmodloader.TextModConstants;
import com.chaosdev.textmodloader.methods.MethodExecutor;
import com.chaosdev.textmodloader.util.CodeLine;
import com.chaosdev.textmodloader.util.Parser;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.annotations.Annotation.AnnotationType;
import com.chaosdev.textmodloader.util.annotations.IAnnotable;
import com.chaosdev.textmodloader.util.codeblocktypes.CodeBlockType;
import com.chaosdev.textmodloader.util.exceptions.SyntaxException;
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
		this(superBlock, new ArrayList<String>());
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
	
	private boolean isClassStart(CodeLine codeline)
	{
		return codeline.line.startsWith(CLASS_DECLARATION + " ");
	}
	
	/**
	 * Checks if the line can start a new code block
	 * 
	 * @param codeline
	 *            the line
	 * @return true, if is block start
	 * @throws SyntaxException
	 *             the parser exception
	 */
	public boolean isBlockStart(CodeLine codeline) throws SyntaxException
	{
		return !codeline.line.isEmpty() && !codeline.line.equals("\n") && !isComment(codeline) && !isMethod(codeline) && !isVariable(codeline) && CodeBlockType.getCodeBlockType(this, codeline) != null;
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
	 * @param codeline
	 *            the line
	 * @return true, if is comment
	 */
	public boolean isComment(CodeLine codeline)
	{
		return blockComment || codeline.line.startsWith("//");
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
		
		CodeBlock cb = null;
		for (int lineNumber = 0; lineNumber < lines.size(); lineNumber++)
		{
			String line = lines.get(lineNumber);
			CodeLine codeline = new CodeLine(lineNumber, line);
			
			if (line.startsWith("/*"))
				blockComment = true;
			if (line.endsWith("*/"))
				blockComment = false;
			if (blockComment || line.isEmpty())
				continue;
			
			System.out.println("  Reading line " + (lineNumber + 1) + ": " + line);
			
			try
			{
				if (cb instanceof HeaderCodeBlock && ((HeaderCodeBlock) cb).getCodeBlockType().isBreakable() && line.startsWith("break"))
					throw new Break();
				
				if (line.startsWith("return "))
					return parser.parse(codeline, line.replaceFirst(Pattern.quote("return "), ""));
				
				if (cb != null && !isBlockStart(codeline) && !line.startsWith("{") && !isBlockEnd(line))
					cb.lines.add(line);
				
				if (isClassStart(codeline))
					cb = new ClassCodeBlock(codeline, this, new ArrayList<String>());
				
				else if (isBlockStart(codeline))
					cb = new HeaderCodeBlock(codeline, this);
				
				else if (line.startsWith("{") && cb == null)
					cb = new CodeBlock(this);
				
				else if (isBlockEnd(line))
				{
					cb.lines.add(line.replace("}", ""));
					cb.execute();
					cb = null;
				}
				
				if (cb == null && !isBlockEnd(line))
					this.executeLine(codeline);
			}
			catch (SyntaxException pex)
			{
				System.err.println("  Syntax error while executing line " + (lineNumber + 1) + ": ");
				System.err.println(pex.getMessage());
			}
			catch (Exception ex)
			{
				System.err.println("  Exception while executing line " + (lineNumber + 1) + ": ");
				System.err.println(ex.getMessage());
			}
		}
		return null;
	}

	/**
	 * Executes a line.
	 * 
	 * @param codeline
	 *            the line
	 * @throws SyntaxException
	 *             the parser exception
	 */
	public void executeLine(CodeLine codeline) throws SyntaxException
	{
		if (codeline.line.isEmpty())
			return;
		else if (blockComment || isComment(codeline))
			return;
		else if (codeline.line.equals("@breakpoint"))
		{
			System.out.println("--Debugbreak");
		}
		else if (isMethod(codeline)) // Method invocation
		{
			Method method = getMethod(codeline);
			executeMethod(method);
		}
		else if (isVariable(codeline)) // Variables
		{
			Variable v = readVariable(codeline);
			this.variables.put(v.name, v);
			this.parser.setCodeBlock(this);
			System.out.println("  Variable \'" + v.name + "\' of type \'" + v.type.toString() + "\' added with value \'" + toString(v.value) + "\'.");
		}
		else
		{
			throw new SyntaxException("Invalid line '" + codeline.line + "'", codeline, 0, 0);
		}
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
	 * @param codeline
	 *            the line
	 * @return the method
	 * @throws SyntaxException
	 *             the parser exception
	 */
	public Method getMethod(CodeLine codeline) throws SyntaxException
	{
		Method read = readMethod(codeline);
		Method method = read;
		if (!method.isValid())
			method = getCustomMethod(method.name);
		if (method == null)
			throw new SyntaxException("Unknown method name '" + read.name + "'", codeline, read.name);
		return method;
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
	 * @throws SyntaxException
	 *             the parser exception
	 */
	public Method readMethod(CodeLine codeline) throws SyntaxException
	{
		// Replaces the method identifier
		int parametersStart = codeline.line.indexOf(METHOD_PARAMETERS_START_CHAR);
		int parametersEnd = codeline.line.lastIndexOf(METHOD_PARAMETERS_END_CHAR);
		if (parametersStart == -1 || parametersEnd == -1)
			throw new SyntaxException("Invalid method signature: Missing parameter list", codeline, codeline.line.length() - 1, 1);
		
		String methodName = codeline.line.substring(0, parametersStart);
		String parameters = codeline.line.substring(parametersStart + 1, parametersEnd);
		String[] aparameters = TextModHelper.createParameterList(parameters, PARAMETER_SPLIT_CHAR.charAt(0));
		Object[] aparameters2 = parser.parse(codeline, aparameters);
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
	 * @throws SyntaxException
	 *             the parser exception
	 */
	public Variable readVariable(CodeLine codeline) throws SyntaxException
	{
		String line = codeline.line.substring(0, codeline.line.indexOf(';'));
		String[] split = TextModHelper.createParameterList(line, ' ');
		Variable var = null;
		if (isType(split[0])) // First part is a type declaration
		{
			Type type = Type.getTypeFromName(split[0]);
			String name = split[1];
			Object value = parser.parse(codeline, line.substring(line.indexOf("=") + 1));
			var = new Variable(type, name, value);
		}
		else // First part is an existing variable name
		{
			Variable var1 = getVariable(codeline, split[0]);
			String operator = split[1];
			Object value = parser.parse(codeline, line.substring(line.indexOf(operator) + operator.length()));
			var = operate(var1, operator, value);
		}
		return var;
	}
	
	public Variable getVariable(CodeLine codeline, String name) throws SyntaxException
	{
		Variable v = variables.get(name);
		
		if (v == null && superCodeBlock != null)
			v = superCodeBlock.getVariable(codeline, name);
		
		if (v == null)
			throw new SyntaxException("Unknown variable name '" + name + "'", codeline, name);
		return v;
	}
	
	/**
	 * Checks if the line is a variable.
	 * 
	 * @param par1
	 *            the par1
	 * @return true, if is variable
	 */
	public boolean isVariable(CodeLine codeline)
	{
		String line = codeline.line;
		if (line.contains(" "))
			line = line.substring(0, line.indexOf(" "));
		if (variables.get(line) != null) // Already existing Variable
			return true;
		else if (isType(line))
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
	public boolean isMethod(CodeLine codeline)
	{
		String line = codeline.line.replace(";", "");
		if (line.contains(METHOD_PARAMETERS_START_CHAR) && line.endsWith(METHOD_PARAMETERS_END_CHAR))
		{
			String methodName = line.substring(0, line.indexOf(METHOD_PARAMETERS_START_CHAR));
			
			if (getCustomMethod(methodName) != null)
				return true;
			
			MethodExecutor executor = TextModHelper.getMethodExecutorFromName(methodName);
			return executor != null;
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
	
	public void setSuperCodeBlock(CodeBlock superCodeBlock)
	{
		this.superCodeBlock = superCodeBlock;
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
