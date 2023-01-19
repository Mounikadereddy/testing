/*
 * Child.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. use is subject to security terms
 *
 */
package gov.va.vba.rbps.coreframework.xom;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


public class Child extends Dependent {

    private static final long serialVersionUID = 1012806344963586341L;

    private boolean             attendedSchoolLastTerm;
    private boolean             isPreviouslyMarried;
    private boolean             isSchoolChild;
    private boolean             isSeriouslyDisabledClaimed;
    private boolean             tuitionPaidByGovt;
    private Education           currentTerm;
    private Education           lastTerm;
    private List<Education>     previousTerms;
    private ChildType           childType;
    private String              unfilteredChildType;
    private Person              livingWith;
    private String              agencyPayingTuition;
    private Date                govtPaymentStartDate;
    private Address             birthAddress;
    private Award               minorSchoolChildAward;
    private Award               priorSchoolChildAward;
    private String           	priorSchoolTermStatus;
    private Date                lastTermInCorpBeginDate;   
    private Date                lastTermInCorpEndDate;
    private Date                lastTermInCorpBeginEffectiveDate;   
    private Date                lastTermInCorpEndEffectiveDate;
    public Date getLastTermInCorpBeginEffectiveDate() {
		return lastTermInCorpBeginEffectiveDate;
	}



	public void setLastTermInCorpBeginEffectiveDate(
			Date lastTermInCorpBeginEffectiveDate) {
		this.lastTermInCorpBeginEffectiveDate = lastTermInCorpBeginEffectiveDate;
	}



	public Date getLastTermInCorpEndEffectiveDate() {
		return lastTermInCorpEndEffectiveDate;
	}



	public void setLastTermInCorpEndEffectiveDate(
			Date lastTermInCorpEndEffectiveDate) {
		this.lastTermInCorpEndEffectiveDate = lastTermInCorpEndEffectiveDate;
	}



	// default constructor
    public Child() {
        previousTerms = new ArrayList<Education>();
    }



    public ChildType getChildType() {
        return childType;
    }
    public void setChildType(final ChildType childType) {
        this.childType = childType;
    }

    public String getUnfilteredChildType() {

        return unfilteredChildType;
    }
    public void setUnfilteredChildType( final String    childType ) {

        this.unfilteredChildType = childType;
    }





    public Person getLivingWith() {
        return livingWith;
    }
    public void setLivingWith(final Person livingWith) {
        this.livingWith = livingWith;
    }

    public Education getLastTerm() {
        return lastTerm;
    }

    public void setLastTerm(final Education lastTerm) {
        this.lastTerm = lastTerm;
    }

    public Award getMinorSchoolChildAward() {
        return minorSchoolChildAward;
    }

    public void setMinorSchoolChildAward(final Award minorSchoolChildAward) {
        this.minorSchoolChildAward = minorSchoolChildAward;
    }

    public Award getPriorSchoolChildAward() {
        return priorSchoolChildAward;
    }

    public void setPriorSchoolChildAward(final Award priorSchoolChildAward) {
        this.priorSchoolChildAward = priorSchoolChildAward;
    }
    
    public boolean isSeriouslyDisabled() {
        return isSeriouslyDisabledClaimed;
    }

    public void setSeriouslyDisabled(final boolean isSeriouslyDisabledClaimed) {
        this.isSeriouslyDisabledClaimed = isSeriouslyDisabledClaimed;
    }

    public Education getCurrentTerm() {
        return currentTerm;
    }

    public void setCurrentTerm(final Education currentTerm) {
        this.currentTerm = currentTerm;
    }

    public void setTuitionPaidByGovt(final boolean tuitionPaidByGovt) {
        this.tuitionPaidByGovt = tuitionPaidByGovt;
    }

    public boolean isTuitionPaidByGovt() {
        return tuitionPaidByGovt;
    }

    public void setAgencyPayingTuition(final String agencyPayingTuition) {
        this.agencyPayingTuition = agencyPayingTuition;
    }

