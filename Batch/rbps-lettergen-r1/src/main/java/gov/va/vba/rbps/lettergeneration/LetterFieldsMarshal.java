/*
 * LetterFieldsMarshal.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;

import gov.va.vba.rbps.coreframework.dto.DisplayAddressVO;
import gov.va.vba.rbps.coreframework.xom.FormType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



/**
 *      This class is a simplification of the data in the XOM classes
 *      and the output of the AWARD web services.  We want the code
 *      in the velocity template to be very simple.  Plus it places
 *      any changes due to the XOM or AWARD code here in java, where
 *      it's more easily picked up by a refactoring in the IDE.
 *
 *      This data structure is passed to velocity and contains all the
 *      variables that velocity uses to insert values into the template.
 *
 *      In the velocity template, it would be completely missed.
 *
 *      @author vafscgopalak
 */
public final class LetterFieldsMarshal {

    private String                  letterHeader;
    private String                  sectionId;
    private String                  css;
    private String 					today;
    
    private String                  sojLine1;
    private String                  sojLine2;
    private String                  sojLine3;
    private String                  sojCity;
    private String					sojCityStateCountryZip;
    private String                  sojCountry;
    private String                  sojState;
    private String                  sojZipPrefix;
    private String                  sojZipSuffix1;
    private String                  sojZipSuffix2;
    
    private String                  firstLast;
    private String                  fmLast;
    private String                  lastFirst;

    private String                  fiduciaryName;
    private String                  fiduciaryPrepositionalPhrase;
    private String                  fiduciaryAttentionText;
    
    private String                  line1;
    private String                  line2;
    private String                  line3;
    private String                  city;
    private String                  country;
    private String                  state;
    private String                  zipPrefix;
    private String                  zipSuffix1;
    private String                  zipSuffix2;
    private String					zip;
    private String					cityStateCountryZip;
    
    private String                  salutation;

    private boolean                 hasPOA;
	private boolean                 forPoa;
    private String					poaNoPoaText;
    private String                  poaName;

    private PaymentTypeMarshal		paymentType;

	private PriorTermMarshal		priorTerm;
    
	private OverPaymentMarshal		overPymt;
	
	private String                  senderName;
    private String                  senderTitle;

    private String                  claimDate;
    private String 					claimNumber;
    
    private String 					fileNumber;
    private String 					lastName;
    private String 					firstName;
    private String					middleName;
    private String					spouseName;
    private	String 					joinedApprovalChildNames;
    private ArrayList<String>		approvalChildNames;
    private String					approvalSpouseName;
    
    private String                  awardAmount;
    private String                  paymentChangeDate;
    private String                  reasonForChange;
    private long                    totalDependents;
    private String					currentMonthlyAmount;
    private String					totalDependentsSentence;
    private String					yourPaymentIncludes;
    private String					weMadeThisDecisionText;
    private String					letUsKnowText;
    private String					denialText;
    
    private List<String>            childrenNames       = new ArrayList<String>();
    private String                  evidences;
    private String                  signature;
    private String                  serviceOrg;
    private List<String>            decisionFormTypes   = new ArrayList<String>();
    private String 					form1;
    private String					form2;
    private String                  pensionGrantNoIncome;
    private String					hereIsWhatToDoCallUs;
    private boolean					hasApprovals;
    private boolean					hasDenials;
    private boolean					hasMilitaryPay;
    
    private String					partOrAll;
    private String					toDate;
    
    private Collection<AwardSummaryMarshal> approvals;
    private Collection<AwardSummaryMarshal> denials;
    private List<BenefitInformation> benefitInformation;
    private List<String> benefitDetailList;
    private List<ExplanationPayment> explanationPayment;

    
    private String 					sendCcTo;
    
    private DisplayAddressVO participantAddressPostalVO;
    private ArrayList<String>		metList;
    
    private String withholdingType;
    private String withholdingPara;
    private boolean hasRetiredPay;
    private boolean hasSeverancePay;
    private boolean hasSeparationPay;
    private String severanceWithholdingPara;
    private String separationWithholdingPara;
    private boolean hasBlindStatus;



