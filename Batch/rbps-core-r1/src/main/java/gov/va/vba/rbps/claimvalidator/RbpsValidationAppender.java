/*
 * EP130ClaimValidatorImpl.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimvalidator;


import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.ClaimLabelType;

import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;


public class RbpsValidationAppender {


	// static class now
	private RbpsValidationAppender(){}

    public static final void addNotNullValidation(final Object obj, final String message, RbpsRepository repo) {

        if (obj != null) {

            return;
        }

        repo.addValidationMessage(message);
    }

    public static final void addNotFutureDateValidation( final Date date, final String message, RbpsRepository repo ) {
    	
    	if ( date != null && SimpleDateUtils.isInFuture( date ) ) {
    		
    		repo.addValidationMessage(message);
    	}
    }

    public static final void addNullValidation(final Object obj, final String message, RbpsRepository repo) {

        if (obj == null) {

            return;
        }

        repo.addValidationMessage(message);
    }


    public static final void addCustomMessage(final String message, RbpsRepository repo) {

        if (message == null) {

            return;
        }

        repo.addValidationMessage(message);
    }


    public static final void addNonEmptyStringValidation(final String string, final String message, RbpsRepository repo) {

        if (string == null || string.trim().length() == 0) {

            repo.addValidationMessage(message);
        }
    }


    public static final <E> void addNonEmptyListValidation(final List<E> list, final String message, RbpsRepository repo) {

        if ( ! CollectionUtils.isEmpty( list ) ) {

            return;
        }

        repo.addValidationMessage(message);
    }


    public static final void addNonZeroNumberValidation(final Number number, final String message, RbpsRepository repo) {

        if (number == null || number.longValue() == 0) {

            repo.addValidationMessage(message);
        }
    }


    public static final void addIsValidClaimLabel(final ClaimLabelType label, final String message, RbpsRepository repo) {

        if (label == null) {

            repo.addValidationMessage(message);
        } else {

            for (ClaimLabelType labelType : ClaimLabelType.values()) {

                if ( ! (  labelType.getValue().endsWith("Reject") || ( labelType.getValue().endsWith("Exception") ) ) && labelType.equals(label) ) {
                    return;
                }
            }

            repo.addValidationMessage(message);
        }
    }

}
