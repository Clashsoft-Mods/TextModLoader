package clashsoft.mods.tml.jmod.util.codeblocktypes;

import clashsoft.mods.tml.TextModConstants;
import clashsoft.mods.tml.jmod.util.CodeLine;
import clashsoft.mods.tml.jmod.util.TextModHelper;
import clashsoft.mods.tml.jmod.util.Variable;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;
import clashsoft.mods.tml.jmod.util.method.CustomMethod;
import clashsoft.mods.tml.jmod.util.types.Type;

public class CBTMethod extends CodeBlockType
{
	public CBTMethod()
	{
		super("");
	}
	
	@Override
	public void setup(CodeBlock codeblock, CodeLine line)
	{
		codeblock.getCodeBlockClass().registerMethod(this.readMethodSignature(codeblock, line));
	}
	
	public CustomMethod readMethodSignature(CodeBlock codeblock, CodeLine codeline)
	{
		String line = codeline.line;
		int h = line.indexOf(" ");
		int i = line.indexOf(TextModConstants.METHOD_PARAMETERS_START_CHAR);
		int j = line.lastIndexOf(TextModConstants.METHOD_PARAMETERS_END_CHAR);
		if (i == -1 || j == -1)
		{
			return null;
		}
		Type type = Type.getTypeFromName(line.substring(0, h).trim());
		String methodName = line.substring(h + 1, i).trim();
		String parameters = line.substring(i + 1, j).trim();
		String[] aparameters = TextModHelper.createParameterList(parameters, TextModConstants.PARAMETER_SPLIT_CHAR.charAt(0));
		
		Variable[] vars = new Variable[aparameters.length];
		for (int m = 0; m < aparameters.length; m++)
		{
			aparameters[m] = aparameters[m].trim();
			String[] split = aparameters[m].split(" ");
			if (split.length >= 2)
			{
				vars[m] = new Variable(Type.getTypeFromName(split[0]), split[1], null);
			}
		}
		return new CustomMethod(methodName, new Object[] {}, vars, new CodeBlock(codeblock.superCodeBlock, codeblock.lines));
	}
	
	@Override
	public Object execute(CodeBlock codeblock)
	{
		return null;
	}
	
	@Override
	public boolean initMatches(CodeBlock codeblock, String init)
	{
		return init.split(" ").length == 2 && Type.getTypeFromName(init.substring(0, init.indexOf(" ")).trim()) != null;
	}
	
	@Override
	public boolean parameterMatches(CodeBlock codeblock, Object... par)
	{
		return true;
	}
}
