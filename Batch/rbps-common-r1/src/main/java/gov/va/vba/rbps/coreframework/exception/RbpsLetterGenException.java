/*
 * RbpsLetterGenException.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.exception;

/**
 * 
 * <p>
 * Title: <code>RbpsLetterGenException</code>
 * </p>
 * <p>
 * Description: This class extends <code>RbpsRuntimeException</code>
 *  {@link RbpsRuntimeExecption}
 * </p>
 * 
 * @author Omar.Gaye
 * @version 1.0
 * @since 05/09/2011
 * 
 * 		Revision History
 * 		Date		Name		Description
 * 		------------------------------------------------------------
 * 		08/23/2011  O.Gaye		Initial Version
 * 
 */
public class RbpsLetterGenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2323670386757359877L;
	/**
	 * 
	 */
	protected Throwable rootCause = null;
	/**
     *
     */
	private String message = null;
	/**
     *
     */
	private Object[] messageArr = null;

	//--- Constructors ---
	
	/**
	 * Constructs a new exception with <code>null</code> as its detail message.
	 * The cause is not initialized, and may subsequently be initialized by a
	 * call to {@link #initCause}.
	 */
	public RbpsLetterGenException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for
	 *            later retrieval by the {@link #getMessage()} method.
	 */
	public RbpsLetterGenException(String message) {
		super(message);
		this.message = message;
	}

    /**
     * Constructs a new exception with the specified cause and a detail
     * message of <tt>(cause==null ? null : cause.toString())</tt> 
     *
     * @param  rootCause the rootCause (which is saved for later retrieval by the
     *         {@link #getRootCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
	public RbpsLetterGenException(Throwable rootCause) {
		super(rootCause);
		this.rootCause = rootCause;
	}
	
    /**
     * Constructs a new exception with the specified message and rootCause.  
     *
     * @param  message the detail message (which is saved for later retrieval
     *         by the {@link #getMessage()} method).
     * @param  rootCause the root cause (which is saved for later retrieval by the
     *         {@link #getRootCause()} method).  (A <tt>null</tt> value is
     *         permitted, and indicates that the cause is nonexistent or
     *         unknown.)
     */
	public RbpsLetterGenException(String message, Throwable rootCause) {
		super(message, rootCause);
		this.message = message;
		this.rootCause = rootCause;
	}
	
    //----- Property methods ---------------
	/**
	 * <p>Set the message.</p>
	 * @param String message
	 *            The new message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * <p>Get the message</p>
	 * @return String message (can be null)
	 */
	public String getMessage() {
		return super.getMessage() == null ? message : super.getMessage();
	}
	
	/**
	 * <p>Set the rootCause of the Exception. This is the primary 
	 * cause of the exception being thrown, the lowest exception 
	 * in the stack trace</p>
	 * @param Throwable rootCause
	 */
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}
	/**
	 * <p>Get the rootCause of the Exception</p>
	 * @return Throwable rootCause
	 */
	public Throwable getRootCause() {
		return rootCause;
	}
	
	/**
	 * <p>Set the message values.</p>
	 * @param oValues
	 *            The new message value parameters.
	 */
	public void setMessageArr(Object[] messageArr) {
		this.messageArr = messageArr;
	}
	
	/**
	 * <p>Get the message values.</p>
	 * @return Object[] (may be null)
	 */
	public Object[] getMessageArr() {
		return messageArr;
	}
}