    public DisplayAddressVO getParticipantAddressPostalVO() {
    	/*DisplayAddressVO newpoAddress=new DisplayAddressVO();
    	newpoAddress.setDisplayAddressLine1("vet POA");
    	newpoAddress.setDisplayAddressLine2("123 main st");
    	newpoAddress.setDisplayAddressLine3("tampa,FL 33547");
    	return newpoAddress;*/
		return participantAddressPostalVO;
	}
	public void setParticipantAddressPostalVO(DisplayAddressVO participantAddressPostalVO) {
		this.participantAddressPostalVO = participantAddressPostalVO;
	}
	public String getSendCcTo() {
		return sendCcTo;
	}
	public void setSendCcTo(String sendCcTo) {
		this.sendCcTo = sendCcTo;
	}
	public String getLetterHeader() {
        return letterHeader;
    }
    public void setLetterHeader(final String letterHeader) {
        this.letterHeader = letterHeader;
    }


    public String getLine1() {

        return line1;
    }
    public void setLine1( final String    line1 ) {
        this.line1 = line1;
    }
    

    public String getLine2() {

        return line2;
    }
    public void setLine2( final String line2 ) {

        this.line2 = line2;
    }


    public String getLine3() {

        return line3;
    }
    public void setLine3( final String line3 ) {

        this.line3 = line3;
    }
    

    public String getCity() {

        return city;
    }
    public void setCity( final String city ) {

        this.city = city;
    }
    

    public String getCountry() {

        return country;
    }
    public void setCountry( final String country ) {

        this.country = country;
    }
    

    public String getState() {
        return state;
    }
    public void setState( final String state ) {
        this.state = ( state != null ) ? state : "";
    }


    public String getZipPrefix() {

        return zipPrefix;
    }
    public void setZipPrefix( final String zipPrefix ) {

        this.zipPrefix = zipPrefix;
    }
    

    public String getZipSuffix1() {

        return zipSuffix1;
    }
    public void setZipSuffix1( final String zipSuffix1 ) {

        this.zipSuffix1 = zipSuffix1;
    }
    

    public String getZipSuffix2() {

        return zipSuffix2;
    }
    public void setZipSuffix2( final String zipSuffix2 ) {

        this.zipSuffix2 = zipSuffix2;
    }
    
    
    public String getZip() {

        return zip;
    }
    public void setZip( String zip ) {

        this.zip = zip;
    }
    
    
    public String getCityStateCountryZip() {

        return cityStateCountryZip;
    }
    public void setCityStateCountryZip( String cityStateCountryZip ) {

        this.cityStateCountryZip = cityStateCountryZip;
    }
    
    
    public boolean getHasPOA() {

        return hasPOA;
    }
    public void setHasPOA( final boolean hasPOA ) {

        this.hasPOA = hasPOA;
    }

    
    public String getPoaNoPoaText() {
        
        return poaNoPoaText;
    }
    public void setPoaNoPoaText( String poaNoPoaText ) {
        
        this.poaNoPoaText = poaNoPoaText;
    }
    
    
    public String getClaimNumber() {
        
        return claimNumber;
    }
    public void setClaimNumber( String claimNumber) {
        
        this.claimNumber = claimNumber;
    }

    
    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(final String sectionId) {
        this.sectionId = sectionId;
    }

    
    public String getFileNumber() {

        return fileNumber;
    }
    public void setFileNumber( String fileNumber) {

        this.fileNumber = fileNumber;
    }


    public String getLastName() {
    	
        return lastName;
    }
    public void setLastName( String lastName ) {
    	
        this.lastName = lastName;
    }


    public String getFirstName() {
    	
        return firstName;
    }
    public void setFirsttName( String firstName ) {
    	
         this.firstName = firstName;
    }


    public String getMiddleName() {
    	
        return middleName;
    }
    public void setMiddleName( String middleName ) {
    	
        this.middleName = middleName;
    }

    
    public String getSpouseName() {

    	return spouseName;
    }
    public void setSpouseName( String spouseName) {

    	this.spouseName = spouseName;;
    }



    public String getCss() {
        return css;
    }
    public void setCss(final String css) {
        this.css = css;
    }


