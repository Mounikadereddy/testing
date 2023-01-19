/*
 * EP130ClaimPreProcessorImpl.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.claimprocessor.impl;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.util.ConnectionFactory;
import gov.va.vba.rbps.claimvalidator.AwardStateValidator;
import gov.va.vba.rbps.coreframework.dao.VetsNetSystemServiceDao;
import gov.va.vba.rbps.coreframework.dao.VetsNetSystemServiceDaoImpl;
import gov.va.vba.rbps.coreframework.dao.exception.NoResultsException;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsClaimDataException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuleExecutionException;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.services.impl.DependentOnAwardFilter;
import gov.va.vba.rbps.services.impl.RbpsFilter;
import gov.va.vba.rbps.services.populators.*;
import gov.va.vba.rbps.services.populators.utils.ClaimStationsBean;
import gov.va.vba.rbps.services.ws.client.handler.awards.FindAwardStateWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.awards.PersonServiceWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DependencyDecisionByAwardProducer;
import gov.va.vba.rbps.services.ws.client.handler.org.OrgWebServiceWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.share.*;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.personService.FindPersonsBySsnsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.state.FindAwardStateResponse;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbersResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindFlashesResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CompareByDateRangeResponse;
import org.apache.commons.collections.MultiMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.util.CollectionUtils;

import javax.naming.NamingException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collection;
import java.util.List;


/**
 *      The PreProcessor is responsible for orchestrating all web services
 *      that provide input data to hydrate the Veteran bean in the repository.
 */
public class EP130ClaimPreProcessor {

    private static Logger logger = Logger.getLogger(EP130ClaimProcessorImpl.class);

    private LogUtils                            	logUtils                        = new LogUtils( logger, true );

    private FindDependentsWSHandler             	findDependentsWSHandler;
    private CorporateDependentsPopulator        	corporateDependentsPopulator;

    private FindFlashesWSHandler                	findFlashesWSHandler;
    private AttorneyFeeAgreementPopulator       	attorneyFeeAgreementPopulator;

    private FindAwardStateWSHandler             	awardsFindAwardStateWSHandler;
    private AwardStatePopulator                	 	awardStatePopulator;

    private OrgWebServiceWSHandler                  orgWebServiceWSHandler;
    private PoaPopulator                        	poaPopulator;

    private RatingComparisonWSHandler           	ratingComparisonWSHandler;
    private FCDRPopulator							fcdrPopulator;
    
    private SojSignatureWSHandler               	sojSignatureWSHandler;

    private DependentOnAwardFilter              	dependentOnAwardFilter;
    private RbpsFilter                          	rbpsFilter                  = new RbpsFilter();

    private ClaimStationsBean                   	claimStationsBean;

    private DependencyDecisionByAwardProducer   	dependencyDecisionByAwardProducer;

    private PersonServiceWSHandler					personServiceWSHandler;
    private UpdateBenefitClaimDependentsWSHandler   updateBenefitClaimDependentsWSHandler;





