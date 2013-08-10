package com.chaosdev.textmodloader.util.codeblock;

import com.chaosdev.textmodloader.util.ParserException;
import com.chaosdev.textmodloader.util.codeblocktypes.CodeBlockType;

public class SpecialCodeBlock extends CodeBlock
{
	public String executionLine;
	
	public SpecialCodeBlock(String executionLine, CodeBlock superBlock)
	{
		super(superBlock);
		this.executionLine = executionLine;
	}
	
	public CodeBlockType getCodeBlockType() throws ParserException
	{
		return CodeBlockType.getCodeBlockType(this, executionLine);
	}
	
	@Override
	public Object execute()
	{
		try
		{
			CodeBlockType cbt = getCodeBlockType();
			cbt.setup(this, executionLine);
			return cbt.execute(new CodeBlock(this.superCodeBlock, this.lines));
		}
		catch (ParserException ex)
		{
			return null;
		}
	}
}
