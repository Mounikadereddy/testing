/*
 * UpdateBenefitClaimDependentsPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 *
 */
package gov.va.vba.rbps.services.populators;


import static gov.va.vba.rbps.coreframework.util.RbpsConstants.PAYEE_CODE;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.CorporateDependentId;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.MarriageTerminationType;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.services.populators.utils.DependentDiffs;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependents;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependents.DependentUpdateInput;
import gov.va.vba.rbps.services.ws.client.mapping.vonapp.claimlabel.UpdateBenefitClaimDependents.DependentUpdateInput.Dependents;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;



/**
 *      Populate the request to the UpdateBenefitClaimDependentsWSHandler.
 *      We've broken the code out here because it's a somewhwat complex
 *      object to create, and we didn't want to pollute the ws handler with
 *      this code.
 *
 *      The request is a list of dependents which are not already in the corporate
 *      database and which are not on awards.  We test both award and minor school
 *      child award for children and just award for spouse.
 *
 *      The test for an award is whether the award exists and has an event date.
 *
 * @author vafsccorbit
 */
public class UpdateBenefitClaimDependentsPopulator {

    private static Logger logger = Logger.getLogger(UpdateBenefitClaimDependentsPopulator.class);


    private        LogUtils             logUtils        = new LogUtils( logger, true );



    /**
     *      This method is used to build an object of UpdateBenefitClaimDependents
     *      which are not already on the corporate data base and not on awards.
     *      The output is used as a request to the updateBenifitClaim Service.
     *
     *      @param repository
     *      @return UpdateBenefitClaimDependents
     */
    public UpdateBenefitClaimDependents buildAddDependentRequest( final RbpsRepository       repository ) {

        Veteran         veteran = repository.getVeteran();
        Spouse          spouse  = null;
        Spouse          prevSpouse  = null;

        if ( veteran.getCurrentMarriage() != null ) {

            spouse = veteran.getCurrentMarriage().getMarriedTo();
            if(veteran.getCurrentMarriage().getTerminationType() !=null &&veteran.getCurrentMarriage().getTerminationType().
            		equals(MarriageTerminationType.DEATH )&& spouse.getDeathDate()==null){
            	spouse.setDeathDate(veteran.getCurrentMarriage().getEndDate());
            	
            }
           
        }
        if(veteran.getLatestPreviousMarriage() != null && veteran.getLatestPreviousMarriage().getMarriedTo()!= null){
        //getting only last one            
        	prevSpouse = veteran.getLatestPreviousMarriage().getMarriedTo();
            logger.debug("In UpdateBenefitClaimDependents buildAddDependentRequest with prevSpouse :"+prevSpouse	);
        	if(prevSpouse != null && prevSpouse.getDeathDate()== null  && 
        			veteran.getLatestPreviousMarriage().getTerminationType()
        			.equals(MarriageTerminationType.DEATH )){
        		
        		prevSpouse.setDeathDate( veteran.getLatestPreviousMarriage().getEndDate());
        		logger.debug("UpdateBenefitClaimDependents buildAddDependentRequest with after set Date of death prevSpouse :"+prevSpouse.toString());
        	}
        	 
        }

        return buildAddDependentRequest( repository,
                                         veteran.getChildren(),
                                         spouse ,prevSpouse);
    }


    /**
     *      This method is used to build an object of UpdateBenefitClaimDependents
     *      which are not already on the corporate data base and not on awards.
     *      The output is used as a request to the updateBenifitClaim Service.
     *
     *      @param repository
     *      @param childrenToAdd
     *      @param spouseToAdd
     *      @return
     */
    public UpdateBenefitClaimDependents buildAddDependentRequest( final RbpsRepository       repository,
                                                                  final List<Child>          childrenToAdd,
                                                                  final Spouse               spouseToAdd ,                                                                  
                                                                  final Spouse               prevSpouse) {
                                                                  
        UpdateBenefitClaimDependents    input           = new UpdateBenefitClaimDependents();
        DependentUpdateInput            dependentUpdate = hydrateDependentInput( repository,
                                                                                 childrenToAdd,
                                                                                 spouseToAdd,
                                                                                 prevSpouse);

        input.setDependentUpdateInput( dependentUpdate );

        return input;
    }


    /**
     *      Creates and returns a  DependentUpdateInput object built from
     *      repository, spouse and list of childrenToAdd who are not
     *      already on the corporate data base and on awards.
     *
     *      @param repository
     *      @param childrenToAdd
     *      @param spouseToAdd
     *      @return
     */
    public DependentUpdateInput hydrateDependentInput( final RbpsRepository     repository,
                                                       final List<Child>        childrenToAdd,
                                                       final Spouse             spouseToAdd,
                                                       final Spouse               prevSpouse ) {

        DependentUpdateInput    input = new DependentUpdateInput();

        input.setFileNumber( repository.getVeteran().getFileNumber() );
        input.setPayeeCode( PAYEE_CODE );
        input.setPtcpntIdVet( "" + CommonUtils.participantIdFor( repository.getVeteran() ) );
        input.setPtcpntIdPayee( input.getPtcpntIdVet() );

        populateNewDependents( repository,
                               input,
                               spouseToAdd,
                               prevSpouse );

        return input;
    }


