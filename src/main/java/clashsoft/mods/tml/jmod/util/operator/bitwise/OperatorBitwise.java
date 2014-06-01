package clashsoft.mods.tml.jmod.util.operator.bitwise;

import clashsoft.mods.tml.jmod.util.operator.Operator;
import clashsoft.mods.tml.jmod.util.types.Type;

public abstract class OperatorBitwise extends Operator
{
	public OperatorBitwise(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return (Type.isInteger(t1) || isPrefixOperator()) && (Type.isInteger(t2) || isPostfixOperator());
	}
	
}