    /**
     *      This method starts the Pre-Processing of Claim data by invoking the
     *      remaining services to provide input data to the Rules Engine
     *
     *      @throws RbpsRuleExecutionException
     *      @throws RbpsClaimDataException
     */
    public void preProcess( final RbpsRepository repository ) {

        try {

            logUtils.debugEnter(repository);

            findAwardState( repository );
            AwardStateValidator awardStateValidator = new AwardStateValidator(repository);
            awardStateValidator.validate();
            if(repository.hasRuleExceptionMessages()) {
                return;
            }

            populateFromVetsNetSystem(repository);

            populateIsPensionAward(repository);

            populateClaimStationAddress(repository);
            //for ccr 1882 remove the Signature from letter
            // use static text for that.
            //populateClaimStationSignature(repository);
            addRatingDataToVeteran(repository);

            if ( repository.getVeteran().getServiceConnectedDisabilityRating() < 30 ) {
                // if less than 30%, pension on, and Vet has a pension, send to manual so that a denial letter is not created
                if(repository.isProcessPensions() && repository.getVeteran().isPensionAward()) {
                    repository.getRuleExceptionMessages().addException("Auto Dependency Processing Reject Reason - Veterans Pension or Survivor Award with under 30 Percent Rating, please process manually.");
                }
                // if less than thirty and not pension, this will go to postprocessor and create a denial letter
            	return;
            }

            // send to manual if Vet is pension, processing pension is on, and Vet has an FCDR
            if(repository.isProcessPensions() && repository.getVeteran().isPensionAward() &&
                repository.getVeteran().getFirstChangedDateofRating() != null ) {

                repository.getRuleExceptionMessages().addException("Auto Dependency Processing Reject Reason - Current version of RBPS cannot calculate event date of Pension Dependency Adjustment, please process manually.");
                return;
            }

            addCorporateDependentsToRepository(repository);
            
            
            populateMissingDependentIdFromCorporateIntoXom( repository );
            
            //CCR 1778
            verifyDependantSSNFromCorp(repository);
            
            seeIfVeteranHasPoa(repository);
            setOnCurrentAwardForXomDependents( repository );
            decideDependencyByAward( repository );
            //CCR1778
            verifyDependantSSNFromDependencyDecision(repository);
            
            recordAttorneyFeeAgreement( repository );

            determineDenialAwardDependents( repository );
            //countNumberOfDependentsOnAward( repository );          
            checkIfVeteranHasChildrenPreviouslyMarried( repository );
            //checkIfChildrenHaveLastTermInCorporate( repository );
            evaluateDependentOnAwardStatus( repository );
            countNumberOfDependentsOnAward( repository );   
            filterRbps( repository );
            updateMissingDependentIdIntoCorporate( repository );
        }
        catch ( Throwable ex ) {

            throwRelevantException( "pre process", ex, true, repository );
        }
        finally {

            logUtils.debugExit(repository);
        }
    }
    
    
    public void verifyDependantSSNFromCorp(RbpsRepository repository){
    	List<CorporateDependent> children = repository.getChildren();
    	
    	CorporateDependent spouse = repository.getSpouse();
    	
    	List<Child> vonappChildren = repository.getVeteran().getChildren();
    	
    	Spouse vonappSpouse = null;
    	
    	if (repository.getVeteran().getCurrentMarriage() != null) {
			vonappSpouse = repository.getVeteran().getCurrentMarriage()
					.getMarriedTo();
		}
		for (CorporateDependent corporateDependent : children) {
			if(StringUtils.isEmpty(corporateDependent.getSocialSecurityNumber())){
				// verify if any date of birth matches..
				for (Child child : vonappChildren) {
					if(child.getCorpParticipantId() != 0) {
						continue;
					}
					if(DateUtils.isSameDay(corporateDependent.getBirthDate(),child.getBirthDate())){
						// there is a matching birth date.. set to manual
						throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason -Submitted SSN/First Name/DOB does not match corporate record.  Please review." );
					}
				}
			}
		}
    	
    	if (vonappSpouse != null  && spouse != null) {
			if (StringUtils.isEmpty(spouse.getSocialSecurityNumber())) {
				if (vonappSpouse.getCorpParticipantId() == 0) {
					if (DateUtils.isSameDay(spouse.getBirthDate(),
							vonappSpouse.getBirthDate())) {
						throw new RbpsRuntimeException(
								"Auto Dependency Processing Reject Reason -Submitted SSN/First Name/DOB does not match corporate record.  Please review.");
					}
				}
			}
		}
    }
    
    
    public void verifyDependantSSNFromDependencyDecision(RbpsRepository repository){
    	
    	List<Child> vonappChildren = repository.getVeteran().getChildren();
    	
    	Spouse vonappSpouse = null;
    	
    	if(repository.getVeteran().getCurrentMarriage() != null){
    		vonappSpouse = repository.getVeteran().getCurrentMarriage().getMarriedTo();
    	}
    	
    	MultiMap dependancyDecisionByAwardMap = dependencyDecisionByAwardProducer.getDependancyDecisionByAwardMap(repository);
    	Collection<DependencyDecisionVO> dependencyDecisions = dependancyDecisionByAwardMap.values();
    	for (DependencyDecisionVO dependencyDecisionVO : dependencyDecisions) {
			if(StringUtils.isEmpty(dependencyDecisionVO.getSocialSecurityNumber())){
				for (Child child : vonappChildren) {
					if(child.getCorpParticipantId() != 0) {
						continue;
					}
					if(DateUtils.isSameDay(child.getBirthDate(), dependencyDecisionVO.getBirthdayDate().toGregorianCalendar().getTime())){
						throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason -Submitted SSN/First Name/DOB does not match dependency decision record.  Please review." );
					}
				}
				
				if (vonappSpouse != null) {
					if (DateUtils.isSameDay(vonappSpouse.getBirthDate(),
							dependencyDecisionVO.getBirthdayDate()
									.toGregorianCalendar().getTime())) {
						throw new RbpsRuntimeException(
								"Auto Dependency Processing Reject Reason -Submitted SSN/First Name/DOB does not match dependency decision record.  Please review.");
					}
				}
			}
		}
    }

    
    
