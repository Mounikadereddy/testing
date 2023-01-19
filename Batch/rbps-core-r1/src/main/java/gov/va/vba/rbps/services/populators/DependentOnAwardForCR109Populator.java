/*
 * DependentOnAwardPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;

import java.util.Date;
import java.util.List;


public class DependentOnAwardForCR109Populator {

    private static Logger logger = Logger.getLogger(DependentOnAwardForCR109Populator.class);

    private final static String                             NOT_AWARD_DEPENDENT = "NAWDDEP";



    public void populateFromDependentOnAward( final FindDependentOnAwardResponse response, final Dependent dependent) {

        try {

            if (response.getReturn().getDependentOnAwardService() == null) {

                dependent.setIsDeniedAward( false );
                return;
            }
            

            dependent.setIsDeniedAward( isDeniedAward( response ) );
            logIfDependentDeniedAward( dependent );
        }
        catch (Throwable ex) {

            throw new RbpsRuntimeException("Unable to build xom dependents.", ex);
        }
    }


    private boolean isDeniedAward(final FindDependentOnAwardResponse response) {

        boolean isDeniedAward       = false;
        Date previousDecisionDate   = null;

        List<DependencyDecisionVO> dependentOnAwardList = response.getReturn()
                                                                  .getDependentOnAwardService()
                                                                  .getDependentOnAwardService();

        for (DependencyDecisionVO dependencyDecisionVO : dependentOnAwardList) {

            Date currentDecisionDate = SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecisionVO.getDecisionDate() );

            if (previousDecisionDate == null) {

                previousDecisionDate = currentDecisionDate;
            }

            if (currentDecisionDate.after(previousDecisionDate)
                 || currentDecisionDate.equals(previousDecisionDate) ) {

                isDeniedAward = dependencyDecisionVO.getDependencyStatusType().equalsIgnoreCase(NOT_AWARD_DEPENDENT);
            }
        }

        return isDeniedAward;
    }


    private void logIfDependentDeniedAward(final Dependent dependent) {

    	CommonUtils.log(logger, String.format(">%s, %s/%d< denied award: >%s<",
                                         dependent.getLastName(),
                                         dependent.getFirstName(),
                                         dependent.getCorpParticipantId(),
                                         dependent.isDeniedAward()) );
    }

}
