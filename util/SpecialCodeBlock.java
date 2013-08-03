package com.chaosdev.textmodloader.util;

import com.chaosdev.textmodloader.util.codeblocktypes.CodeBlockType;

public class SpecialCodeBlock extends CodeBlock
{
	public String executionLine;
	
	public SpecialCodeBlock(String executionLine, CodeBlock superBlock)
	{
		super(superBlock);
		this.executionLine = executionLine;
	}
	
	public CodeBlockType getCodeBlockType()
	{
		return CodeBlockType.getCodeBlockType(this, executionLine);
	}

	@Override
	public void execute()
	{
		CodeBlockType cbt = getCodeBlockType();
		cbt.setup(this, executionLine);
		cbt.execute(new CodeBlock(this.superCodeBlock, this.lines));
	}
}
