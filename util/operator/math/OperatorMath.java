package clashsoft.mods.tml.util.operator.math;

import clashsoft.mods.tml.util.operator.Operator;
import clashsoft.mods.tml.util.types.Type;

public abstract class OperatorMath extends Operator
{
	public OperatorMath(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return (Type.isNumeric(t1) || isPrefixOperator()) && (Type.isNumeric(t2) || isPostfixOperator());
	}
}
