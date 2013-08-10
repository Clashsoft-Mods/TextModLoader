package com.chaosdev.textmodloader.util.annotations;

import static com.chaosdev.textmodloader.util.annotations.Annotation.AnnotationType.NOTHING;

import java.util.HashMap;
import java.util.Map;

import com.chaosdev.textmodloader.util.ParserException;
import com.chaosdev.textmodloader.util.codeblock.ClassCodeBlock;
import com.chaosdev.textmodloader.util.codeblock.CodeBlock;
import com.chaosdev.textmodloader.util.method.Method;

public abstract class Annotation
{
	public static enum AnnotationType
	{
		NOTHING, VARIABLE, METHOD
	}
	
	public static Map<String, Annotation>	annotations	= new HashMap<String, Annotation>();
	
	public static Annotation				AUTHOR		= new Annotation("author", NOTHING)
														{
															@Override
															public void apply(String annotLine, CodeBlock codeblock, IAnnotable object)
															{
																if (object instanceof ClassCodeBlock)
																	((ClassCodeBlock) codeblock).author = annotLine;
															}
														};
	
	public String							name;
	public AnnotationType					type;
	
	public Annotation(String name, AnnotationType type)
	{
		this.name = name;
		this.type = type;
		
		annotations.put(name, this);
	}
	
	public static Annotation fromLine(CodeBlock codeblock, String line, String nextLine) throws ParserException
	{
		Method m = codeblock.readMethod(line);
		return annotations.get(m.name);
	}
	
	public abstract void apply(String annotLine, CodeBlock superCodeBlock, IAnnotable object);
}
