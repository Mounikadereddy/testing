/*
 * Veteran.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.coreframework.xom;


import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Veteran extends Person implements Serializable {

    private static final long serialVersionUID = -43149340158075762L;

    private boolean             isReceivingMilitaryRetirePay;
    private boolean             hasPOA;
    private double              serviceConnectedDisabilityRating;
    private String              fileNumber;
    private Date                ratingDate;
    private Date                ratingEffectiveDate;
    private Date				firstChangedDateofRating;
    private SalutationType      salutationType;
    private AwardStatus         awardStatus;
    private Claim               claim;
    private MaritalStatusType   maritalStatusType;
    private Phone               dayTimePhone;
    private Phone               nightTimePhone;
    private boolean				hasMilitaryPay;
    private double				currentMonthlyAmount;
    private int					totalNumberOfDependents;
    private Date                allowableDate;
    private boolean             priorSchoolTermRejected;
    private String              priorSchoolTermDatesString;
    private String              priorSchoolChildName;
    private String 				paymentType;
    private boolean				hasStepChildOrUndefinedChildType;
    private Boolean             netWorthOverLimit;
    private BigDecimal          netWorthLimit;
    private boolean             isPensionAward;
    private String 				withholdingType;
    private boolean 			hasRetiredPay;
    private boolean 			hasSeverancePay;
    private boolean 			hasSeparationPay;
    private String 			severanceWithholdingPara;
    private String 			separationWithholdingPara;
    private boolean 			hasBlindStatus;

  
	public Boolean isNetWorthOverLimit() {
        return netWorthOverLimit;
    }

    public void setNetWorthOverLimit(Boolean netWorthOverLimit) {
        this.netWorthOverLimit = netWorthOverLimit;
    }

	public String getPriorSchoolTermDatesString() {
		return priorSchoolTermDatesString;
	}

	public void setPriorSchoolTermDatesString(String priorSchoolTermDatesString) {
		this.priorSchoolTermDatesString = priorSchoolTermDatesString;
	}
	
	public String getPriorSchoolChildName() {
		return priorSchoolChildName;
	}

	public void setPriorSchoolChildName(String priorSchoolChildName) {
		this.priorSchoolChildName = priorSchoolChildName;
	}
	
    public boolean isPriorSchoolTermRejected() {
		return priorSchoolTermRejected;
	}

    
	public void setPriorSchoolTermRejected(boolean priorSchoolTermRejected) {
		this.priorSchoolTermRejected = priorSchoolTermRejected;
	}


	public SalutationType getSalutation() {
        return salutationType;
    }

    public void setSalutation(final SalutationType salutationType) {
        this.salutationType = salutationType;
    }

    public String getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(final String fileNumber) {
        this.fileNumber = fileNumber;
    }

    public double getServiceConnectedDisabilityRating() {
        return serviceConnectedDisabilityRating;
    }

    public void setServiceConnectedDisabilityRating(
            final double serviceConnectedDisabilityRating) {
        this.serviceConnectedDisabilityRating = serviceConnectedDisabilityRating;
    }

    public Date getRatingDate() {
        return ratingDate;
    }

    public void setRatingDate(final Date ratingDate) {
        this.ratingDate = ratingDate;
    }

    public Date getRatingEffectiveDate() {
        return ratingEffectiveDate;
    }

    public void setRatingEffectiveDate(final Date ratingEffectiveDate) {
        this.ratingEffectiveDate = ratingEffectiveDate;
    }

    public Date getFirstChangedDateofRating() {
        return firstChangedDateofRating;
    }

    public void setFirstChangedDateofRating(final Date firstChangedDateofRating) {
        this.firstChangedDateofRating = firstChangedDateofRating;
    }
    
    public boolean isReceivingMilitaryRetirePay() {
        return isReceivingMilitaryRetirePay;
    }

    public void setReceivingMilitaryRetirePay(
            final boolean isReceivingMilitaryRetirePay) {
        this.isReceivingMilitaryRetirePay = isReceivingMilitaryRetirePay;
    }

    public void setClaim(final Claim claim) {
        this.claim = claim;
    }

    public Claim getClaim() {
        return claim;
    }

    public boolean isSameClaimId( final long claimId) {

        return this.claim.isSameClaimId( claimId );
    }

    public void setHasPOA(final boolean hasPOA) {
        this.hasPOA = hasPOA;
    }

    public boolean hasPOA() {
        return hasPOA;
    }

    public void setAwardStatus(final AwardStatus awardStatus) {
        this.awardStatus = awardStatus;
    }

    public AwardStatus getAwardStatus() {
        return awardStatus;
    }

    public void setMaritalStatus(final MaritalStatusType maritalStaus) {
        this.maritalStatusType = maritalStaus;
    }

    public MaritalStatusType getMaritalStatus() {
        return maritalStatusType;
    }

    public void setDayTimePhone(final Phone dayTimePhone) {
        this.dayTimePhone = dayTimePhone;
    }

    public Phone getDayTimePhone() {
        return dayTimePhone;
    }

    public void setNightTimePhone(final Phone nightTimePhone) {
        this.nightTimePhone = nightTimePhone;
    }

    public Phone getNightTimePhone() {
        return nightTimePhone;
    }
    
    public void setHasMilitaryPay(final boolean hasMilitaryPay) {
        this.hasMilitaryPay = hasMilitaryPay;
    }
    
    public boolean hasMilitaryPay() {
        return hasMilitaryPay;
    }
    
    public double getCurrentMonthlyAmount() {
    	return currentMonthlyAmount;
    }
    public void setCurrentMonthlyAmount( double currentMonthlyAmount) {
    	this.currentMonthlyAmount = currentMonthlyAmount;
    }
    
    public int getTotalNumberOfDependents() {
    	return totalNumberOfDependents;
    }
    public void setTotalNumberOfDependents( int totalNumberOfDependents) {
    	this.totalNumberOfDependents = totalNumberOfDependents;
    }
    
    public Date getAllowableDate() {
        return allowableDate;
        
    }

    public void setAllowableDate(final Date allowableDate) {
        this.allowableDate = allowableDate;
    }
    public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	
    public boolean isHasStepChildOrUndefinedChildType() {
		return hasStepChildOrUndefinedChildType;
	}

	public void setHasStepChildOrUndefinedChildType(
			boolean hasStepChildOrUndefinedChildType) {
		this.hasStepChildOrUndefinedChildType = hasStepChildOrUndefinedChildType;
	}
	 public void setIsPensionAward(boolean hasPension) {
	        this.isPensionAward = hasPension;
	    }

	    public boolean isPensionAward() {
	        return this.isPensionAward;
	    }
	    public BigDecimal getNetWorthLimit() {
	        return netWorthLimit;
	    }

	    public void setNetWorthLimit(BigDecimal netWorthLimit) {
	        this.netWorthLimit = netWorthLimit;
	    }

		public String getWithholdingType() {
			return withholdingType;
		}

		public void setWithholdingType(String withholdingType) {
			this.withholdingType = withholdingType;
		}

		public String getWithholdingPara() {
			return severanceWithholdingPara;
		}

		public void setWithholdingPara(String withholdingPara) {
			this.severanceWithholdingPara = withholdingPara;
		}
	   public boolean isHasRetiredPay() {
			return hasRetiredPay;
		}

		public void setHasRetiredPay(boolean hasRetiredPay) {
			this.hasRetiredPay = hasRetiredPay;
		}

		public boolean isHasSeverancePay() {
			return hasSeverancePay;
		}

		public void setHasSeverancePay(boolean hasSeverancePay) {
			this.hasSeverancePay = hasSeverancePay;
		}

		public boolean isHasSeparationPay() {
			return hasSeparationPay;
		}

		public void setHasSeparationPay(boolean hasSeparationPay) {
			this.hasSeparationPay = hasSeparationPay;
		}

		public String getSeveranceWithholdingPara() {
			return severanceWithholdingPara;
		}

		public void setSeveranceWithholdingPara(String severanceWithholdingPara) {
			this.severanceWithholdingPara = severanceWithholdingPara;
		}

		public String getSeparationWithholdingPara() {
			return separationWithholdingPara;
		}

		public void setSeparationWithholdingPara(String separationWithholdingPara) {
			this.separationWithholdingPara = separationWithholdingPara;
		}

	   public boolean isHasBlindStatus() {
			return hasBlindStatus;
		}

		public void setHasBlindStatus(boolean hasBlindStatus) {
			this.hasBlindStatus = hasBlindStatus;
		}

	@Override
    public String toString() {

        long    claimId = 0;

        if ( claim != null ) {

            claimId = claim.getClaimId();
        }

        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("File #",                               fileNumber )
                .append("Claim id",                             claimId )
                .appendSuper(super.toString())
                .append("Disability Rating",                    serviceConnectedDisabilityRating )
                .append("Rating Date",                          ratingDate )
                .append("Rating Effective Date",                ratingEffectiveDate )
                .append("FCDR", 								 firstChangedDateofRating)
                .append("Receiving Military Retire Pay?",       isReceivingMilitaryRetirePay )
                .append("Salutation",                           salutationType )
                .append("Award Status",                         awardStatus )
                .append("Claim",                                claim )
                .append("Has POA?",                             hasPOA )
                .append("Has Military Pay?",                    hasMilitaryPay )
                .append("Marital Status",                       maritalStatusType )
                .append("Day Time Phone",                       dayTimePhone )
                .append("Night Time Phone",                     nightTimePhone )
                .append("Allowable Date",     					allowableDate)
                .append("Prior School Term Dates String",     	priorSchoolTermDatesString)
                .append("Prior School Child Name",     	    	priorSchoolChildName)
                .append("Is Prior School Term Rejected?",     	priorSchoolTermRejected)
                .append("paymentType",   					  	paymentType)
                .append("StepChild Or Undefined ChildType",     hasStepChildOrUndefinedChildType )
                .append("is Pension Award?",                    isPensionAward )
                .append("withholding Type",   					withholdingType )
                .append("hasRetiredPay",                   		hasRetiredPay)
                .append("has Severance Pay",                  	hasSeverancePay)
                .append("has Separation Pay",                   hasSeparationPay)
                .append("severance Withholding Para",           severanceWithholdingPara)
                .append("separation Withholding Para",          separationWithholdingPara)
                .append("has Blind Status",        				hasBlindStatus)
                .toString();
    }

}
