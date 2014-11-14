package clashsoft.mods.tml.jmod.util.operator.conditional;

public class OperatorConditionalOr extends OperatorBoolean
{
	public OperatorConditionalOr(String operator)
	{
		super(operator);
	}
	
	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		if (par1 instanceof Boolean && par2 instanceof Boolean)
		{
			return (Boolean) par1 || (Boolean) par2;
		}
		return par1;
	}
	
}
