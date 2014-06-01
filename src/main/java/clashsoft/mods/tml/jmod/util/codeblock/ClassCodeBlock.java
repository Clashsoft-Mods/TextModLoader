package clashsoft.mods.tml.jmod.util.codeblock;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import clashsoft.mods.tml.jmod.util.CodeLine;
import clashsoft.mods.tml.jmod.util.TextModHelper;
import clashsoft.mods.tml.jmod.util.exceptions.SyntaxException;
import clashsoft.mods.tml.jmod.util.method.Method;

public class ClassCodeBlock extends CodeBlock
{
	public static Map<String, ClassCodeBlock> classCodeBlocks = new HashMap<String, ClassCodeBlock>();
	
	private CodeLine					header				= null;
	
	public String						className			= "";
	public String						author				= "";
	
	public ClassCodeBlock				extendedClass		= null;
	public Map<String, ClassCodeBlock>	inplementedClasses	= new HashMap<String, ClassCodeBlock>();
	
	public Map<String, Method>			customMethods		= new HashMap<String, Method>();
	
	protected ClassCodeBlock()
	{
		this(null);
	}
	
	public ClassCodeBlock(CodeLine header)
	{
		this(header, new LinkedList<String>());
	}
	
	public ClassCodeBlock(CodeLine header, List<String> lines)
	{
		this(header, null, lines);
	}
	
	public ClassCodeBlock(CodeLine header, CodeBlock superBlock, List<String> lines)
	{
		super(superBlock, lines);
		this.header = header;
	}

	public void analyseHeader(CodeLine header) throws SyntaxException
	{
		String[] split = TextModHelper.createParameterList(header.line, ' ');
		
		if (!split[0].equals(CLASS_DECLARATION))
			throw new SyntaxException("Invalid class syntax, missing 'class'", header, 0, 0);
		className = split[1];
		if (split.length > 2)
		{
			if (split[2].equals("extends"))
				if (split.length == 4)
					this.extendedClass = getClass(split[3]);
				else
					throw new SyntaxException("Invalid class syntax, class name expected after 'extends'", header, "extends");
			else
				throw new SyntaxException("Invalid token in class syntax", header, split[2]);
		}
		
		classCodeBlocks.put(className, this);
	}

	public ClassCodeBlock getClass(String string)
	{
		ClassCodeBlock ccb = classCodeBlocks.get(string);
		return ccb;
	}

	@Override
	public ClassCodeBlock getCodeBlockClass()
	{
		return this;
	}
	
	@Override
	public Method getCustomMethod(String name)
	{
		return customMethods.get(name);
	}
	
	public void registerMethod(Method method)
	{
		if (!method.name.equals(""))
		{
			this.customMethods.put(method.name, method);
			this.parser.setCodeBlock(this);
			System.out.println("  Method \'" + method.name + "\' added.");
		}
	}
	
	@Override
	public Object execute()
	{
		try
		{
			analyseHeader(header);
		}
		catch (SyntaxException ex)
		{
			System.err.println(ex.getMessage());
		}
		return super.execute();
	}
}
