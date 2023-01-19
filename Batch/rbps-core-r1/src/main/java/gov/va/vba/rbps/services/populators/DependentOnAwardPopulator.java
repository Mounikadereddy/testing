/*
 * DependentOnAwardPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.exception.RbpsServiceException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dep.FindDependentOnAwardResponse;

import java.util.HashMap;
import java.util.Map;



/**
 *      Convert data we get from the findDependentOnAward WS to corporate data
 *      on the repository.  Sets whether or not the veteran has an
 *      attorney fee agreement.
 */
public class DependentOnAwardPopulator {

    private static Logger logger = Logger.getLogger(DependentOnAwardPopulator.class);



   /**
    * Process data from the findDependentOnAward web service and sets
    * whether or not the dependent is on the current award.
    *
    * @author Tom.Corbin
    * @param response - this is the response from the findDependentOnAward web service.
    *               it contains the information whether or not a dependent
    *               is on a current award.
    * @throws RbpsServiceException
    */
    public void populateFromDependentOnAward( final RbpsRepository repository,final FindDependentOnAwardResponse response ) {

        try {

            CorporateDependent dependent = findDependent( repository, response );

            if ( dependent == null ) {

                return;
            }

            boolean  oldValue = dependent.isOnAward();
            dependent.setOnAward( convertResponseOnAwardValue( response ) );

           logIfDependentOnAward( dependent, oldValue );
        }
        catch ( Throwable ex ) {

            throw new RbpsRuntimeException( "Unable to build corporate dependents.", ex );
        }
    }


    private boolean convertResponseOnAwardValue( final FindDependentOnAwardResponse response ) {

        return CommonUtils.convertBoolean( response.getReturn().getIsDependentOnAward() );
    }


    private CorporateDependent findDependent( final RbpsRepository repository, final FindDependentOnAwardResponse response ) {

        Map<Long, CorporateDependent> dependentMap = populateDependentMap( repository );

        return dependentMap.get( response.getReturn().getDependentID() );
    }


    private Map<Long, CorporateDependent> populateDependentMap( final RbpsRepository repository ) {

        Map<Long, CorporateDependent> dependentMap = new HashMap<Long, CorporateDependent>();

        addChildrenToMap( dependentMap, repository );
        addSpouseToMap( dependentMap, repository );

        return dependentMap;
    }


    private void addSpouseToMap( final Map<Long, CorporateDependent> dependentMap, final RbpsRepository repository ) {


        if ( doesNotHaveValueForSpouse( repository ) ) {

            return;
        }

        CorporateDependent spouse = repository.getSpouse();

        dependentMap.put( spouse.getParticipantId(), spouse );
    }


    private void addChildrenToMap( final Map<Long, CorporateDependent> dependentMap, final RbpsRepository repository ) {

        for (CorporateDependent child : repository.getChildren() ) {

            dependentMap.put( child.getParticipantId(), child );
        }
    }


    private boolean doesNotHaveValueForSpouse( final RbpsRepository repository ) {

        return null == repository.getSpouse();
    }



    private void logIfDependentOnAward( final CorporateDependent    dependent,
                                        final boolean               oldValue ) {

      CommonUtils.log( logger,
                 String.format( ">%s, %s/%d< is on the award: >%s<\twas on award: >%s<",
                                dependent.getLastName(),
                                dependent.getFirstName(),
                                dependent.getParticipantId(),
                                dependent.isOnAward(),
                                oldValue ));
    }

}
