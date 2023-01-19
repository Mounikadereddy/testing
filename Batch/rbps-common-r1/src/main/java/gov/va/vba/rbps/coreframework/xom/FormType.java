/*
 * FormType.java
 * 
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import java.util.Arrays;
import java.util.List;

/**
 * Form Type enum: use to identify form types form type
 * 
 * @author vafscpeterj
 * @since June 13 2011
 * 
 */

public enum FormType {

	FORM_21_686C("21-686c", "Application Request To Add And/Or Remove Dependents"), 
	FORM_21_674("21-674", "Request for Approval Of School Attendance");

	private List<String> values;
	
	private FormType(String... values) {
		this.values = Arrays.asList(values);
	}

	public static FormType find(String name) {
		for (FormType form : FormType.values()) {
			if (form.values.contains(name))
				return form;
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
