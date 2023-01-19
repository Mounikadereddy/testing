/*
 * Salutation.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

public enum SalutationType {
	MR("Mr."), 
	MISS("Miss"), 
	MS("Ms."),
	MRS("Mrs.");
	
	private List<String> values;
	
	private SalutationType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static SalutationType find(String name) {
		for (SalutationType salutation : SalutationType.values()) {
			if (salutation.values.contains(name))
				return salutation;
		}
		return null;
	}

	public String getValue() {
		return values.get(0);
	}
	
}
