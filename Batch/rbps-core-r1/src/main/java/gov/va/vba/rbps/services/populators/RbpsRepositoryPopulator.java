/*
 * RbpsRepositoryPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.*;
import gov.va.vba.rbps.coreframework.vo.RelationshipType;
import gov.va.vba.rbps.coreframework.xom.*;
import gov.va.vba.rbps.services.impl.VdcClaimFilter;
import gov.va.vba.rbps.services.populators.utils.CacheInvalidator;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.userinformation.*;
import org.apache.commons.collections.map.MultiKeyMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.CollectionUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.*;


/**
 *      This class populates the Veteran object with claim data retrieved from the UserInformation WS
 */
public class RbpsRepositoryPopulator {

    private static Logger logger = Logger.getLogger(RbpsRepositoryPopulator.class);

    private LogUtils                            logUtils            = new LogUtils( logger, true );

    private Map<Long, VnpPerson>                vnpPersonMap;
    private MultiKeyMap                         vnpPtcpntPhoneMap;
    private Map<Long, VnpPtcpntAddrs>           vnpPtcpntAddrsMap;
    private Map<Long, VnpPtcpnt>                vnpPtcpntMap;
    private Map<Long, VnpChildStudnt>           vnpChildStudntMap;
    private Map<Long, Child>                    veteransExistingChildrenMap;
    private Map<Long, List<VnpChildSchool>>     participantIdSchoolListMap;
    private boolean                             shouldPerformSchoolChildIndWorkaround;
    private CacheInvalidator                    cacheInvalidator;

    private Veteran                             veteran;



    /**
     *      Populates RbpsRepository with the claim data received from UserInformation WS
     *      @param userInformation
     */
    public void populateFromVonapp( final UserInformation userInformation, RbpsRepository repo) {

        if (userInformation == null) {
            logUtils.log( " => UserInformation is null.", repo);
            return;
        }

        if ( CollectionUtils.isEmpty(userInformation.getBnftClaim() ) ) {
        	logUtils.log( "=> Claim Data is null.", repo);
            return;
        }
        
        if ( userInformation.getBnftClaim().get(0).getVnpPtcpntVetId() == null) {
        	logUtils.log( "=> Claim Data VnpPtcpntVetId is null.", repo);
            return;
        }

        setVnpObjectMaps(userInformation);
        setVeteran(userInformation, repo);
        set686cDependents(userInformation.getVnpPtcpntRlnshp());
        set674Dependents();
        setRepository(repo, userInformation.getProcessData(), veteran);
        populateSpouseForLatestPreviousMarriage(repo);
        cleanup();
        invalidateCache(repo);
        resetLogUtilsStackLevel();
        // defect 530998
        setupChidBirhtAddress(userInformation, repo);
    }

	/**
	 * This method set a child birth city/state if the data presented in
	 * userInformation response defect 530998
	 */
	private void setupChidBirhtAddress(final UserInformation userInformation,
			RbpsRepository repo) {
		List<VnpPerson> personList = userInformation.getPersonData();
		List<Child> childList = repo.getVeteran().getChildren();
		for (VnpPerson p : personList) {
			if (p.getBirthCityNm() != null || p.getBirthStateCd() != null) {
				for (Child cd : childList) {
					// null pointer exception in SR # 780616.
					if (cd.getSsn() != null && cd.getSsn().equals(p.getSsnNbr())) {
						Address address = new Address();
						address.setCity(p.getBirthCityNm());
						address.setState(p.getBirthStateCd());
						cd.setBirthAddress(address);
					}
				}
			}
		}
	}

    /**
     * This method resets all the instance variables to its original state i.e. null.
     */
    private void cleanup() {

        vnpPersonMap                = null;
        vnpPtcpntPhoneMap           = null;
        vnpPtcpntAddrsMap           = null;
        vnpPtcpntMap                = null;
        vnpChildStudntMap           = null;
        veteransExistingChildrenMap = null;
        participantIdSchoolListMap  = null;
        veteran                     = null;
    }


    private final void setRepository( final RbpsRepository    repository,
                                final VnpProc           vnpProc, Veteran vet ) {

        repository.setVeteran(vet);
        if (vnpProc == null ) {
            return;
        }

        repository.setVnpProcId(vnpProc.getVnpProcId());
        repository.setVnpProcStateType(vnpProc.getVnpProcStateTypeCd());
        repository.setVnpCreatdDt(vnpProc.getCreatdDt());
        repository.setVnpLastModifdDt(vnpProc.getLastModifdDt());
        repository.setJrnDt(vnpProc.getJrnDt());
        repository.setJrnLctnId(vnpProc.getJrnLctnId());
        repository.setJrnObjId(vnpProc.getJrnObjId());
        repository.setJrnStatusTypeCd(vnpProc.getJrnStatusTypeCd());
        repository.setJrnUserId(vnpProc.getJrnUserId());
        
    }


    private void set686cDependents(final List<VnpPtcpntRlnshp> vnpPtcpntRlnshpData) {

        for (final VnpPtcpntRlnshp vnpPtcpntRlnshp : vnpPtcpntRlnshpData) {

            // Adds Veteran's dependent information
            addDependent(vnpPtcpntRlnshp, veteran);
        }

        for (final VnpPtcpntRlnshp vnpPtcpntRlnshp : vnpPtcpntRlnshpData) {

            if (veteran.getCurrentMarriage() != null) {

                // Adds Veteran's spouse's dependent information
                addDependent(vnpPtcpntRlnshp, veteran.getCurrentMarriage().getMarriedTo());

            }
        }


        populateCurrentMarriageForSpouse();
    }


    private void populateCurrentMarriageForSpouse() {

        if ( veteran.getCurrentMarriage() == null ) {

            return;
        }

        veteran.getCurrentMarriage().getMarriedTo().setCurrentMarriage( populateMarriage(veteran.getCurrentMarriage() ));
    }


