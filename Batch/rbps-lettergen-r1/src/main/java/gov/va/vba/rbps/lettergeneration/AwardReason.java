/*
 * AwardReason.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.logging.Logger;
//import gov.va.vba.rbps.coreframework.util.CommonUtils;
//import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.PropertyRewriter;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.apache.commons.lang.builder.ToStringBuilder;



/**
 *      The reason a dependent was approved or denied.
 */
public class AwardReason {

    private static Logger logger = Logger.getLogger(AwardReason.class);


//    private CommonUtils             utils               = new CommonUtils();
//    private LogUtils                logUtils            = new LogUtils( logger, true );

    private int                     sequenceNumber;
    private String                  dependentName;
    private String                  awardLineReasonType;
    private String                  awardLineReasonTypeDescription;
    private String                  dependencyDecisionType;
    private String                  dependencyDecisionTypeDescription;
    private ReasonTranslator        translator;
    private Long                    claimId;
    private Date 					effectiveDate;
    private Date 					eventDate;
    protected long 					depndentPersonID;
    private String                  firstName;
    private String                  fullName;
    private ApprovalType            approvalType;
    private boolean                 isChild;
    private long                    corpParticipantId;
    private boolean                 currentClaim;
    private boolean                 activeDependent;


    public AwardReason() {}


    public AwardReason( final String                dependentName ) {

        this.dependentName  = dependentName;
    }


    public String getFormattedReason() {

        return getFormattedReason( false );
    }


    public boolean hasTranslationWithMissingDependent() {

        ReasonTranslation reason = getReasonTranslation();

        return reason.requiresDependent() && StringUtils.isBlank( getDependentName() );
    }


    public String getFormattedReason( final boolean   inFuture ) {

        ReasonTranslation   reason      = getReasonTranslation();
        String              translation = reason.getReason( this, approvalType, dependentName );

        translation = modifyTense( translation, inFuture );

        // Add asterisk to letter grid to claim(s) being currently processed. Denial claims do not need this as they are
        // not on the letter grid
        if(approvalType == null || approvalType.equals(ApprovalType.DENIAL)) {
            return translation;
        } else {
            return translation + (currentClaim ? " *" : "");
        }
    }


    private ReasonTranslation getReasonTranslation() {

        ReasonTranslation       reason = null;

//        logUtils.log( String.format( "have dependencyDecisionType >%s< translation: %s",
//                                     dependencyDecisionType,
//                                     translator.hasTranslation( dependencyDecisionType ) ) );
//        logUtils.log( String.format( "have awardLineReasonType >%s< translation:    ",
//                                     awardLineReasonType,
//                                     translator.hasTranslation( awardLineReasonType ) ) );

        if ( translator.hasTranslation( dependencyDecisionType ) ) {

            reason = translator.translate( this,
                                           dependencyDecisionType,
                                           dependencyDecisionTypeDescription );
        }

        if ( reason == null ) {

            reason = translator.translate( this,
                                           awardLineReasonType,
                                           awardLineReasonTypeDescription );
        }

//        if ( reason == null ) {
//
//            logUtils.log( "Couldn't translate" );
//            return new ReasonTranslation( "bogus", "bogus", "bogus" );
//        }

        return reason;
    }


    private String modifyTense( final String translation, final boolean inFuture ) {

        String      modified = translation;

        if ( ! shouldHandleFutureTense( inFuture ) ) {

            return modified;
        }

        modified = modified.replaceAll( "turned 18 and has been removed from your award",
                                        "will turn 18 and be removed from your award" );
        modified = modified.replaceAll( "turned 23 and has been removed from your award",
                                        "will turn 23 and be removed from your award" );
        modified = modified.replaceAll( "was",          "will be" );
        modified = modified.replaceAll( "turned",       "will turn" );
        modified = modified.replaceAll( "has been",     "will be" );
        modified = modified.replaceAll( "are not attending school",
                                        "will not be attending school" );

        return modified;
    }


    public boolean isEmpty() {

        return getFirstName() == null
                    && getClaimId() == null
                    && awardLineReasonType == null
                    && dependencyDecisionType == null;
    }


    private boolean shouldHandleFutureTense( final boolean inFuture ) {

        return inFuture && approvalType == ApprovalType.REMOVAL;
    }


    public void makeHtmlSafe() {

        PropertyRewriter        rewriter    = new PropertyRewriter();
        List<String>            properties  = Arrays.asList( "dependentName",
                                                             "awardLineReasonType",
                                                             "awardLineReasonTypeDescription",
                                                             "dependencyDecisionType",
                                                             "dependencyDecisionTypeDescription",
                                                             "claimId",
                                                             "firstName",
                                                             "fullName" );

        rewriter.modifyProperties( this, properties, new StringPropertyEscaper() );
    }








    public String getAwardLineReasonType() {

        return awardLineReasonType;
    }

    public String getAwardLineReasonTypeDescription() {

        return awardLineReasonTypeDescription;
    }
    public String getDependencyDecisionType() {

        return dependencyDecisionType;
    }
    public String getDependencyDecisionTypeDescription() {

        return dependencyDecisionTypeDescription;
    }


