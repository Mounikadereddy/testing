package gov.va.vba.rbps.services.ws.client.handler.awards.util;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.springframework.util.CollectionUtils;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.dto.CorporateDependent;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;

import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.services.populators.FindDependencyDecisionByAwardPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;




public class CountNumberOfDependentsOnAward {
	
	
	private static Logger logger = Logger.getLogger( CountNumberOfDependentsOnAward.class );
	
	// JR - 08-16-2018 - RTC Story # 801817 - added maxDependentChildren and set it to 7
	private static int maxDependentChildren = 7;
	
//	private CommonUtils 							utils;
    private LogUtils            					logUtils        	= new LogUtils( logger, true );
//    private SimpleDateUtils     					dateUtils       	= new SimpleDateUtils();
	private FindDependencyDecisionByAwardPopulator	populator			= new FindDependencyDecisionByAwardPopulator();  
	
	public int getTotalDependentCount( RbpsRepository repository, FindDependencyDecisionByAwardResponse response ) {
		
		
		List<DependencyDecisionVO> dependencyDecisionList = response.getReturn().getDependencyDecision().getDependencyDecision();
		
        if ( CollectionUtils.isEmpty( dependencyDecisionList ) ) {

            logUtils.log( "Empty dependencyDecisionList ", repository );
            //return 0;
        }
		
		//int countOfXomDependentsNotOnAward 	=  getCountOfXomDependentsNotOnAward( repository, response );
		//int countOfDependentsOnAward 		=  getCountOfDependentsOnAward( dependencyDecisionList );
		//int totalDependents					=  countOfXomDependentsNotOnAward + countOfDependentsOnAward;
		
		// JR - 07-30-2015 - CCR 2732 - reject the claim if number of children on the award > 5 and adding children.
        int totalDependentChildren = 0;
		List<Child> xomChildren = repository.getVeteran().getChildren();
		repository.getVeteran().setTotalNumberOfDependents( 0 );
		repository.getVeteran().getAwardStatus().setNumDependentsOnAward(0);
		
        if (xomChildren != null && !xomChildren.isEmpty() ) {
    		logUtils.log("\nxomChildren count = " + xomChildren.size() + "\n", repository );
    		for ( final Child child : xomChildren ) {               
    			logUtils.log("\nxomchild name = " + child.getFirstName() + "\n", repository );
            }
    		
    		List<Long> childrenOnAwardList = countDependents(dependencyDecisionList, repository);
    		int numChildrenOnAward = 0;
    		if (childrenOnAwardList != null) numChildrenOnAward = childrenOnAwardList.size();
    		
    		logUtils.log( "\nnumChildrenOnAward = " + numChildrenOnAward, repository );
    		
    		// JR - 08-16-2018 - RTC Story # 801817 - modified to use maxDependentChildren  instead of 5 for max dependent children
        	if ( numChildrenOnAward >= maxDependentChildren ) {
        		throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason - The number of Dependent children on Award is >= " + maxDependentChildren + ". Please review." );
        	}
        	
        	repository.getVeteran().getAwardStatus().setNumDependentsOnAward( numChildrenOnAward );	
        	
        	int numChildrenNotOnAward = countXomChildrenNotOnAward(repository, childrenOnAwardList);
        	
        	logUtils.log( "\nnumChildrenNotOnAward = " + numChildrenNotOnAward, repository );
        	
        	totalDependentChildren = numChildrenOnAward + numChildrenNotOnAward;
        	repository.getVeteran().setTotalNumberOfDependents( totalDependentChildren );
		
        	logUtils.log( "\ntotalDependentChildren = " + totalDependentChildren, repository );
		
        	// JR - 08-16-2018 - RTC Story # 801817 - modified to use maxDependentChildren  instead of 5 for max dependent children
        	if ( totalDependentChildren > maxDependentChildren ) {
        		throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason - Total number of Dependent children is > " + maxDependentChildren + ". Please review." );
        	}
		
        	//Rule CP0130
        	//if ( totalDependents > 5 )
        	//	throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason - The number of Dependents on Award is > 5. Please review." );
        }
		return totalDependentChildren;
	}

	
	
