/*
 * CorporateDependentsPopulator.java
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
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.vo.RelationshipType;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.FindDependentsResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.familytree.Shrinq3Person;

import java.util.ArrayList;
import java.util.List;


/**
 *      Convert data we get from the findDependents WS to corporate data
 *      on the repository.
 */
public class CorporateDependentsPopulator {

    private static Logger logger = Logger.getLogger(CorporateDependentsPopulator.class);

    public static final String         CHILD_RELATIONSHIP      = "Child";
    public static final String         SPOUSE_RELATIONSHIP     = "Spouse";


//    private CommonUtils         utils;
    private LogUtils            logUtils    = new LogUtils( logger, true );


   /**
    *       Process data from the findDependents web service and produce
    *       a list of corporate dependents on the RbpsRepository.
    *
    *       @param response - this is the response from the findDependents web service.
    *               it contains the information from the corporate database
    *               about all the dependents a veteran may have.
    *       @throws RbpsServiceException
    */
    public void populateFromDependents( final FindDependentsResponse response, final RbpsRepository repository ) {

        List<CorporateDependent>  dependents = convertResponseToListOfDependents( response );

        addDependentsToRespository( dependents, repository );

//        logCorporateChildren();
    }


    public List<CorporateDependent> convertResponseToListOfDependents( final FindDependentsResponse response ) {

        try {

            List<CorporateDependent> dependents = new ArrayList<CorporateDependent>();

            for ( Shrinq3Person person : response.getReturn().getPersons() ) {

                String      relationship = person.getRelationship();
//                logUtils.log( "found dependent of type " + relationship );
//                logUtils.log( "CorporateDependentsPopulator: spouse code is: " + RelationshipType.SPOUSE.getValue() );

                if ( relationship.equalsIgnoreCase( RelationshipType.CHILD.getValue() ) ) {

//                    logUtils.log( "CorporateDependentsPopulator: adding child to this list of dependents:\n" + dependents );
                    addChild( person, dependents );
                }
                else if ( relationship.equalsIgnoreCase( RelationshipType.SPOUSE.getValue() ) ) {

//                    logUtils.log( "CorporateDependentsPopulator: adding spouse to this list of dependents:\n" + dependents );
                    addSpouse( person, dependents  );
                }
            }

//            logUtils.log( "final list of dependents:\n" + utils.stringBuilder( dependents ) );
            return dependents;
        }
        catch ( Throwable ex ) {

            throw new RbpsRuntimeException( "Unable to build corporate dependents.", ex );
        }
    }


    private void addDependentsToRespository( final List<CorporateDependent> dependents, final RbpsRepository repository ) {

        try {

            for ( CorporateDependent person : dependents ) {

                RelationshipType      relationship = person.getRelationship();

                if ( relationship.equals( RelationshipType.CHILD ) ) {

                    repository.addChild( person );
                }
                else if ( relationship.equals( RelationshipType.SPOUSE ) ) {

                	repository.setSpouse( person );
                }
            }
        }
        catch ( Throwable ex ) {

            throw new RbpsRuntimeException( "Unable to build corporate dependents.", ex );
        }
    }


   /**
    *       Take data from the <code>person</cod> that we got from the <code>findDependents</code>
    *       web service and construct a <code>CorporateDependent</code>
    *       from that.   Adds the child to the rbps repository.
    *
    *       @param person - this is a person that we got from the <code>findDependents</code>
    *               web service
    *       @throws RbpsServiceException
    */
    public void addChild( final Shrinq3Person person, final List<CorporateDependent> dependents ) {

        CorporateDependent   child = constructChild( person );
//        logUtils.log( "adding child: " + child );

        dependents.add( child );
    }


    private CorporateDependent constructChild( final Shrinq3Person person ) {

        CorporateDependent   child = new CorporateDependent();

        constructDependent( person, child );

        return child;
    }


   /**
    *       Take data from the <code>person</cod> that we got from the <code>findDependents</code>
    *       web service and construct a <code>CorporateDependent</code>
    *       from that.   Adds the spouse to the rbps repository.
    *
    *       @param person - this is a person that we got from the <code>findDependents</code>
    *               web service
    *       @throws RbpsServiceException
    */
    public void addSpouse( final Shrinq3Person person, final List<CorporateDependent> dependents ) {

        CorporateDependent      spouse      = constructSpouse( person );
//        logUtils.log( "adding spouse: " + utils.stringBuilder( spouse ) );

        dependents.add( spouse );
    }


    private CorporateDependent constructSpouse( final Shrinq3Person person ) {

        CorporateDependent spouse = new CorporateDependent();

        constructDependent( person, spouse );
        spouse.setRelationship( RelationshipType.SPOUSE );

        return spouse;
    }


   /**
    *       Take data from the <code>person</cod> that we got from the <code>findDependents</code>
    *       web service and construct a <code>CorporateDependent</code>
    *       from that.  The dependent is being passed in, so all we have to do is fill out
    *       fields on that object.
    *
    *       @param person - this is a person that we got from the <code>findDependents</code>
    *               web service
    *       @param dependent - this is the dependent whose fields will get filled out
    *               by this method.
    *       @throws RbpsServiceException
    */
    public void constructDependent( final Shrinq3Person         person,
                                    final CorporateDependent    dependent ) {

        if ( person == null ) {

            return;
        }

        dependent.setParticipantId( Long.parseLong( person.getPtcpntId() ) );
        dependent.setFirstName( person.getFirstName() );
//        dependent.setMiddleName( person.getMiddleName() );
        dependent.setLastName( person.getLastName() );
        dependent.setSocialSecurityNumber( person.getSsn() );
        dependent.setBirthDate( SimpleDateUtils.convertDate( "birth date", person.getDateOfBirth() ) );
//        dependent.setGender( convertGender( person.getGender() ) );
//        dependent.setEmail( person.getEmailAddress() );
        dependent.setOnAward( convertAwardIndicator( person.getAwardIndicator() ) );
        dependent.setRelationship( RelationshipType.CHILD );
    }


    public void logCorporateChildren( final RbpsRepository repository ) {

        logUtils.logCorporateChildren( repository );
    }


    public boolean convertVeteranIndicator( final String veteranIndicator ) {

        return CommonUtils.indicatorIsPositive( veteranIndicator );
    }


    public boolean convertAwardIndicator( final String awardIndicator ) {

        return CommonUtils.indicatorIsPositive( awardIndicator );
    }

}
