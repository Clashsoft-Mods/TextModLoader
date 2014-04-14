package clashsoft.mods.tml.util.operator.conditional;

import clashsoft.mods.tml.util.operator.Operator;
import clashsoft.mods.tml.util.types.Type;

public abstract class OperatorBoolean extends Operator
{
	public OperatorBoolean(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return (Type.isBoolean(t1) || isPrefixOperator()) && (Type.isBoolean(t2) || isPostfixOperator());
	}
}