	public int getCountOfXomDependentsNotOnAward( RbpsRepository repository, FindDependencyDecisionByAwardResponse response ) {
		
		int countOfXomDependentsNotOnAward = addIfSpouseNotOnAward( repository, response ) + 
		                                     addifChildrenNotOnAward( repository, response );
		
		return countOfXomDependentsNotOnAward;
	}
	
	
	
	private int addIfSpouseNotOnAward( RbpsRepository repository, FindDependencyDecisionByAwardResponse response ) {
		
		if ( repository.getVeteran().getCurrentMarriage() == null ) {
			return 0;
		}
		
		long spouseCorpId = repository.getVeteran().getCurrentMarriage().getMarriedTo().getCorpParticipantId();
		List<DependencyDecisionVO> dependencyDecisionList = populator.getDependencyDecisionListByParticipantId( response, spouseCorpId, repository );
		
        if ( CollectionUtils.isEmpty( dependencyDecisionList ) ) {

            logUtils.log( "Empty dependencyDecisionList for Spouse, hence returning 1", repository );
            return 1;
        }
		return 0;
	}
	

	
	private int addifChildrenNotOnAward( RbpsRepository repository, FindDependencyDecisionByAwardResponse response ) {	
		
		List<Child> xomChildren = repository.getVeteran().getChildren();
		
        if ( CollectionUtils.isEmpty( xomChildren ) ) {

            logUtils.log( "No Corporate Dependent Children ", repository );
            return 0;
        }

        int childCount = 0;
        //count of children submitted with the claim who are not on award
        for ( Child xomChild : xomChildren ) {

            List <DependencyDecisionVO> dependencyDecisionVOList = populator.getDependencyDecisionListByParticipantId( response, xomChild.getCorpParticipantId(), repository );
            
            if ( CollectionUtils.isEmpty( dependencyDecisionVOList ) ) {
            	childCount ++;
            	continue;
            }
            
            childCount =+ checkifChildIsRemovedFromAward( xomChild, dependencyDecisionVOList );
            
        }
        return childCount;	
	}
	
	
	private int checkifChildIsRemovedFromAward( Child xomChild, List <DependencyDecisionVO> dependencyDecisionVOList ) {
		
		for ( DependencyDecisionVO decision : dependencyDecisionVOList ) {
			
			if ( decision.getDependencyStatusType().equalsIgnoreCase( "NAWDDEP" ) && decision.getAwardEffectiveDate() != null &&
					SimpleDateUtils.xmlGregorianCalendarToDay( decision.getAwardEffectiveDate() ).after( new Date() ) ) {

				return 0;
			}
		}
		
		return 1;
	}
	
	
	private int getCountOfDependentsOnAward( List<DependencyDecisionVO> dependencyDecisionVOList ) {	
		
		Hashtable<Long, Long>		         hashTbl	= new Hashtable<Long, Long>();
		MultiMap        dependencyDecisionByAwardMap 	= null;;
		ArrayList<Long> longList 						= new ArrayList<Long>( hashTbl.values() );
		int 			dependentCount 					= 0;
		
		try {
			dependencyDecisionByAwardMap 	= populateDependencyDecisionByAwardMap( dependencyDecisionVOList, hashTbl );
			
			for ( Long participantId : longList ) {
				
				List <DependencyDecisionVO> dependencyDecisionList = populator.getDependencyDecisionListByParticipantId( dependencyDecisionByAwardMap, participantId.longValue() );
				
				dependentCount = dependentCount + countDependentsNotRemovedFromAward( dependencyDecisionList );
			}
		}
		finally {
			
			dependencyDecisionByAwardMap = null;
		}
		return dependentCount;
	}
		

	
    private MultiMap populateDependencyDecisionByAwardMap( final List <DependencyDecisionVO>  dependencyDecisionVOList, Hashtable<Long, Long> hashTbl ) {

        MultiMap dependencyDecisionByAwardMap = new MultiHashMap();

        for (DependencyDecisionVO dependencyDecisionVO : dependencyDecisionVOList ){
        	
            dependencyDecisionByAwardMap.put( dependencyDecisionVO.getPersonID(), dependencyDecisionVO );
            hashTbl.put( dependencyDecisionVO.getPersonID() , dependencyDecisionVO.getPersonID() );
        }
        return dependencyDecisionByAwardMap;
    }
	
	
    
