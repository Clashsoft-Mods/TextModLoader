package com.chaosdev.textmodloader.util.operator.bitwise;

public class OperatorBitShiftLeft extends OperatorBitwise
{
	public OperatorBitShiftLeft(String operator)
	{
		super(operator);
	}

	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		if (par1 instanceof Number && par2 instanceof Number)
			return ((Number)par1).intValue() << ((Number)par2).intValue();
		return par1;
	}
	
}
