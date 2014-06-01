package clashsoft.mods.tml.jmod.util.operator.math;

public class OperatorMinus extends OperatorMath
{

	public OperatorMinus(String operator)
	{
		super(operator);
	}

	@Override
	public <T, U> Double operate(T par1, U par2)
	{
		if (par1 instanceof Number && par2 instanceof Number)
			return ((Number)par1).doubleValue() - ((Number)par2).doubleValue();
		return Double.NaN;
	}
	
}