    private int countDependentsNotRemovedFromAward( List <DependencyDecisionVO> dependencyDecisionList ) {
    	
    	
		for ( DependencyDecisionVO dependencyDecision	:	dependencyDecisionList ) {	
			
			if ( dependencyDecision.getDependencyStatusType().equalsIgnoreCase( "NAWDDEP" )  ) {
					
				if ( SimpleDateUtils.xmlGregorianCalendarToDay( dependencyDecision.getAwardEffectiveDate() ).after( new Date() ) ) {
					return 1;
				}
				else {
					return 0;
				}
			}
		}
		return 1;	
    }
    
    private List<Long> countDependents(
			List<DependencyDecisionVO> dependencyDecList,
			RbpsRepository repository) {
		logger.debug("BEGIN countDependents");
		
		if ( CollectionUtils.isEmpty( dependencyDecList ) ) {

            logUtils.log( "Empty dependencyDecisionList ", repository );
            return null;
        }
		String errMsg;
		
		DependencyDecisionVO curDec = null;

		HashMap<Long, List<DependencyDecisionVO>> myHashMap = new HashMap<Long, List<DependencyDecisionVO>>();
		List<DependencyDecisionVO> myDepList;
		
		for (int idx = 0; idx < dependencyDecList.size(); idx++) {
			curDec = dependencyDecList.get(idx);

			errMsg = "curDec =  " + curDec.getPersonID()
					+ " with decisionType = "
					+ curDec.getDependencyDecisionType() + ", statusType = "
					+ curDec.getDependencyStatusType() + ", effective date = "
					+ curDec.getAwardEffectiveDate();
			logger.debug(errMsg);
			myDepList = myHashMap.get(curDec.getPersonID());
			if (myDepList == null) {
				myDepList = new ArrayList<DependencyDecisionVO>();
				myDepList.add(curDec);
				myHashMap.put(curDec.getPersonID(), myDepList);
			} else
				myDepList.add(curDec);
		}

		int count = 0;
		List<Long> childrenOnAward = new ArrayList<Long>();
		for (Map.Entry<Long, List<DependencyDecisionVO>> entry : myHashMap
				.entrySet()) {
			myDepList = entry.getValue();
			
			if (isDependentActive(myDepList, repository)) {
				count++;
				childrenOnAward.add(entry.getKey());
			}
		}

		logger.debug("END countDependents");
		return childrenOnAward;
	}

