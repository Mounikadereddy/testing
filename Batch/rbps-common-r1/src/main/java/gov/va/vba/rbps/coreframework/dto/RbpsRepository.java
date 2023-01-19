/*
 * RpbsRepository.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.coreframework.dto;


import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.xom.RuleExceptionMessages;
import gov.va.vba.rbps.coreframework.xom.Veteran;

import javax.xml.datatype.XMLGregorianCalendar;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;


/**
 * This class stores all the new eClaim data coming from the SHARE or eBenefits
 * applications.
 */
public class RbpsRepository implements Serializable {

    private static final long serialVersionUID = -5282636849781303823L;

    /**
     *      The vnpProcId is used by e/turbo benefits/VDC to track a claim through the
     *      process of the veteran filling out the form because until it gets
     *      completely filled out, the benefitId won't be generated.
     *
     *      We only need it to update the claim status. The
     *      VonapWebServicesHandler.vnpProcUpdate needs it to update the claim
     *      status.
     */

    private boolean                     isValidClaim;
    private boolean                     isFiduciary;
    private Long                        vnpProcId;
    private String                      vnpProcStateType;
    private XMLGregorianCalendar		vnpCreatdDt;
    private XMLGregorianCalendar		vnpLastModifdDt;
	private XMLGregorianCalendar 		jrnDt;
    private String 						jrnLctnId;
    private String 						jrnObjId;
    private String 						jrnStatusTypeCd;
    private String 						jrnUserId;    
    private String                      ruleExecutionInfo;
    private List<CorporateDependent>    children;
    private CorporateDependent          spouse;
    private Veteran                     veteran;
    private RuleExceptionMessages       messages;
    private String                      journalStr;
    private Set<String>                 validationMessages;
    private String                      poaOrganizationName;
    private String                      fiduciaryName;
    private String                      fiduciaryPrepositionalPhrase;
    private String                      fiduciaryAttentionText;
    private String                      claimProcessingState;
    private Long                        claimStationLocationId;
    private ClaimStationAddress         claimStationAddress;
    private int  						countForWhatWeDecided;
    private Date						toDate;
    private String						partOrAll;
    private long						bnftClaimId;
    private	BigDecimal					totalNetAmount;
    
    //647228/298341, Completed Claims with Reject Claim Labels (RTC  298341) - ESCP 545
    private Date						awardAuthorizedDate;
    private boolean						isLetterSuccess;
    private boolean                     processPensions;
    //SR# 723083
//    private List<ServiceConnectedRatings>	serviceConnectedRatingList;
    private boolean            			veteranAbove30AndDenail;
    private Date						lessThan30EffectiveDateAfterLatestEffectiveDate;
    
    private Long 						awardEventID;
    private String 						awardEventType;
    
    private DisplayAddressVO	participantAddressVO;
    private DisplayAddressVO	paymentAddressVO;
    private DisplayAddressVO	vetMailingAddressVO;
    private LinkedHashSet<DependentVO> 		dependentsPriorToFirstChangeDate = new LinkedHashSet<DependentVO>();

    private String sjnId;
    
    public String getSjnId() {
		return sjnId;
	}

	public void setSjnId(String sjnId) {
		this.sjnId = sjnId;
	}

	public Long getAwardEventID() {
		return awardEventID;
	}

	public void setAwardEventID(Long awardEventID) {
		this.awardEventID = awardEventID;
	}

	public String getAwardEventType() {
		return awardEventType;
	}

	public void setAwardEventType(String awardEventType) {
		this.awardEventType = awardEventType;
	}

	// JR - 09-04-2018 - SR # 813152 - rating map of all combined percent ratings
    TreeMap<Date, Integer> ratingMap;

	public RbpsRepository() {

        children                     = new ArrayList<CorporateDependent>();
        messages                     = new RuleExceptionMessages();
        ruleExecutionInfo            = "";
        claimProcessingState         = "";
        journalStr                   = "";
        validationMessages           = new LinkedHashSet<String>();
//        serviceConnectedRatingList   = new ArrayList<ServiceConnectedRatings>();
    }


    public Veteran getVeteran() {
        return veteran;
    }
    public void setVeteran(final Veteran veteran) {
        this.veteran = veteran;
    }

