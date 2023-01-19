/*
 * EducationLevel.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.Arrays;
import java.util.List;


public enum EducationLevelType {

    HOME_SCHOOLED("Home School", "HomeSch"),
    HIGH_SCHOOL("High School", "HighSch"),
    POST_SECONDARY("College", "College");

    private final List<String> values;

    private EducationLevelType(final String... values) {
        this.values = Arrays.asList(values);
    }

    public static EducationLevelType find(final String name) {
        for (EducationLevelType education : EducationLevelType.values()) {
            if (education.values.contains(name)) {
                return education;
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
}
