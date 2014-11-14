package clashsoft.mods.tml.jmod.util.operator.math;

import clashsoft.mods.tml.jmod.util.operator.Operator;
import clashsoft.mods.tml.jmod.util.types.Type;

public abstract class OperatorMath extends Operator
{
	public OperatorMath(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return (Type.isNumeric(t1) || this.isPrefixOperator()) && (Type.isNumeric(t2) || this.isPostfixOperator());
	}
}