    public Set<String> getValidationMessages() {

        return validationMessages;
    }
    public String getFormattedValidationMessages() {

        return CommonUtils.join( new ArrayList<String>( validationMessages ), ";" );
    }
    public void addValidationMessages(final List<String> messages) {

        for ( String msg : messages ) {

            addValidationMessage( msg );
        }
    }
    public void addValidationMessage(final String message) {

        if (message == null) {

            return;
        }

        String  cleanedUpMessage = message.replaceAll( "RbpsRuntimeException: ", "" );
        this.validationMessages.add( cleanedUpMessage );
    }
    public void clearValidationMessages() {

        this.validationMessages.clear();
    }
    public boolean hasValidationMessages() {

        return ! getValidationMessages().isEmpty();
    }




    /**
     * @return ProcID
     */
    public Long getVnpProcId() {
        return vnpProcId;
    }

    /**
     * @param vnpProcId
     */
    public void setVnpProcId(final Long vnpProcId) {
        this.vnpProcId = vnpProcId;
    }
    public boolean validProcId() {

        return getVnpProcId() != null || getVnpProcId() != 0;
    }


    /**
     * @return bnftClaimId
     */
    public Long getBnftClaimId() {
        return bnftClaimId;
    }    

    /**
     * @param bnftClaimId
     */
    public void setBnftClaimId(final Long bnftClaimId) {
        this.bnftClaimId = bnftClaimId;
    }


    /**
     * @return List of Corporate Children
     */
    public List<CorporateDependent> getChildren() {

        if (children == null) {
            children = new ArrayList<CorporateDependent>();
        }

        return children;
    }

    /**
     * @param children
     */
    public void setChildren(final List<CorporateDependent> children) {
        this.children = children;
    }

    /**
     * @param newChild
     */
    public void addChild(final CorporateDependent newChild) {
        getChildren().add(newChild);
    }

    /**
     * @return hasChildren
     */
    public boolean hasChildren() {
        return ! getChildren().isEmpty();
    }

    /**
     * @return getSpouse
     */
    public CorporateDependent getSpouse() {
        return spouse;
    }

    /**
     * @param spouse
     */
    public void setSpouse(final CorporateDependent spouse) {
        this.spouse = spouse;
    }



    /**
     * @return numberOfDependentsOnAward
     */
    public int getNumberOfDependentsOnAward() {

        int count = 0;

        for (CorporateDependent child : getChildren()) {

            if ( ! child.isOnAward() ) {

                continue;
            }

            count++;
        }

        if ( spouse != null && spouse.isOnAward() ) {

            count++;
        }

        return count;
    }

    /**
     * @param vnpProcStateType
     */
    public void setVnpProcStateType(final String vnpProcStateType) {
        this.vnpProcStateType = vnpProcStateType;
    }

    /**
     * @return getVnpProcStateType
     */
    public String getVnpProcStateType() {
        return vnpProcStateType;
    }

    
    
    public XMLGregorianCalendar getVnpCreatdDt() {
        return vnpCreatdDt;
    }

    public void setVnpCreatdDt(XMLGregorianCalendar value) {
        this.vnpCreatdDt = value;
    } 
    
    
    public XMLGregorianCalendar getVnpLastModifdDt() {
        return vnpLastModifdDt;
    }

    public void setVnpLastModifdDt(XMLGregorianCalendar value) {
        this.vnpLastModifdDt = value;
    } 
    
    public XMLGregorianCalendar getJrnDt() {
		return jrnDt;
	}


	public void setJrnDt(XMLGregorianCalendar jrnDt) {
		this.jrnDt = jrnDt;
	}


	public String getJrnLctnId() {
		return jrnLctnId;
	}


	public void setJrnLctnId(String jrnLctnId) {
		this.jrnLctnId = jrnLctnId;
	}


	public String getJrnObjId() {
		return jrnObjId;
	}


	public void setJrnObjId(String jrnObjId) {
		this.jrnObjId = jrnObjId;
	}


	public String getJrnStatusTypeCd() {
		return jrnStatusTypeCd;
	}


	public void setJrnStatusTypeCd(String jrnStatusTypeCd) {
		this.jrnStatusTypeCd = jrnStatusTypeCd;
	}


	public String getJrnUserId() {
		return jrnUserId;
	}


	public void setJrnUserId(String jrnUserId) {
		this.jrnUserId = jrnUserId;
	}
    
    public DisplayAddressVO getParticipantAddressVO() {
		return participantAddressVO;
	}

	public void setParticipantAddressVO(DisplayAddressVO participantAddressVO) {
		this.participantAddressVO = participantAddressVO;
	}

	public DisplayAddressVO getPaymentAddressVO() {
		return paymentAddressVO;
	}

