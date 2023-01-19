/*
 * GenderType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */

package gov.va.vba.rbps.coreframework.xom;

import gov.va.vba.rbps.coreframework.util.Separator;

import java.util.Arrays;
import java.util.List;

public enum GenderType {
	MALE("Male", "M"),
	FEMALE("Female", "F");

	private List<String> values;

	private GenderType(final String... values) {
		this.values = Arrays.asList(values);
	}

	public static GenderType find(final String name) {
		for (GenderType gender : GenderType.values()) {
			if (gender.values.contains(name)) {
                return gender;
            }
		}
		return null;
	}

	public String getValue() {
		return values.get(0);
	}

	public String getCode() {
		return values.get(1);
	}

	@Override
    public String toString() {
		// copied code from CommonUtils class to avoid XOM jar file issue
        Separator       sep     = new Separator( ", " );
        StringBuffer    buffer  = new StringBuffer();

        for ( String item : values ) {

            buffer.append( sep );
            buffer.append( item );
        }
        
        return buffer.toString();
	}
}
