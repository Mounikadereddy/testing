/*
 * DependentStatusType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.Arrays;
import java.util.List;


/**
 *      Dependent Status enum: use to identify the Award Status for Dependents
 */
public enum DependentStatusType {

    MINOR_CHILD("Minor Child",                              "MC"),
    SCHOOL_CHILD("School Child",                            "SCHCHD"),
    SERIOUSLY_DISABLED_CHILD("Seriously Disabled Child",    "HC"),
    SPOUSE("Spouse",                                        "SP"),
    NOT_AN_AWARD_DEPENDENT("Not an Award Dependent",        "NAWDDEP");

    private final List<String> values;

    private DependentStatusType(final String... values) {
        this.values = Arrays.asList(values);
    }

    public static DependentStatusType find(final String name) {

        for (DependentStatusType status : DependentStatusType.values()) {

            if (status.values.contains(name)) {
                return status;
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
