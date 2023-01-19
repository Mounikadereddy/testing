/*
 * RelationshipType.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. use is subject to security terms
 *
 */

package gov.va.vba.rbps.coreframework.vo;


import java.util.Arrays;
import java.util.List;


/**
 *      This enum keeps all the relationship types
 *      that our project needs from the VnpPtcpntRlnshp table
 */
public enum RelationshipType {

    CHILD("Child",                 "CHILD"     ),
    SPOUSE("Spouse",               "S"         ),
    PARENT("Parent",               ""          ),
    POA("Power of Attorney For",   "POA"       ),
    CUSTODIAN("Legal Custodian",   ""          ),
    GUARDIAN("Guardian",           "G"         );


    private final List<String> values;


    private RelationshipType(final String... values) {
        this.values = Arrays.asList(values);
    }

    public static RelationshipType find(final String name) {

        for (RelationshipType relationshipType : RelationshipType.values()) {

            if (relationshipType.values.contains(name)) {

                return relationshipType;
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
