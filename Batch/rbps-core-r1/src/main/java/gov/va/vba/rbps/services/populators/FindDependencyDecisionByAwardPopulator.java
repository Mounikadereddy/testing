/*
 * FindDependencyDecisionByAwardPopulator.java
 *
 * Copyright 2011 U.S. Department of Veterans Affairs
 * U.S. Government PROPRIETARY/CONFIDENTIAL. Use is subject to security terms.
 */
package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Award;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.ChildType;
import gov.va.vba.rbps.coreframework.xom.Dependent;
import gov.va.vba.rbps.coreframework.xom.FormType;
import gov.va.vba.rbps.services.ws.client.handler.awards.util.DecisionDetailsProcessor;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.springframework.util.CollectionUtils;


/**
 *      Given a response from the web service that returns a dependency decision list for a veteran,
 *      decide for each child if they are already a school child.
 */
public class FindDependencyDecisionByAwardPopulator {

    private static Logger logger = Logger.getLogger(FindDependencyDecisionByAwardPopulator.class);


    private static final String                 SCHOOL_CHILD_DECN_TYPE = "SCHATTB";
    private static final String                 SCHOOL_CHILD_TERM_TYPE = "SCHATTT";
    private static final String                 SCHOOL_CHILD_STATUS_TYPE = "SCHCHD";
    private static final String                 MINOR_CHILD_STATUS_TYPE  = "MC";
    private static final String                 MINOR_CHILD_TURNS_18_TYPE  = "T18";
    private final static String                 NOT_AWARD_DEPENDENT      = "NAWDDEP";
    private final static String                 ELECT_CHAPTER_35 = "EC35";
    private final static String                 FAILED_TO_CONFIRM  = "FTOCDEP";

    private LogUtils            logUtils        = new LogUtils( logger, true );



    public void setChildAwardDetails(final FindDependencyDecisionByAwardResponse    findDependencyDecisionByAwardResponse,
                                     final List <Child>                             xomChildren,
                                     final RbpsRepository 							repository) {

 
        defaultNewSchoolChild(xomChildren);

        MultiMap         dependencyDecisionByAwardMap = populateDependencyDecisionByAward( findDependencyDecisionByAwardResponse, repository );
       	
        try{
	        if ( CollectionUtils.isEmpty( dependencyDecisionByAwardMap ) ) {
	
	            logUtils.log( "Empty dependencyDecisionByAwardMap ", repository );
	            return;
	        }
	
	        if ( CollectionUtils.isEmpty( xomChildren ) ) {
	
	            logUtils.log( "No Corporate Dependent Children ", repository  );
	            return;
	        }
	
	        for ( Child xomChild : xomChildren ) {
	
	            List <DependencyDecisionVO> dependencyDecisionVOList = getDependencyDecisionListByParticipantId( dependencyDecisionByAwardMap,
	                                                                                                             xomChild.getCorpParticipantId() );
	
	            processStandAlone674( xomChild, dependencyDecisionVOList, repository );
	            populateIsNewSchoolChild( xomChild, dependencyDecisionVOList );
	            setIfChildWasOnAwardAsMinor( xomChild, dependencyDecisionVOList );
	            setIfChildWasTerminatedOn18thBirthday( xomChild, dependencyDecisionVOList );
	            populateLastSchoolTermOnAward(xomChild, dependencyDecisionVOList );
	        }
       	}
       	finally {
       		
       		dependencyDecisionByAwardMap = null;
       	}
        
    }


    private void defaultNewSchoolChild(final List<Child> xomChildren) {

        for ( Child xomChild : xomChildren ) {

            if (xomChild.getForms().contains(FormType.FORM_21_674)) {

                xomChild.setNewSchoolChild( true );
            }
        }
    }


    @SuppressWarnings("unchecked")
    public List<DependencyDecisionVO> getDependencyDecisionListByParticipantId( final MultiMap      dependencyDecisionByAwardMap,
                                                                                final long          personId ) {

        List<DependencyDecisionVO>  result = (List<DependencyDecisionVO>) dependencyDecisionByAwardMap.get( personId );

        if ( result == null ) {

            result = new ArrayList<DependencyDecisionVO>();
        }

        return result;
    }


