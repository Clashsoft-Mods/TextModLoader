package com.chaosdev.textmodloader.util.operator.eqrel;

public class OperatorGreaterThanOrEquals extends OperatorGreaterThan
{
	public OperatorGreaterThanOrEquals(String operator)
	{
		super(operator);
	}
	
	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		return (Boolean)(super.<T, U>operate(par1, par2)) || par1 == par2;
	}
}
