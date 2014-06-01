package clashsoft.mods.tml.jmod.util.types;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import net.minecraft.item.ItemStack;

public class Type
{
	public static List<Type>	types		= new LinkedList<Type>();
	
	public static Type			VOID		= new Type(Void.class, "void");
	public static Type			OBJECT		= new Type(Object.class, "Object");
	
	public static Type			STRING		= new Type(String.class, "String");
	public static Type			CHAR		= new Type(Character.class, "char", "Character");
	public static Type			BOOLEAN		= new Type(Boolean.class, "boolean", "Boolean");
	public static Type			BYTE		= new Type(Byte.class, "byte", "Byte");
	public static Type			SHORT		= new Type(Short.class, "short", "Short");
	public static Type			INT			= new Type(Integer.class, "int", "Integer");
	public static Type			LONG		= new Type(Long.class, "long", "Long");
	public static Type			FLOAT		= new Type(Float.class, "float", "Float");
	public static Type			DOUBLE		= new Type(Double.class, "double", "Double");
	
	public static Type			ITEMSTACK	= new Type(ItemStack.class, "ItemStack");
	
	public String[]				names;
	public Class				type;
	
	public Type(Class type, String... names)
	{
		this.type = type;
		this.names = names;
		
		types.add(this);
	}
	
	public static boolean isNumeric(Type type)
	{
		return isInteger(type) || type == FLOAT || type == DOUBLE;
	}
	
	public static boolean isInteger(Type type)
	{
		return type == BYTE || type == SHORT || type == INT || type == LONG || type == CHAR;
	}
	
	public static boolean isBoolean(Type type)
	{
		return type == BOOLEAN;
	}
	
	public static Type getTypeFromName(String name)
	{
		for (Type t : types)
		{
			for (String s : t.names)
			{
				if (s.trim().equals(name.trim()))
					return t;
			}
		}
		return VOID;
	}
	
	public static Type getTypeFromClass(Class c)
	{
		for (Type t : types)
		{
			if (t.type != null && t.type.equals(c))
				return t;
		}
		return VOID;
	}
	
	public static Type getTypeFromObject(Object o)
	{
		return o != null ? getTypeFromClass(o.getClass()) : VOID;
	}
	
	@Override
	public String toString()
	{
		return "Type[names=" + Arrays.toString(names) + ", type=" + type + "]";
	}
}