    public boolean isGrant() {

        return ApprovalType.GRANT.equals( approvalType );
    }
    public boolean isRemoval() {

        return ApprovalType.REMOVAL.equals( approvalType );
    }
    public boolean isGrantOrRemoval() {

        return ApprovalType.GRANT.equals( approvalType ) || ApprovalType.REMOVAL.equals( approvalType );
    }
    public boolean isDenial() {

        return ApprovalType.DENIAL.equals( approvalType );
    }
    public void setGrant( final boolean grant ) {

        this.approvalType = grant ? ApprovalType.GRANT : ApprovalType.DENIAL;
    }
    public void setApprovalType( final ApprovalType   approvalType ) {

        this.approvalType = approvalType;
    }


    public void setDependentName( final String dependentName ) {

        this.dependentName = dependentName;
    }
    public String getDependentName() {

        return dependentName;
    }


    public String getFirstName() {

        return firstName;
    }
    public void setFirstName( final String firstName ) {

        this.firstName = firstName;
    }


    public String getFullName() {

        return fullName;
    }
    public void setFullName( final String fullName ) {

        if ( StringUtils.isBlank( fullName ) ) {

            this.fullName = "";

            return;
        }

        this.fullName = WordUtils.capitalize( fullName.toLowerCase() );
    }



    public boolean isChild() {

        return isChild;
    }
    public void setChild( final boolean isChild ) {

        this.isChild = isChild;
    }



    public boolean hasClaimId() {

        return claimId != null;
    }
    public Long getClaimId() {

        return claimId;
    }
    public void setClaimId( final Long claimId ) {

        this.claimId = claimId;
    }


    public void setDependencyDecisionType( final String dependencyDecisionType ) {

        this.dependencyDecisionType = dependencyDecisionType;
    }


    public void setDependencyDecisionTypeDescription( final String dependencyDecisionTypeDescription ) {

        this.dependencyDecisionTypeDescription = dependencyDecisionTypeDescription;
    }


    public void setAwardLineReasonType( final String awardLineReasonType ) {

        this.awardLineReasonType = awardLineReasonType;
    }


    public void setAwardLineReasonTypeDescription( final String awardLineReasonTypeDescription ) {

        this.awardLineReasonTypeDescription = awardLineReasonTypeDescription;
    }


    public void setTranslator( final ReasonTranslator translator ) {

        this.translator = translator;
    }



    public long getCorpParticipantId() {

        return corpParticipantId;
    }
    public void setCorpParticipantId( final long corpParticipantId ) {

        this.corpParticipantId = corpParticipantId;
    }



    public int getSequenceNumber() {

        return sequenceNumber;
    }
    public void setSequenceNumber( final int sequenceNumber ) {

        this.sequenceNumber = sequenceNumber;
    }

    public Date getEffectiveDate() {
    	
    	return effectiveDate;
    }
    public void setEffectiveDate( Date effectiveDate ) {
    	
    	this.effectiveDate = effectiveDate;
    }

    public boolean getCurrentClaim() {
        return currentClaim;
    }

    public void setCurrentClaim(boolean currentClaim) {
        this.currentClaim = currentClaim;
    }


    public Date getEventDate() {
		return eventDate;
	}


	public void setEventDate(Date eventDate) {
		this.eventDate = eventDate;
	}


	public boolean isActiveDependent() {
		return activeDependent;
	}


	public void setActiveDependent(boolean activeDependent) {
		this.activeDependent = activeDependent;
	}


	public long getDepndentPersonID() {
		return depndentPersonID;
	}


	public void setDepndentPersonID(long depndentPersonID) {
		this.depndentPersonID = depndentPersonID;
	}


	@Override
    public boolean equals( final Object obj ) {

        if ( this == obj ) {
            return true;
        }
        if ( obj == null ) {
            return false;
        }
        if ( !(obj instanceof AwardReason) ) {
            return false;
        }

        AwardReason other = (AwardReason) obj;

        if ( corpParticipantId != other.corpParticipantId ) {

//            logUtils.log( String.format( "%s is not equal to %s", getFirstName(), other.getFirstName() ) );
            return false;
        }

//        logUtils.log( String.format( "%s IS equal to %s", getFirstName(), other.getFirstName() ) );
        return true;
    }

    @Override
    public int hashCode() {

        final int   prime   = 31;
        int         result  = 1;

        result = prime * result
                + (int) (corpParticipantId ^ (corpParticipantId >>> 32));

//        logUtils.debug( String.format( "%s hash code is %d for participant id %d", getFirstName(), result, getCorpParticipantId() ) );
        return result;
    }


//    @Override
//    public int hashCode() {
//
//        int code = new HashCodeBuilder().append( getCorpParticipantId() ).hashCode();
//
//        logUtils.log( String.format( "%s hash code is %d for participant id %d", getFirstName(), code, getCorpParticipantId() ) );
//        return code;
//    }


    @Override
    public String toString() {

        return new ToStringBuilder( this )
            .append( "sequence number",                         getSequenceNumber() )
            .append( "dependent name",                          getDependentName() )
            .append( "award line reason type",                  awardLineReasonType )
            .append( "award line reason type description",      awardLineReasonTypeDescription )
            .append( "dependency decision type",                dependencyDecisionType )
            .append( "dependency decision type description",    dependencyDecisionTypeDescription )
            .append( "effective date",                          effectiveDate ) 
            .append( "event date",                              eventDate )
            .append( "is child",                                isChild() )
            .append( "first name",                              getFirstName() )
            .append( "full name",                               getFullName() )
            .append( "approval type",                           approvalType )
            .append( "claim id",                                claimId )
            .append( "corp participant id",                     getCorpParticipantId() )
            .append( "is current claim",                        currentClaim )
            .append( "dependent active",                        activeDependent )
            .toString();
    }
}
