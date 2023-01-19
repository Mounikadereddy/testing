/*
 * DependentOnAwardFilter.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.impl;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.coreframework.xom.Marriage;
import gov.va.vba.rbps.coreframework.xom.Person;
import gov.va.vba.rbps.coreframework.xom.Spouse;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DecisionDetailsProcessor;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DependencyDecisionByAwardProducer;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DependencyDecisionUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;


/**
 *      Filter dependents that are already on an award.
 */
public class DependentOnAwardFilter {

    private static Logger logger = Logger.getLogger(DependentOnAwardFilter.class);

    private LogUtils                                logUtils                             =  new LogUtils( logger, true );

    private boolean                                 shouldProcessDeniedWith674Dependents =  false;
    private DependencyDecisionByAwardProducer       dependencyDecisionByAwardProducer;


    public void evaluateDependentOnAward( final RbpsRepository  repository ) {

        List <Dependent>    xomDependents           = new ArrayList<Dependent>();
        List <Dependent>    removeDependentList     = new ArrayList<Dependent>();
//        List <Dependent>    throwExceptionList      = new ArrayList<Dependent>();

        xomDependents        = buildDependentList( repository );

        evaluateSpouseGrant( repository );
//        throwExceptionList  =  evaluateEachDependentDenied( xomDependents, repository );
        removeDependentList =  evaluateEachDependentOnAward( xomDependents, repository );

        removeDependents( removeDependentList, repository );
//        throwException ( throwExceptionList );
    }


    private void removeDependents( final List<Dependent> removeDependentList, final RbpsRepository  repository  ) {

        for ( Dependent dep : removeDependentList ) {

            if ( dep instanceof Spouse ) {

//                logUtils.log( String.format( "Removing xom spouse >%s, %s< already on award", dep.getLastName(), dep.getFirstName() ) );
                continue;
            }

            logUtils.log( String.format( "Removing xom child >%s, %s< already on award",
                                dep.getLastName(),
                                dep.getFirstName() ), repository );

            repository.getVeteran().getChildren().remove( dep );
        }
    }


//    private void throwException( final List<Dependent> throwExceptionList  ) {
//
//        List <String> dependentList =   new ArrayList<String>();
//
//        for ( Dependent dep : throwExceptionList ) {
//
//            dependentList.add( getFullName( dep ) );
//        }
//
//        if ( dependentList.size() > 0) {
//
//            throw new RbpsRuntimeException( exceptionMessage( dependentList ) );
//        }
//    }

//
//    private String getFullName(final Dependent dep) {
//
//        final String SPACE = " ";
//
//        return dep.getFirstName() + SPACE + dep.getLastName();
//    }
//
//
//    private String exceptionMessage( final List <String> dependentList) {
//
//        return String.format( "process since dependent(s) >%s< are already denied/removed from award ", CommonUtils.join( dependentList, "," ) ) ;
//    }


    private List <Dependent> evaluateEachDependentOnAward( final List<Dependent> xomDependents, final RbpsRepository  repository ) {

        List <Dependent>    removeDependentList  = new ArrayList<Dependent>();


        for ( final Dependent dep   :   xomDependents ) {

            if ( ! dep.isOnCurrentAward() ) {

                logUtils.log( String.format( "xom dependent >%s, %s< is not on current award",
                                    dep.getLastName(),
                                    dep.getFirstName() ), repository );

                if ( dep instanceof Spouse ) {
                	
                    throwExceptionIfMarriageDateNotPresent( dep );
                }
                continue;
            }

            if ( isChildWith674( dep, repository ) ) {

                dep.setOnCurrentAward( false );
                logUtils.log( String.format( "Set CurrentAward to false for xom dependent >%s, %s<",
                                    dep.getLastName(),
                                    dep.getFirstName() ), repository );
            }
//            else if ( dep.getForms().contains(FormType.FORM_21_686C)) {
            else {

                logUtils.log( String.format( "Should remove xom dependent already on award >%s, %s<",
                                    dep.getLastName(),
                                    dep.getFirstName() ), repository );
                removeDependentList.add( dep );
            }
        }

        return removeDependentList;
    }

    
    private void evaluateSpouseGrant( final RbpsRepository  repository ) {
    
    	Marriage marriage = repository.getVeteran().getCurrentMarriage();
    	Dependent spouse  = null;
    	
    	if ( marriage != null ) {
    		
    		spouse = marriage.getMarriedTo();
    		spouse.setOnCurrentAward ( isGrant ( repository, spouse ) );
		}
    }

//    private List <Dependent> evaluateEachDependentDenied( final List<Dependent> xomDependents, final RbpsRepository  repository ) {
//
//        List <Dependent>    removeDependentList  = new ArrayList<Dependent>();
//
//
//        for ( final Dependent dep   :   xomDependents ) {
//
//            evaluateDependentForDenied( repository, removeDependentList, dep );
//        }
//
//        return removeDependentList;
//    }


