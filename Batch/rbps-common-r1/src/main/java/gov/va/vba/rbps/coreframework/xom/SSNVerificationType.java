/*
 * SSNVerificationType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

public enum SSNVerificationType {
	BIRLS("BIRLS Verified", "4"), 
	CHILD_UNDER_2("Not Required-Child Under Two", "3"),
	SSA_VERIFIED_NO_NUMBER("SSA Verified No Number Exists", "9"),
	UNVERIFIED("Unverified", "0"),
	VERIFIED_BY_SSA("Verified By SSA", "1"),
	VERIFIED_BY_VBA("Verified By VBA", "2");
	
	
	private final List<String> values;

	private SSNVerificationType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static SSNVerificationType find(String name) {
		for (SSNVerificationType ssn : SSNVerificationType.values()) {
			if (ssn.values.contains(name))
				return ssn;
		}
		return null;
	}

	public String getValue() {
		return values.get(0);
	}
	
	public String getCode() {
		return values.get(1);
	}
	
}