    private void populateSpouseForLatestPreviousMarriage(RbpsRepository repo ) {
    	
    	if ( veteran.getLatestPreviousMarriage() == null ) {
    		
    		return;
    	}
    	
    	Spouse previousSpouse = veteran.getLatestPreviousMarriage().getMarriedTo();
    	
    	Marriage	marriage	= new Marriage();
    	marriage.setStartDate(veteran.getLatestPreviousMarriage().getStartDate());
    	marriage.setEndDate(veteran.getLatestPreviousMarriage().getEndDate());
    	marriage.setMarriedPlace(veteran.getLatestPreviousMarriage().getMarriedPlace());
    	marriage.setTerminationPlace(veteran.getLatestPreviousMarriage().getTerminationPlace());
    	marriage.setTerminationType(veteran.getLatestPreviousMarriage().getTerminationType());
    	
    	Spouse spouse	= new Spouse();
    	spouse.setFirstName(veteran.getFirstName());
    	spouse.setLastName(veteran.getLastName());
    	spouse.setBirthDate(veteran.getBirthDate());
    	spouse.setSsn(veteran.getSsn());
    	spouse.setCorpParticipantId(veteran.getCorpParticipantId());
    	marriage.setMarriedTo(spouse);
    	
    	previousSpouse.setCurrentMarriage( marriage );
    }

    
    private void addDependent( final VnpPtcpntRlnshp    vnpPtcpntRlnshp,
                               final Person             person) {

        if (vnpPtcpntRlnshp != null &&
            person != null &&
            vnpPtcpntRlnshp.getVnpPtcpntIdA() != null &&
            vnpPtcpntRlnshp.getVnpPtcpntIdA().equals(person.getVnpParticipantId())) {

            addChildOrSpouse(vnpPtcpntRlnshp, person);
        }
    }


    private void addChildOrSpouse( final VnpPtcpntRlnshp    vnpPtcpntRlnshp,
                                   final Person             person) {

        final String relationshipType  = vnpPtcpntRlnshp.getPtcpntRlnshpTypeNm();
        final Long participantB        = vnpPtcpntRlnshp.getVnpPtcpntIdB();

        if (relationshipType == null || participantB == null ) {
            return;
        }

        if ( relationshipType.equalsIgnoreCase(RelationshipType.CHILD.getValue())) {

            addChild(vnpPtcpntRlnshp, person, participantB);

        } else if (relationshipType.equalsIgnoreCase(RelationshipType.SPOUSE.getValue())) {

            addSpouse(vnpPtcpntRlnshp, person, participantB);

        }
    }


    private void addChild(final VnpPtcpntRlnshp vnpPtcpntRlnshp, final Person person, final Long participantB) {

        final Child child = createChild(participantB, vnpPtcpntRlnshp);

        if (child == null) {
            return;
        }

        person.addChild(child);
    }


    private void addSpouse(final VnpPtcpntRlnshp vnpPtcpntRlnshp, final Person person, final Long participantB) {

        final Marriage marriage = createMarriage(participantB, vnpPtcpntRlnshp);

        if (marriage == null) {
            return;
        }

        if (vnpPtcpntRlnshp.getEndDt() == null ) {

            // Current Spouse
            person.setCurrentMarriage(marriage);
        }
        else {

            // Previous spouses
            person.addPreviousMarriage(marriage);

            populateLatestPreviousMarriage(person, marriage);
        }
    }


    private void populateLatestPreviousMarriage(final Person person, final Marriage marriage) {

        if (person.getLatestPreviousMarriage() == null) {

            person.setLatestPreviousMarriage(marriage);
        }
        else if ( marriage.getEndDate().after( person.getLatestPreviousMarriage().getEndDate()) ){

            person.setLatestPreviousMarriage(marriage);
        }
    }

    private Marriage populateMarriage(final Marriage currentMarriage ) {
         final Marriage marriage = new Marriage();

         marriage.setStartDate(currentMarriage.getStartDate());
            marriage.setEndDate(currentMarriage.getEndDate());
            marriage.setMarriedPlace(currentMarriage.getMarriedPlace());
            marriage.setMarriedTo(null);
            marriage.setTerminationPlace(null);
            marriage.setTerminationType(null);

         return marriage;

    }

