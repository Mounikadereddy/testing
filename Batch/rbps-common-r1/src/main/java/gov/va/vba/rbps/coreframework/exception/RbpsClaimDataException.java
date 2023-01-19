/*
 * RpbsClaimDataException.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.exception;


/**
 *
 * <p>
 * Title: RpbsClaimDataException
 * </p>
 * <p>
 * Description: RpbsClaimDataException wrap all exceptions related to
 * data validation failure
 * RBPSException supports the chained exception facility that
 * allows a root cause Throwable to be wrapped by this class or one of its
 * descendants.
 * </p>
 *
 * @author Omar.Gaye
 * @version 1.0
 * @since 07/17/2011
 *
 * 		Revision History
 * 		Date		Name		Description
 * 		------------------------------------------------------------
 * 		07/17/2011  O.Gaye		Initial Version
 *
 */
public class RbpsClaimDataException extends RbpsRuntimeException {
	/**
     *
     */
	private static final long serialVersionUID = 6717002522247488861L;
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
	public RbpsClaimDataException() {
		super();
	}

	/**
	 * Constructs a new exception with the specified detail message.
	 *
	 * @param message
	 *            the detail message. The detail message is saved for
	 *            later retrieval by the {@link #getMessage()} method.
	 */
	public RbpsClaimDataException(final String message) {
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
	public RbpsClaimDataException(final Throwable rootCause) {
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
	public RbpsClaimDataException(final String message, final Throwable rootCause) {
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
	@Override
    public void setMessage(final String message) {
		this.message = message;
	}

	/**
	 * <p>Get the message</p>
	 *
	 * @return String message (can be null)
	 */
	@Override
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
	@Override
    public void setRootCause(final Throwable rootCause) {
		this.rootCause = rootCause;
	}
	/**
	 * <p>Get the rootCause of the Exception</p>
	 *
	 * @return Throwable rootCause
	 */
	@Override
    public Throwable getRootCause() {
		return rootCause;
	}

	/**
	 * <p>Set the message values.</p>
	 *
	 * @param oValues
	 * The new message value parameters.
	 */
	@Override
    public void setMessageArr(final Object[] messageArr) {
		this.messageArr = messageArr;
	}

	/**
	 * <p>Get the message values.</p>
	 *
	 * @return Object[] (may be null)
	 */
	@Override
    public Object[] getMessageArr() {
		return messageArr;
	}

}
