package com.chaosdev.textmodloader.util.codeblocktypes;

import java.util.LinkedList;
import java.util.List;

import com.chaosdev.textmodloader.methods.MethodExecuter;
import com.chaosdev.textmodloader.util.CodeBlock;
import com.chaosdev.textmodloader.util.TextModHelper;

public abstract class CodeBlockType
{
	public static List<CodeBlockType>	codeBlockTypes	= new LinkedList<CodeBlockType>();
	
	private String						initializer;
	private Class[]						parameters;
	
	public static CodeBlockType			IF				= new CodeBlockTypeIf("if");
	public static CodeBlockType			FOR				= new CodeBlockTypeFor("for");
	public static CodeBlockType			FOREACH			= new CodeBlockTypeForeach("for");
	public static CodeBlockType			WHILE			= new CodeBlockTypeWhile("while");
	
	public CodeBlockType(String initializer, Class... parameters)
	{
		this.initializer = initializer;
		this.parameters = parameters;
		codeBlockTypes.add(this);
	}
	
	public String getInitializer()
	{
		return initializer;
	}
	
	public Class[] getParameters()
	{
		return parameters;
	}
	
	public abstract void setup(CodeBlock codeblock, String line);
	
	public abstract Object execute(CodeBlock codeblock);
	
	public char getSplitChar()
	{
		return ',';
	}
	
	public boolean lineMatches(CodeBlock codeblock, String line)
	{
		int brace1Pos = line.indexOf("(");
		int brace2Pos = line.indexOf(")");
		
		if (brace1Pos != -1 && brace2Pos != -1)
		{
			String init = line.substring(0, brace1Pos);
			String par = line.substring(brace1Pos + 1, brace2Pos);
			String[] pars = TextModHelper.createParameterList(par, getSplitChar());
			Object[] pars2 = codeblock.parser.parse(pars);
			return matches(codeblock, init, pars2);
		}
		return false;
	}
	
	public boolean matches(CodeBlock codeblock, String init, Object... par)
	{
		return (initMatches(codeblock, init) && parameterMatches(codeblock, par));
	}
	
	public boolean initMatches(CodeBlock codeblock, String init)
	{
		return this.getInitializer().equals(init);
	}
	
	public boolean parameterMatches(CodeBlock codeblock, Object... par)
	{
		return MethodExecuter.matches(par, this.getParameters());
	}
	
	public static CodeBlockType getCodeBlockType(CodeBlock codeblock, String line)
	{
		for (CodeBlockType type : codeBlockTypes)
		{
			if (type.lineMatches(codeblock, line))
				return type;
		}
		return null;
	}
	
	public static CodeBlockType getCodeBlockType(CodeBlock codeblock, String init, Object... par)
	{
		for (CodeBlockType type : codeBlockTypes)
		{
			if (type.matches(codeblock, init, par))
				return type;
		}
		return null;
	}
	
	public boolean isBreakable()
	{
		return false;
	}
}
