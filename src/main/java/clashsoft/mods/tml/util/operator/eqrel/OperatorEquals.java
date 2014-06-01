package clashsoft.mods.tml.util.operator.eqrel;

import clashsoft.mods.tml.util.operator.Operator;
import clashsoft.mods.tml.util.types.Type;

public class OperatorEquals extends Operator
{
	public OperatorEquals(String operator)
	{
		super(operator);
	}

	@Override
	public boolean canOperate(Type t1, Type t2)
	{
		return true;
	}
	
	@Override
	public <T, U> Object operate(T par1, U par2)
	{
		return par1 == par2;
	}
}
