package clashsoft.mods.tml.jmod.util.method;

import clashsoft.mods.tml.jmod.util.codeblock.CodeBlock;

public abstract class Method
{
	public String	name;
	public Object[]	parameters;
	
	public Method(String name, Object... parameters)
	{
		this.name = name;
		this.parameters = parameters;
	}
	
	/**
	 * Executes the method
	 * 
	 * @param superCodeBlock
	 *            the CodeBlock the Method is called in
	 * @param parameters
	 *            the parameters for calling the method
	 * @return
	 */
	public abstract Object execute(CodeBlock superCodeBlock, Object... parameters);
	
	public boolean isValid()
	{
		return true;
	}
}
