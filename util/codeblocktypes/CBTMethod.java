package com.chaosdev.textmodloader.util.codeblocktypes;

import com.chaosdev.textmodloader.util.TextModConstants;
import com.chaosdev.textmodloader.util.TextModHelper;
import com.chaosdev.textmodloader.util.Variable;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;
import com.chaosdev.textmodloader.util.method.CustomMethod;
import com.chaosdev.textmodloader.util.types.Type;

public class CBTMethod extends CodeBlockType
{	
	public CBTMethod()
	{
		super("");
	}

	@Override
	public void setup(CodeBlock codeblock, String line)
	{
		codeblock.getCodeBlockClass().registerMethod(readMethodSigature(codeblock, line));
	}
	
	public CustomMethod readMethodSigature(CodeBlock codeblock, String line)
	{
		int h = line.indexOf(" ");
		int i = line.indexOf(TextModConstants.METHOD_PARAMETERS_START_CHAR);
		int j = line.lastIndexOf(TextModConstants.METHOD_PARAMETERS_END_CHAR);
		if (i == -1 || j == -1)
			return null;
		Type type = Type.getTypeFromName(line.substring(0, h).trim());
		String methodName = line.substring(h + 1, i).trim();
		String parameters = line.substring(i + 1, j).trim();
		String[] aparameters = TextModHelper.createParameterList(parameters, TextModConstants.PARAMETER_SPLIT_CHAR.charAt(0));
		
		Variable[] vars = new Variable[aparameters.length];
		for (int m = 0; m < aparameters.length; m++)
		{
			aparameters[m] = aparameters[m].trim();
			String[] split = aparameters[m].split(" ");
			vars[m] = new Variable(Type.getTypeFromName(split[0]), split[1], null);
		}
		return new CustomMethod(methodName, new Object[] {}, vars, codeblock);
	}

	@Override
	public Object execute(CodeBlock codeblock)
	{
		return null;
	}

	@Override
	public boolean initMatches(CodeBlock codeblock, String init)
	{
		return init.indexOf(" ") != -1 && Type.getTypeFromName(init.substring(0, init.indexOf(" ")).trim()) != null;
	}

	@Override
	public boolean parameterMatches(CodeBlock codeblock, Object... par)
	{
		return true;
	}
}
