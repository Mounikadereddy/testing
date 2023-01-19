/*
 * LetterFields.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.DisplayAddressVO;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.PropertyRewriter;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.*;
import gov.va.vba.rbps.lettergeneration.batching.util.BatchingUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;



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
 *      @author vafsccorbit
 */
public final class LetterFields {

    private static Logger logger = Logger.getLogger(LetterFields.class);

    private LogUtils                logUtils                = new LogUtils( logger, true );

    private RbpsRepository          repository;
    private Veteran                 veteran;
    private AwardSummariesFilter    awardSummariesFilter;

    private String                  letterHeader;
    private String                  sectionId;
    private String                  css;
    private String                  salutation;

    private String                  firstLast;
    private String                  fmLast;
    private String                  lastFirst;
    private String                  line1;
    private String                  line2;
    private String                  line3;
    private String                  city;
    private String                  country;
    private String                  state;
    private String                  zipPrefix;
    private String                  zipSuffix1;
    private String                  zipSuffix2;

    private String                  sojLine1;
    private String                  sojLine2;
    private String                  sojLine3;
    private String                  sojCity;
    private String                  sojCountry;
    private String                  sojState;
    private String                  sojZipPrefix;
    private String                  sojZipSuffix1;
    private String                  sojZipSuffix2;

    private boolean                 hasPOA;
    private boolean                 forPoa;
    private String                  poaName;

    private boolean                 hasPension;
    private BigDecimal              pensionNetWorthLimit;

    private String                  fiduciaryName;
    private String                  fiduciaryPrepositionalPhrase;
    private String                  fiduciaryAttentionText;

    private String                  senderName;
    private String                  senderTitle;

    private Date                    claimDate;
    private String                  awardAmount;
    private String                  paymentChangeDate;
    private String                  reasonForChange;
    private long                    totalDependents;
    private double					currentMonthlyAmount;
    private List<String>            childrenNames       = new ArrayList<String>();
    private String                  evidences;
    private String                  signature;
    private String                  serviceOrg;
    private List<FormType>          decisionFormTypes   = new ArrayList<FormType>();
    private List<AwardSummary>      summaries;
    private List<BenefitInformation> benefitInformation;
    private List<String> benefitDetailList;
    private List<ExplanationPayment> explanationPayment;
    private Date 					toDate;
    private String 					partOrAll;
    
    private boolean					hasApprovals;
    private boolean					hasDenials;
    private boolean					hasMilitaryPay;
    
    private Collection<AwardSummary> approvals;
    private Collection<AwardSummary> denials;
    private ArrayList<String>		 approvalChildNames;
    private String					 approvalSpouseName;
    private boolean					priorSchoolTermRejected;
    private String					priorSchoolTermDatesString;
    private String					priorSchoolChildName;
    
    private String 					sendCcTo;
    
    private boolean					isOverPayment;
    
    private DisplayAddressVO participantAddressPostalVO;
    
    private String withholdingType;
    private String withholdingPara;
    private boolean hasRetiredPay;
    private boolean hasSeverancePay;
    private boolean hasSeparationPay;
    private String severanceWithholdingPara;
    private String separationWithholdingPara;
    private boolean hasBlindStatus;

	public LetterFields(){
    	
    }

    public LetterFields( final RbpsRepository               repo,
                         final List<AwardSummary>           summaries ) {

        this.repository         = repo;
        this.veteran            = repo.getVeteran();
        this.summaries          = summaries;
        awardSummariesFilter    = new AwardSummariesFilter();
    }


