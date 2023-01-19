/*
 * PaymentTypeMarshal.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.lettergeneration;

public class PaymentTypeMarshal {

	private boolean		checkPayment;
	

	public PaymentTypeMarshal() {
        //      Do nothing.
    }

    public boolean getCheckPayment() {
		return checkPayment;
	}

	public void setCheckPayment(boolean checkPayment) {
		this.checkPayment = checkPayment;
	}
}
