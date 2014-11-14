package clashsoft.mods.tml.jmod.util.exceptions;

import clashsoft.mods.tml.jmod.util.CodeLine;

/**
 * The Class ParserException.
 */
public class SyntaxException extends TextModException
{
	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= -670702564983864871L;
	
	public CodeLine				line;
	
	private CodeLine			codeline;
	private int					errorStart			= -1;
	private int					errorLength			= 0;
	
	/**
	 * Instantiates a new parser exception.
	 */
	public SyntaxException()
	{
		super();
	}
	
	/**
	 * Instantiates a new parser exception.
	 * 
	 * @param message
	 *            the message
	 */
	public SyntaxException(String message, CodeLine line, int errorstart, int errorlength)
	{
		super(message);
		this.codeline = line;
		this.errorStart = errorstart;
		this.errorLength = errorlength;
	}
	
	/**
	 * Instantiates a new parser exception.
	 * 
	 * @param message
	 *            the message
	 */
	public SyntaxException(String message, CodeLine line, String error)
	{
		this(message, line, line.find(error), error.length());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage()
	{
		StringBuilder sb = new StringBuilder(super.getMessage().length() + this.codeline.line.length() + 12 + this.errorStart + this.errorLength);
		sb.append(super.getMessage()).append("\n");
		sb.append("In line #").append(this.codeline.lineNumber + 1).append(":\n");
		sb.append(this.codeline.line).append("\n");
		if (this.errorStart != -1)
		{
			for (int i = 0; i < this.errorStart; i++)
			{
				sb.append(' ');
			}
			sb.append('^');
			if (this.errorLength != 0)
			{
				for (int i = 0; i < this.errorLength - 1; i++)
				{
					sb.append('^');
				}
			}
		}
		return sb.toString();
	}
}
