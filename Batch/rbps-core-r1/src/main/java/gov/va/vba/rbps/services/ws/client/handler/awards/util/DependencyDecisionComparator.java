/*
 * DependencyDecisionComparator
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;


import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;

import java.util.Comparator;


/**
 *      Enable the sorting of dependency decisions by decision date and then event date.
 */
public class DependencyDecisionComparator implements Comparator<DependencyDecisionVO> {

    private SimpleDateUtils             dateUtils       =   new SimpleDateUtils();
    private DependencyDecisionUtils     decisionUtils   =   new DependencyDecisionUtils();


    @Override
    public int compare(final DependencyDecisionVO lhs, final DependencyDecisionVO rhs) {
    	// changes for RTC 304011
    	if (lhs.getAwardEffectiveDate() == null) return 1;
        if (rhs.getAwardEffectiveDate() == null) return -1;
        int result = dateUtils.xmlGregorianCalendarToDay( lhs.getAwardEffectiveDate() ).compareTo( dateUtils.xmlGregorianCalendarToDay( rhs.getAwardEffectiveDate() ));

        if ( sameDecisionDay( result ) ) {

            result = dateUtils.xmlGregorianCalendarToDay( lhs.getEventDate() ).compareTo( dateUtils.xmlGregorianCalendarToDay( rhs.getEventDate() ));
        }

        if ( sameDecisionDay( result ) && grantBeforeDenialOnSameDay(lhs, rhs) ) {

            result =  rhs.getDependencyDecisionType().compareTo( lhs.getDependencyDecisionType() );
        }

        return result;
    }


    public boolean sameDecisionDay( final int result ) {

        return result == 0;
    }


    private boolean grantBeforeDenialOnSameDay( final DependencyDecisionVO lhs, final DependencyDecisionVO rhs ) {

        return decisionUtils.isGrant( lhs ) && decisionUtils.isDenial( rhs );
    }
}