    public void init() {

    	logUtils.log( "Begin LetterFields.init()\n", repository );
        if ( summaries != null ) {
        	awardSummariesFilter.init( summaries );
        	setBenefitInformation(awardSummariesFilter.buildBenefitInformation(summaries));
        	setBenefitDetailList(awardSummariesFilter.buildBenefitDetailList(summaries));
        	setExplanationPayment(awardSummariesFilter.buildExplanationPayment(summaries, repository.getDependentsPriorToFirstChangeDate()));
            logSummaries();
        }
        
        try {
	        assignFirstLast();
	        assignLastFirst();
	        assignFmLast();
	        setSojAddress();
	        setVeteranAddress( veteran );
	
	        setSalutation( veteran.getSalutation() );
	        setHasPOA( veteran.hasPOA() );
	        setForPoa( true );
	        setPoaName( repository.getPoaOrganizationName() );
	        //RTC iD 373716 . Adding cc to POA

            // RTC# 1310694 - Change notification letter - related to RBPS Pension Dependency Claims, Dependents with no Income ESCP 002859
            setHasPension( veteran.isPensionAward() );
            setPensionNetWorthLimit( veteran.getNetWorthLimit() );
	        
	        if(repository.getPoaOrganizationName() !=null){
	        	setSendCcTo(repository.getPoaOrganizationName());
	        }
	        
	        if(repository.getParticipantAddressPostalVO() != null){
	        	setParticipantAddressPostalVO(repository.getParticipantAddressPostalVO());
	        }

	    	setPriorSchoolTermRejected(veteran.isPriorSchoolTermRejected());
	    	setPriorSchoolTermDatesString(veteran.getPriorSchoolTermDatesString());
	    	setPriorSchoolChildName(veteran.getPriorSchoolChildName());
	    	
	        this.fiduciaryPrepositionalPhrase = null;
	        this.fiduciaryName = null;
	        this.fiduciaryAttentionText = null;
	        
	        if ( repository.isFiduciary() ) {
	        	
	        	logUtils.log( "repository FiduciaryName" + repository.getFiduciaryName(), repository );
	        	logUtils.log( "repository FiduciaryPrepositionalPhrase" + repository.getFiduciaryPrepositionalPhrase(), repository );
	        	logUtils.log( "repository FiduciaryAttentionText" + repository.getFiduciaryAttentionText(), repository );
	        	
	            setFiduciaryName( repository.getFiduciaryName() );
	            setFiduciaryPrepositionalPhrase( repository.getFiduciaryPrepositionalPhrase() );
	            setFiduciaryAttentionText(repository.getFiduciaryAttentionText());
	        }
	
	        setLetterHeader(null);
	        setSectionId( "RBPS" );
	        setCss(null);
	        setClaimDate( veteran.getClaim().getReceivedDate() );
	        setAwardAmount(null);
	        setPaymentChangeDate(null);
	        setReasonForChange(null);
	
	        //calcTotalDependants( repository );
	        setTotalDependents( repository.getCountForWhatWeDecided() );
	        setCurrentMonthlyAmount( repository.getVeteran().getCurrentMonthlyAmount() );
	        
	        accumulateChildrenNames();
	
	        setToDate( repository.getToDate() );
	        setPartOrAll( repository.getPartOrAll() );
	        
	        setEvidences(null);
	        setSignature(null);
	        setServiceOrg(null);
	
	        setSenderName( repository.getClaimStationAddress().getServiceManagerSignature() );
	        setSenderTitle( repository.getClaimStationAddress().getServiceManagerTitle() );
	        assignDecisionForms( veteran );
	
	        setHasApprovals();
	        setHasDenials();
	        setHasMilitaryPay( repository.getVeteran().hasMilitaryPay() );
	        
	        setApprovals();
	        setDenials();
	        setApprovalChildNames();
	        setApprovalSpouseName();
	        
	        makeStringsHtmlSafe();
	        
	        logUtils.log( "LetterFields.init(): setting OverPayment\n", repository );
	        
	        setOverPayment(false);
	        if (repository.getTotalNetAmount() != null && repository.getTotalNetAmount().compareTo(BigDecimal.ZERO) < 0) {
	        	setOverPayment(true);
	        } 
	        
	        logUtils.log( "LetterFields.init(): after setOverPayment\n", repository );
	        setWithholdingType(veteran.getWithholdingType());
	        setWithholdingPara(veteran.getWithholdingPara());
	        setHasRetiredPay(veteran.isHasRetiredPay());
			setHasSeparationPay(veteran.isHasSeparationPay());
			setHasSeverancePay(veteran.isHasSeverancePay());
			setSeparationWithholdingPara(veteran.getSeparationWithholdingPara());
			setSeveranceWithholdingPara(veteran.getSeveranceWithholdingPara());
			setHasBlindStatus(veteran.isHasBlindStatus());

        } catch ( Exception ex ) {
        	
        	String message = ex.getMessage();
        	
        	if (  message == null ) {
        		message = "Unexpected exception occurred in LetterFields";
        	}
        	
        	throw new RbpsRuntimeException( message );
        }
        logUtils.log( "End LetterFields.init()\n", repository );
    }
    

