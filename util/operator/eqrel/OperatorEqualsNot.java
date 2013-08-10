package com.chaosdev.textmodloader.util.operator.eqrel;

import com.chaosdev.textmodloader.util.operator.Operator;
import com.chaosdev.textmodloader.util.types.Type;

public class OperatorEqualsNot extends Operator
{
	public OperatorEqualsNot(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return true;
	}
	
	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		return par1 != par2;
	}
	
}
