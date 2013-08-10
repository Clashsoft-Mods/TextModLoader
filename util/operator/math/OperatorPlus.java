package com.chaosdev.textmodloader.util.operator.math;

import com.chaosdev.textmodloader.util.types.Type;

public class OperatorPlus extends OperatorMath
{
	public OperatorPlus(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return super.canOperate(t1, t2) || (t1 == Type.STRING || t2 == Type.STRING);
	}
	
	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		if (par1 instanceof String || par2 instanceof String)
			return par1.toString() + par2.toString();
		else if (par1 instanceof Number && par2 instanceof Number)
			return ((Number)par1).doubleValue() + ((Number)par2).doubleValue();
		return par1;
	}
	
}