    private void makeStringsHtmlSafe() {

        PropertyRewriter        rewriter    = new PropertyRewriter();
        List<String>            properties  = Arrays.asList( "letterHeader",
                                                             "sectionId",
                                                             "css",
                                                             "salutation",

                                                             "firstLast",
                                                             "lastFirst",
                                                             "line1",
                                                             "line2",
                                                             "line3",
                                                             "city",
                                                             "country",
                                                             "state",
                                                             "zipPrefix",
                                                             "zipSuffix1",
                                                             "zipSuffix2",

                                                             "sojLine1",
                                                             "sojLine2",
                                                             "sojLine3",
                                                             "sojCity",
                                                             "sojCountry",
                                                             "sojState",
                                                             "sojZipPrefix",
                                                             "sojZipSuffix1",
                                                             "sojZipSuffix2",

                                                             "hasPOA",
                                                             "forPoa",
                                                             "poaName",

                                                             "fiduciaryName",
                                                             "fiduciaryPrepositionalPhrase",
                                                             "fiduciaryAttentionText",

                                                             "senderName",
                                                             "senderTitle",

                                                             "awardAmount",
                                                             "paymentChangeDate",
                                                             "reasonForChange",
                                                             "evidences",
                                                             "signature",
                                                             "serviceOrg");

        rewriter.modifyProperties( this, properties, new StringPropertyEscaper() );

//        private List<String>            childrenNames       = new ArrayList<String>();
    }

    private void assignFmLast() {

        String 			firstName	= veteran.getFirstName();
        String 			lastName	=veteran.getLastName().toLowerCase();
        String 			middleName=null;
        if ( ! StringUtils.isBlank( veteran.getMiddleName() ) ) {

        	middleName	= veteran.getMiddleName();	
        }
        String fmLname=null;
        if(firstName !=null){
        	fmLname=firstName.substring(0,1)+" ";
        }
        if(middleName !=null){
        	fmLname=fmLname+middleName.substring(0,1)+" ";
        }
        if(lastName !=null){
        	fmLname=fmLname+lastName;
        }
    	if(fmLname !=null &&(fmLname.trim()).length()>30){
    		fmLname=fmLname.trim().substring(0,30);
    	}
    	setFmLast( fmLname.trim().toUpperCase());
    }
    private void assignFirstLast() {

        String 			firstName	= WordUtils.capitalize( veteran.getFirstName().toLowerCase() );
        String 			lastName	= WordUtils.capitalize( veteran.getLastName().toLowerCase() );

    	String firstLastName = String.format("%s %s", WordUtils.capitalize( firstName ), WordUtils.capitalize( lastName ) );
    	
    	setFirstLast( firstLastName.trim() );
    }


    private void assignLastFirst() {

        String 			firstName	= WordUtils.capitalize( veteran.getFirstName().toLowerCase() );
        String 			lastName	= WordUtils.capitalize( veteran.getLastName().toLowerCase() );
        String 			middleName	= "";
 
        if ( ! StringUtils.isBlank( veteran.getMiddleName() ) ) {

        	middleName	= WordUtils.capitalize( veteran.getMiddleName().toLowerCase() );	
        }

    	String lastFirstName = String.format("%s, %s %s", WordUtils.capitalize( lastName ), WordUtils.capitalize( firstName ), WordUtils.capitalize( middleName ) );
    	
        setLastFirst( lastFirstName.trim() );
    }


    public void assignDecisionForms( final Veteran    veteran ) {

        decisionFormTypes = veteran.getClaim().getForms();
    }


    private void setSojAddress() {

        setSojLine1( repository.getClaimStationAddress().getAddressLine1() );
        setSojLine2( repository.getClaimStationAddress().getAddressLine2() );
        setSojLine3( repository.getClaimStationAddress().getAddressLine3() );
        setSojCity( repository.getClaimStationAddress().getCity() );
        setSojState( repository.getClaimStationAddress().getState() );
        setSojZipPrefix( repository.getClaimStationAddress().getZipCode() );
        setSojZipSuffix1( repository.getClaimStationAddress().getZipPlus4() );
        setSojZipSuffix2( repository.getClaimStationAddress().getZipSecondSuffix() );
    }