	public void setPaymentAddressVO(DisplayAddressVO paymentAddressVO) {
		this.paymentAddressVO = paymentAddressVO;
	}

	public DisplayAddressVO getVetMailingAddressVO() {
		return vetMailingAddressVO;
	}

	public void setVetMailingAddressVO(DisplayAddressVO vetMailingAddressVO) {
		this.vetMailingAddressVO = vetMailingAddressVO;
	}

	public void init() {}


    public void destroy() {

        setVeteran(null);
        getChildren().clear();
        setVnpProcId(null);
        setSpouse(null);
        setValidClaim(false);
        setVnpProcStateType(null);
        getRuleExceptionMessages().getMessages().clear();
        setRuleExecutionInfo("");
        setClaimProcessingState("");
        setPoaOrganizationName( null );
        setIsFiduciary(false);
        setFiduciaryName( null );
        setFiduciaryPrepositionalPhrase( null );
        setFiduciaryAttentionText( null );
//        clearJournalStr();
        clearValidationMessages();
        setClaimStationLocationId( null );
        setClaimStationAddress( null );
        setVeteranAbove30AndDenial(false);
        setLessThan30EffectiveDateAfterLatestEffectiveDate(null);
    }


    /**
     * @param isValidClaim the isValidClaim to set
     */
    public void setValidClaim(final boolean isValidClaim) {
        this.isValidClaim = isValidClaim;
    }

    /**
     * @return the isValidClaim
     */
    public boolean isValidClaim() {
        return isValidClaim;
    }


    /**
     * @param isFiduciary the isFiduciary to set
     */
    public void setIsFiduciary(final boolean isFiduciary) {
        this.isFiduciary = isFiduciary;
    }

    /**
     * @return the isFiduciary
     */
    public boolean isFiduciary() {
        return isFiduciary;
    }

    /**
     * @param withHeldYear the withHeldYear to set
     */
    public void setToDate(final Date toDate) {
        this.toDate = toDate;
    }

    /**
     * @return the withHeldYear
     */
    public Date getToDate() {
        return toDate;
    }   


    /**
     * @param adjustmentAmount the adjustmentAmount to set
     */
    public void setPartOrAll(final String partOrAll) {
        this.partOrAll = partOrAll;
    }

    /**
     * @return the adjustmentAmount
     */
    public String getPartOrAll() {
        return partOrAll;
    }    
    
    

    /**
     * @param messages
     */
    public void setRuleExceptionMessages(final RuleExceptionMessages messages) {
        this.messages = messages;
    }
    /**
     * @return ruleExceptionMessages
     */
    public RuleExceptionMessages getRuleExceptionMessages() {
        return messages;
    }
    public boolean hasRuleExceptionMessages() {

        return ! getRuleExceptionMessages().getMessages().isEmpty();
    }
    public String getFormattedRuleExceptionMessages() {

        return CommonUtils.join( new ArrayList<String>( messages.getMessages() ), ";" );
    }


    /**
     * @param ruleExecutionInfo
     */
    public void setRuleExecutionInfo(final String ruleExecutionInfo) {
        this.ruleExecutionInfo = ruleExecutionInfo;
    }
    /**
     * @return ruleExecutionInfo
     */
    public String getRuleExecutionInfo() {
        return ruleExecutionInfo;
    }



    public void setClaimProcessingState(final String claimProcessingState) {
        this.claimProcessingState = claimProcessingState;
    }

    public String getClaimProcessingState() {
        return claimProcessingState;
    }


    public void addJournalStr(final String journalStr) {

        this.journalStr += journalStr;
    }
    
    public void clearJournalStr() {

        this.journalStr = "";
    }
    
    public String getJournalStr() {

        return journalStr;
    }


    /**
     * @return the poaOrganizationName
     */
    public String getPoaOrganizationName() {

        return poaOrganizationName;
    }

    /**
     * @param poaOrganizationName the poaOrganizationName to set
     */
    public void setPoaOrganizationName( final String poaOrganizationName ) {

        this.poaOrganizationName = poaOrganizationName;
    }


    /**
     * @return the fiduciaryName
     */
    public String getFiduciaryName() {

        return fiduciaryName;
    }


    /**
     * @param fiduciaryName the fiduciaryName to set
     */
    public void setFiduciaryName( final String fiduciaryName ) {

        this.fiduciaryName = fiduciaryName;
    }


