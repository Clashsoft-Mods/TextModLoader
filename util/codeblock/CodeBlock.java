package com.chaosdev.textmodloader.util.codeblock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.chaosdev.textmodloader.methods.MethodExecuter;
import com.chaosdev.textmodloader.util.*;
import com.chaosdev.textmodloader.util.annotations.Annotation;
import com.chaosdev.textmodloader.util.annotations.Annotation.AnnotationType;
import com.chaosdev.textmodloader.util.annotations.IAnnotable;
import com.chaosdev.textmodloader.util.codeblocktypes.CodeBlockType;
import com.chaosdev.textmodloader.util.method.Method;
import com.chaosdev.textmodloader.util.method.PredefinedMethod;
import com.chaosdev.textmodloader.util.operator.Operator;
import com.chaosdev.textmodloader.util.types.Type;

public class CodeBlock implements IAnnotable
{
	public boolean					blockComment	= false;
	
	public CodeBlock				superCodeBlock;
	public List<CodeBlock>			codeBlocks;
	public List<String>				lines;
	public Map<String, Variable>	variables		= new HashMap<String, Variable>();
	
	public Parser					parser;
	
	public CodeBlock(CodeBlock superBlock)
	{
		this(superBlock, new LinkedList<String>());
	}
	
	public CodeBlock(CodeBlock superBlock, List<String> lines)
	{
		this.superCodeBlock = superBlock;
		this.lines = lines;
		this.codeBlocks = new LinkedList<CodeBlock>();
		this.parser = new Parser(this);
	}
	
	public ClassCodeBlock getCodeBlockClass()
	{
		return superCodeBlock != null ? superCodeBlock.getCodeBlockClass() : null;
	}
	
	public Map<String, Variable> getVariables()
	{
		Map<String, Variable> var = new HashMap<String, Variable>();
		var.putAll(variables);
		if (superCodeBlock != null)
			var.putAll(superCodeBlock.getVariables());
		return var;
	}
	
	public boolean isBlockStart(String line) throws ParserException
	{
		return !line.isEmpty() && !line.equals("\n") && !isComment(line) && !isMethod(line) && !isVariable(line) && CodeBlockType.getCodeBlockType(this, line) != null;
	}
	
	public boolean isBlockEnd(String line)
	{
		return line.endsWith("}");
	}
	
	public boolean isComment(String line)
	{
		return blockComment || line.startsWith("//");
	}
	
	public Object execute()
	{
		Annotation nextAnnotation;
		CodeBlock cb = null;
		for (int i = 0; i < lines.size(); i++)
		{
			String line = lines.get(i).trim();
			
			if (line.trim().startsWith("/*"))
				blockComment = true;
			if (line.trim().endsWith("*/"))
				blockComment = false;
			
			try
			{	
				if (cb instanceof SpecialCodeBlock && ((SpecialCodeBlock) cb).getCodeBlockType().isBreakable() && line.equals("break;"))
					cb = null;
				
				if (line.startsWith("return "))
					return parser.directParse(line.replaceFirst(Pattern.quote("return "), ""));
				
				if (cb != null && !isBlockStart(line) && !line.startsWith("{") && !isBlockEnd(line))
					cb.lines.add(line);
				
				if (isBlockStart(line))
					cb = new SpecialCodeBlock(line, this);
				
				else if (line.startsWith("{") && cb == null)
					cb = new CodeBlock(this);
				
				else if (isBlockEnd(line))
				{
					cb.lines.add(line.replace("}", "").trim());
					cb.execute();
					codeBlocks.add(cb);
					cb = null;
				}
				
				if (cb == null)
					this.executeLine(line);
			}
			catch (ParserException pex)
			{
				System.out.println("  Syntax error while executing line " + i + ": ");
				throw new RuntimeException(pex);
			}
			catch (Exception ex)
			{
				System.out.println("  Exception while executing line " + i + ": ");
				throw new RuntimeException(ex);
			}
		}
		return null;
	}
	
	public void executeLine(String line) throws ParserException
	{
		if (TextModHelper.isLineValid(line))
		{
			System.out.println("  Reading line: " + line);
			if (isMethod(line)) // Method invocation
			{
				Method method = getMethod(line);
				executeMethod(method);
			}
			else if (isVariable(line)) // Variables
			{
				Variable v = getVariable(line);
				this.variables.put(v.name, v);
				this.parser.update(this);
				System.out.println("  Variable \'" + v.name + "\' of type \'" + v.type.toString() + "\' added with value \'" + v.value.toString() + "\'.");
			}
			else
			{
				throw new ParserException("Invalid line '" + line + "'");
			}
		}
	}
	
	public Method getMethod(String line) throws ParserException
	{
		PredefinedMethod method = (PredefinedMethod) readMethod(line);
		if (method.isValid())
			return method;
		else
			return getCustomMethod(method.name);
	}
	
	public Method getCustomMethod(String name)
	{
		return getCodeBlockClass().getCustomMethod(name);
	}
	
	public Method readMethod(String line) throws ParserException
	{
		// Replaces the method identifier
		line = line.replaceFirst("[>]", "").trim();
		int i = line.indexOf(TextModConstants.METHOD_PARAMETERS_START_CHAR);
		int j = line.lastIndexOf(TextModConstants.METHOD_PARAMETERS_END_CHAR);
		if (i == -1 || j == -1)
			return null;
		String methodName = line.substring(0, i);
		String parameters = line.substring(i + 1, j);
		String[] aparameters = TextModHelper.createParameterList(parameters, TextModConstants.PARAMETER_SPLIT_CHAR.charAt(0));
		for (int m = 0; m < aparameters.length; m++)
		{
			aparameters[m] = aparameters[m].trim();
		}
		Object[] aparameters2 = parser.parse(aparameters);
		return new PredefinedMethod(methodName, aparameters2);
	}
	
	public Object executeMethod(Method method)
	{
		return method.execute(this, method.parameters);
	}
	
	public Variable getVariable(String line) throws ParserException
	{
		String[] split = TextModHelper.createParameterList(line.replace(";", ""), ' ');
		Variable var = null;
		if (isType(split[0])) // First part is a type declaration
		{
			Type type = Type.getTypeFromName(split[0]);
			String name = split[1];
			Object value = parser.directParse(split[3]);
			var = new Variable(type, name, value);
		}
		else // First part is an existing variable name
		{
			Variable var1 = variables.get(split[0]);
			String operator = split[1];
			Object value = parser.directParse(split[2]);
			var = operate(var1, operator, value);
		}
		return var;
	}
	
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
	
	public boolean isMethod(String par1)
	{
		par1 = par1.replace(";", "");
		if (par1.contains(TextModConstants.METHOD_PARAMETERS_START_CHAR) && par1.endsWith(TextModConstants.METHOD_PARAMETERS_END_CHAR))
		{
			MethodExecuter executer = TextModHelper.getMethodExecuterFromName(par1.substring(0, par1.indexOf(TextModConstants.METHOD_PARAMETERS_START_CHAR)));
			return executer != null;
		}
		return false;
	}
	
	public boolean isType(String par1)
	{
		return Type.getTypeFromName(par1) != null;
	}
	
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

	@Override
	public AnnotationType getAnnotationType()
	{
		return AnnotationType.NOTHING;
	}
}