    public void setVeteranAddress( final Veteran veteran ) {

        setLine1( veteran.getMailingAddress().getLine1() );
        setLine2( veteran.getMailingAddress().getLine2() );
        setLine3( veteran.getMailingAddress().getLine3() );
        setCity( veteran.getMailingAddress().getCity() );
        setState( veteran.getMailingAddress().getState() );
        setCountry( veteran.getMailingAddress().getCountry() );
        setZipPrefix( veteran.getMailingAddress().getZipPrefix() );
        setZipSuffix1( veteran.getMailingAddress().getZipSuffix1() );
        setZipSuffix2( veteran.getMailingAddress().getZipSuffix2() );
    }


    public void calcTotalDependants( final RbpsRepository repository ) {

        int     numDependants = 0;

        if ( ! CollectionUtils.isEmpty( getApprovalChildNames() ) ) {

            numDependants = getApprovalChildNames().size();
        }

        if ( StringUtils.isNotEmpty( getApprovalSpouseName() ) ) {

            numDependants++;
        }

        setTotalDependents( numDependants );
    }


    public String getLetterHeader() {
        return letterHeader;
    }


    public void setLetterHeader(final String letterHeader) {
        this.letterHeader = letterHeader;
    }


//  public String getLetterDate() {
//      return letterDate;
//    }
//  public void setLetterDate(final String letterDate) {
//      this.letterDate = letterDate;
//    }


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

        this.state = state;
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