    public List <DependencyDecisionVO> populateDependencyDecisionForDependent( final FindDependencyDecisionByAwardResponse 	findDependencyDecisionByAwardResponse, 
    		                                                                   final Dependent 							   	dependent,
    		                                                                   final RbpsRepository 						repository) {

        MultiMap         			dependencyDecisionByAwardMap = null;
        List <DependencyDecisionVO> dependencyDecisionVOList 	 = null;
        
        try {
	        dependencyDecisionByAwardMap = populateDependencyDecisionByAward( findDependencyDecisionByAwardResponse, repository );
	
	        if ( CollectionUtils.isEmpty( dependencyDecisionByAwardMap ) ) {
	
	            logUtils.log( "Empty dependencyDecisionByAwardMap ", repository  );
	            return null;
	        }
	        dependent.setIsEverOnAward(false);
	        dependencyDecisionVOList = getDependencyDecisionListByParticipantId( dependencyDecisionByAwardMap,
	                                                                             dependent.getCorpParticipantId() );
	        if ( !CollectionUtils.isEmpty( dependencyDecisionVOList ) ) {
	        	
	        	dependent.setIsEverOnAward(true);
	        	populateDobAndSsnIfMissingForTheDependent( dependencyDecisionVOList.get(0), dependent );
	        } 
	        
	        isDeniedAward( dependencyDecisionVOList, dependent,repository );
	        isFailedToConfromOrEc35(dependencyDecisionVOList, repository);
        }
        finally {
        	
        	dependencyDecisionByAwardMap = null;
        }
        return dependencyDecisionVOList;
    }


    private void populateDobAndSsnIfMissingForTheDependent( DependencyDecisionVO dependencyDecisionVO, Dependent dependent ) {
    	
    	if ( dependent.getBirthDate() == null ) {
    		
    		dependent.setBirthDate( SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecisionVO.getBirthdayDate() ) );
    	}
    	
    	if ( dependent.getSsn() == null ) {
    		
    		dependent.setSsn(  dependencyDecisionVO.getSocialSecurityNumber() );
    	}
    	// RTC-373694 - RBPSBatch: Spouse Removal Before Spouse Add Date
    	logger.debug("dependent.getawardEffective date  before set is :"+dependent.getAwardEffectiveDate());
    	logger.debug("dependencyDecisionVO.getAwardEffectiveDate()  before set is :"+dependencyDecisionVO.getAwardEffectiveDate());
    	
    	if(dependent.getAwardEffectiveDate() ==null && dependencyDecisionVO.getAwardEffectiveDate() !=null){
    		dependent.setAwardEffectiveDate( 
    				SimpleDateUtils.xmlGregorianCalendarToDay(
    						dependencyDecisionVO.getAwardEffectiveDate()));
    	}
    	logger.debug("dependent.getawardEffective date  after set is :"+dependent.getAwardEffectiveDate());
    	
    }

    
    private void processStandAlone674( final Child child, final List <DependencyDecisionVO> dependencyDecisionVOList, final RbpsRepository repository ) {
    	
    			if ( CollectionUtils.isEmpty( dependencyDecisionVOList ) ) {
    				
    				// if child has no previous decision, child should have 686c ( valid child type )
    				if ( ! isValidChildType( child, repository ) ) {
    					
    					logUtils.log( String.format( "Attempt to add standalone school child >%s< who was not previously on the Award", 
    																						CommonUtils.getStandardLogName( child ) ), repository  );
    					throw new RbpsRuntimeException( 
    							String.format( "Attempt to add school child >%s< who was not previously on the Award. Please develop for 686c.", 
    																						CommonUtils.getStandardLogName( child ) ) );
    				}
    			}
    			logUtils.log( String.format( " Child Type  for child >%s<", CommonUtils.getStandardLogName( child ) ), repository  );
    			logger.debug(  "is :"+child.getChildType()  );
				
    			/*RTC item 434297 Spouse removal - RBPS portion
    			 *  don't default childType
    			 * 
    			 * else {
    				
    				//setting child type to Biological Child for standalone 674
    				logUtils.log( String.format( "Setting Child Type to BIOLOGICAL  for standalone child >%s<", CommonUtils.getStandardLogName( child ) ), repository  );
    				
    				 child.setChildType( ChildType.BIOLOGICAL_CHILD );
    			}*/
    }
    

    
    private boolean isValidChildType( final Child child, final RbpsRepository repository ) {
 	   
 	   if ( child.getChildType().equals( ChildType.BIOLOGICAL_CHILD ) ) {
 		   
 		   return true;
 	   }
 	   if ( child.getChildType().equals( ChildType.STEPCHILD ) ) {
 		   
 		   return true;
 	   }
 	   
 	   if ( child.getChildType().equals( ChildType.ADOPTED_CHILD ) ) {
 		   
 		   String message = String.format( "Auto Dependency Processing Reject Reason - Child :  %s reported as adopted. Please develop for adoption paperwork.", 
																														CommonUtils.getStandardLogName( child ) );
 		  logUtils.log( message, repository  );
 		  throw new RbpsRuntimeException( message );
 	   }
 	   
 	   return false;
    }

    
    
    private void isDeniedAward( final List <DependencyDecisionVO> dependencyDecisionVOList, final Dependent dependent, RbpsRepository repository) {

        boolean isDeniedAward           = false;
        Date    previousEffectiveDate   = null;

        DecisionDetailsProcessor detailsProcessor = new DecisionDetailsProcessor();
        detailsProcessor.sortDependencyDecisionList( dependencyDecisionVOList );

        for (DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList) {

            Date    awardEffectiveDate      = SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecisionVO.getAwardEffectiveDate() );
            Date    currentEffectiveDate    = SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecisionVO.getAwardEffectiveDate() );

            if ( previousEffectiveDate == null ) {

                previousEffectiveDate = currentEffectiveDate;
            }