	private boolean isDependentActive(
			List<DependencyDecisionVO> dependencyDecList,
			RbpsRepository repository) {
		logger.debug("BEGIN isDependentActive");
		
		// changes for RTC 304011
		DependencyDecisionVO depndDec = null;
		for (int count = 0; count < dependencyDecList.size(); count++) {
			depndDec = dependencyDecList.get(count);
			if (depndDec.getAwardEffectiveDate() == null) {
				if (depndDec.getDependencyStatusType().compareTo("NAWDDEP") != 0) {

					// this may not happen
					return true;
				} else {
					return false;
				}
			}
		}
		// end of changes for 304011
		Collections.sort(dependencyDecList,
				new Comparator<DependencyDecisionVO>() {
					@Override
					public int compare(DependencyDecisionVO o1,
							DependencyDecisionVO o2) {
						return o1.getAwardEffectiveDate().compare(
								o2.getAwardEffectiveDate());
					}
				});

		DependencyDecisionVO curDec = null;
		DependencyDecisionVO pastDec = null;
		DependencyDecisionVO futureDec = null;
		String errMsg;
		Date today = new Date();

		int idx = 0;
		do {
			curDec = dependencyDecList.get(idx);
			idx++;
			if (curDec.getAwardEffectiveDate() != null
					&& curDec.getAwardEffectiveDate().toGregorianCalendar()
							.getTime().compareTo(today) >= 0) {
				futureDec = curDec;
				break;
			} else
				pastDec = curDec;
		} while (idx < dependencyDecList.size());

		if (logger.isDebugEnabled()) {
			if (pastDec != null) {
				errMsg = "pastDec = " + pastDec.getPersonID()
						+ " with decisionType = "
						+ pastDec.getDependencyDecisionType()
						+ ", statusType = " + pastDec.getDependencyStatusType()
						+ ", effective date = "
						+ pastDec.getAwardEffectiveDate();
				logger.debug(errMsg);
			}

			if (futureDec != null) {
				errMsg = "futureDec = " + futureDec.getPersonID()
						+ " with decisionType = "
						+ futureDec.getDependencyDecisionType()
						+ ", statusType = "
						+ futureDec.getDependencyStatusType()
						+ ", effective date = "
						+ futureDec.getAwardEffectiveDate();
				logger.debug(errMsg);
			}
		}

		boolean isGrant = false;
		//Fortify Null Dereference 
		if (pastDec != null && pastDec.getDependencyStatusType() != null
				&& pastDec.getDependencyStatusType().compareTo("NAWDDEP") != 0
				&& isChild(pastDec.getPersonID(), repository)) {
			logger.debug("pastDec is a grant");
			isGrant = true;
		  //Fortify Null Dereference issue fix
		} else if (futureDec != null && futureDec.getDependencyStatusType() != null) {
			if (futureDec.getDependencyStatusType().compareTo("NAWDDEP") != 0
					&& isChild(futureDec.getPersonID(), repository)) {
				logger.debug("futureDec is a grant");
				isGrant = true;
			}
		}


		logger.debug("END isDependentActive");
		return isGrant;
	}

	private boolean isChild(Long personId,
							RbpsRepository repository)
	{
		logger.debug("BEGIN isChild");
		if (personId == null) return false;
		//Fortify Null Dereference fix
		if (repository.getChildren() == null) return false;
		
		for (CorporateDependent p:repository.getChildren()) {
			if (p.getParticipantId().compareTo(personId) == 0)
				return true;
		}
		
		logger.debug("END isChild");
		return false;
	}
	
	private int countXomChildrenNotOnAward( RbpsRepository repository, List<Long> childrenOnAward ) {	

		List<Child> xomChildren = repository.getVeteran().getChildren();
		
		
        if ( CollectionUtils.isEmpty( xomChildren ) ) {

            logUtils.log( "No xom Dependent Children ", repository );
            return 0;
        }
        
		if (childrenOnAward == null || childrenOnAward.isEmpty()) {
			return xomChildren.size();
		}


        int childCount = 0;
        
        //count of children submitted with the claim who are not on award
        for ( Child xomChild : xomChildren ) {    
            if ( !childrenOnAward.contains(xomChild.getCorpParticipantId()) )  {
            	childCount ++;
            	logUtils.log("\nxomChild not on award, name = " + xomChild.getFirstName() + ", ptcpntId = " + xomChild.getCorpParticipantId(), repository );
            } else {
            	logUtils.log("\nxomChild already on award, name = " + xomChild.getFirstName() + ", ptcpntId = " + xomChild.getCorpParticipantId(), repository );	
            }
        }
        return childCount;	
	}
}