        if ( StringUtils.isBlank( zipSuffix2 ) && this.country.equalsIgnoreCase("USA") ) {

            this.zipSuffix1 = "0000";

            return;
        }
        if ( zipSuffix1 != null )
        {
        	this.zipSuffix1 = zipSuffix1;
        }
        else {
        	this.zipSuffix1 = "";
        }
    }


    public String getZipSuffix2() {

        return zipSuffix2;
    }
    public void setZipSuffix2( final String zipSuffix2 ) {

        this.zipSuffix2 = zipSuffix2;
    }
    public String getZip() {

    	if ( StringUtils.isEmpty( zipSuffix1 ) ) {
    		return zipPrefix;
    	}
        return String.format( "%s-%s", zipPrefix, zipSuffix1 );
    }


    public boolean getHasPOA() {

        return hasPOA;
    }
    public void setHasPOA( final boolean hasPOA ) {

        this.hasPOA = hasPOA;
    }


    public boolean getHasPension() {

        return hasPension;
    }
    public void setHasPension( final boolean hasPension ) {

	    this.hasPension = hasPension;
    }


    public BigDecimal getPensionNetWorthLimit() {

	    return pensionNetWorthLimit;
    }
    public void setPensionNetWorthLimit( final BigDecimal pensionNetWorthLimit ) {
	    this.pensionNetWorthLimit = pensionNetWorthLimit;
    }

    public String getClaimNumber() {
        //
        //      They don't want the claim id, but the file number in VVA.
        //
        return veteran.getFileNumber();
    }


    public String getSectionId() {
        return sectionId;
    }
    public void setSectionId(final String sectionId) {
        this.sectionId = sectionId;
    }


    public String getFileNumber() {

        return veteran.getFileNumber();
    }


    public String getLastName() {
        return capCaseName( veteran.getLastName() );
    }


    public String getFirstName() {
        return capCaseName( veteran.getFirstName() );
    }


    public String getMiddleName() {
        return capCaseName( veteran.getMiddleName() );
    }


    public String getSpouseName() {

        if ( veteran.getCurrentMarriage() == null ) {
            return "";
        }

        if ( veteran.getCurrentMarriage().getMarriedTo() == null ) {
            return "";
        }

        Spouse spouse = veteran.getCurrentMarriage().getMarriedTo();

//        return String.format( "%s, %s", spouse.getLastName(), spouse.getFirstName() );
        return capCaseName( spouse.getFirstName() );
    }


    private String capCaseName( final String   name ) {

        if ( name == null ) {

            return null;
        }

        return WordUtils.capitalize( name.toLowerCase() );
    }


    public String getCss() {
        return css;
    }
    public void setCss(final String css) {
        this.css = css;
    }


    public Date getClaimDate() {
        return claimDate;
    }
    public void setClaimDate(final Date claimDate) {
        this.claimDate = claimDate;
    }

    public String getClaimDateString() {
    	
    	return SimpleDateUtils.convertDate( "","MMM d, yyyy", claimDate );
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

    
    public double getCurrentMonthlyAmount() {
    	return currentMonthlyAmount;
    }
    public void setCurrentMonthlyAmount( double currentMonthlyAmount) {
    	this.currentMonthlyAmount = currentMonthlyAmount;
    }
    
    public List<String> getChildrenNames() {
        return childrenNames;
    }
    public void setChildrenNames(final List<String> childrenNames) {
        this.childrenNames = childrenNames;
    }


    private void accumulateChildrenNames() {

        for ( Child kid : veteran.getChildren() ) {

//            getChildrenNames().add( String.format( "%s, %s", kid.getLastName(), kid.getFirstName() ) );
            getChildrenNames().add( kid.getFirstName() );
        }
    }

    public String getJoinedApprovalChildNames() {

        if ( getApprovalChildNames().isEmpty() ) {

            return "";
        }

        String      prefix = "child ";

        if ( getApprovalChildNames().size() > 1 ) {

            prefix = "children ";
        }

        if ( getApprovalChildNames().size() > 2 ) {
            String joined = new BatchingUtils().join(  getApprovalChildNames().subList( 0, getApprovalChildNames().size() - 1 ), ", " );

            joined += String.format( ", and %s", getApprovalChildNames().get( getApprovalChildNames().size() - 1 ) );

            return prefix + joined;
        }

        return prefix + CommonUtils.join(  getApprovalChildNames(), " and " );
    }


    private void logSummaries() {

        logUtils.log( "Grants/Removals:\n", repository );
        logSummaryList( awardSummariesFilter.getApprovals() );

        logUtils.log( "\n\nDenials:\n", repository );
        logSummaryList( awardSummariesFilter.getDenials() );
    }


    private void logSummaryList( final Collection<AwardSummary> summaries ) {

        for ( AwardSummary summary : summaries ) {

            logUtils.log( CommonUtils.stringBuilder( summary ), repository );
        }
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
    public String getFmLast() {
		return fmLast;
	}

	public void setFmLast(String fmLast) {
		this.fmLast = fmLast;
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
    public void setSalutation( final SalutationType salutation ) {

        if ( salutation == null ) {

            this.salutation = "";
            return;
        }

        this.salutation = salutation.getValue();
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

        if ( StringUtils.isBlank( sojZipSuffix1 ) ) {

            this.sojZipSuffix1 = "0000";

            return;
        }

        this.sojZipSuffix1 = sojZipSuffix1;
    }



    public String getSojZipSuffix2() {

        return sojZipSuffix2;
    }
    public void setSojZipSuffix2( final String sojZipSuffix2 ) {

        if ( StringUtils.isBlank( sojZipSuffix2 ) ) {

            this.sojZipSuffix2 = "0000";

            return;
        }

        this.sojZipSuffix2 = sojZipSuffix2;
    }


    public String getSojZip() {

        if ( ! StringUtils.isBlank( sojZipSuffix1 ) ) {
            return String.format( "%s-%s", sojZipPrefix, sojZipSuffix1 );
        }
        else {
            return sojZipPrefix;
        }
    }



    public List<FormType> getDecisionFormTypes() {

        return decisionFormTypes;
    }
    public void setDecisionFormTypes( final List<FormType> decisionFormTypes ) {

        this.decisionFormTypes = decisionFormTypes;
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
    public void setHasApprovals() {

    	if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
    		this.hasApprovals = false;
    		return;
    	}
        this.hasApprovals = awardSummariesFilter.getHasApprovals();
    }

    
    public boolean getHasDenials() {

        return this.hasDenials;
    }
    public void setHasDenials() {

    	if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
    		this.hasDenials = true;
    		return;
    	}
    	else{
    		
    		if (repository.getVeteranAbove30AndDenial()){
    			this.hasDenials = true;
        		return;
    		}
    	}
    	
    	this.hasDenials = awardSummariesFilter.getHasDenials();
    }

    
    public boolean getHasMilitaryPay() {

        return this.hasMilitaryPay;
    }
    public void setHasMilitaryPay( boolean hasMilitaryPay ) {

        this.hasMilitaryPay = hasMilitaryPay;
    }


    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }
    public String getToDate() {
        return SimpleDateUtils.convertDate( "","MMM d, yyyy", toDate );
    }   


    public void setPartOrAll(final String partOrAll) {
        this.partOrAll = partOrAll;
    }
    public String getPartOrAll() {
        return partOrAll;
    }    
    
    
    public Collection<AwardSummary> getApprovals() {

    	return  getSortedApprovals();
    }
    @SuppressWarnings("unchecked")
	public Collection<AwardSummary> getSortedApprovals() {

    	@SuppressWarnings("rawtypes")
		List approvalsList= (List)approvals;
        Collections.sort(approvalsList, 
                new Comparator<AwardSummary>()
                {
                             public int compare(AwardSummary o1, AwardSummary o2) 
                             {
                                          return o1.getPaymentChangeDate().compareTo(o2.getPaymentChangeDate());
                             }
                });
return approvalsList;
    }
    public void setApprovals() {

    	 if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
    	
    		this.approvals = new ArrayList<AwardSummary>();
    		return;
    	}
        this.approvals = awardSummariesFilter.getApprovals();
    }

    
    public Collection<AwardSummary> getDenials() {

        return this.denials;
    }
    public void setDenials() {
    	
    	if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
    		this.denials = new ArrayList<AwardSummary>();
    		return;
    	}
        this.denials = awardSummariesFilter.getDenials();
    }

    
    public List<String> getApprovalChildNames() {

        return this.approvalChildNames;
    }
    public void setApprovalChildNames() {
    	
    	if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
    		this.approvalChildNames = new ArrayList<String>();
    		return;
    	}
    	this.approvalChildNames = new ArrayList<String>( awardSummariesFilter.getApprovalChildNames() );
    }

    
    public String getApprovalSpouseName() {

        return approvalSpouseName;
    }
    public void setApprovalSpouseName() {
    	
    	if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
    		this.approvalSpouseName = "";
    		return;
    	}
        approvalSpouseName = awardSummariesFilter.getApprovalSpouseName();
    }
    
    
    public void setFiduciaryName( final String fiduciaryName ) {

        this.fiduciaryName = fiduciaryName;
    }
    public String getFiduciaryName() {

        return this.fiduciaryName;
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


    public void setLogit( final boolean logit ) {

        logUtils.setLogit( logit );

        if ( awardSummariesFilter != null ) {

            awardSummariesFilter.setLogit( logit );
        }
    }
    
    public boolean isPriorSchoolTermRejected() {
		return priorSchoolTermRejected;
	}

	public void setPriorSchoolTermRejected(boolean priorSchoolTermRejected) {
		this.priorSchoolTermRejected = priorSchoolTermRejected;
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

	public String getSendCcTo() {
		return sendCcTo;
	}

	public void setSendCcTo(String sendCcTo) {
		this.sendCcTo = sendCcTo;
	}
	
	public boolean isOverPayment() {
		return isOverPayment;
	}

	public void setOverPayment(boolean isOverPayment) {
		this.isOverPayment = isOverPayment;
	}

	public List<BenefitInformation> getBenefitInformation() {
		return benefitInformation;
	}

	public void setBenefitInformation(List<BenefitInformation> benefitInformation) {
		this.benefitInformation = benefitInformation;
	}

	public List<String> getBenefitDetailList() {
		return benefitDetailList;
	}

	public void setBenefitDetailList(List<String> benefitDetailList) {
		this.benefitDetailList = benefitDetailList;
	}

	public List<ExplanationPayment> getExplanationPayment() {
		return explanationPayment;
	}

	public void setExplanationPayment(List<ExplanationPayment> explanationPayment) {
		this.explanationPayment = explanationPayment;
	}

	public DisplayAddressVO getParticipantAddressPostalVO() {
		return participantAddressPostalVO;
	}

	public void setParticipantAddressPostalVO(DisplayAddressVO participantAddressPostalVO) {
		this.participantAddressPostalVO = participantAddressPostalVO;
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
