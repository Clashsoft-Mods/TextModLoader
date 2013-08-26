package com.chaosdev.textmodloader.util.operator.bitwise;

import com.chaosdev.textmodloader.util.operator.Operator;
import com.chaosdev.textmodloader.util.types.Type;

public abstract class OperatorBitwise extends Operator
{
	public OperatorBitwise(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return (Type.isInteger(t1) || isPrefixOperator()) && (Type.isInteger(t2) || isPostfixOperator());
	}
	
}
