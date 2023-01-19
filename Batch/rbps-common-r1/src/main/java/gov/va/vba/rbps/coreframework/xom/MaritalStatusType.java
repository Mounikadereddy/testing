/*
 * MaritalStatusType.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

public enum MaritalStatusType {
	MARRIED("Married", "Married"), 
	DIVORCED("Divorced", "Divorced"), 
	NEVER_MARRIED("Never Married", "Never"), 
	WIDOWED("Widowed", "Widowed"), 
	SEPARATED("Separated", "Separated");
	
	private List<String> values;
	
	private MaritalStatusType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static MaritalStatusType find(String name) {
		for (MaritalStatusType status : MaritalStatusType.values()) {
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