    public String getClaimDate() {

        return claimDate;
    }
    public void setClaimDate(final String claimDate) {
        this.claimDate = claimDate;
    }


    public String getAwardAmount() {
        return awardAmount;
    }
    public void setAwardAmount(final String awardAmount) {
        this.awardAmount = awardAmount;
    }


    public String getPaymentChangeDate() {
        return paymentChangeDate;
    }
    public void setPaymentChangeDate(final String paymentChangeDate) {
        this.paymentChangeDate = paymentChangeDate;
    }


    public String getReasonForChange() {
        return reasonForChange;
    }
    public void setReasonForChange(final String reasonForChange) {
        this.reasonForChange = reasonForChange;
    }


    public long getTotalDependents() {
        return totalDependents;
    }
    public void setTotalDependents(final long totalDependents) {
        this.totalDependents = totalDependents;
    }
    
    
    public String getCurrentMonthlyAmount() {
    	return currentMonthlyAmount;
    }
    public void setCurrentMonthlyAmount( double currentMonthlyAmount) {
    	this.currentMonthlyAmount = String.format( "$%.2f", currentMonthlyAmount );
    }
    
    
    public String getTotalDependentsSentence() {
    	
        return totalDependentsSentence;
    }
    public void setTotalDependentsSentence(String totalDependentsSentence) {
    	
        this.totalDependentsSentence = totalDependentsSentence;
    }    

    
    public String getYourPaymentIncludes() {
    	
    	return yourPaymentIncludes;
    }
    public void setYourPaymentIncludes( final String yourPaymentIncludes ) {
    	
    	this.yourPaymentIncludes = yourPaymentIncludes;
    }    

   
    public String getWeMadeThisDecisionText() {
    	
    	return weMadeThisDecisionText; 
    }
    public void setWeMadeThisDecisionText( final String weMadeThisDecisionText ) {
    	
    	this.weMadeThisDecisionText = weMadeThisDecisionText; 
    }
    
    
    public String getFmLast() {
		return fmLast;
	}
	public void setFmLast(String fmLast) {
		this.fmLast = fmLast;
	}
	public String getLetUsKnowText() {
    	
    	return letUsKnowText; 
    }
    public void setLetUsKnowText( final String letUsKnowText ) {
    	
    	this.letUsKnowText = letUsKnowText; 
    }    
    
    
    public String getDenialText() {
    	
    	return denialText;
    }
    public String setDenialText( final String denialText ) {
    	
    	return this.denialText = denialText;
    }   
    
    

    public List<String> getChildrenNames() {
    	
        return childrenNames;
    }
    public void setChildrenNames(final List<String> childrenNames) {
    	
        this.childrenNames = childrenNames;
    }


    public String getJoinedApprovalChildNames() {

        return joinedApprovalChildNames;
    }
    public void setJoinedApprovalChildNames( String joinedApprovalChildNames ) {

        this.joinedApprovalChildNames = joinedApprovalChildNames ;
    }

    
    public String getEvidences() {
        return evidences;
    }
    public void setEvidences(final String evidences) {
        this.evidences = evidences;
    }

    
    public String getSignature() {
        return signature;
    }
    public void setSignature(final String signature) {
        this.signature = signature;
    }


    public String getServiceOrg() {
        return serviceOrg;
    }
    public void setServiceOrg(final String serviceOrg) {
        this.serviceOrg = serviceOrg;
    }


    public String getSalutation() {

        return salutation;
    }
    public void setSalutation( final String salutation ) {

        this.salutation = salutation;
    }

    
    public String getSojLine1() {

        return sojLine1;
    }
    public void setSojLine1( final String sojLine1 ) {

        this.sojLine1 = sojLine1;
    }



    public String getSojLine2() {

        return sojLine2;
    }
    public void setSojLine2( final String sojLine2 ) {

        this.sojLine2 = sojLine2;
    }



    public String getSojLine3() {

        return sojLine3;
    }
    public void setSojLine3( final String sojLine3 ) {

        this.sojLine3 = sojLine3;
    }



    public String getSojCity() {

        return sojCity;
    }
    public void setSojCity( final String sojCity ) {

        this.sojCity = sojCity;
    }



