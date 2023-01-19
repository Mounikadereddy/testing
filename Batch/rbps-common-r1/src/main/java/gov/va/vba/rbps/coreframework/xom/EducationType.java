/*
 * EducationType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.Arrays;
import java.util.List;


public enum EducationType {

    PART_TIME("Part Time"),
    FULL_TIME("Full Time");

    private List<String> values;

    private EducationType(final String... values) {
        this.values = Arrays.asList(values);
    }

    public static EducationType find(final String name) {
        for (EducationType education : EducationType.values()) {
            if (education.values.contains(name)) {
                return education;
            }
        }
        return null;
    }

    public String getValue() {
        return values.get(0);
    }
}
