/*
 * RpbsException.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.exception;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <p>
 * Title: RbpsException
 * </p>
 * <p>
 * Description: RbpsException is similar to {@link java.lang.Execption}
 * This is the common superclass for all RBPS checked exceptions.
 * RBPSException and its subclasses support the chained exception facility that
 * allows a root cause Throwable to be wrapped by this class or one of its
 * descendants.
 * </p>
 * See its companion at {@link RbpsRuntimeException} for unchecked exceptions
 * 
 * @author Omar.Gaye
 * @version 1.0
 * @since 05/09/2011
 * 
 * 		Revision History
 * 		Date		Name		Description
 * 		------------------------------------------------------------
 * 		05/09/2011  O.Gaye		Initial Version
 * 
 */
public class RbpsException extends Exception {
	/**
     *
     */
	private static final long serialVersionUID = 6717002522247488861L;
	/**
     *
     */
	private List<RbpsException> exceptionsList = new ArrayList<RbpsException>();
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
	public RbpsException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 * 
	 * @param message
	 *            the detail message. The detail message is saved for
	 *            later retrieval by the {@link #getMessage()} method.
	 */
	public RbpsException(String message) {
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
	public RbpsException(Throwable rootCause) {
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
	public RbpsException(String message, Throwable rootCause) {
		super(message, rootCause);
		this.message = message;
		this.rootCause = rootCause;
	}
	
    //----- Property methods ---------------
	/**
	 * <p>Set the message.</p>
	 * 
	 * @param String message
	 *            The new message.
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * <p>Get the message</p>
	 * 
	 * @return String message (can be null)
	 */
	public String getMessage() {
		return super.getMessage() == null ? message : super.getMessage();
	}
	
	/**
	 * <p>
	 * Set the rootCause of the Exception. This is the primary 
	 * cause of the exception being thrown, the lowest exception 
	 * in the stack trace
	 * </p>
	 * 
	 * @param Throwable rootCause
	 */
	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}
	/**
	 * <p>Get the rootCause of the Exception</p>
	 * 
	 * @return Throwable rootCause
	 */
	public Throwable getRootCause() {
		return rootCause;
	}
	
	/**
	 * <p>Set the message values.</p>
	 * 
	 * @param oValues
	 *            The new message value parameters.
	 */
	public void setMessageArr(Object[] messageArr) {
		this.messageArr = messageArr;
	}
	
	/**
	 * <p>Get the message values.</p>
	 * 
	 * @return Object[] (may be null)
	 */
	public Object[] getMessageArr() {
		return messageArr;
	}
	
    //----- Multiple Exceptions Utility -----------
	/**
	 * <p>
	 * This is a slight variation on exception chaining. It is useful when a
	 * method throws multiple exceptions.Instead of only being able to retrieve
	 * information on one of the exceptions, make the root cause of this class
	 * to be the first exception thrown, then add any other exceptions to it.
	 * </p>
	 * 
	 * @param RbpsException exception
	 */
    public void addException(RbpsException exception){
        this.exceptionsList.add(exception);
    }
    
	/**
	 * <p>
	 * Returns a list of exceptions that were thrown by a method with multiple
	 * exception clauses. Note: if only one exception was thrown (root cause)
	 * this list will be empty.
	 * </p>
	 * 
	 * @return List<RbpsException> exceptionsList
	 */
	public List<RbpsException> getExceptionsList() {
		return exceptionsList;
	}
}
