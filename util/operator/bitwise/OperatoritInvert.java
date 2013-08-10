package com.chaosdev.textmodloader.util.operator.bitwise;

public class OperatoritInvert extends OperatorBitwise
{
	
	public OperatoritInvert(String operator)
	{
		super(operator);
	}

	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		if (par1 instanceof Byte && par2 instanceof Number)
			return ~((Number)par1).intValue();
		return par1;
	}
	
}