/*
 * CorporateParticipantIdPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.CorporateDependentId;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;

import java.util.ArrayList;
import java.util.List;
import org.springframework.util.CollectionUtils;

/**
 *      Convert data we get from the findDependents WS to corporate participant
 *      ids on the xom dependenets.
 */
public class CorporateParticpantIdPopulator {

    private static Logger logger = Logger.getLogger(CorporateParticpantIdPopulator.class);


    private static final CorporateDependentId       NULL_DEPENDENT_ID = new CorporateDependentId( 0L, null, null, null );

    //private CommonUtils                     utils;
    private LogUtils                        logUtils                        = new LogUtils( logger, true );
//    private RbpsRepository                  repository;
    private CorporateDependentsPopulator    corporateDependentsPopulator;


   /**
    *       For each XOM dependent w/o a corporate participant Id,
    *       populate the corporate particpant id from the data in
    *       the response from the FindDependents web service.
    *
    *       @param response - this is the response from the findDependents web service.
    *               it contains the information from the corporate database
    *               about all the dependents a veteran may have.
    *       @throws RbpsServiceException
    */
    public void populateParticipantIdFromDependents( final FindDependentsResponse response, RbpsRepository repository ) {

//        logUtils.log( "response: >" + utils.stringBuilder( response.getReturn().getPersons() ) );
        List<CorporateDependentId>    idList = getCurrentListOfCorporateDependents( response );

        populateXomChildrenCorporateIds( idList, repository );
        populateXomSpouseCorporateId( idList, repository );

        checkForXomDependentsWithoutCorporateId(repository);
    }


   private void checkForXomDependentsWithoutCorporateId(RbpsRepository repository) {

       for ( Child child 	:	repository.getVeteran().getChildren() ) {

           if (child.getCorpParticipantId() == 0) {

               throwMissingDependentException( child, "child", repository );
           }
       }

       Spouse  spouse = getSpouseFromRepository(repository);
       if ( spouse != null && spouse.getCorpParticipantId() == 0 ) {

           throwMissingDependentException( spouse, "spouse", repository );
       }
   }


    private void populateXomSpouseCorporateId( final List<CorporateDependentId>    idList, RbpsRepository repository ) {

        Spouse spouse = getSpouseFromRepository(repository);
        
        if ( spouse == null ) {
        	
        	return;
        }
        
        setCorporateParticipantIdOnDependent( idList, spouse, repository );
    }


    private void populateXomChildrenCorporateIds( final List<CorporateDependentId>    idList, RbpsRepository repository ) {

    	if( CollectionUtils.isEmpty( repository.getVeteran().getChildren()) ) {
    		
    		return;
    	}
    	
        for ( Child child : repository.getVeteran().getChildren() ) {

            setCorporateParticipantIdOnDependent( idList, child, repository );
        }
    }


    private void setCorporateParticipantIdOnDependent( final List<CorporateDependentId> idList,
                                                       final Dependent                  dependent,
                                                       RbpsRepository repository) {

        CorporateDependentId dependentId   	= getCorporateDependentIdFrom( dependent );
        CorporateDependentId found      	= getMatchingCorporateDependentIdFromList( idList, dependentId );

        logFoundCorporateId( dependent, found, repository );
        dependent.setCorpParticipantId( found.getParticipantId() );
    }


    private static final Spouse getSpouseFromRepository(RbpsRepository repository) {

        if ( repository.getVeteran().getCurrentMarriage() == null ) {

            return null;
        }

        return repository.getVeteran().getCurrentMarriage().getMarriedTo();
    }


    private boolean dependentNeedsCorporateParticipantId( final Dependent dependent ) {

        return dependent != null && dependent.getCorpParticipantId() == 0;
    }


    private CorporateDependentId getMatchingCorporateDependentIdFromList( final List<CorporateDependentId>  idList,
                                                                          final CorporateDependentId        dependentId ) {

        int index = idList.indexOf( dependentId );

        if ( index < 0 ) {

            return NULL_DEPENDENT_ID;
        }

        CorporateDependentId    found = idList.get( index );

        return found;
    }


    private CorporateDependentId getCorporateDependentIdFrom( final Dependent dependent ) {

        return new CorporateDependentId( dependent.getCorpParticipantId(),
                                         dependent.getFirstName(),
                                         dependent.getBirthDate(),
                                         dependent.getSsn() );
    }


    private List<CorporateDependentId> getCurrentListOfCorporateDependents( final FindDependentsResponse response ) {

        List<CorporateDependentId>  idList              = new ArrayList<CorporateDependentId>();
        List<CorporateDependent>    dependentList;

        dependentList = corporateDependentsPopulator.convertResponseToListOfDependents( response );
//        logUtils.log( "list of dependents: " + utils.stringBuilder( dependentList ) );

        for ( CorporateDependent dependent : dependentList ) {

            idList.add( dependent.getId() );
        }

        return idList;
    }


    private void throwMissingDependentException( final Dependent    dependent,
                                                 final String       typeOfDependent,
                                                 RbpsRepository repository) {

        throw new RbpsServiceException(
                 String.format("The %s >%s, %s< of Veterean >%s, %s< with file number >%s< does not have a Corporate Participant Id",
                               typeOfDependent,
                               dependent.getLastName(),
                               dependent.getFirstName(),
                               repository.getVeteran().getLastName(),
                               repository.getVeteran().getFirstName(),
                               repository.getVeteran().getFileNumber() ) );
    }


    private void logFoundCorporateId( final Dependent               dependent,
                                      final CorporateDependentId    found,
                                      RbpsRepository repository) {

        logUtils.log( String.format( "CorporateParticipantIdPopulator: found >%s< for %s dependent >%s, %s/%s<",
                                     found.getParticipantId(),
                                     dependent.getClass().getSimpleName(),
                                     dependent.getLastName(),
                                     dependent.getFirstName(),
                                     dependent.getSsn() ), repository );
    }





//
//
//    public void setRepository( final RbpsRepository repository ) {
//
//        this.repository = repository;
//    }


    public void setCorporateDependentsPopulator( final CorporateDependentsPopulator corporateDependentsPopulator ) {

        this.corporateDependentsPopulator = corporateDependentsPopulator;
    }
    /*
	public void setUtils(CommonUtils utils) {
		
		this.utils = utils;
	}
	*/
}