    public String getSojCityStateCountryZip() {

        return sojCityStateCountryZip;
    }
    public void setSojCityStateCountryZip( final String sojCityStateCountryZip ) {

        this.sojCityStateCountryZip = sojCityStateCountryZip;
    }
    
    


    public String getSojCountry() {

        return sojCountry;
    }
    public void setSojCountry( final String sojCountry ) {

        this.sojCountry = sojCountry;
    }



    public String getSojState() {

        return sojState;
    }
    public void setSojState( final String sojState ) {

        this.sojState = sojState;
    }



    public String getSojZipPrefix() {

        return sojZipPrefix;
    }
    public void setSojZipPrefix( final String sojZipPrefix ) {

        this.sojZipPrefix = sojZipPrefix;
    }



    public String getSojZipSuffix1() {

        return sojZipSuffix1;
    }
    public void setSojZipSuffix1( final String sojZipSuffix1 ) {

        this.sojZipSuffix1 = sojZipSuffix1;
    }


    public String getSojZipSuffix2() {

        return sojZipSuffix2;
    }
    public void setSojZipSuffix2( final String sojZipSuffix2 ) {

        this.sojZipSuffix2 = sojZipSuffix2;
    }


    public String getSojZip() {

    	return sojZipPrefix;
    }
    public void setSojZip( String sojZipPrefix ) {

    	this.sojZipPrefix = sojZipPrefix;
    }

    
    public String getToday() {
    	
    	return today;
    }
    public void setToday( String today) {
    	
    	this.today = today;
    }
    

    public List<String> getDecisionFormTypes() {

        return decisionFormTypes;
    }
    public void setDecisionFormTypes( final List<FormType> formTypes ) {
    	
    	for( FormType formType: formTypes ){
    		
    		decisionFormTypes.add( formType.getValue() );
    	}
    }

    
    public String getForm1() {
    	
    	return form1;
    }
    public void setForm1( final String form1 ) {

        this.form1 = form1;
    }

    
    public String getForm2() {
    	
    	return form2;
    }
    public void setForm2( final String form2 ) {

        this.form2 = form2;
    }


    public String getPensionGrantNoIncome() {
        return pensionGrantNoIncome;
    }


    public void setPensionGrantNoIncome(String pensionGrantNoIncome) {
        this.pensionGrantNoIncome = pensionGrantNoIncome;
    }


    public String getHereIsWhatToDoCallUs() {
    	
    	return hereIsWhatToDoCallUs;
    }
    public void setHereIsWhatToDoCallUs( final String hereIsWhatToDoCallUs ) {

        this.hereIsWhatToDoCallUs = hereIsWhatToDoCallUs;
    } 
    
    
    public String getSenderName() {

        return senderName;
    }
    public void setSenderName( final String senderName ) {

        this.senderName = senderName;
    }



    public String getSenderTitle() {

        return senderTitle;
    }
    public void setSenderTitle( final String senderTitle ) {

        this.senderTitle = senderTitle;
    }



    public boolean getForPoa() {

        return forPoa;
    }
    public void setForPoa( final boolean forPoa ) {

        this.forPoa = forPoa;
    }



    public String getPoaName() {

        return poaName;
    }
    public void setPoaName( final String poaName ) {

        this.poaName = poaName;
    }



    public String getFirstLast() {

        return firstLast;
    }
    public void setFirstLast( final String firstLast ) {

        this.firstLast = firstLast;
    }



    public String getLastFirst() {

        return lastFirst;
    }
    public void setLastFirst( final String lastFirst ) {

        this.lastFirst = lastFirst;
    }


    public boolean getHasApprovals() {

        return hasApprovals;
    }
    public void setHasApprovals(boolean hasApprovals) {

        this.hasApprovals = hasApprovals;
    }

    
    public boolean getHasDenials() {

        return this.hasDenials;
    }
    public void setHasDenials( boolean hasDenials ) {

        this.hasDenials = hasDenials;
    }

    
    public boolean getHasMilitaryPay() {

        return this.hasMilitaryPay;
    }
    public void setHasMilitaryPay( boolean hasMilitaryPay ) {

        this.hasMilitaryPay = hasMilitaryPay;
    }


