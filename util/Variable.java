package com.chaosdev.textmodloader.util;

import com.chaosdev.textmodloader.util.types.Type;

// TODO: Auto-generated Javadoc
/**
 * The Class Variable.
 */
public class Variable
{
	
	/** The type. */
	public Type		type;
	
	/** The name. */
	public String	name;
	
	/** The value. */
	public Object	value;
	
	/**
	 * Instantiates a new variable.
	 *
	 * @param type the type
	 * @param name the name
	 * @param value the value
	 */
	public Variable(Type type, String name, Object value)
	{
		this.type = type;
		this.name = name;
		this.value = value;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Variable[type=" + type + ", name=" + name + ", value=" + value + "]";
	}
}