    private Marriage createMarriage(final Long participantB, final VnpPtcpntRlnshp vnpPtcpntRlnshp) {

        if (vnpPtcpntRlnshp == null) {
            return null;
        }

        final Marriage marriage = new Marriage();

        marriage.setStartDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpPtcpntRlnshp.getBeginDt()));
        marriage.setEndDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpPtcpntRlnshp.getEndDt()));
        marriage.setMarriedPlace(createMarriageAddress(vnpPtcpntRlnshp.getMarageCityNm(),vnpPtcpntRlnshp.getMarageStateCd(), vnpPtcpntRlnshp.getMarageCntryNm()));
        marriage.setMarriedTo(createSpouse(participantB, vnpPtcpntRlnshp));
        marriage.setTerminationPlace(createMarriageAddress(vnpPtcpntRlnshp.getMarageTrmntnCityNm(), vnpPtcpntRlnshp.getMarageTrmntnStateCd(), vnpPtcpntRlnshp.getMarageTrmntnCntryNm()));
        marriage.setTerminationType(MarriageTerminationType.find(vnpPtcpntRlnshp.getMarageTrmntnTypeCd()));

        return marriage;
    }


    private Address createMarriageAddress(final String city, final String state, final String country) {

        final Address address = new Address();

        address.setCity(city);
        address.setState(state);
        address.setCountry(country);

        return address;
    }


    private Spouse createSpouse(final Long participantB, final VnpPtcpntRlnshp vnpPtcpntRlnshp) {

        final Spouse spouse = new Spouse();

        setDependentFields(participantB, spouse, vnpPtcpntRlnshp);

        final VnpPerson vnpPerson = vnpPersonMap.get(participantB);

        if (vnpPerson != null) {
            // Subject to change based on VetInd character.
            spouse.setVet(getNPESafeBooleanValue(vnpPerson.getVetInd(), RbpsConstants.INDICATOR_Y));
            spouse.setFileNumber(vnpPerson.getFileNbr());
        }

        return spouse;
    }


    private Child createChild( final Long               participantB,
                               final VnpPtcpntRlnshp    vnpPtcpntRlnshp) {

        final VnpPerson vnpPerson = vnpPersonMap.get(participantB);

        if (vnpPerson == null) {
            return null;
        }

        final Child child = new Child();

        setDependentFields(participantB, child, vnpPtcpntRlnshp);
        
        logger.debug( "  RbpsRepositoryPopulator   **********vnpPtcpntRlnshp.getFamilyRlnshpTypeNm()    " + vnpPtcpntRlnshp.getFamilyRlnshpTypeNm());
        logger.debug( "Child type******************" + ChildType.find(vnpPtcpntRlnshp.getFamilyRlnshpTypeNm()));

        child.setChildType(ChildType.find(vnpPtcpntRlnshp.getFamilyRlnshpTypeNm()));
        child.setUnfilteredChildType( vnpPtcpntRlnshp.getFamilyRlnshpTypeNm() );
      //        setSchoolChild( vnpPerson, child );

        // Subject to change based on SruslyDsabldInd character.
        child.setSeriouslyDisabled(getNPESafeBooleanValue(vnpPerson.getVnpSruslyDsabldInd(), RbpsConstants.INDICATOR_Y));
        child.setPreviouslyMarried(getNPESafeBooleanValue(vnpPtcpntRlnshp.getChildPrevlyMarriedInd(), RbpsConstants.INDICATOR_Y));

        if (vnpPtcpntRlnshp.getFamilyRlnshpTypeNm() != null && !vnpPtcpntRlnshp.getFamilyRlnshpTypeNm().isEmpty()) {
            child.addForm(FormType.FORM_21_686C);
        }



        return child;
    }

    private void dependentHasIncome(VnpPtcpntRlnshp  vnpPtcpntRlnshp, Dependent dependent) {
        if (vnpPtcpntRlnshp.getDepHasIncomeInd() != null) {
            dependent.setHasIncome(vnpPtcpntRlnshp.getDepHasIncomeInd().equalsIgnoreCase(RbpsConstants.INDICATOR_Y));
        }
    }

    private void isNetWorthOverLimit(VnpBnftClaim  claim, Veteran veteran) {
        if (claim.getNetWorthOverLimitInd() != null) {
            veteran.setNetWorthOverLimit(claim.getNetWorthOverLimitInd().equalsIgnoreCase(RbpsConstants.INDICATOR_Y));
        }
    }

    public void setSchoolChild( final VnpPerson vnpPerson, final Child child, RbpsRepository repo ) {

        String          schoolChildIndStr   = vnpPerson.getVnpSchoolChildInd();
        boolean         schoolChildInd      = getNPESafeBooleanValue( vnpPerson.getVnpSchoolChildInd(),
                                                                      RbpsConstants.INDICATOR_Y );
        VdcClaimFilter  vdcClaimFilter      = new VdcClaimFilter();

        if ( vdcClaimFilter.isVdcClaim(repo ) ) {

            CommonUtils.log( logger, "\n" + RbpsConstants.BAR_FORMAT + "\n\tThis is a VDC Claim, so not using share workaround, setting schoolChild to " + schoolChildInd );
            child.setSchoolChild( schoolChildInd );
            return;
        }

        if ( ! shouldPerformSchoolChildIndWorkaround ) {

             CommonUtils.log( logger, "\n" + RbpsConstants.BAR_FORMAT + "\n\tConfigured not to use share workaround, setting schoolChild to " + schoolChildInd );
             child.setSchoolChild( schoolChildInd );
             return;
        }

         //
         //      This is the workaround:
         //
         //      If the user chooses "18 - 23", then
         //      share sets school child ind to blank.
         //      => so we set it to true if blank.
         //
         //      If the user chooses "under 18", then
         //      share sets school child to true.
         //      => so we set it to false if true.
         //
         if ( StringUtils.isBlank( schoolChildIndStr ) ) {

             CommonUtils.log( logger, "\n" + RbpsConstants.BAR_FORMAT + "\n\tFound a blank schoolChildInd, setting schoolChild to true" );
             child.setSchoolChild( true );
         }
         if ( schoolChildInd ) {

             CommonUtils.log( logger, "\n" + RbpsConstants.BAR_FORMAT + "\n\tFound a true schoolChildInd, setting schoolChild to false" );
             child.setSchoolChild( false );
         }
    }


    private void setDependentFields(final Long ptcpntIdB, final Dependent dependent, final VnpPtcpntRlnshp vnpPtcpntRlnshp) {
        addVnpObjectFields(ptcpntIdB, dependent);

        if (vnpPtcpntRlnshp != null) {
            dependentHasIncome(vnpPtcpntRlnshp, dependent);

            // if ptcpntIdA is a veteran and ptcpntIdB lives with ptcpntIdA then dependent lives with veteran
            if (vnpPtcpntRlnshp.getVnpPtcpntIdA() != null &&
                    vnpPtcpntRlnshp.getVnpPtcpntIdA().equals(veteran.getVnpParticipantId())) {

                // Subject to change based on LivesWithRelatdPersonInd character.
                dependent.setLivingWithVeteran(getNPESafeBooleanValue( vnpPtcpntRlnshp.getLivesWithRelatdPersonInd(),
                        RbpsConstants.INDICATOR_Y));
            }
        }


    }


    private void setVeteran(final UserInformation userInformation, RbpsRepository repo) {

        veteran = new Veteran();
        repo.setVeteran( veteran );
        final List<VnpPerson> personData = userInformation.getPersonData();

        if (CollectionUtils.isEmpty(personData)) {

            logger.debug(this.getClass().getName() + " : " + new Exception().getStackTrace()[0].getMethodName() + "VnpPerson is empty or null. Couldn't set Veteran.");
            return;
        }

        final VnpPerson veteranVnpPerson = findVeteranFromClaimData(userInformation.getBnftClaim().get(0));

        if ( null == veteranVnpPerson ) {
        	repo.setBnftClaimId(userInformation.getBnftClaim().get(0).getBnftClaimId() );
        	throw new RbpsRuntimeException( "Veteran object missing in UserInformation response" );
        }
        
        addVnpObjectFields(veteranVnpPerson.getVnpPtcpntId(), veteran);
        if ( ! CollectionUtils.isEmpty(userInformation.getBnftClaim())) {
            VnpBnftClaim claim = userInformation.getBnftClaim().get(0);
            isNetWorthOverLimit(claim, veteran);
            addVnpBnftClaimFields(claim, repo);
        }

        addVnpProcFormFields(userInformation.getProcFormData());
        addVnpPtcpntPhoneFields(veteranVnpPerson.getVnpPtcpntId());

        veteran.setFileNumber(veteranVnpPerson.getFileNbr());
        veteran.setSalutation(SalutationType.find(veteranVnpPerson.getTitleTxt()));
        veteran.setMaritalStatus(MaritalStatusType.find(veteranVnpPerson.getMartlStatusTypeCd()));
    }


    private VnpPerson findVeteranFromClaimData(final VnpBnftClaim vnpBnftClaim) {

        final Long vetId = vnpBnftClaim.getVnpPtcpntVetId();
        return vnpPersonMap.get(vetId);
    }


