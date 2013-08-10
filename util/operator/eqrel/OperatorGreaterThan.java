package com.chaosdev.textmodloader.util.operator.eqrel;

import com.chaosdev.textmodloader.util.operator.math.OperatorMath;

public class OperatorGreaterThan extends OperatorMath
{
	public OperatorGreaterThan(String operator)
	{
		super(operator);
	}
	
	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		if (par1 instanceof Number && par2 instanceof Number)
			return ((Number)par1).doubleValue() > ((Number)par2).doubleValue();
		return false;
	}
}
