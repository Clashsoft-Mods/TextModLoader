package clashsoft.mods.tml.jmod.util.operator.conditional;

import clashsoft.mods.tml.jmod.util.operator.Operator;
import clashsoft.mods.tml.jmod.util.types.Type;

public abstract class OperatorBoolean extends Operator
{
	public OperatorBoolean(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return (Type.isBoolean(t1) || this.isPrefixOperator()) && (Type.isBoolean(t2) || this.isPostfixOperator());
	}
}
