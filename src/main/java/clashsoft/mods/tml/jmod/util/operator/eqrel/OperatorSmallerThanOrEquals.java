package clashsoft.mods.tml.jmod.util.operator.eqrel;

public class OperatorSmallerThanOrEquals extends OperatorSmallerThan
{
	public OperatorSmallerThanOrEquals(String operator)
	{
		super(operator);
	}
	
	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		return (Boolean)super.<T, U>operate(par1, par2) || par1 == par2;
	}
}
