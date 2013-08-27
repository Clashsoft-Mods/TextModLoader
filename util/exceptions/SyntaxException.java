package com.chaosdev.textmodloader.util.exceptions;

import com.chaosdev.textmodloader.util.CodeLine;

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
		StringBuilder sb = new StringBuilder(super.getMessage().length() + codeline.line.length() + 12 + errorStart + errorLength);
		sb.append(super.getMessage()).append("\n");
		sb.append("In line #").append(codeline.lineNumber + 1).append(":\n");
		sb.append(codeline.line).append("\n");
		if (errorStart != -1)
		{
			for (int i = 0; i < errorStart; i++)
			{
				sb.append(' ');
			}
			sb.append('^');
			if (errorLength != 0)
			{
				for (int i = 0; i < errorLength - 1; i++)
				{
					sb.append('^');
				}
			}
		}
		return sb.toString();
	}
}