    public void evaluateDependentForDenied( final RbpsRepository    repository,
                                            final List<Dependent>   throwExceptionList,
                                            final Dependent         dep ) {

        if ( ! dep.isDeniedAward() ) {

            logUtils.log( String.format( "xom dependent >%s, %s< is not denied or removed from award",
                                dep.getLastName(),
                                dep.getFirstName() ), repository );
            return;
        }


        if ( dep.isOnCurrentAward() ) {

            return;
        }


        if ( isRemarriedDeniedSpouse( repository, dep ) ) {

            return;
        }


        if ( isRemarriedDeniedStepchild( repository, dep ) ) {

            return;
        }


        if ( isChildWith674( dep, repository ) && shouldProcessDeniedWith674Dependents ) {

            return;
        }

        if ( isRemoval( repository, dep ) ) {

            return;
        }

        logUtils.log( String.format( "Adding denied xom child %s to exception list",
        								CommonUtils.getStandardLogName( dep ) ), repository );

        throwExceptionList.add(dep );
    }


    public boolean isChildWith674( final Dependent dep, final RbpsRepository repository ) {

        boolean result = dep.getForms().contains(FormType.FORM_21_674);

        if ( ! result ) {

            return result;
        }

        logUtils.log( String.format( "isDeniedAward true && xom dependent >%s, %s< has 674",
                            dep.getLastName(),
                            dep.getFirstName() ), repository );

        return result;
    }


    public boolean isRemarriedDeniedStepchild( final RbpsRepository repository,
                                               final Dependent dep ) {

        boolean result = dep instanceof Child
                            && isStepchild( dep )
                            && isMarriagedAfterDenied( repository, dep );

        if ( ! result ) {

            return result;
        }

        logUtils.log( String.format( "isDeniedAward true && xom dependent >%s, %s< is a Step Child and veteran marriage date after denial/removal date",
                            dep.getLastName(),
                            dep.getFirstName() ), repository );

        return result;
    }


    public boolean isRemarriedDeniedSpouse( final RbpsRepository repository,
                                            final Dependent dep ) {

        boolean result = dep instanceof Spouse
                            && isMarriagedAfterDenied( repository, dep );

        if ( ! result ) {

            return result;
        }

        logUtils.log( String.format( "isDeniedAward true  xom dependent >%s, %s< is the spouse and veteran marriage date after denial/removal date",
                            dep.getLastName(),
                            dep.getFirstName() ), repository );

        //      The turning off of on current award isn't really
        //      necessary - you can't be both on award and denied.
        //      We're just being thorough.
        //
        dep.setIsDeniedAward( false );
        dep.setOnCurrentAward( false );

        return result;
    }


    public boolean isStepchild( final Dependent dep ) {

        return ( ( Child ) dep).getChildType().equals( ChildType.STEPCHILD );
    }


    public boolean isMarriagedAfterDenied( final RbpsRepository   repository,
                                           final Dependent        dep ) {

        throwExceptionIfMarriageDateNotPresent( repository.getVeteran() );

        return repository.getVeteran().getCurrentMarriage().getStartDate().after( dep.getDeniedDate() );
    }


    private List <Dependent> buildDependentList( final RbpsRepository  repository ) {

        List <Dependent>    xomDependents       = new ArrayList<Dependent>();
//        NullSafeGetter      grabber             = new NullSafeGetter();
        Spouse              spouse              = (Spouse) NullSafeGetter.getAttribute( repository, "veteran.currentMarriage.marriedTo" );

        xomDependents.addAll( repository.getVeteran().getChildren() );
        checkVetHasStepChild( repository.getVeteran() );
        if ( spouse != null ) {

            xomDependents.add( spouse );
        }

        return xomDependents;
    }


    public boolean isRemoval( final RbpsRepository repository, final Dependent dependent ) {

        List<DependencyDecisionVO>  dependencyDecisionByIdList  = getSortedDependencyDecisionByIdList( repository, dependent );

        if ( CollectionUtils.isEmpty( dependencyDecisionByIdList ) ) {

            return false;
        }

        return isRemoval( repository, dependencyDecisionByIdList );
    }


    private boolean isRemoval( final RbpsRepository                 repository,
                               final List<DependencyDecisionVO>     dependencyDecisionByIdList ) {

        DependencyDecisionUtils     decisionUtils               = new DependencyDecisionUtils();

        if ( multipledependencyDecisions( dependencyDecisionByIdList ) ) {

            List<DependencyDecisionVO> cleanedupList        =   removeFutureDateClaims( repository, dependencyDecisionByIdList );
            
            if ( CollectionUtils.isEmpty( cleanedupList ) ) {

                return false;
            }
            int size                                        =   cleanedupList.size();
            DependencyDecisionVO    lastDecision            =   cleanedupList.get( size - 1 );
            DependencyDecisionVO    penultimateDecision     =   cleanedupList.get( size - 2 );

            return  ( decisionUtils.isDenial( lastDecision ) && decisionUtils.isGrant( penultimateDecision ) );
        }

        return false;
    }