//    private VnpPerson findVeteranFromPersonData(final List<VnpPerson> personData) {
//
//        for (VnpPerson vnpPerson : personData) {
//
//            if (getNPESafeBooleanValue(vnpPerson.getVetInd(), RbpsConstants.INDICATOR_Y)) {
//
//                return vnpPerson;
//            }
//        }
//
//        return personData.get(0);
//    }


    private void addVnpProcFormFields(final List<VnpProcForm> vnpProcFormList) {

        if (vnpProcFormList == null) {

            return;
        }

        if (veteran.getClaim() == null) {

            veteran.setClaim(new Claim());
        }

        for (final VnpProcForm vnpProcForm : vnpProcFormList) {

            veteran.getClaim().addForm(FormType.find(vnpProcForm.getFormTypeCd()));
        }
    }


    private void addVnpBnftClaimFields(final VnpBnftClaim vnpBnftClaim, RbpsRepository repo) {

        if (vnpBnftClaim == null) {
            return;
        }

        veteran.setClaim(createClaim(vnpBnftClaim, repo));
    }


    private static final Claim createClaim(final VnpBnftClaim vnpBnftClaim, RbpsRepository repo) {

        final Claim claim = new Claim();

        if ( vnpBnftClaim.getBnftClaimId() != null ) {
        	claim.setClaimId(vnpBnftClaim.getBnftClaimId());
        }
        claim.setHasAttachments(getNPESafeBooleanValue(vnpBnftClaim.getAtchmsInd(), RbpsConstants.INDICATOR_Y));
        claim.setEndProductCode(EndProductType.find(vnpBnftClaim.getEndPrdctTypeCd()));
        claim.setReceivedDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpBnftClaim.getClaimRcvdDt()));
        claim.setClaimLabel(ClaimLabelType.find(vnpBnftClaim.getBnftClaimTypeCd()));
        
        //for ccr 2192
        claim.setJrnObjId(vnpBnftClaim.getJrnObjId());
       // repo.setClaimEndProductCode(EndProductType.find(vnpBnftClaim.getEndPrdctTypeCd()).getValue());
        repo.setClaimStationLocationId( getNPESafeLongValue( vnpBnftClaim.getClaimJrsdtnLctnId() ) );

        return claim;
    }


    private void addVnpObjectFields(final Long ptcpntId, final Person person) {

        addVnpPersonFields(ptcpntId, person, vnpPersonMap);
        addVnpPtcpntFields(ptcpntId, person, vnpPtcpntMap);
        addVnpPtcpntAddrsFields(ptcpntId, person, vnpPtcpntAddrsMap);
    }


    private static void addVnpPtcpntFields(final Long ptcpntId, final Person person, Map<Long, VnpPtcpnt> vnpPartMap) {

        final VnpPtcpnt vnpPtcpnt = vnpPartMap.get(ptcpntId);
        if (vnpPtcpnt == null) {
            return;
        }

        long corpPtcpntId;

        try {
            corpPtcpntId = vnpPtcpnt.getCorpPtcpntId().longValue();

        } catch(final Exception ex) {

            corpPtcpntId = 0;
        }

        person.setCorpParticipantId(corpPtcpntId);
    }


    private static final void addVnpPtcpntAddrsFields(final Long ptcpntId, final Person person, Map<Long, VnpPtcpntAddrs> vnpAddrsMap) {

        final VnpPtcpntAddrs vnpPtcpntAddrs = vnpAddrsMap.get(ptcpntId);

        if (vnpPtcpntAddrs == null) {
            return;
        }

        // Right now vnpPtcpntAddrsMap contains only participants mailing address.
        person.setMailingAddress(createAddress(vnpPtcpntAddrs));
        person.setEmail(vnpPtcpntAddrs.getEmailAddrsTxt());
    }


    private void addVnpChildStudntFields(final VnpChildStudnt vnpChildStudnt) {

        if (vnpChildStudnt == null) {
            return;
        }

        final Child child = get686Child(vnpChildStudnt.getVnpPtcpntId(), vnpPersonMap, veteran);

        if (child == null) {
            return;
        }

        child.setAgencyPayingTuition( vnpChildStudnt.getAgencyPayingTuitnNm() );
        setTuitionPaidByGovernment( vnpChildStudnt, child );
        child.setGovtPaymentStartDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpChildStudnt.getGovtPaidTuitnStartDt()));

        if (vnpChildStudnt.getMarageDt() != null) {

            child.setCurrentMarriage(createChildMarriage(vnpChildStudnt.getMarageDt()));
        }
    }


    private static final void setTuitionPaidByGovernment( final VnpChildStudnt   vnpChildStudnt,
                                             final Child            child ) {

        //
        //  Share is sending the first letter of the value in educational
        //  assistance. So set to false when it is N (for none) or null
        //
        final boolean tuitionPaidFor = vonapSaysTuitionPaidByGovernment( vnpChildStudnt );
        child.setTuitionPaidByGovt( tuitionPaidFor );
    }


    private static final boolean vonapSaysTuitionPaidByGovernment( final VnpChildStudnt vnpChildStudnt ) {

        return ! (vnpChildStudnt.getGovtPaidTuitnInd() == null
                  || vnpChildStudnt.getGovtPaidTuitnInd().equalsIgnoreCase("N"));
    }


    private static final Marriage createChildMarriage(final XMLGregorianCalendar marriageDate) {

        final Marriage marriage = new Marriage();

        marriage.setStartDate(SimpleDateUtils.xmlGregorianCalendarToDay(marriageDate));

        return marriage;
    }


    private static final Education createCurrentTerm(final VnpChildSchool currentVnpChildSchool) {

        if (currentVnpChildSchool == null) {
            return null;
        }

        final Education education = new Education();

        education.setCourseEndDate(SimpleDateUtils.xmlGregorianCalendarToDay(currentVnpChildSchool.getGradtnDt()));
        education.setCourseName(currentVnpChildSchool.getCourseNameTxt());

        // Same as CourseEndDate for Current Term
        education.setExpectedGraduationDate(SimpleDateUtils.xmlGregorianCalendarToDay(currentVnpChildSchool.getGradtnDt()));
        education.setHoursPerWeek(getNPESafeIntValue(currentVnpChildSchool.getCurntHoursPerWkNum()));
        education.setOfficialCourseStartDate(SimpleDateUtils.xmlGregorianCalendarToDay(currentVnpChildSchool.getSchoolTermStartDt()));
        education.setSchool(createSchool(currentVnpChildSchool));
        education.setSessionsPerWeek(getNPESafeIntValue(currentVnpChildSchool.getCurntSessnsPerWkNum()));
        education.setEducationType(getEducationType(currentVnpChildSchool));
        education.setEducationLevelType(EducationLevelTypeLookup.find(currentVnpChildSchool.getFullTimeStudntTypeCd()));
        education.setSubjectName(currentVnpChildSchool.getPartTimeSchoolSubjctTxt());
        education.setCourseStudentStartDate(SimpleDateUtils.xmlGregorianCalendarToDay(currentVnpChildSchool.getSchoolActualExpctdStartDt()));

        return education;
    }


    private static final EducationType getEducationType(final VnpChildSchool childSchool) {

        if ( childSchool.getCurntSessnsPerWkNum() == null
             || childSchool.getCurntHoursPerWkNum() == null ) {

            return EducationType.FULL_TIME;
        }


        if ( childSchool.getCurntSessnsPerWkNum().intValue() == 0
             && childSchool.getCurntHoursPerWkNum().intValue() == 0 ) {

            return EducationType.FULL_TIME;
       }

        return EducationType.PART_TIME;
    }


    private static final School createSchool(final VnpChildSchool vnpChildSchool) {

        final School school = new School();

        school.setAddress(createChildSchoolAddress(vnpChildSchool));
        school.setName(vnpChildSchool.getCurntSchoolNm());
        school.setEduInstnPtcpntId( getNPESafeLongBigDecValue(vnpChildSchool.getCurrentEduInstnPtcpntId()));

        return school;
    }


    private static final Address createChildSchoolAddress(final VnpChildSchool vnpChildSchool) {

        final Address address = new Address();

        address.setLine1(vnpChildSchool.getCurntSchoolAddrsOneTxt());
        address.setLine2(vnpChildSchool.getCurntSchoolAddrsTwoTxt());
        address.setLine3(vnpChildSchool.getCurntSchoolAddrsThreeTxt());
        address.setZipPrefix(vnpChildSchool.getCurntSchoolAddrsZipNbr());
        address.setState(vnpChildSchool.getCurntSchoolPostalCd());

        return address;
    }


    private static final Address createAddress(final VnpPtcpntAddrs vnpPtcpntAddrs) {

        if (vnpPtcpntAddrs == null) {
            return null;
        }

        final Address address = new Address();

        address.setCity(vnpPtcpntAddrs.getCityNm());
        address.setCountry(vnpPtcpntAddrs.getCntryNm());
        address.setLine1(vnpPtcpntAddrs.getAddrsOneTxt());
        address.setLine2(vnpPtcpntAddrs.getAddrsTwoTxt());
        address.setLine3(vnpPtcpntAddrs.getAddrsThreeTxt());
        address.setState(vnpPtcpntAddrs.getPostalCd());
        address.setZipPrefix(vnpPtcpntAddrs.getZipPrefixNbr());
        address.setZipSuffix1(vnpPtcpntAddrs.getZipFirstSuffixNbr());
        address.setZipSuffix2(vnpPtcpntAddrs.getZipSecondSuffixNbr());
        address.setMltyPostalTypeCd(vnpPtcpntAddrs.getMltyPostalTypeCd());
        address.setMltyPostOfficeTypeCd(vnpPtcpntAddrs.getMltyPostOfficeTypeCd());
        return address;
    }


    private void addVnpPtcpntPhoneFields(final Long ptcpntId) {

        veteran.setDayTimePhone(createPhone((VnpPtcpntPhone) vnpPtcpntPhoneMap.get(ptcpntId, PhoneType.DAY.getValue())));
        veteran.setNightTimePhone(createPhone((VnpPtcpntPhone) vnpPtcpntPhoneMap.get(ptcpntId, PhoneType.NIGHT.getValue())));

    }


    private Phone createPhone(final VnpPtcpntPhone vnpPtcpntPhone) {

        if (vnpPtcpntPhone == null) {
            return null;
        }

        final Phone phone = new Phone();

        phone.setAreaCode(getExceptionSafeLongValue(vnpPtcpntPhone.getAreaNbr()));
        phone.setCountryCode(getExceptionSafeLongValue(vnpPtcpntPhone.getCntryNbr()));
        phone.setExtension(getExceptionSafeLongValue(vnpPtcpntPhone.getExtnsnNbr()));
        phone.setPhoneNumber(getExceptionSafeLongValue(vnpPtcpntPhone.getPhoneNbr()));

        return phone;
    }


    private static final void addVnpPersonFields(final Long ptcpntId, final Person person, Map<Long, VnpPerson> vnpPersMap) {

        final VnpPerson vnpPerson = vnpPersMap.get(ptcpntId);

        if (vnpPerson == null) {
            return;
        }

        person.setVnpParticipantId(vnpPerson.getVnpPtcpntId());
        person.setBirthDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpPerson.getBrthdyDt()));
        person.setFirstName(vnpPerson.getFirstNm().trim() );
        person.setLastName(vnpPerson.getLastNm().trim() );
        person.setGender(GenderType.find(vnpPerson.getGenderCd()));
        person.setAlive(vnpPerson.getDeathDt() == null);
        person.setSuffixName(vnpPerson.getSuffixNm());
        person.setNoSsnReason(NoSSNReasonType.find(vnpPerson.getNoSsnReasonTypeCd()));
        person.setMiddleName(vnpPerson.getMiddleNm());
        person.setSsn(vnpPerson.getSsnNbr());
        person.setSsnVerification(SSNVerificationType.find(vnpPerson.getSsnVrfctnStatusTypeCd()));
        person.setDeathDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpPerson.getDeathDt()));
        
    }


    private void setVnpObjectMaps(final UserInformation userInformation) {

        setVnpPersonMap(userInformation.getPersonData());
        setVnpPtcpntPhoneMap(userInformation.getVnpPtcpntPhoneData());
        setVnpPtcpntAddrsMap(userInformation.getAddressData());
        setVnpPtcpntMap(userInformation.getParticipantData());
        setVnpChildStudntMap(userInformation.getChildStudnt());
        setParticipantIdSchoolListMap (userInformation.getChildSchool());
    }


    private void setVnpPtcpntMap(final List<VnpPtcpnt> participantData) {

        vnpPtcpntMap = new HashMap<Long, VnpPtcpnt>();

        if (participantData == null) {
            return;
        }

        for ( final VnpPtcpnt vnpPtcpnt : participantData ) {

            if (vnpPtcpnt != null && vnpPtcpnt.getVnpPtcpntId() != 0) {
                vnpPtcpntMap.put(vnpPtcpnt.getVnpPtcpntId(), vnpPtcpnt);
            }
        }

    }


    private void setVnpPtcpntAddrsMap(final List<VnpPtcpntAddrs> addressData) {

        vnpPtcpntAddrsMap = new HashMap<Long, VnpPtcpntAddrs>();

        if (addressData == null) {
            return;
        }

        for ( final VnpPtcpntAddrs vnpPtcpntAddrs : addressData ) {
            // Right now we need just mailing address, so adding only mailing address.
            if (vnpPtcpntAddrs != null &&
                vnpPtcpntAddrs.getVnpPtcpntId() != null &&
                getNPESafeBooleanValue(vnpPtcpntAddrs.getPtcpntAddrsTypeNm(), RbpsConstants.MAILING_ADDRESS)) {
                vnpPtcpntAddrsMap.put(vnpPtcpntAddrs.getVnpPtcpntId(), vnpPtcpntAddrs);
            }
        }

    }


    private void setVnpPtcpntPhoneMap(final List<VnpPtcpntPhone> vnpPtcpntPhoneData) {

        vnpPtcpntPhoneMap = new MultiKeyMap();

        if (vnpPtcpntPhoneData == null) {
            return;
        }

        for ( final VnpPtcpntPhone vnpPtcpntPhone : vnpPtcpntPhoneData ) {

            if (vnpPtcpntPhone != null && vnpPtcpntPhone.getVnpPtcpntId() != null) {
                vnpPtcpntPhoneMap.put(getExceptionSafeLongValue(vnpPtcpntPhone.getVnpPtcpntId()), vnpPtcpntPhone.getPhoneTypeNm(), vnpPtcpntPhone);
            }
        }

    }


    private void setVnpPersonMap(final List<VnpPerson> personData) {

        vnpPersonMap = new HashMap<Long, VnpPerson>();

        if (personData == null) {
            return;
        }

        for ( final VnpPerson vnpPerson : personData ) {

            if (vnpPerson != null && vnpPerson.getVnpPtcpntId() != 0) {
                vnpPersonMap.put(vnpPerson.getVnpPtcpntId(), vnpPerson);
            }
        }

    }


    private static final List<Education> createPreviousTermList(final List<VnpChildSchool> previousVnpChildSchoolList) {

        final List<Education> previousTermList = new ArrayList<Education>();

        for (final VnpChildSchool prevVnpChildSchool : previousVnpChildSchoolList) {

            final Education prevCurrentTerm = createCurrentTerm(prevVnpChildSchool);
            final Education prevPreviousTerm = createPreviousTerm(prevVnpChildSchool);

            if (prevCurrentTerm != null) {
                // current term of previous 674 is also a previous term.
                previousTermList.add(prevCurrentTerm);
            }
            if (prevPreviousTerm != null) {
                previousTermList.add(prevPreviousTerm);
            }
        }

        return previousTermList;
    }


    private static final Education createPreviousTerm(final VnpChildSchool vnpChildSchool) {
    	CommonUtils.log( logger, "\n" + RbpsConstants.BAR_FORMAT + "BEGIN createPreviousTerm");
        if ((vnpChildSchool == null
            ||(vnpChildSchool.getPrevHoursPerWkNum() == null || vnpChildSchool.getPrevHoursPerWkNum().intValue() == 0)
            && vnpChildSchool.getPrevSchoolAddrsOneTxt() == null
            //&& vnpChildSchool.getPrevSchoolAddrsTwoTxt() == null
            //&& vnpChildSchool.getPrevSchoolAddrsThreeTxt() == null
            && vnpChildSchool.getPrevSchoolAddrsZipNbr() == null
            && vnpChildSchool.getPrevSchoolPostalCd() == null
            && (vnpChildSchool.getPrevSessnsPerWkNum() == null || vnpChildSchool.getPrevSessnsPerWkNum().intValue() == 0)
            && vnpChildSchool.getLastTermEndDt() == null
            && vnpChildSchool.getLastTermStartDt() == null)) {
        	
        	CommonUtils.log( logger, "\n" + RbpsConstants.BAR_FORMAT + "\n\tNo Data found for Previous Term Found");
            return null;
        }

        final Education previousTerm = new Education();

        previousTerm.setHoursPerWeek(getNPESafeIntValue(vnpChildSchool.getPrevHoursPerWkNum()));
        previousTerm.setSchool(createPreviousSchool(vnpChildSchool));
        previousTerm.setSessionsPerWeek(getNPESafeIntValue(vnpChildSchool.getPrevSessnsPerWkNum()));
        previousTerm.setCourseEndDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpChildSchool.getLastTermEndDt()));
        previousTerm.setOfficialCourseStartDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpChildSchool.getLastTermStartDt()));

        //
        //      To match with Rule mapping setCourseStudentStartDate is set with
        //      LastTermStartDt - to address CP0145 - Minor School Child Award With 18th Birth Day
        //
        previousTerm.setCourseStudentStartDate(SimpleDateUtils.xmlGregorianCalendarToDay(vnpChildSchool.getLastTermStartDt()));
        
        CommonUtils.log( logger, "\n" + RbpsConstants.BAR_FORMAT + "BEGIN createPreviousTerm");
        return previousTerm;
    }


    private static final School createPreviousSchool(final VnpChildSchool vnpChildSchool) {

        final School previousSchool = new School();

        previousSchool.setAddress(createPreviousSchoolAddress(vnpChildSchool));
        previousSchool.setName(vnpChildSchool.getPrevSchoolNm());
        previousSchool.setEduInstnPtcpntId( getNPESafeLongValue(vnpChildSchool.getPrevEduInstnPtcpntId()) );

        return previousSchool;
    }


    private static final Address createPreviousSchoolAddress(final VnpChildSchool vnpChildSchool) {

        final Address previousAddress = new Address();

        previousAddress.setLine1(vnpChildSchool.getPrevSchoolAddrsOneTxt());
        previousAddress.setLine2(vnpChildSchool.getPrevSchoolAddrsTwoTxt());
        previousAddress.setLine3(vnpChildSchool.getPrevSchoolAddrsThreeTxt());
        previousAddress.setZipPrefix(vnpChildSchool.getPrevSchoolAddrsZipNbr());
        previousAddress.setState(vnpChildSchool.getPrevSchoolPostalCd());

        return previousAddress;
    }


    private void createVeteransExistingChildrenMap() {

        veteransExistingChildrenMap = new HashMap<Long, Child>();

        for (final Child child : veteran.getChildren()) {

            veteransExistingChildrenMap.put(child.getVnpParticipantId(), child);
        }
    }


    private void setVnpChildStudntMap(final List<VnpChildStudnt> vnpChildStudntList) {

        vnpChildStudntMap = new HashMap<Long, VnpChildStudnt>();

        if (vnpChildStudntList == null) {
            return;
        }

        for ( final VnpChildStudnt vnpChildStudnt : vnpChildStudntList ) {

            if (vnpChildStudnt != null && vnpChildStudnt.getVnpPtcpntId() != null) {
                vnpChildStudntMap.put(vnpChildStudnt.getVnpPtcpntId(), vnpChildStudnt);
            }
        }
    }


    private void setParticipantIdSchoolListMap (final List<VnpChildSchool> vnpChildSchoolList){

        participantIdSchoolListMap = new HashMap<Long, List<VnpChildSchool>>();

        if (CollectionUtils.isEmpty(vnpChildSchoolList)) {
            return;
        }

        for (final VnpChildSchool vnpChildSchool : vnpChildSchoolList) {

            if (vnpChildSchool == null || vnpChildSchool.getVnpPtcpntId() == null) {
                continue;
            }

            final Long vnpPtcpntId = vnpChildSchool.getVnpPtcpntId();

            if (participantIdSchoolListMap.get(vnpPtcpntId) == null) {

                final List<VnpChildSchool> vnpSchoolListForAChild  = new ArrayList<VnpChildSchool>();
                vnpSchoolListForAChild.add(vnpChildSchool);
                participantIdSchoolListMap.put(vnpPtcpntId, vnpSchoolListForAChild);

            } else {

                participantIdSchoolListMap.get(vnpPtcpntId).add(vnpChildSchool);
            }
        }
    }


    private static final VnpChildSchool getCurrentVnpChildSchool(final List<VnpChildSchool> vnpSchoolListForAChild) {

        if (CollectionUtils.isEmpty(vnpSchoolListForAChild)) {
            return null;
        }

        VnpChildSchool currentVnpChildSchool     = vnpSchoolListForAChild.get(0);
        XMLGregorianCalendar currentTermStartDate = null;

        if (currentVnpChildSchool != null) {
            currentTermStartDate = currentVnpChildSchool.getSchoolTermStartDt();
        }

        for (int i = 1; i < vnpSchoolListForAChild.size(); i++) {

            final VnpChildSchool vnpChildSchool = vnpSchoolListForAChild.get(i);

            if (vnpChildSchool == null || vnpChildSchool.getSchoolTermStartDt() == null) {
                continue;
            }

            if (currentTermStartDate == null || vnpChildSchool.getSchoolTermStartDt().toGregorianCalendar().compareTo(currentTermStartDate.toGregorianCalendar()) > 0) {

                currentVnpChildSchool    = vnpChildSchool;
                currentTermStartDate     = currentVnpChildSchool.getSchoolTermStartDt();
            }
        }

        return currentVnpChildSchool;
    }


    private void set674Dependents() {

        createVeteransExistingChildrenMap();

        // Long 674partcipantId = get686Id();

        for (final Long participantId : participantIdSchoolListMap.keySet()) {

            addVnpChildSchoolFields(participantId, vnpPersonMap, veteran);
        }

        for ( final Long participantId : vnpChildStudntMap.keySet()) {

            addVnpChildStudntFields(vnpChildStudntMap.get(participantId));
        }
    }


    private void addVnpChildSchoolFields(final Long participantId, Map<Long, VnpPerson> vnpPersMap, Veteran vet) {

        final Child child = get686Child(participantId, vnpPersonMap, vet);

        if (child == null) {
            return;
        }


        final List<VnpChildSchool>    vnpSchoolListForAChild     = participantIdSchoolListMap.get(participantId);
        final VnpChildSchool          currentVnpChildSchool      = getCurrentVnpChildSchool(vnpSchoolListForAChild);
        final List<VnpChildSchool>    previousVnpChildSchoolList = getPreviousVnpChildSchoolList(vnpSchoolListForAChild, currentVnpChildSchool);

        child.setCurrentTerm(createCurrentTerm(currentVnpChildSchool));

        // Previous term of current 674 is the last term.
        child.setLastTerm(createPreviousTerm(currentVnpChildSchool));
        child.setPreviousTerms(createPreviousTermList(previousVnpChildSchoolList));

        for (int ii = 0; ii < vnpSchoolListForAChild.size(); ii++) {

            child.addForm(FormType.FORM_21_674);
            child.setSchoolChild( true );
//            if ( shouldPerformSchoolChildIndWorkaround ) {
//
//                child.setSchoolChild( true );
//            }
        }
    }


    private static final Child get686Child(final Long participantId, Map<Long, VnpPerson> vnpPersMap, Veteran vet) {

        final VnpPerson child674 = vnpPersMap.get(participantId);

        for (final Child child : vet.getChildren()) {

            if ( ! childBirthDateMatchesVonapBirthDate( child, child674 ) ) {

                continue;
            }

            if ( ! firstAndLastNamesMatch( child, child674 )) {

                continue;
            }

            return child;
        }

        return null;
    }


    private static final boolean firstAndLastNamesMatch( final Child child, final VnpPerson child674 ) {

        return child.getFirstName().equalsIgnoreCase( child674.getFirstNm() )
                && child.getLastName().equalsIgnoreCase( child674.getLastNm() );
    }


    private static final boolean childBirthDateMatchesVonapBirthDate( final Child      child,
                                                         final VnpPerson  child674 ) {

        if ( child674.getBrthdyDt() == null ) {
        	
        	return false;
        }
        
        Date    vonapBirthDate = SimpleDateUtils.xmlGregorianCalendarToDay(child674.getBrthdyDt());

        if ( child.getBirthDate() == null ) {

            return false;
        }
        
        if ( vonapBirthDate == null ) {

            return false;
        }

        return DateUtils.isSameDay( child.getBirthDate(), vonapBirthDate );
    }


    private static final List<VnpChildSchool> getPreviousVnpChildSchoolList(final List<VnpChildSchool> vnpChildSchoolList, final VnpChildSchool currentVnpChildSchool) {

        final List<VnpChildSchool> previousSchoolList = new ArrayList<VnpChildSchool>(vnpChildSchoolList);
        previousSchoolList.remove(currentVnpChildSchool);

        return previousSchoolList;
    }


    private static final int getNPESafeIntValue(final BigDecimal bigDecimalField) {

        if (bigDecimalField == null) {

            return 0;
        }

        return bigDecimalField.intValue();
    }


    private static final long getNPESafeLongBigDecValue(final BigDecimal bigDecimalField) {

        if (bigDecimalField == null) {

            return 0;
        }

        return bigDecimalField.longValue();
    }


    private static final long getNPESafeLongValue(final Long longField) {

        if (longField == null) {

            return 0;
        }

        return longField.longValue();
    }

    private static final boolean getNPESafeBooleanValue(final String string, final String anotherString) {

        if (string == null) {

            return false;
        }

        return string.equalsIgnoreCase(anotherString);
    }


    private static final long getExceptionSafeLongValue(final String inputString) {

        long longValue = 0;

        final String stringValue  =   CommonUtils.getNumericString(inputString);

        if ( StringUtils.isBlank( stringValue ) ) {

            return longValue;
        }

        try{

            longValue = Long.valueOf(stringValue).longValue();
        }
        catch (final Throwable ex) {

            throw new RbpsRuntimeException( String.format( "Unable to parse >%s< into a long value.",
                                                           stringValue ), ex );
        }

        return longValue;
    }


    public void invalidateCache(RbpsRepository repo) {

        if ( cacheInvalidator == null ) {

            logUtils.log( "No cache invalidator, can't increment", repo);
            return;
        }

        cacheInvalidator.increment();
    }


    private void resetLogUtilsStackLevel() {

        LogUtils.newClaim();
    }




    public void setShouldPerformSchoolChildIndWorkaround( final boolean shouldPerformSchoolChildIndWorkaround ) {

        this.shouldPerformSchoolChildIndWorkaround = shouldPerformSchoolChildIndWorkaround;
    }

    public void setCacheInvalidator( final CacheInvalidator invalidator ) {

        cacheInvalidator = invalidator;
    }
}
