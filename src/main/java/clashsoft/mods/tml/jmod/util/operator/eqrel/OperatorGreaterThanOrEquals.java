package clashsoft.mods.tml.jmod.util.operator.eqrel;

public class OperatorGreaterThanOrEquals extends OperatorGreaterThan
{
	public OperatorGreaterThanOrEquals(String operator)
	{
		super(operator);
	}
	
	@Override
	public <T, U> Boolean operate(T par1, U par2)
	{
		return super.<T, U>operate(par1, par2) || par1 == par2;
	}
}
