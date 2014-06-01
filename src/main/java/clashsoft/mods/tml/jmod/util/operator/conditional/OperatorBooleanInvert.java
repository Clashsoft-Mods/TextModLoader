package clashsoft.mods.tml.jmod.util.operator.conditional;

public class OperatorBooleanInvert extends OperatorBoolean
{
	public OperatorBooleanInvert(String operator)
	{
		super(operator);
	}

	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		return !((Boolean)par2);
	}

	/* (non-Javadoc)
	 * @see com.chaosdev.textmodloader.util.operator.Operator#isPrefixOperator()
	 */
	@Override
	public boolean isPrefixOperator()
	{
		return true;
	}
}
