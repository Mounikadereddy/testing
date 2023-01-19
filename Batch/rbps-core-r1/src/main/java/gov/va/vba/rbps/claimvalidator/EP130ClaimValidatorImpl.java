/*
 * EP130ClaimValidatorImpl.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimvalidator;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsClaimDataException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;


/**
 *      The EP130ClaimValidatorImpl class is responsible for running all
 *      pre-rules validations to ensure that our XOM object from the repository,
 *      the Veteran object, is valid and contains sane, expected data.
 */
public class EP130ClaimValidatorImpl extends ClaimValidatorInterface {

    private static Logger logger = Logger.getLogger(EP130ClaimValidatorImpl.class);

    private static final String FAIL = "Auto Dependency Processing Validation Reject Reason - ";

    private LogUtils                    logUtils            = new LogUtils( logger, true );


    /**
     * validate() invokes the following methods to validate a Veteran
     * Step 1: validateVeteran() (Validate the Veteran itself)
     *
     * @throws RbpsRuntimeException, RbpsClaimDataException
     * @return void
     */
    @Override
    public void validate( RbpsRepository repo ) {

        logUtils.debugEnter( repo );

        try {
            validateVeteran(repo);

            if ( repo.hasValidationMessages() ) {

                throw new RbpsClaimDataException( "Claims Data is not valid for processing by RBPS. Reasons : " + repo.getFormattedValidationMessages() );
            }

            repo.setValidClaim(true);
        }
        finally {

            logUtils.debugExit( repo );
        }
    }


    /** performs basic Veteran validations
     *
     * 1 - Service Connected Disability Rating Dates
     * 2 - File Number
     * 3 - Previous Award data
     *
     * @return void
     */
    private void validateVeteran(RbpsRepository repo) {

        RbpsValidationAppender.addNotNullValidation(repo.getVeteran(),                            FAIL + "A veteran must be specified.", repo);
        
        if ( repo.getVeteran() == null ) {
        	return;
        }
        
        if ( repo.getVeteran().getServiceConnectedDisabilityRating() >= 30 ) {
        	
	        RbpsValidationAppender.addNotNullValidation(repo.getVeteran().getRatingDate(),            FAIL + "Veteran must have a Rating with a Rating Date.", repo);
	        RbpsValidationAppender.addNotNullValidation(repo.getVeteran().getRatingEffectiveDate(),   FAIL + "Veteran must have a Rating with a Rating Effective Date.", repo);
	        RbpsValidationAppender.addNotNullValidation(repo.getVeteran().getAwardStatus(),           FAIL + "Veteran must have existing Award.", repo);
        }
	}

}
