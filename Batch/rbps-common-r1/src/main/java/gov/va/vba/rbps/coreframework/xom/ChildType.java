/*
 * ChildType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.Arrays;
import java.util.List;


public enum ChildType {

    BIOLOGICAL_CHILD("Biological"),
    STEPCHILD("Stepchild"),
    ADOPTED_CHILD("Adopted Child"),
    UNDEFINED("Undefined");

    private final List<String> values;

    private ChildType(final String... values) {
        this.values = Arrays.asList(values);
    }


    public static ChildType find(final String name) {
        for (ChildType childType : ChildType.values()) {
            if (childType.values.contains(name)) {
                return childType;
            }
        }

        return UNDEFINED;
    }


    public String getValue() {

        return values.get(0);
    }
}
