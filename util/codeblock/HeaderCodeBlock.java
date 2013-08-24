package com.chaosdev.textmodloader.util.codeblock;

import com.chaosdev.textmodloader.util.codeblocktypes.CodeBlockType;
import com.chaosdev.textmodloader.util.exceptions.ParserException;

public class HeaderCodeBlock extends CodeBlock
{
	public String	executionLine;
	
	public HeaderCodeBlock(String executionLine, CodeBlock superBlock)
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
		CodeBlockType cbt;
		try
		{
			cbt = getCodeBlockType();
			cbt.setup(this, executionLine);
			return cbt.execute(new CodeBlock(this.superCodeBlock, this.lines));
		}
		catch (ParserException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