    private void populateNewDependents( final RbpsRepository            repository,
                                        final DependentUpdateInput      input,
                                        final Spouse                    spouseToAdd,
                                        final Spouse                    prevSpouse ) {

        populateNewChildren( repository, input );
        populateSpouse( repository, input, spouseToAdd,prevSpouse );

        setNumDependents( input );
    }


    private void populateSpouse( final RbpsRepository         repository,
                                 final DependentUpdateInput   input,
                                 final Spouse                 spouseToAdd ,
                                 final Spouse                 prevSpouse ) {

        if ( spouseToAdd == null && prevSpouse==null ) {

            logUtils.log( "no spouse to add, skipping" , repository );
            return;
        }

        addSpouseToInput( input, spouseToAdd, prevSpouse,repository );
    }


    /**
     *      Adds list of new children who are not
     *      already on the corporate data base and
     *      on awards to DependentUpdateInput object.
     *
     * @param repository
     * @param input
     */
    public void populateNewChildren( final RbpsRepository           repository,
                                     final DependentUpdateInput     input ) {

        List<Child> childrenToAdd = getListOfChildrenToAdd( repository );

        addNewChildren( input, childrenToAdd, repository );
    }


    private void addNewChildren( final DependentUpdateInput  input,
                                 final List<Child>           children,
                                 RbpsRepository repository) {

        if ( CollectionUtils.isEmpty( children ) ) {

            logUtils.log( "no kids to add, skipping", repository );
            return;
        }

        for ( Child child : children ) {

            addChildToInput( input, child, repository );
        }
    }


    private void addSpouseToInput( final DependentUpdateInput input, final Spouse spouse,final Spouse prevSpouse, final RbpsRepository repository ) {

        Dependents  dependent = new Dependents();
        if ((spouse ==null ||( !spouseShouldBeAdded( spouse, repository )
        		&& spouse.getDeathDate()==null))&&
        		(prevSpouse ==null ||(!spouseShouldBeAdded( prevSpouse, repository )
        				&& prevSpouse.getDeathDate()==null))){
        	return;
        }

        if (spouse!=null && (spouseShouldBeAdded( spouse, repository ) || spouse.getDeathDate()!=null)) {

        	 populateDependentFields( spouse, dependent );
        }
        if (prevSpouse!=null && (spouseShouldBeAdded( prevSpouse, repository ) || prevSpouse.getDeathDate()!=null)) {

        	 populateDependentFields( prevSpouse, dependent );
        }

        
        dependent.setParticipantRelationshipTypeName( CorporateDependentsPopulator.SPOUSE_RELATIONSHIP );

        logUtils.log( "adding spouse to update in corporate: " + dependent.getLastName() + ", " + dependent.getFirstName() + " ssn: " + dependent.getSsn()+"spouse date of death:"+dependent.getDeathDate(), repository );
        input.getDependents().add( dependent );
    }


    private boolean spouseShouldBeAdded( final Spouse spouse, final RbpsRepository repository ) {

    	if ( spouse ==null || spouse.isEverOnAward() ){
    		
    		return false;
    	}
    	
    	return true;
    }


    public boolean spouseOnAward(final Spouse spouse, RbpsRepository repository) {

        logUtils.log( "spouse award present: " + awardPresent( spouse.getAward() ), repository );
        return awardPresent( spouse.getAward() );
    }


    public boolean spouseInRepository( final Spouse spouse, RbpsRepository repository ) {

        if ( repository.getSpouse() == null ) {

            logUtils.log( "spouse not in repository",repository );
            return false;
        }

        CorporateDependent      corpSpouse = repository.getSpouse();

        CorporateDependentId    xomId   = new CorporateDependentId( spouse );
        CorporateDependentId    corpId  = new CorporateDependentId( corpSpouse.getParticipantId(),
                                                                   corpSpouse.getFirstName(),
                                                                   corpSpouse.getBirthDate(),
                                                                   corpSpouse.getSocialSecurityNumber() );

        if ( xomId.equals( corpId ) ) {

            return true;
        }

        logUtils.log( String.format( "The Spouse on the form >%s, %s/%d/%s< does not match the Corporate Spouse >%s, %s/%d/%s<",
                                     spouse.getLastName(),
                                     spouse.getFirstName(),
                                     CommonUtils.participantIdFor( spouse ),
                                     spouse.getSsn(),
                                     repository.getSpouse().getLastName(),
                                     repository.getSpouse().getFirstName(),
                                     repository.getSpouse().getParticipantId(),
                                     repository.getSpouse().getSocialSecurityNumber() ), repository );

        return false;
    }


    private void addChildToInput( final DependentUpdateInput input, final Child child, RbpsRepository repository ) {

        Dependents  dependent = new Dependents();

        populateDependentFields( child, dependent );

        logUtils.log( "adding child to update in corporate: " + dependent.getLastName() + ", " + dependent.getFirstName() + " ssn: " + dependent.getSsn(), repository );
        input.getDependents().add( dependent );
    }


