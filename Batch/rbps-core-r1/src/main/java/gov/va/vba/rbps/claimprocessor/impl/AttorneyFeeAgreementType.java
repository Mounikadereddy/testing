/*
 *  AttorneyFeeAgreementType.java
 *
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.claimprocessor.impl;

import java.util.Arrays;
import java.util.List;

public enum AttorneyFeeAgreementType {

	ATTORNEY_FEE("Attorney Fee"), 
	CONVERTED("Converted - Potential Attorney Fee"), 
	POTENTIAL("Potential Attorney Fee"), 
	PRIVATE("Private Attorney - Fees Payable");

	private final List<String> values;

	private AttorneyFeeAgreementType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static AttorneyFeeAgreementType find(String name) {
		for (AttorneyFeeAgreementType fee : AttorneyFeeAgreementType.values()) {
			if (fee.values.contains(name))
				return fee;
		}
		return null;
	}

	public String getValue() {
		return values.get(0);
	}
}