    /**
     * @return the fiduciaryPrepositionalPhrase
     */
    public String getFiduciaryPrepositionalPhrase() {

        return fiduciaryPrepositionalPhrase;
    }


    /**
     * @param fiduciaryPrepositionalPhrase the fiduciaryPrepositionalPhrase to set
     */
    public void setFiduciaryPrepositionalPhrase( final String fiduciaryprepositionalPhrase ) {

        this.fiduciaryPrepositionalPhrase = fiduciaryprepositionalPhrase;
    }

    /**
     * @return the fiduciaryAttentionText
     */
    public String getFiduciaryAttentionText( ) {

        return this.fiduciaryAttentionText;
    }

    /**
     * @param fiduciaryAttentionText the fiduciaryAttentionText to set
     */
    public void setFiduciaryAttentionText( final String fiduciaryAttentionText ) {

        this.fiduciaryAttentionText = fiduciaryAttentionText;
    }


    public ClaimStationAddress getClaimStationAddress() {

        if ( claimStationAddress == null ) {

            claimStationAddress = new ClaimStationAddress();
        }

        return this.claimStationAddress;
    }
    public void setClaimStationAddress( final ClaimStationAddress claimStationAddress ) {

        this.claimStationAddress = claimStationAddress;
    }



    public Long getClaimStationLocationId() {

        return claimStationLocationId;
    }
    public void setClaimStationLocationId( final Long claimStationLocationId ) {

        this.claimStationLocationId = claimStationLocationId;
    }


    public boolean isSameClaimId( final long claimId) {

        return this.veteran.isSameClaimId( claimId );
    }
    
   public int getCountForWhatWeDecided() {
    	
    	return countForWhatWeDecided;
    }
    
    
    public void setCountForWhatWeDecided( int countForWhatWeDecided) {
    	
    	this.countForWhatWeDecided = countForWhatWeDecided;
    }
    
    public BigDecimal getTotalNetAmount() {
		return totalNetAmount;
	}


	public void setTotalNetAmount(BigDecimal totalNetAmount) {
		this.totalNetAmount = totalNetAmount;
	}

	/* 647228/298341, Completed Claims with Reject Claim Labels (RTC  298341) - ESCP 545 */
	public Date getAwardAuthorizedDate() {
		return awardAuthorizedDate;
	}


	public void setAwardAuthorizedDate(Date awardAuthorizedDate) {
		this.awardAuthorizedDate = awardAuthorizedDate;
	}


	public boolean isLetterSuccess() {
		return isLetterSuccess;
	}


	public void setLetterSuccess(boolean isLetterSuccess) {
		this.isLetterSuccess = isLetterSuccess;
	}
	/* 647228/298341 End */


	//SR# 723083
	public boolean getVeteranAbove30AndDenial() {
		return veteranAbove30AndDenail;
	}

	//SR# 723083
	public void setVeteranAbove30AndDenial(boolean veteranAbove30AndDenail) {
		this.veteranAbove30AndDenail = veteranAbove30AndDenail;
	}

	//SR# 723083
	public Date getLessThan30EffectiveDateAfterLatestEffectiveDate() {
		return lessThan30EffectiveDateAfterLatestEffectiveDate;
	}

	//SR# 723083
	public void setLessThan30EffectiveDateAfterLatestEffectiveDate(
			Date lessThan30EffectiveDateAfterLatestEffectiveDate) {
		this.lessThan30EffectiveDateAfterLatestEffectiveDate = lessThan30EffectiveDateAfterLatestEffectiveDate;
	}	
	
	// JR - 09-04-2018 - SR # 813152 - get, set methods for ratingMap
	public TreeMap<Date, Integer> getRatingMap() {
		return ratingMap;
	}
	
	public void setRatingMap(TreeMap<Date, Integer> ratingMap) {
		this.ratingMap = ratingMap;
	}

    public boolean isProcessPensions() {
        return processPensions;
    }

    public void setProcessPensions(boolean processPensions) {
        this.processPensions = processPensions;
    }

	public DisplayAddressVO getParticipantAddressPostalVO() {
		return participantAddressVO;
	}


	public LinkedHashSet<DependentVO> getDependentsPriorToFirstChangeDate() {
		return dependentsPriorToFirstChangeDate;
	}

	public void setDependentsPriorToFirstChangeDate(LinkedHashSet<DependentVO> dependentsPriorToFirstChangeDate) {
		this.dependentsPriorToFirstChangeDate = dependentsPriorToFirstChangeDate;
	}
}