    public String getAgencyPayingTuition() {
        return agencyPayingTuition;
    }

    public Date getGovtPaymentStartDate() {
        return govtPaymentStartDate;
    }

    public void setGovtPaymentStartDate(final Date govtPaymentStartDate) {
        this.govtPaymentStartDate = govtPaymentStartDate;
    }

    public void setSchoolChild(final boolean isSchoolChild) {
        this.isSchoolChild = isSchoolChild;
    }

    public boolean isSchoolChild() {
        return isSchoolChild;
    }

    public void setPreviouslyMarried(final boolean isPreviouslyMarried) {
        this.isPreviouslyMarried = isPreviouslyMarried;
    }

    public boolean isPreviouslyMarried() {
        return isPreviouslyMarried;
    }

    public void setBirthAddress(final Address birthAddress) {
        this.birthAddress = birthAddress;
    }

    public Address getBirthAddress() {
        return birthAddress;
    }

    public void setPreviousTerms(final List<Education> previousTerms) {
        this.previousTerms = previousTerms;
    }

    public List<Education> getPreviousTerms() {
        return previousTerms;
    }

    public void addPreviousTerm(final Education previousTerm) {
        this.previousTerms.add(previousTerm);
    }

    public void setAttendedSchoolLastTerm(final boolean attendedSchoolLastTerm) {
        this.attendedSchoolLastTerm = attendedSchoolLastTerm;
    }

    public boolean getAttendedSchoolLastTerm() {
    	
    	if ( previousTerms == null) {
    		return false;
    	}
    	return ( ! previousTerms.isEmpty() );
    }

    public String getPriorSchoolTermStatus() {
        return priorSchoolTermStatus;
    }

    public void setPriorSchoolTermStatus(String priorSchoolTermStatus) {
        this.priorSchoolTermStatus = priorSchoolTermStatus;
    }
     
    public Date getLastTermInCorpBeginDate() {
        return lastTermInCorpBeginDate;
        
    }

    public void setLastTermInCorpBeginDate(final Date lastTermInCorpBeginDate) {
        this.lastTermInCorpBeginDate = lastTermInCorpBeginDate;
    }
    
    public Date getLastTermInCorpEndDate() {
        return lastTermInCorpEndDate;
        
    }

    public void setLastTermInCorpEndDate(final Date lastTermInCorpEndDate) {
        this.lastTermInCorpEndDate = lastTermInCorpEndDate;
    }
    
    @Override
    public String toString() {

        //return ToStringBuilder.reflectionToString( this, RbpsConstants.RBPS_TO_STRING_STYLE );
      return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
              .appendSuper(super.toString())
              .append("Attended School Last Term?",   attendedSchoolLastTerm)
              .append("Is Previsouly Married?",       isPreviouslyMarried)
              .append("Is School Child?",             isSchoolChild)
              .append("Is Seriously Disabled?",       isSeriouslyDisabledClaimed)
              .append("Tuition Paid By Govt?",        tuitionPaidByGovt)
              .append("Current Term",                 currentTerm)
              .append("Last Term",                    lastTerm)
              .append("Previous Terms",               previousTerms)
              .append("Child Type",                   childType)
              .append("Living With",                  livingWith)
              .append("Govt Payment Start Date",      govtPaymentStartDate)
              .append("Birth Address",                birthAddress)
              .append("Minor School Child Award",     minorSchoolChildAward)
              .append("Prior School Child Award",     priorSchoolChildAward)
              .append("Prior School Term Status",     priorSchoolTermStatus)
              .append("Last Term In Corp Begin Date",     lastTermInCorpBeginDate)
              .append("Last Term In Corp End Date",     lastTermInCorpEndDate)
              .append("Last Term In Corp Begin Effective Date",     lastTermInCorpBeginEffectiveDate)
              .append("Last Term In Corp End Effective Date",     lastTermInCorpEndEffectiveDate)
              .toString();
    }
}
