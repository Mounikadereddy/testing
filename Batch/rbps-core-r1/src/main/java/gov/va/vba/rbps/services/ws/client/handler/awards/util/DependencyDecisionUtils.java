/*
 * DependencyDecisionUtils
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.DependentDecisionType;
import gov.va.vba.rbps.coreframework.xom.DependentStatusType;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AmendDependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;

import java.util.Arrays;
import java.util.List;



public class DependencyDecisionUtils {


    private List<DependentDecisionType>     denialDecisionTypes = Arrays.asList( DependentDecisionType.RATED_NOT_HELPLESS,
                                                                                 DependentDecisionType.OVER_18_NOT_IN_SCHOOL_OR_HELPLESS,
                                                                                 DependentDecisionType.SCHOOL_ATTENDENCE_TERMINATES,
                                                                                 DependentDecisionType.TURNS_18,
                                                                                 DependentDecisionType.TURNS_23,
                                                                                 DependentDecisionType.RELATIONSHIP_NOT_ESTABLISHED );
    private List<DependentStatusType>       denialStatustypes   = Arrays.asList( DependentStatusType.NOT_AN_AWARD_DEPENDENT );



    public boolean isGrant( final Award     award ) {

        return ! isDenial( award );
    }


    public boolean isDenial( final Award award ) {

        return denialDecisionTypes.contains( award.getDependencyDecisionType() )
                || denialStatustypes.contains( award.getDependencyStatusType() );
    }




    public boolean isGrant( final DependencyDecisionVO dependencyDecision ) {

        return ! isDenial( dependencyDecision );
    }


    public boolean isDenial( final DependencyDecisionVO dependencyDecision ) {

        return denialDecisionTypes.contains( DependentDecisionType.find( dependencyDecision.getDependencyDecisionType() ) )
                || denialStatustypes.contains( DependentStatusType.find( dependencyDecision.getDependencyStatusType() ) );
    }





    public boolean isRequestSeriouslyDisabled( final AmendDependencyDecisionVO requestDependencyDecision ) {

        return requestDependencyDecision.getDependencyDecisionType().equals( DependentDecisionType.RATED_SERIOUSLY_DISABLED.getCode() );
    }


    public boolean requestIsSchoolAttendanceBegins( final AmendDependencyDecisionVO requestDependencyDecision ) {

        return requestDependencyDecision.getDependencyDecisionType().equals( DependentDecisionType.SCHOOL_ATTENDANCE_BEGIN_DATE.getCode() );
    }
}
