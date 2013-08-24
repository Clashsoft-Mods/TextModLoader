package com.chaosdev.textmodloader.util.operator.conditional;

import com.chaosdev.textmodloader.util.operator.Operator;
import com.chaosdev.textmodloader.util.types.Type;

public class OperatorInstanceof extends Operator
{
	public OperatorInstanceof(String operator)
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
		if (par2 instanceof Type)
			return ((Type) par2).type.isInstance(par1);
		return false;
	}
}
