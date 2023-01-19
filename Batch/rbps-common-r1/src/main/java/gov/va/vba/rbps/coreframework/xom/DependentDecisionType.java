/*
 * DependentDecisionType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.Arrays;
import java.util.List;


/**
 *      Dependent Decision enum: use to identify the Award Decision for Dependents
 */
public enum DependentDecisionType {

    ELIGIBLE_MINOR_CHILD("Eligible Minor Child",                                "EMC"),
    SCHOOL_ATTENDANCE_BEGIN_DATE("School Attendance Begin Date",                "SCHATTB"),
    RATED_SERIOUSLY_DISABLED("Rated Seriously Disabled",                        "RATHEL"),
    DEPENDENCY_ESTABLISHED("Dependency Established",                            "DEPEST"),
    MARRIAGE("Marriage",                                                        "MARR"),

    RATED_NOT_HELPLESS("Rated Not Helpless",                                    "RATNHEL"),
    OVER_18_NOT_IN_SCHOOL_OR_HELPLESS("Over 18 - Not in School or Helpless",    "O18NISOH"),
    SCHOOL_ATTENDENCE_TERMINATES("School Attendance Terminates",                "SCHATTT"),
    TURNS_18("Turns 18",                                                        "T18"),
    TURNS_23("Turns 23",                                                        "T23"),
    RELATIONSHIP_NOT_ESTABLISHED("Relationship Not Established",                "RNE"),
    DEATH("Death",                												"D"),
    MARRIAGE_TERMINATED("Marriage Terminated",  								"MT");

    private final List<String> values;

    private DependentDecisionType(final String... values) {
        this.values = Arrays.asList(values);
    }

    public static DependentDecisionType find(final String name) {

        for (DependentDecisionType decision : DependentDecisionType.values()) {

            if (decision.values.contains(name)) {
                return decision;
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
