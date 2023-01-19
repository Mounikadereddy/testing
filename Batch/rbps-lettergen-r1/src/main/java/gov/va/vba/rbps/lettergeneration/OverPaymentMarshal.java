
/*
 * OverPaymentMarshal.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.lettergeneration;

public class OverPaymentMarshal {

	private boolean		overPayment;
	

	public OverPaymentMarshal() {
        //      Do nothing.
    }
	
	public boolean getOverPayment() {
		return overPayment;
	}

	public void setOverPayment(boolean overPayment) {
		this.overPayment = overPayment;
	}

}