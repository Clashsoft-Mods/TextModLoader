package clashsoft.mods.tml.util.operator.math.unary;

import clashsoft.mods.tml.util.operator.math.OperatorMath;

public class OperatorUnaryMinus extends OperatorMath
{	
	public OperatorUnaryMinus(String operator)
	{
		super(operator);
	}

	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		return par1 == null ? ((Number)par2).doubleValue() - 1 : ((Number)par1).doubleValue() - 1;
	}

	/* (non-Javadoc)
	 * @see com.chaosdev.textmodloader.util.operator.Operator#isPrefixOperator()
	 */
	@Override
	public boolean isPrefixOperator()
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see com.chaosdev.textmodloader.util.operator.Operator#isPostfixOperator()
	 */
	@Override
	public boolean isPostfixOperator()
	{
		return true;
	}
}