    public void populateClaimStationAddress( final RbpsRepository repository ) {

        //
        //      Workaround for the issue that VDC claims have a station id instead of location id,
        //      so we translate them here.
        //
        Long locationId = claimStationsBean.getLocationId( repository.getClaimStationLocationId(), repository );
        repository.setClaimStationLocationId( locationId );
        claimStationsBean.populateClaimStationData( repository );
    }

    //ccr 1882 this method is not used any more 
    /*
    public void populateClaimStationSignature( final RbpsRepository repository ) {

        if ( repository.getClaimStationAddress().hasSignature() ) {

            return;
        }

        FindSignaturesByStationNumberResponse response;
        
        response = sojSignatureWSHandler.getSignatures( repository,
        									repository.getClaimStationAddress().getStationId() );

        SojSignaturePopulator.assignVeteranServiceManagerSignature( repository, response );
    }
    */


    /**
	 * @param repository
	 */
    private void populateFromVetsNetSystem(RbpsRepository repository) {
        Connection conn = null;
        try {
            conn = ConnectionFactory.getConnection();
            VetsNetSystemServiceDao vetsNetSystemServiceDao = new VetsNetSystemServiceDaoImpl(conn);
            repository.setProcessPensions(vetsNetSystemServiceDao.isProcessingPensions());
            BigDecimal netWorthLimit = vetsNetSystemServiceDao.findByFieldName("BGS_RBPS_NETWORTH_LIMIT_AMT").getValueNumber();

            if (netWorthLimit == null) {
                throw new RbpsRuntimeException("net worth limit is null");
            }
            repository.getVeteran().setNetWorthLimit(netWorthLimit);
        } catch (NamingException | SQLException | NoResultsException exception) {
            throw new RbpsRuntimeException("Could not populate from VTSNET_SYSTEM: " + exception.getMessage());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException exception) {
                    logger.error("Unable to close connection: " + exception.getMessage());
                }
            }
        }

    }


    private void populateIsPensionAward(RbpsRepository repository) {

        Connection conn = null;

        Long vetId = repository.getVeteran().getCorpParticipantId();

        try {
        	//task 009232 use new datasource jdbc/wbrbps/CorpDB
            //conn = ConnectionFactory.getConnection("jdbc/vbms/CorpDB");
            conn = ConnectionFactory.getConnection("jdbc/wbrbps/CorpDB");

            logger.debug("Calling ws_award_data_prc.isPensionAward");
            CallableStatement cs = conn.prepareCall("{call ws_award_data_prc.isPensionAward(?,?)}");
            cs.registerOutParameter(2, Types.VARCHAR);
            cs.setLong(1, vetId);
            cs.execute();

            String pensionAward = cs.getString(2);
            repository.getVeteran().setIsPensionAward(pensionAward.equals("Y"));

            logger.debug("Veteran's Pension Award is " + repository.getVeteran().isPensionAward());

        } catch (SQLException e) {
            throw new RbpsRuntimeException("Call to ws_award_data_prc.isPensionAward procedure failed " + e.getMessage());
        } catch (NamingException e) {
            throw new RbpsRuntimeException("Failed to retrieve datasource: " + e.getMessage());
        } catch(Exception e) {
            String detailedMessage = "Call to ws_award_data_prc.isPensionAward procedure failed ";

            throw new RbpsRuntimeException(detailedMessage, e.getCause());
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    throw new RbpsRuntimeException("Unable to close connection: " + e.getMessage());
                }
            }
        }
    }

    /**
     *      Finds out if the veteran has a poa
     */
    private void seeIfVeteranHasPoa( final RbpsRepository repository ) {
        final FindPOAsByFileNumbersResponse response = orgWebServiceWSHandler.findPOAsByFileNumber( repository );
        poaPopulator.populatePoa( repository, response);
    }
    
    
    private void addRatingDataToVeteran( final RbpsRepository repository ) {

    	CompareByDateRangeResponse  ratingsDatesResponse    = ratingComparisonWSHandler.findApplicableRatingsData( repository );
    	if(ratingsDatesResponse != null) {
            fcdrPopulator.populateRatingDates(ratingsDatesResponse, repository);
        }
    }


    /**
     *      Add Corporate Dependents
     */
    private void addCorporateDependentsToRepository( final RbpsRepository repository ) {

        final FindDependentsResponse response = findDependentsWSHandler.findDependents( repository );
        corporateDependentsPopulator.populateFromDependents(response, repository);
    }
    
    

    public void setOnCurrentAwardForXomDependents( final RbpsRepository repository ) {

        OnAwardPopulator.setOnCurrentAwardForXomDependent( repository );
    }
    
    

    public void decideDependencyByAward( final RbpsRepository repository ) {

        dependencyDecisionByAwardProducer.setIsNewSchoolChild( repository );
    }


    /**
     *      Records the Attorney Fee Agreement
     */
    private void recordAttorneyFeeAgreement( final RbpsRepository repository ) {

        final FindFlashesResponse response = findFlashesWSHandler.call( repository );
        logger.debug("FindFlashesResponse"+response);
        attorneyFeeAgreementPopulator.populateFromFlashes(response, repository );
    }
    

    /**
     *      Find Award State
     */
    private void findAwardState( final RbpsRepository repository ) {

        final FindAwardStateResponse response = awardsFindAwardStateWSHandler.call( repository );
        awardStatePopulator.populateFromFindAwardStateWS( response, repository );
    }


    public void determineDenialAwardDependents( final RbpsRepository repository ) {

        try {

            determineDenialAwardForChildren( repository );
            determineDenialAwardForSpouse( repository );
            determineDenialAwardForPreviousSpouse( repository );
        }
        
        catch ( Throwable ex ) {

            throwRelevantException( "record dependents on award for", ex, false, repository );
        }
    }
    

    public void countNumberOfDependentsOnAward( final RbpsRepository repository ) {
    	
    	dependencyDecisionByAwardProducer.countNumberOfDependentsOnAward( repository );
    	
    }
    

    public void checkIfVeteranHasChildrenPreviouslyMarried( final RbpsRepository repository ) {
    	
    	dependencyDecisionByAwardProducer.checkIfVeteranHasChildrenPreviouslyMarried( repository );
    }
    
    
    public void checkIfChildrenHaveLastTermInCorporate( final RbpsRepository repository ) {
    	
    	dependencyDecisionByAwardProducer.checkIfChildrenHaveLastTermInCorporate( repository );
    }
    
    
    public void evaluateDependentOnAwardStatus( final RbpsRepository repository ) {

        dependentOnAwardFilter.evaluateDependentOnAward( repository );
    }
    
    
    private void filterRbps( final RbpsRepository repository ) {

        rbpsFilter.filter( repository );
    }

    
	private void determineDenialAwardForChildren( final RbpsRepository repository ) {
		
		if ( repository.getVeteran().getChildren().size() == 0 ) {
		    return;
		}

		for (final Dependent child : repository.getVeteran().getChildren()) {
		    dependencyDecisionByAwardProducer.populateDependencyDecisionForDependent( repository, child );
		}
	}

	
	private void determineDenialAwardForSpouse( final RbpsRepository repository ) {
		
		if ( repository.getVeteran().getCurrentMarriage() == null ) {
            return;
        }
        dependencyDecisionByAwardProducer.populateDependencyDecisionForDependent( repository,
        																		   repository
                                                                                  .getVeteran()
                                                                                  .getCurrentMarriage()
                                                                                  .getMarriedTo() );
	}

	
	private void determineDenialAwardForPreviousSpouse( final RbpsRepository repository ) {
		
		if ( repository.getVeteran().getLatestPreviousMarriage() == null ) {
            return;
        }
		Spouse previousSpouse = repository.getVeteran().getLatestPreviousMarriage().getMarriedTo();
		previousSpouse.setIsChildPresentForSpouse( false );
		
        dependencyDecisionByAwardProducer.populateDependencyDecisionForDependent( repository, previousSpouse );     
        
//        if ( ! previousSpouse.isEverOnAward() ) {
//        	
//        	throw new RbpsRuntimeException( String.format( "Previous Spouse %s %s submitted for removal was never on Award", 
//        													previousSpouse.getFirstName(), previousSpouse.getLastName() ) );
//        }
        
//        if ( previousSpouse.isDeniedAward() ) {
//        	
//        	throw new RbpsRuntimeException( String.format( "Previous Spouse %s %s submitted for removal was already removed from Award", 
//        													previousSpouse.getFirstName(), previousSpouse.getLastName() ) );
//        }
        
        if (! CollectionUtils.isEmpty( repository.getVeteran().getChildren() ) ){
        	
        	previousSpouse.setIsChildPresentForSpouse( true );
        }
	}

	
	private void populateMissingDependentIdFromCorporateIntoXom( final RbpsRepository repository ) {
		
		FindPersonsBySsnsResponse 	response 		= personServiceWSHandler.call( repository );
		PersonServiceBySsnPopulator	personPopulator	= new PersonServiceBySsnPopulator();
		personPopulator.populateCorporateDependentsBySsns( response, repository );
	}

	
	private void updateMissingDependentIdIntoCorporate( final RbpsRepository repository ) {
		
		updateBenefitClaimDependentsWSHandler.updateDependents(repository);
		populateMissingDependentIdFromCorporateIntoXom( repository );

	}
	
	
    private void throwRelevantException( final String       	task,
                                         final Throwable    	ex,
                                         final boolean      	addToValidationMessages,
                                         final RbpsRepository 	repository) {


        if ( addToValidationMessages ) {
        	repository.addValidationMessages( CommonUtils.getExceptionMessages( ex ) );
        }

        throw ( (RuntimeException) ex);
    }


    
    
    public void setFindDependentsWSHandler( final FindDependentsWSHandler findDependentsWSHandler) {
        this.findDependentsWSHandler = findDependentsWSHandler;
    }

    public void setCorporateDependentsPopulator( final CorporateDependentsPopulator corporateDependentsPopulator) {
        this.corporateDependentsPopulator = corporateDependentsPopulator;
    }

    public void setFindFlashesWSHandler( final FindFlashesWSHandler findFlashesWSHandler) {
        this.findFlashesWSHandler = findFlashesWSHandler;
    }

    public void setAttorneyFeeAgreementPopulator( final AttorneyFeeAgreementPopulator attorneyFeeAgreementPopulator) {
        this.attorneyFeeAgreementPopulator = attorneyFeeAgreementPopulator;
    }

    public void setAwardStatePopulator(final AwardStatePopulator awardStatePopulator) {
        this.awardStatePopulator = awardStatePopulator;
    }

    public void setAwardsFindAwardStateWSHandler( final FindAwardStateWSHandler awardsFindAwardStateWSHandler) {
        this.awardsFindAwardStateWSHandler = awardsFindAwardStateWSHandler;
    }

    public void setOrgWebServiceWSHandler(final OrgWebServiceWSHandler orgWebServiceWSHandler) {
        this.orgWebServiceWSHandler = orgWebServiceWSHandler;
    }
    public void setPoaPopulator( final PoaPopulator poaPopulator ) {
        this.poaPopulator = poaPopulator;
    }

    public void setRatingComparisonWSHandler( final RatingComparisonWSHandler ratingComparisonWSHandler ) {
        this.ratingComparisonWSHandler = ratingComparisonWSHandler;
    }

    public void setDependentOnAwardFilter( final DependentOnAwardFilter dependentOnAwardFilter ) {
        this.dependentOnAwardFilter = dependentOnAwardFilter;
    }

    public void setClaimStationsBean( final ClaimStationsBean claimStationsBean ){
        this.claimStationsBean = claimStationsBean;
    }

    public void setDependencyDecisionByAwardProducer( final DependencyDecisionByAwardProducer dependencyDecisionByAwardProducer) {
        this.dependencyDecisionByAwardProducer = dependencyDecisionByAwardProducer;
    }
   
    public void setSojSignatureWSHandler( final SojSignatureWSHandler sojSignatureWSHandler ) {
        this.sojSignatureWSHandler = sojSignatureWSHandler;
    }
        
    public void setFcdrPopulator( final FCDRPopulator fcdrPopulator ) {
        this.fcdrPopulator = fcdrPopulator;
    }
    
    public void setPersonServiceWSHandler( final PersonServiceWSHandler personServiceWSHandler ) {
    	
    	this.personServiceWSHandler = personServiceWSHandler;
    }

    public void setUpdateBenefitClaimDependentsWSHandler( final UpdateBenefitClaimDependentsWSHandler     updateBenefitClaimDependentsWSHandler ) {

        this.updateBenefitClaimDependentsWSHandler = updateBenefitClaimDependentsWSHandler;
    }

}
