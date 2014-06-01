package clashsoft.mods.tml.jmod.util.operator.bitwise;

public class OperatorBitInvert extends OperatorBitwise
{
	public OperatorBitInvert(String operator)
	{
		super(operator);
	}

	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		if (par2 instanceof Number)
			return ~((Number)par2).intValue();
		return par2;
	}
	
	@Override
	public boolean isPrefixOperator()
	{
		return true;
	}
}
