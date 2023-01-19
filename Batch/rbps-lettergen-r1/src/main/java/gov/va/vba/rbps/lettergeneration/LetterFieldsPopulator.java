/*
 * LetterFields.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.lettergeneration;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.PropertyRewriter;
import gov.va.vba.rbps.coreframework.util.Separator;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.coreframework.xom.SalutationType;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.lettergeneration.batching.util.BatchingUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;
import org.springframework.util.CollectionUtils;



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
public final class LetterFieldsPopulator {

    private static Logger logger = Logger.getLogger( LetterFieldsPopulator.class );

    private LogUtils                logUtils                = new LogUtils( logger, true );

    private RbpsRepository          repository;
    private Veteran                 veteran;
    private AwardSummariesFilter    awardSummariesFilter;

    private String                  letterHeader;
    private String                  sectionId;
    private String                  css;
    private String                  salutation;

    private String                  firstLast;
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
    
    private boolean					hasApprovals;
    private boolean					hasDenials;
    
    private Collection<AwardSummary> approvals;
    private Collection<AwardSummary> denials;
    private ArrayList<String>		 approvalChildNames;
    private String					 approvalSpouseName;
    private boolean					priorSchoolTermRejected;
    private String					priorSchoolTermDatesString;
    
	public LetterFieldsPopulator( final RbpsRepository               repo,
                         		  final List<AwardSummary>           summaries ) {

        this.repository         = repo;
        this.veteran            = repo.getVeteran();
        this.summaries          = summaries;
        awardSummariesFilter    = new AwardSummariesFilter();
    }


    public void init() {

        awardSummariesFilter.init( summaries );
        logSummaries();

        assignFirstLast();
        assignLastFirst();

        setSojAddress();
        setVeteranAddress( veteran );

        setSalutation( veteran.getSalutation() );
        setHasPOA( veteran.hasPOA() );
        setForPoa( true );
        setPoaName(repository.getPoaOrganizationName() );

        setPriorSchoolTermRejected(veteran.isPriorSchoolTermRejected());
        setPriorSchoolTermDatesString(veteran.getPriorSchoolTermDatesString());
        
        if ( repository.isFiduciary() ) {

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

        setEvidences(null);
        setSignature(null);
        setServiceOrg(null);

        setSenderName( repository.getClaimStationAddress().getServiceManagerSignature() );
        setSenderTitle( repository.getClaimStationAddress().getServiceManagerTitle() );
        assignDecisionForms( veteran );

        setHasApprovals();
        setHasDenials();
        
        setApprovals();
        setDenials();
        setApprovalChildNames();
        setApprovalSpouseName();
        
        makeStringsHtmlSafe();
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
                                                             "serviceOrg",
                                                             
                                                             "hasApprovals",
                                                             "hasDenials",
                                                             "approvals",
                                                             "denials" );

        rewriter.modifyProperties( this, properties, new StringPropertyEscaper() );

//        private List<String>            childrenNames       = new ArrayList<String>();
    }


    private void assignFirstLast() {

        Separator       sep     	= new Separator( " " );
        String 			firstName	= WordUtils.capitalize( veteran.getFirstName() );
        String 			lastName	= WordUtils.capitalize( veteran.getLastName() );
        List<String>    names   = Arrays.asList( firstName, lastName );
        StringBuffer    buffer  = new StringBuffer();

        for ( String name : names ) {

            if ( StringUtils.isBlank( name ) ) {

                continue;
            }

            buffer.append( sep );
            buffer.append( name );
        }

        setFirstLast( buffer.toString() );
    }


    private void assignLastFirst() {

        Separator       sep     = new Separator( ", " );
        List<String>    names   = Arrays.asList( veteran.getLastName(), veteran.getFirstName() );
        StringBuffer    buffer  = new StringBuffer();

        for ( String name : names ) {

            if ( StringUtils.isBlank( name ) ) {

                continue;
            }

            buffer.append( sep );
            buffer.append( name );
        }

        if ( ! StringUtils.isBlank( veteran.getMiddleName() ) ) {

            buffer.append( " " );
            buffer.append( veteran.getMiddleName() );
        }

        setLastFirst( buffer.toString() );
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

        if ( StringUtils.isBlank( zipSuffix1 ) && this.country.equalsIgnoreCase("USA") ) {

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

    	if ( StringUtils.isBlank( zipSuffix1 ) ) {
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

        logUtils.log( "Grants/Removals:\n", repository);
        logSummaryList( awardSummariesFilter.getApprovals());

        logUtils.log( "\n\nDenials:\n", repository);
        logSummaryList( awardSummariesFilter.getDenials());
    }


    private void logSummaryList( final Collection<AwardSummary> summaries) {

        for ( AwardSummary summary : summaries ) {

            logUtils.log( CommonUtils.stringBuilder( summary ), repository);
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

        this.hasApprovals = awardSummariesFilter.getHasApprovals();
    }

    public boolean getHasDenials() {

        return this.hasDenials;
    }

    public void setHasDenials() {

        this.hasDenials = awardSummariesFilter.getHasDenials();
    }

    public Collection<AwardSummary> getApprovals() {

    	return this.approvals;
    }

    public void setApprovals() {

        this.approvals = awardSummariesFilter.getApprovals();
    }

    public Collection<AwardSummary> getDenials() {

        return this.denials;
    }

    public void setDenials() {

        this.denials = awardSummariesFilter.getDenials();
    }

    public List<String> getApprovalChildNames() {

        return this.approvalChildNames;
    }

    public void setApprovalChildNames() {

    	this.approvalChildNames = new ArrayList<String>( awardSummariesFilter.getApprovalChildNames() );
    }

    public String getApprovalSpouseName() {

        return approvalSpouseName;
    }

    public void setApprovalSpouseName() {

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

/*
    public void setCommonUtils( final CommonUtils utils ) {

        this.utils = utils;
    }

*/

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
}