    public String getToDate() {
        return toDate;
    }    
    public void setToDate(final String toDate) {
        this.toDate = toDate;
    }


    public String getPartOrAll() {
        return partOrAll;
    } 
    public void setPartOrAll(final String partOrAll) {
        this.partOrAll = partOrAll;
    }

    
    
    public Collection<AwardSummaryMarshal> getApprovals() {

    	return this.approvals;
    }
    public void setApprovals(Collection<AwardSummaryMarshal> approvals ) {

        this.approvals = approvals;
    }

    
    public Collection<AwardSummaryMarshal> getDenials() {

        return this.denials;
    }
    public void setDenials( Collection<AwardSummaryMarshal> denials) {

        this.denials = denials;
    }

    
    public List<String> getApprovalChildNames() {

        return this.approvalChildNames;
    }
    public void setApprovalChildNames( List<String> approvalChildNames ) {
    	
    	if ( approvalChildNames == null ) {
    		this.approvalChildNames = null;
    		return;
    	}
    	
    	Queue<String> childQueue = new LinkedList<String>();
    	
    	for( String childName: approvalChildNames ) {
    		
    		childQueue.add( childName );
    	}

    	
    	this.approvalChildNames = new ArrayList<String>( childQueue );
    }

    
    public String getApprovalSpouseName() {

        return approvalSpouseName;
    }
    public void setApprovalSpouseName( String approvalSpouseName ) {

        this.approvalSpouseName = approvalSpouseName;
    }
    
    
    public String getFiduciaryName() {

        return this.fiduciaryName;
    }
    public void setFiduciaryName( final String fiduciaryName ) {

        this.fiduciaryName = fiduciaryName;
    }

    
    public List<String> getBenefitDetailList() {
		return benefitDetailList;
	}
	public void setBenefitDetailList(List<String> benefitDetailList) {
		this.benefitDetailList = benefitDetailList;
	}
	public void setFiduciaryPrepositionalPhrase( final String fiduciaryPrepositionalPhrase ) {

        this.fiduciaryPrepositionalPhrase = fiduciaryPrepositionalPhrase;
    }

    public String getFiduciaryPrepositionalPhrase() {

        return this.fiduciaryPrepositionalPhrase;
    }

    public void setFiduciaryAttentionText(final String fiduciaryAttentionText) {

        this.fiduciaryAttentionText = fiduciaryAttentionText;
    }

    public String getFiduciaryAttentionText() {

        return this.fiduciaryAttentionText;
    }
    
    public PaymentTypeMarshal getPaymentType() {
		return paymentType;
	}
    
	public void setPaymentType(PaymentTypeMarshal paymentType) {
		this.paymentType = paymentType;
	}
	
	public PriorTermMarshal getPriorTerm() {
		return priorTerm;
	}
	public void setPriorTerm(PriorTermMarshal priorTerm) {
		this.priorTerm = priorTerm;
	}
    
    public OverPaymentMarshal getOverPymt() {
		return overPymt;
	}
	public void setOverPymt(OverPaymentMarshal overPymt) {
		this.overPymt = overPymt;
	}
	public List<BenefitInformation> getBenefitInformation() {
		return benefitInformation;
	}
	public void setBenefitInformation(List<BenefitInformation> benefitInformation) {
		this.benefitInformation = benefitInformation;
	}
	public List<ExplanationPayment> getExplanationPayment() {
		return explanationPayment;
	}
	public void setExplanationPayment(List<ExplanationPayment> explanationPayment) {
		this.explanationPayment = explanationPayment;
	}
	public ArrayList<String> getMetList() {
		return this.metList;
	}
	public void setMetList(ArrayList<String> list) {
		this.metList = list;
	}
	public String getWithholdingType() {
		return withholdingType;
	}

	public void setWithholdingType(String withholdingType) {
		this.withholdingType = withholdingType;
	}

	public String getWithholdingPara() {
		return withholdingPara;
	}

	public void setWithholdingPara(String withholdingPara) {
		this.withholdingPara = withholdingPara;
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
}
