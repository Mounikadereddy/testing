/*
 * NoSSNReasonType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

public enum NoSSNReasonType {
	NO_SSN_FROM_SSA("No SSN Assigned by SSA", "NSAS"), 
	NONRESIDENT_ALIEN("Nonresident Alien", "NRA");

private List<String> values;
	
	private NoSSNReasonType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static NoSSNReasonType find(String name) {
		for (NoSSNReasonType status : NoSSNReasonType.values()) {
			if (status.values.contains(name))
				return status;
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