    /**
     *      Populating values to the dependent
     *
     *      @param xomDependent
     *      @param dependent
     */
    public static final void populateDependentFields( final Dependent    xomDependent,
                                         final Dependents   dependent ) {

    	if ( xomDependent.getCorpParticipantId() != 0 ){
    		
    		dependent.setParticipantId( Long.toString(xomDependent.getCorpParticipantId() ) );
    	}
        dependent.setFirstName( xomDependent.getFirstName() );
        dependent.setMiddleName( xomDependent.getMiddleName() );
        dependent.setLastName( xomDependent.getLastName() );
        dependent.setParticipantRelationshipTypeName( CorporateDependentsPopulator.CHILD_RELATIONSHIP );
        dependent.setBirthDate( SimpleDateUtils.convertDate( "birthDate",
                                                       SimpleDateUtils.SIMPLE_DATE_FORMAT,
                                                       xomDependent.getBirthDate() ) );
        dependent.setSsn( xomDependent.getSsn() );
        setGender( xomDependent, dependent );
        dependent.setDeleteRequest( "N" );
        dependent.setRelateInd( "Y" );
       
        /*dependent.setDeathDate(SimpleDateUtils.convertDate( "deathDate",
                SimpleDateUtils.SIMPLE_DATE_FORMAT,
                xomDependent.getDeathDate()) );
       */
        if( xomDependent.getDeathDate()!=null){
        	String deathdate=SimpleDateUtils.convertDate( "deathDate",
        			SimpleDateUtils.SIMPLE_DATE_FORMAT,
                xomDependent.getDeathDate()).replaceAll("/", "");
        	dependent.setDeathDate(deathdate);
        }
		// code change for 530998
		if (xomDependent instanceof Child) {
			try {
				Child cd = (Child) xomDependent;
				if ( cd.getBirthAddress() != null) {
					dependent.setCityOfBirth(cd.getBirthAddress().getCity());
					dependent.setStateOfBirth(cd.getBirthAddress().getState());
				}
			} catch (Exception e) {
				System.out.println("UpdateBenefitClaimDependentsPopulator.populateDependentFields():" + e.toString());
			}
		}
		
        
//        dependant.setProofOfDependencyInd( value );
//        dependant.setRelateInd( value );
    }


    private static final void setGender( final Dependent     xomDependent,
                            final Dependents    dependent ) {

        dependent.setGender( (String) NullSafeGetter.getAttribute( xomDependent, "gender.code" ) );
    }


    /**
     * Setting the number of dependents
     *
     * @param input
     */
    public void setNumDependents( final DependentUpdateInput input ) {

        input.setNumberOfDependents( "" + input.getDependents().size() );
    }


    /**
     * Creating map of CorporateDependent with ParticipantId as the key
     *
     * @param repository
     * @return
     */
    public static final List<CorporateDependentId> createCorporateDependentList( final RbpsRepository     repository ) {

        List<CorporateDependentId>    list = new ArrayList<CorporateDependentId>();

        for ( CorporateDependent    child : repository.getChildren() ) {

            list.add( child.getId() );
        }

        return list;
    }


    /**
     *      Creating a list of children to be added
     *
     *      @param repository
     *      @return
     */
    public List<Child> getListOfChildrenToAdd( final RbpsRepository repository ) {

//        List<CorporateDependentId>      corporateChildList  = createCorporateDependentList( repository );
        List<Child>                     newChildren         = new ArrayList<Child>();

        for ( Child child : repository.getVeteran().getChildren() ) {

        	if ( child.isEverOnAward() ){
        		
        		continue;
        	}
        	
//            if ( ! childShouldBeAdded( corporateChildList, child, repository ) ) {
//
//                continue;
//            }

            newChildren.add( child );
        }

        return newChildren;
    }


    /**
     *      Checks if the child should be added or not
     *
     *      @param corporateChildList
     *      @param child
     *      @return
     */
    public boolean childShouldBeAdded( final List<CorporateDependentId>     corporateChildList,
                                       final Child                          child,
                                       RbpsRepository repository) {

        return ! childAlreadyInCorporateList(corporateChildList, child);
    }


    /**
     *      Checks if the child already on award or not
     *
     *      @param child
     *      @return
     */
    public static final boolean childOnAward(final Child child) {

        return awardPresent( child.getAward() )
                || awardPresent( child.getMinorSchoolChildAward() );
    }


    /**
     *      Check if the award is present
     *
     *      @param award
     *      @return
     */
    public static final boolean awardPresent( final Award   award ) {

        return (award != null && award.getEventDate() != null);
    }


    /**
     *      Checks if the child not already in the corporate list
     *
     *      @param corporateChildList
     *      @param child
     *      @return
     */
    public static final boolean childAlreadyInCorporateList( final List<CorporateDependentId>     corporateChildList,
                                                   final Child                          child ) {

        CorporateDependentId  childId = new CorporateDependentId( CommonUtils.participantIdFor( child ),
                                                                  child.getFirstName(),
                                                                  child.getBirthDate(),
                                                                  child.getSsn() );
        return corporateChildList.contains( childId );
    }
}
