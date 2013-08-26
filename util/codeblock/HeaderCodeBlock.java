package com.chaosdev.textmodloader.util.codeblock;

import com.chaosdev.textmodloader.util.CodeLine;
import com.chaosdev.textmodloader.util.codeblocktypes.CodeBlockType;
import com.chaosdev.textmodloader.util.exceptions.SyntaxException;

public class HeaderCodeBlock extends CodeBlock
{
	public CodeLine	executionLine;
	
	public HeaderCodeBlock(CodeLine executionLine, CodeBlock superBlock)
	{
		super(superBlock);
		this.executionLine = executionLine;
	}
	
	public CodeBlockType getCodeBlockType() throws SyntaxException
	{
		return CodeBlockType.getCodeBlockType(this, executionLine.line);
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
		catch (SyntaxException ex)
		{
			ex.printStackTrace();
			return null;
		}
	}
}