    public boolean isGrant( final RbpsRepository repository, final Dependent dependent ) {

        List<DependencyDecisionVO>  dependencyDecisionByIdList  = getSortedDependencyDecisionByIdList( repository, dependent );

        if ( CollectionUtils.isEmpty( dependencyDecisionByIdList ) ) {

            return false;
        }

        return isGrant( repository, dependencyDecisionByIdList );
    }  
    

    private boolean isGrant( final RbpsRepository                 repository,
                             final List<DependencyDecisionVO>     dependencyDecisionByIdList ) {

        DependencyDecisionUtils     decisionUtils       = new DependencyDecisionUtils();
        List<DependencyDecisionVO> cleanedupList        =   removeFutureDateClaims( repository, dependencyDecisionByIdList );
        
        if ( CollectionUtils.isEmpty( cleanedupList ) ) {

            return false;
        }
        
        int size                                        =   cleanedupList.size();
        DependencyDecisionVO    lastDecision            =   cleanedupList.get( size - 1 );

       return  ( decisionUtils.isGrant( lastDecision ) );

    }   
    
    
    private List<DependencyDecisionVO> removeFutureDateClaims( final RbpsRepository                 repository,
                                                               final List<DependencyDecisionVO>     dependencyDecisionByIdList ) {

        Date 						today			= new Date();
        List<DependencyDecisionVO> cleanedupList    =   new ArrayList<DependencyDecisionVO>();

        for ( DependencyDecisionVO dependencyDecision   :   dependencyDecisionByIdList) {

            Date    eventDate = SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecision.getEventDate() );

            if ( SimpleDateUtils.isOnOrBefore( eventDate, today ) ) {

                cleanedupList.add( dependencyDecision );
            }
        }

        logUtils.log( "Dependency Decision List after removing future date claims: >%s< :\n" + CommonUtils.stringBuilder( cleanedupList ), repository );

        return cleanedupList;
    }


    private List<DependencyDecisionVO> getSortedDependencyDecisionByIdList( final RbpsRepository repository, final Dependent dependent ) {

        DecisionDetailsProcessor    detailsProcessor            = new DecisionDetailsProcessor();
        List<DependencyDecisionVO>  dependencyDecisionByIdList  = getDependencyDecisionList( repository,
                                                                                             dependent.getCorpParticipantId() );

        logUtils.log( String.format( "List from map by participant Id: >%s< :\n", dependent.getCorpParticipantId() )
                      + CommonUtils.stringBuilder( dependencyDecisionByIdList ), repository );

        detailsProcessor.sortDependencyDecisionList( dependencyDecisionByIdList );

        return dependencyDecisionByIdList;
    }


    private List<DependencyDecisionVO> getDependencyDecisionList( final RbpsRepository repository, final long depCorpParticipantId) {

            return dependencyDecisionByAwardProducer.
                                        getDependencyDecisionListForDependent( repository,
                                                                               repository.getVeteran().getCorpParticipantId(),
                                                                               depCorpParticipantId );
    }


    private boolean multipledependencyDecisions( final List<DependencyDecisionVO> dependencyDecisionByIdList ) {

        return ( dependencyDecisionByIdList.size() > 1 );
    }


    private void throwExceptionIfMarriageDateNotPresent( final Person        person ) {

        if ( isMarriageDatePresent( person ) ) {

            return;
        }

            throw new RbpsRuntimeException( exceptionMessageForMarriageDate( person ) );
    }


    private boolean isMarriageDatePresent( final Person        person ) {

        Marriage marriage   = person.getCurrentMarriage();

        return ( marriage != null && marriage.getStartDate() != null );
    }


    private String exceptionMessageForMarriageDate( final Person person ) {

        return String.format( "The current marriage date was not provided for %s",
        						CommonUtils.getStandardLogName( person ) );
    }



    public void setShouldProcessDeniedWith674Dependents( final boolean shouldProcessDeniedWith674Dependents ) {

        this.shouldProcessDeniedWith674Dependents = shouldProcessDeniedWith674Dependents;
    }

    public void setDependencyDecisionByAwardProducer( final DependencyDecisionByAwardProducer dependencyDecisionByAwardProducer ){

        this.dependencyDecisionByAwardProducer = dependencyDecisionByAwardProducer;
    }

	public void checkVetHasStepChild(Veteran veteran) {

		// this is used in spouse removal
		if (CollectionUtils.isEmpty(veteran.getChildren())) {

			return;
		}
		List<Child> childrenList = veteran.getChildren();

		for (int i = 0; i < childrenList.size(); i++) {
			Child child = childrenList.get(i);
			logger.debug("*** DependentOnAwardFilter: ... with child.getChildType() for child"+ child.getFirstName()+":"
					+ child.getChildType());

			if (child.getChildType() == null
					|| child.getChildType().equals(ChildType.STEPCHILD)
					|| child.getChildType().equals(ChildType.UNDEFINED)) {
				veteran.setHasStepChildOrUndefinedChildType(true);
				return;
			}
		}
	}
}
