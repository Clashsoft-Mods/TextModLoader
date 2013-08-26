package com.chaosdev.textmodloader.util.operator;

import java.util.HashMap;

import com.chaosdev.textmodloader.util.operator.bitwise.*;
import com.chaosdev.textmodloader.util.operator.conditional.OperatorBooleanInvert;
import com.chaosdev.textmodloader.util.operator.conditional.OperatorConditionalAnd;
import com.chaosdev.textmodloader.util.operator.conditional.OperatorConditionalOr;
import com.chaosdev.textmodloader.util.operator.conditional.OperatorInstanceof;
import com.chaosdev.textmodloader.util.operator.eqrel.*;
import com.chaosdev.textmodloader.util.operator.math.*;
import com.chaosdev.textmodloader.util.types.Type;

public abstract class Operator
{
	/** A map containing all operators */
	public static HashMap<String, Operator>	operators					= new HashMap<String, Operator>();
	
	/* ------------------------------------ Arithmetric operators ------------------------------------ */
	
	public static Operator					PLUS						= new OperatorPlus("+");
	public static Operator					MINUS						= new OperatorMinus("-");
	public static Operator					MULTIPLY					= new OperatorMultiply("*");
	public static Operator					DIVIDE						= new OperatorDivide("/");
	public static Operator					REMAINDER					= new OperatorRemainder("%");
	
	/* ------------------------------------ Bitwise operators ------------------------------------ */
	
	public static Operator					BIT_AND						= new OperatorBitAnd("&");
	public static Operator					BIT_OR						= new OperatorBitOr("|");
	public static Operator					BIT_XOR						= new OperatorBitXor("^");
	public static Operator					BIT_INVERT					= new OperatorBitInvert("~");
	public static Operator					BIT_SHIFT_LEFT				= new OperatorBitShiftLeft("<<");
	public static Operator					BIT_SHIFT_RIGHT				= new OperatorBitShiftRight(">>");
	public static Operator					BIT_SHIFT_RIGHT_UNSIGNED	= new OperatorBitShiftRightUnsigned(">>>");
	
	/* ------------------------------------ Conditional and boolean operators ------------------------------------ */
	
	public static Operator					BOOLEAN_INVERT				= new OperatorBooleanInvert("!");
	public static Operator					CONDITIONAL_AND				= new OperatorConditionalAnd("&&");
	public static Operator					CONDITIONAL_OR				= new OperatorConditionalOr("||");
	
	/* ------------------------------------ Equality and Relational operators ------------------------------------ */
	
	public static Operator					EQUALS						= new OperatorEquals("==");
	public static Operator					EQUALS_NOT					= new OperatorEqualsNot("!=");
	public static Operator					GREATER_THAN				= new OperatorGreaterThan(">");
	public static Operator					SMALLER_THAN				= new OperatorSmallerThan("<");
	public static Operator					GREATER_THAN_OR_EQUALS		= new OperatorGreaterThanOrEquals(">=");
	public static Operator					SMALLER_THAN_OR_EQUALS		= new OperatorSmallerThanOrEquals("<=");
	
	/* ------------------------------------ Type Comparison (instanceof) ------------------------------------- */
	
	public static Operator					INSTANCEOF					= new OperatorInstanceof("instanceof");
	
	/* ------------------------------------ --------------------------- -------------------------------------- */ 
	
	public String							operator;
	
	public Operator(String operator)
	{
		this.operator = operator;
		operators.put(operator, this);
	}
	
	public static Operator fromString(String op)
	{
		return operators.get(op);
	}
	
	public static Operator fromStart(String start)
	{
		for (Operator op : operators.values())
		{
			if (op.operator.startsWith(start))
				return op;
		}
		return null;
	}
	
	public abstract boolean canOperate(Type t1, Type t2);
	
	public abstract <T, U> Object operate(T par1, U par2);

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return this.getClass().getSimpleName();
	}

	public boolean isPrefixOperator()
	{
		return false;
	}

	public boolean isPostfixOperator()
	{
		return false;
	}
}
