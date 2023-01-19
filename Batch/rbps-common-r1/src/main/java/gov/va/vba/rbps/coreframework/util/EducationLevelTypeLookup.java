/*
 * EducationLevelTypeLookup.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.util;


import gov.va.vba.rbps.coreframework.xom.EducationLevelType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EducationLevelTypeLookup {


    private static List<Pair<EducationLevelType,List<String>>>     codes = new ArrayList<Pair<EducationLevelType,List<String>>>();


    public static EducationLevelType find( final String      value ) {

        init();

        for (Pair<EducationLevelType,List<String>> typeCode : codes ) {

            for ( String code : typeCode.getSecond()  ) {

                if ( code.equalsIgnoreCase( value) ) {

                    return typeCode.getFirst();
                }
            }
        }

        return null;
    }


    private static void init() {

        if ( ! codes.isEmpty() ) {

            return;
        }

        codes.add( new Pair<EducationLevelType,List<String>>( EducationLevelType.HOME_SCHOOLED,
                Arrays.asList( "Home School", "HomeSch" )));

        codes.add( new Pair<EducationLevelType,List<String>>( EducationLevelType.HIGH_SCHOOL,
                Arrays.asList( "High School", "HighSch" )));

        codes.add( new Pair<EducationLevelType,List<String>>( EducationLevelType.POST_SECONDARY,
                Arrays.asList( "College", "POSTSCNDY" )));

    }
}
