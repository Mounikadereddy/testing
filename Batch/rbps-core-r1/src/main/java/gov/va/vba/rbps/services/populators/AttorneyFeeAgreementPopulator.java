/*
 * AttorneyFeeAgreementPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.impl.AttorneyFeeAgreementType;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.AwardStatus;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFlashesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Flash;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.util.CollectionUtils;


/**
 *      Convert data we get from the findFlashes WS to corporate data
 *      on the repository.  Sets whether or not the veteran has an
 *      attorney fee agreement.
 */
public class AttorneyFeeAgreementPopulator {

    private static Logger logger = Logger.getLogger(AttorneyFeeAgreementPopulator.class);

    private LogUtils                logUtils    = new LogUtils( logger, true );
    private List<String>            lower       = new ArrayList<String>();


   /**
    * Process data from the findFlashes web service and sets
    * whether or not the veteran has an attorney fee agreement.
    *
    * @author Tom.Corbin
    * @param response - this is the response from the findFlashes web service.
    *               it contains the information from the corporate database
    *               about all the flashes a veteran may have.
    * @throws RbpsServiceException
    */
    public void populateFromFlashes( final FindFlashesResponse response, final RbpsRepository repository ) {

        try {

//            logResponse( response );

            List<String> lower = getLowerCaseIndicators();

            boolean    hasAttorneyFeeAgreement = false;
            for ( Flash flash : response.getReturn().getFlashes() ) {

//                logFlash( flash );

                if ( flashIndicatesAttorneyFeeAgreement( lower, flash, repository ) ) {
                    hasAttorneyFeeAgreement = true;

                    break;
                }

//                if ( ! flashAssigned( flash ) ) {
//
//                    break;
//                }
            }

            logIfVeteranHasAgreement( hasAttorneyFeeAgreement, repository );
            recordHasAttorneyFeeAgreement( hasAttorneyFeeAgreement, repository );
            flashIndicatesBlindStatus(response,repository);
        }
        catch ( Throwable ex ) {

            throw new RbpsRuntimeException( "Unable to build corporate dependents.", ex );
        }
    }


    private void recordHasAttorneyFeeAgreement( final boolean hasAttorneyFeeAgreement, final RbpsRepository repository ) {

        if ( repository.getVeteran().getAwardStatus() == null ) {

        	repository.getVeteran().setAwardStatus( new AwardStatus() );
        }

        repository.getVeteran()
                .getAwardStatus()
                .setHasAttorneyFeeAgreement( hasAttorneyFeeAgreement );
    }


    private boolean flashIndicatesAttorneyFeeAgreement( final List<String>   lower,
                                                        final Flash          flash,
                                                        final RbpsRepository repository) {

        boolean found = lower.contains( flash.getFlashName().toLowerCase().trim() )
                        && flashAssigned( flash );

        if ( found ) {
            logUtils.log( "*************** found it **************\n\t" + flash.getFlashName(), repository );
        }

        return found;
    }

    
	public void flashIndicatesBlindStatus(final FindFlashesResponse response, final RbpsRepository repository) {

		boolean blindStatus = false;

		for (Flash flash : response.getReturn().getFlashes()) {
			blindStatus = ("blind".equals(flash.getFlashName().toLowerCase().trim()) && flashAssigned( flash ))||
							("converted - blind".equals(flash.getFlashName().toLowerCase().trim()) && flashAssigned( flash ));

			if (blindStatus) {
				
				logUtils.log("*************** found it Blind **********\n\t" + flash.getFlashName(), repository);
				break;
			}

		}
		repository.getVeteran().setHasBlindStatus(blindStatus);;
	}

    //
    //      Corey says that all the assigned flashes are at the top, so once
    //      we find an unassigned flash, we can skip the rest.
    //
    private boolean flashAssigned( final Flash flash ) {

        return CommonUtils.indicatorIsPositive( flash.getAssignedIndicator() );
    }


    private List<String> getLowerCaseIndicators() {

        if ( ! CollectionUtils.isEmpty( lower ) ) {

            return lower;
        }


        Arrays.asList(  AttorneyFeeAgreementType.values() );
        for ( AttorneyFeeAgreementType indicator : AttorneyFeeAgreementType.values() ) {

            lower.add( indicator.getValue().toLowerCase() );
        }

        return lower;
    }


    private void logIfVeteranHasAgreement( final boolean hasAttorneyFeeAgreement, final RbpsRepository repository ) {

        Veteran    veteran = repository.getVeteran();
        logUtils.log(  String.format( "%s has an attorney fee agreement: >%s<",
        		CommonUtils.getStandardLogName( veteran ),
                                      hasAttorneyFeeAgreement ), repository );
    }


}
