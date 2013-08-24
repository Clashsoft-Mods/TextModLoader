package com.chaosdev.textmodloader.util.operator.math;

import com.chaosdev.textmodloader.util.operator.Operator;
import com.chaosdev.textmodloader.util.types.Type;

public abstract class OperatorMath extends Operator
{
	public OperatorMath(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return Type.isNumeric(t1) && Type.isNumeric(t2);
	}
}
