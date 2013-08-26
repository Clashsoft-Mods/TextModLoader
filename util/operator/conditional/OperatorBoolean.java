package com.chaosdev.textmodloader.util.operator.conditional;

import com.chaosdev.textmodloader.util.operator.Operator;
import com.chaosdev.textmodloader.util.types.Type;

public abstract class OperatorBoolean extends Operator
{
	public OperatorBoolean(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return (Type.isBoolean(t1) || isPrefixOperator()) && (Type.isBoolean(t2) || isPostfixOperator());
	}
}
