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
	private int					errorIndex			= -1;
	
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
	public SyntaxException(String message, CodeLine line, int errorindex)
	{
		super(message);
		this.codeline = line;
		this.errorIndex = errorindex;
	}
	
	/**
	 * Instantiates a new parser exception.
	 * 
	 * @param message
	 *            the message
	 */
	public SyntaxException(String message, CodeLine line, String error)
	{
		this(message, line, line.find(error));
	}

	/* (non-Javadoc)
	 * @see java.lang.Throwable#getMessage()
	 */
	@Override
	public String getMessage()
	{
		StringBuilder sb = new StringBuilder(super.getMessage().length() + codeline.line.length());
		sb.append(super.getMessage()).append("\n");
		sb.append("In line #").append(codeline.lineNumber + 1).append(":\n");
		sb.append(codeline.line).append("\n");
		for (int i = 0; i < errorIndex; i++)
		{
			sb.append(' ');
		}
		sb.append('^');
		return sb.toString();
	}
}
