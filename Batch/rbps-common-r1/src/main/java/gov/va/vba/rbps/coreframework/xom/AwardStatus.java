/*
 * AwardStatus.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.xom;


import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 *      used to determine if the current award is in a state
 *      that's ok to process.
 *
 *      The award status must be:
 *
 *          * no changes made
 *          * not suspended
 *          * no GAO used
 *          * no attorney fee agreement
 *          * is running true
 *          * not proposed
 */
public class AwardStatus implements Serializable {

    private static final long serialVersionUID = 5040433765807189366L;

    private boolean    changesMade;
    private boolean    isSuspended;
    private boolean    isGAOUsed;
    private boolean    hasAttorneyFeeAgreement;
    private int        numDependentsOnAward;
    private boolean    isRunningAward;          //      active compensation is current
    private boolean    isProposed;              //      rating changes


    public void setChangesMade(final boolean changesMade) {
        this.changesMade = changesMade;
    }

    public boolean isChangesMade() {
        return changesMade;
    }

    public void setIsSuspended(
            final boolean isCurrentNotSuspendedOrTerminated) {
        this.isSuspended = isCurrentNotSuspendedOrTerminated;
    }

    public boolean isSuspended() {
        return isSuspended;
    }

    public void setGAOUsed(final boolean isGAOUsed) {
        this.isGAOUsed = isGAOUsed;
    }

    public boolean isGAOUsed() {
        return isGAOUsed;
    }

    public void setHasAttorneyFeeAgreement(final boolean hasAttorneyFeeAgreement) {
        this.hasAttorneyFeeAgreement = hasAttorneyFeeAgreement;
    }

    public boolean hasAttorneyFeeAgreement() {
        return hasAttorneyFeeAgreement;
    }

    public void setNumDependentsOnAward(final int numDependentsOnAward) {
        this.numDependentsOnAward = numDependentsOnAward;
    }

    public int getNumDependentsOnAward() {
        return numDependentsOnAward;
    }

    public void setRunningAward(final boolean isRunningAward) {
        this.isRunningAward = isRunningAward;
    }

    public boolean isRunningAward() {
        return isRunningAward;
    }

    public void setProposed(final boolean isProposed) {
        this.isProposed = isProposed;
    }

    public boolean isProposed() {
        return isProposed;
    }

    @Override
    public String toString() {
        return new ToStringBuilder( this, ToStringStyle.MULTI_LINE_STYLE)
                .append( "Changes Made?",                   changesMade )
                .append( "Is Suspended?",                   isSuspended )
                .append( "Is GAO Used?",                    isGAOUsed )
                .append( "Has Attorney Fee Agreement?",     hasAttorneyFeeAgreement )
                .append( "Is Running Award?",               isRunningAward )
                .append( "Is Proposed?",                    isProposed )
                .append( "# Dependents on Award",           numDependentsOnAward )
                .toString();
    }
}