//            if ( ! SimpleDateUtils.isInFuture( awardEffectiveDate )
//                    && SimpleDateUtils.isOnOrAfter( currentEffectiveDate, previousEffectiveDate ) ) {
//
//       
 // for spouse we need Previous marriage terminated date
 // for child we can ignore that case if there is no awardEffective date.
            if (awardEffectiveDate ==null && dependencyDecisionVO.getRelationshipTypeDescription().equalsIgnoreCase("Spouse") &&
            			dependencyDecisionVO.getDependencyStatusType().equalsIgnoreCase(NOT_AWARD_DEPENDENT))
            {
            	 String message = String.format( "Auto Dependency Processing Reject Reason -"
            	 		+ " RBPS is not able to determine Previous marriage terminated date.");
							
            	 		logUtils.log( message, repository  );
            	 			throw new RbpsRuntimeException( message );
            }
            if (awardEffectiveDate !=null && ! SimpleDateUtils.isInFuture( awardEffectiveDate )
                    && SimpleDateUtils.isOnOrAfter( currentEffectiveDate, previousEffectiveDate ) ) {
            	isDeniedAward = dependencyDecisionVO.getDependencyStatusType().equalsIgnoreCase(NOT_AWARD_DEPENDENT);
                dependent.setIsDeniedAward( isDeniedAward );
                dependent.setDeniedDate( currentEffectiveDate );
                previousEffectiveDate = currentEffectiveDate;
            }
        }
    }


    public List<DependencyDecisionVO> getDependencyDecisionListByParticipantId( final FindDependencyDecisionByAwardResponse     findDependencyDecisionByAwardResponse,
                                                                                final long                                      corpParticipantId,
                                                                                final RbpsRepository 							repository) {

    	MultiMap         			dependencyDecisionByAwardMap = null;
    	List<DependencyDecisionVO>  result 						 = null;
    	
    	try {
	        dependencyDecisionByAwardMap = populateDependencyDecisionByAward( findDependencyDecisionByAwardResponse, repository );
	        result = getDependencyDecisionListByParticipantId( dependencyDecisionByAwardMap, corpParticipantId );
    	}
    	finally {
    		dependencyDecisionByAwardMap = null;
    	}

        return result;
    }
    
    /**
     * Checking if DependencyDecisionType is either FTOCEP OR EC35 if yes then do the process manually
     * @param dependencyDecisionVOList
     */
    public void isFailedToConfromOrEc35(final List<DependencyDecisionVO> dependencyDecisionVOList, final RbpsRepository repository) {
    	for (DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList) {
    		logger.debug("isFailedToConfromOrEc35 DependencyDecisionType " + dependencyDecisionVO.getDependencyDecisionType());
        	if(dependencyDecisionVO.getDependencyDecisionType().equalsIgnoreCase(FAILED_TO_CONFIRM) || dependencyDecisionVO.getDependencyDecisionType().equalsIgnoreCase(ELECT_CHAPTER_35)){
        		String message = "Auto Dependency Processing Reject Reason - 'Elects Chapter 35' or a 'Failed to Confirm Dependency' decision exists. Please process manually.";
        		logger.debug("checkForFailedConfromOrEc35 is true");
        		logUtils.log(message, repository);
        		throw new RbpsRuntimeException( message );
        	}
		}
    }


    public MultiMap populateDependencyDecisionByAward( final FindDependencyDecisionByAwardResponse findDependencyDecisionByAwardResponse, final RbpsRepository repository ) {

        DependencyDecisionReturnVO dependencyDecisionReturnVO = findDependencyDecisionByAwardResponse.getReturn();

        if (findDependencyDecisionByAwardResponse == null || findDependencyDecisionByAwardResponse.getReturn() == null ) {

            logUtils.log( "findDependencyDecisionByAwardResponse is null.", repository  );
            return null;
        }

        return recordDependencyDecisionByAwardInfo( dependencyDecisionReturnVO );
    }


    public MultiMap recordDependencyDecisionByAwardInfo( final DependencyDecisionReturnVO dependencyDecisionReturnVO ) {

        List <DependencyDecisionVO>  dependencyDecisionVOList       =   dependencyDecisionReturnVO.getDependencyDecision().getDependencyDecision();
        MultiMap                    dependencyDecisionByAwardMap    =   populateDependencyDecisionByAwardMap( dependencyDecisionVOList );

        return dependencyDecisionByAwardMap;
    }


    public MultiMap populateDependencyDecisionByAwardMap( final List <DependencyDecisionVO>  dependencyDecisionVOList) {

        MultiMap dependencyDecisionByAwardMap = new MultiHashMap();

        for (DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList ){

            dependencyDecisionByAwardMap.put(dependencyDecisionVO.getPersonID(), dependencyDecisionVO );
        }

        return dependencyDecisionByAwardMap;
    }


    private void populateIsNewSchoolChild(final Child xomChild, final List <DependencyDecisionVO> dependencyDecisionVOList ) {

        xomChild.setNewSchoolChild( isNewSchoolChild( dependencyDecisionVOList ) );
    }


    private boolean isNewSchoolChild( final List <DependencyDecisionVO> dependencyDecisionVOList ) {

        boolean isNewSchoolChild = true;

        for ( DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList ) {

            if ( dependencyDecisionVO.getDependencyStatusType().equalsIgnoreCase( SCHOOL_CHILD_STATUS_TYPE ) ) {

                isNewSchoolChild = false;
            }
        }

        return isNewSchoolChild;
    }
    
    
    private void setIfChildWasOnAwardAsMinor(final Child xomChild, final List <DependencyDecisionVO> dependencyDecisionVOList ) {
    	
    	xomChild.setOnAwardAsMinorChild( false );
    	
        for ( DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList ) {

            if ( dependencyDecisionVO.getDependencyStatusType().equalsIgnoreCase( MINOR_CHILD_STATUS_TYPE ) ) {

            	xomChild.setOnAwardAsMinorChild( true );
            	break;
            }
        }
    }
    
    private void setIfChildWasTerminatedOn18thBirthday(final Child xomChild, final List <DependencyDecisionVO> dependencyDecisionVOList ) {
    	
    	xomChild.setTerminatedOn18thBirthday( false );
    	
        for ( DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList ) {

            if ( dependencyDecisionVO.getDependencyStatusType().equalsIgnoreCase( MINOR_CHILD_TURNS_18_TYPE ) ) {

            	xomChild.setTerminatedOn18thBirthday( true );
            	break;
            }
        }
    }
    
    private void populateLastSchoolTermOnAward(final Child xomChild, final List <DependencyDecisionVO> dependencyDecisionVOList ) {

		Collections.sort(dependencyDecisionVOList, 
				 new Comparator<DependencyDecisionVO>()
				 {
					@Override 	public int compare(DependencyDecisionVO o1, DependencyDecisionVO o2) 
					{
                        // changes for RTC 304011
						if (o1.getAwardEffectiveDate() == null) return 1;
                        if (o2.getAwardEffectiveDate() == null) return -1;
						return o1.getAwardEffectiveDate().compare(o2.getAwardEffectiveDate());
					}
				 });
		
        for ( DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList ) {

        	String message 	=	String.format( "populateLastSchoolTermChild: DependencyDecision type %s, effective date : %s  ",dependencyDecisionVO.getDependencyDecisionType().toString(),
        			dependencyDecisionVO.getAwardEffectiveDate() == null? "AwardEffectiveDate is null" :dependencyDecisionVO.getAwardEffectiveDate().toString());									
			CommonUtils.log( logger, message );
        }

        int numEntries = dependencyDecisionVOList.size();
        if (numEntries > 1) { 	
        	DependencyDecisionVO lastDecisionVO = dependencyDecisionVOList.get(numEntries-1);
        	DependencyDecisionVO secondLastDecisionVO = dependencyDecisionVOList.get(numEntries-2);
        	if (secondLastDecisionVO.getDependencyDecisionType().equalsIgnoreCase( SCHOOL_CHILD_DECN_TYPE ) &&
        		lastDecisionVO.getDependencyDecisionType().equalsIgnoreCase( SCHOOL_CHILD_TERM_TYPE )) {
        		// ccr 2694 changing Corp date to effective date
        		xomChild.setLastTermInCorpBeginDate(secondLastDecisionVO.getEventDate().toGregorianCalendar().getTime());
        		xomChild.setLastTermInCorpEndDate(lastDecisionVO.getEventDate().toGregorianCalendar().getTime());
        		xomChild.setLastTermInCorpBeginEffectiveDate(secondLastDecisionVO.getAwardEffectiveDate().toGregorianCalendar().getTime());
        		xomChild.setLastTermInCorpEndEffectiveDate(lastDecisionVO.getAwardEffectiveDate().toGregorianCalendar().getTime());
        	}
        }
    }
}
