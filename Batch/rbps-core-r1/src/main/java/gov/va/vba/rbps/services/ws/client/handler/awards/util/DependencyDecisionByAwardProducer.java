/*
 * FindDependencyDecisionByAwardProducer
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.ws.client.handler.awards.util;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.services.populators.FindDependencyDecisionByAwardPopulator;
import gov.va.vba.rbps.services.ws.client.handler.awards.FindDependencyDecisionByAwardWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;

import java.util.List;

import org.apache.commons.collections.MultiMap;


/**
 *      Front end for getting the list of dependency decisions.
 *      They are cached by claim id (cache size is only 1) so that if
 *      RBPS looks for this information more than once for the same claim,
 *      which it does, then it won't go to the WS twice.
 */
public class DependencyDecisionByAwardProducer {

    private static Logger                               logger          = Logger.getLogger(DependencyDecisionByAwardProducer.class);

    private LogUtils                                    logUtils        = new LogUtils( logger, true );

    private FindDependencyDecisionByAwardWSHandler      findDependencyDecisionByAwardWSHandler;
    private FindDependencyDecisionByAwardPopulator      findDependencyDecisionByAwardPopulator;
    private CountNumberOfDependentsOnAward				countNumberOfDependentsOnAward 			= new CountNumberOfDependentsOnAward();
    private CheckIfVeteranHasChildrenPreviouslyMarried  checkIfChildrenPreviouslyMarried		= new CheckIfVeteranHasChildrenPreviouslyMarried();
    private ValidateStudentLastTermWithCorporate		validateStudentLastTermWithCorporate	= new ValidateStudentLastTermWithCorporate();
    

    private FindDependencyDecisionByAwardResponse       response;
    private long                                        claimId;


    public List<DependencyDecisionVO> getDependencyDecisionListForDependent( final RbpsRepository        repository,
                                                                             final long                  vetCorpId,
                                                                             final long                  dependentCorpId ) {

        response = getDependencyDecisionResponse( repository, repository.getVeteran().getClaim().getClaimId() );

        return filterResponseByDependent(dependentCorpId, repository);
    }

    
    public void  checkIfChildrenHaveLastTermInCorporate( RbpsRepository repository ) {
    	
    	validateStudentLastTermWithCorporate.checkIfChildrenHaveLastTermInCorporate( repository, response );
    }
    
    public void checkIfVeteranHasChildrenPreviouslyMarried( RbpsRepository repository ) {
    	
    	checkIfChildrenPreviouslyMarried.checkIfChildrenPreviouslyMarried( repository, response );
    }
    
    public void countNumberOfDependentsOnAward( RbpsRepository repository ) {
    	
    	countNumberOfDependentsOnAward.getTotalDependentCount( repository, response );
    }
    
    
    private List<DependencyDecisionVO> filterResponseByDependent( final long dependentCorpId, final RbpsRepository repository) {

        return findDependencyDecisionByAwardPopulator.getDependencyDecisionListByParticipantId( response, dependentCorpId, repository );
    }


    public List <DependencyDecisionVO> populateDependencyDecisionForDependent( final RbpsRepository  repository, final Dependent dependent ) {

        response = getDependencyDecisionResponse( repository, repository.getVeteran().getClaim().getClaimId() );

        return populateDependencyDecision( dependent, repository ) ;

    }


    private List<DependencyDecisionVO> populateDependencyDecision( final Dependent dependent, final RbpsRepository repository) {

        return findDependencyDecisionByAwardPopulator.populateDependencyDecisionForDependent( response, dependent, repository );
    }


    public void setIsNewSchoolChild( final RbpsRepository repository) {

       response                         = getDependencyDecisionResponse( repository, repository.getVeteran().getClaim().getClaimId() );
       List <Child>     xomChildren     = repository.getVeteran().getChildren();

       findDependencyDecisionByAwardPopulator.setChildAwardDetails( response, xomChildren, repository );
    }


    public FindDependencyDecisionByAwardResponse getDependencyDecisionResponse( final RbpsRepository        repository,
                                                                                final long                  claimId ) {

        if ( this.claimId != claimId ) {

            response        = findDependencyDecisionByAwardWSHandler.call( repository );
            this.claimId    = claimId;

            logUtils.log( "(not in cache) Calling findDependencyDecision for veteran corporate claim id: " + claimId, repository  );
        }

        return response;
    }



    public void setFindDependencyDecisionByAwardPopulator( final FindDependencyDecisionByAwardPopulator findDependencyDecisionByAwardPopulator){

        this.findDependencyDecisionByAwardPopulator = findDependencyDecisionByAwardPopulator;
    }


    public void setFindDependencyDecisionByAwardWSHandler( final FindDependencyDecisionByAwardWSHandler findDependencyDecisionByAwardWSHandler){

        this.findDependencyDecisionByAwardWSHandler = findDependencyDecisionByAwardWSHandler;
    }


    /**
     *      For testing
     *
     *      @param vetCorpId
     *      @param findDependencyDecisionByAwardResponse
     */
    public void addFakeResponse( final long                                             claimId,
                                 final FindDependencyDecisionByAwardResponse            findDependencyDecisionByAwardResponse){

        this.claimId        = claimId;
        this.response       = findDependencyDecisionByAwardResponse;
    }


	public MultiMap getDependancyDecisionByAwardMap(RbpsRepository repository) {
		FindDependencyDecisionByAwardResponse dependencyDecisionResponse = getDependencyDecisionResponse( repository, repository.getVeteran().getClaim().getClaimId() );
		return this.findDependencyDecisionByAwardPopulator.populateDependencyDecisionByAward(dependencyDecisionResponse, repository);
	}
}
