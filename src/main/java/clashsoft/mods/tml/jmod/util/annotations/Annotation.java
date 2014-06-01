package clashsoft.mods.tml.jmod.util.annotations;

import java.util.HashMap;
import java.util.Map;

import clashsoft.mods.tml.jmod.util.CodeLine;
import clashsoft.mods.tml.jmod.util.codeblock.ClassCodeBlock;
import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;
import clashsoft.mods.tml.jmod.util.exceptions.SyntaxException;
import clashsoft.mods.tml.jmod.util.method.Method;

public abstract class Annotation
{
	public static enum AnnotationType
	{
		NOTHING, VARIABLE, METHOD
	}
	
	public static Map<String, Annotation>	annotations	= new HashMap<String, Annotation>();
	
	public static Annotation				AUTHOR		= new Annotation("author", AnnotationType.NOTHING)
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
	
	public static Annotation fromLine(CodeBlock codeblock, CodeLine line, CodeLine nextLine) throws SyntaxException
	{
		Method m = codeblock.readMethod(line);
		return annotations.get(m.name);
	}
	
	public abstract void apply(String annotLine, CodeBlock superCodeBlock, IAnnotable object);
}
