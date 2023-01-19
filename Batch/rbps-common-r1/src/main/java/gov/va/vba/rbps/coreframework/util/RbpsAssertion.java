/*
 * RbpsAssertion.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.util;

import static gov.va.vba.rbps.coreframework.util.RbpsConstants.CLAIM_STATUS_READY_FOR_RBPS;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.xom.ClaimLabelType;

import java.util.List;

/**
 * An assertion framework for the RBPS code, to make code more readable and less
 * cluttered with exceptions and enforce data integrity before invoking the
 * Rules engine
 * 
 * @author vafscpeterj
 * @since 03/21/2011
 * @version 1.0
 * 
 */

public class RbpsAssertion {

	/**
	 * Throws NullPointerException if the Object passed in is null
	 * 
	 * @param obj
	 * @param message
	 * @throws NullPointerException
	 */
	public static void assertNotNull(Object obj, String message) {
		if (obj == null) {
			throw new NullPointerException(defaultIfMessageEmpty(message));
		}
	}
	
	/**
	 * Throws IllegalArgumentException if the Object passed in is NOT null
	 * 
	 * @param obj
	 * @param message
	 * @throws IllegalArgumentException
	 */
	public static void assertNull(Object obj, String message) {
		if (obj != null) {
			throw new IllegalArgumentException(defaultIfMessageEmpty(message));
		}
	}

	/**
	 * Throws IllegalArgumentException if the String is null or empty
	 * 
	 * @author vafscpeterj
	 * @param string
	 * @param message
	 * @throws IllegalArgumentException
	 */
	public static void assertNonEmptyString(String string, String message) {
		assertNotNull(string, message);
		if (string.trim().length() == 0) {
			throw new IllegalArgumentException(defaultIfMessageEmpty(message));
		}
	}

	/**
	 * Throws IllegalArgumentException if the list is null
	 * 
	 * @author vafscpeterj
	 * @param <E>
	 * @param list
	 * @param message
	 * @throws IllegalArgumentException
	 */
	public static <E> void assertNonEmptyList(List<E> list, String message) {
		assertNotNull(list, message);
		if (list.isEmpty()) {
			throw new IllegalArgumentException(defaultIfMessageEmpty(message));
		}
	}

	/**
	 * Throws IllegalArgumentException if the number passed in is not zero
	 * 
	 * @author vafscpeterj
	 * @param number
	 * @param message
	 * @throws IllegalArgumentException
	 */
	public static void assertNumberNonZero(Number number, String message) {
		assertNotNull(number, message);
		if (number.longValue() == 0) {
			throw new IllegalArgumentException(defaultIfMessageEmpty(message));
		}
	}

	/**
	 * Throws RbpsRuntimeException if the two integers are not equal
	 * 
	 * @author vafscpeterj
	 * @param expectedValue
	 * @param givenValue
	 * @param message
	 * @throws RbpsRuntimeException
	 */
	public static void assertEquals(int expectedValue, int givenValue, String message) {
		if (expectedValue != givenValue) {
			throw new RbpsRuntimeException(defaultIfMessageEmpty(message));
		}
	}

	/**
	 * Throws RbpsRunTimeException if the first two Strings are not equal
	 * 
	 * @author vafscpeterj
	 * @param expected
	 * @param given
	 * @param message
	 * @throws RbpsRuntimeException
	 */
	public static void assertEquals(String expected, String given, String message) {
		if (expected == null || given == null || !expected.equalsIgnoreCase(given)) {
			throw new RbpsRuntimeException(defaultIfMessageEmpty(message));
		}
	}

	/**
	 * Throws NumberFormatException if the String passed in cannot be converted to Long
	 * 
	 * @author vafscpeterj
	 * @param numericString
	 * @param message
	 * @throws NumberFormatException
	 */
	public static void assertIsStringLong(String numericString, String message) {
		try {
			new Long(numericString);
		} catch (NumberFormatException e) {
			throw new NumberFormatException(defaultIfMessageEmpty(message));
		}
	}
	
	/**
	 * Throws IllegalArgumentException if the label passed in is not in scope
	 * 
	 * @author vafscpeterj
	 * @param status
	 * @param message
	 * @throws IllegalArgumentException
	 */
	
	public static void assertIsValidClaimLabel(ClaimLabelType label, String message) {
		assertNotNull(label, message);
		for (ClaimLabelType labelType : ClaimLabelType.values()) {
			
			 if( ! (  labelType.getValue().endsWith("Reject") || ( labelType.getValue().endsWith("Exception") ) ) && labelType.equals(label) ) {
				 
				 return;
			 }
		}
		throw new IllegalArgumentException(defaultIfMessageEmpty(message));	
	}
	
	/**
	 * Throws IllegalArgumentException if the string passed in is not 'Ready'
	 * 
	 * @author vafscpeterj
	 * @param status
	 * @param message
	 * @throws IllegalArgumentException
	 */
	public static void assertIsReadyStatus(String status, String message) {
		assertNotNull(status, message);
		if (!status.equalsIgnoreCase(CLAIM_STATUS_READY_FOR_RBPS)) {
			throw new IllegalArgumentException(defaultIfMessageEmpty(message));
		}
	}
	
	/**
	 * returns the string passed in if not null, otherwise return default string
	 * 
	 * @author vafscpeterj
	 * @param message
	 * @return String
	 */
	private static String defaultIfMessageEmpty(String message) {
		return (message == null || message.trim().isEmpty()) ? "No message specified" : message;
	}
}
