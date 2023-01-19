/*
 * RpbsServiceException.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.exception;
/**
 * 
 * <p>
 * Title: <code>RbpsServiceException</code>
 * </p>
 * <p>
 * Description: This class extends <code>RbpsRuntimeException</code>
 *  {@link RbpsRuntimeExecption}
 *  I am thinking of using the class only at the very top of the RBPS application
 *  meaning at the Facade level: 
 *  <code>public class RbpsServiceFacade throws RbpsServiceException<code>
 * </p>
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
 * 
 */
public class RbpsServiceException extends RbpsRuntimeException {

	private static final long serialVersionUID = -1722411842127703197L;
	protected Throwable rootCause = null;
	private String message = null;

	public RbpsServiceException() {
		super();
	}

	public RbpsServiceException(String message) {
		super(message);
		this.message = message;
	}

	public RbpsServiceException(Throwable rootCause) {
		super(rootCause);
		this.rootCause = rootCause;
	}
	
	public RbpsServiceException(String message, Throwable rootCause) {
		super(message, rootCause);
		this.rootCause = rootCause;
		this.message = message;
	}
	

	public Throwable getRootCause() {
		return rootCause;
	}

	public void setRootCause(Throwable rootCause) {
		this.rootCause = rootCause;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
