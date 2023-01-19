package gov.va.vba.rbps.services.populators;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimvalidator.GenericUserInformationValidatorImpl;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.CombinedPercentVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.DecisionsAtIssueServiceReturnVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.GetDecisionsAtIssueResponse;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDecisionAnalysisVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDecisionsAtIssueVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDecisionsAtIssueVO.HDisabilityList;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDecisionsAtIssueVO.HIndividualUnemployabilityList;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDecisionsAtIssueVO.HSpecialMoCompList;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDecisionsAtIssueVO.HSpecialProvisionLawList;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDisabilityEvaluationVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDisabilityVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HDisabilityVO.HDisabilityEvaluationList;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HIndividualUnemployabilityVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HSpecialMoCompVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.HSpecialProvisionLawVO;
import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.springframework.util.CollectionUtils;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;





public class DecisionsAtIssuePopulator {
	
	
	private static Logger logger = Logger.getLogger(DecisionsAtIssuePopulator.class);
	
	private LogUtils            logUtils    				= new LogUtils( logger, true );
	private MultiMap			eventDateCombinedPercentMap = null;
	
	
	public void determineFCDR( final RbpsRepository repository, 
			 				   final GetDecisionsAtIssueResponse response ) {
		try {
			eventDateCombinedPercentMap	= new MultiHashMap();

			DecisionsAtIssueServiceReturnVO returnVO = response.getReturn();
			if ( returnVO == null ) {
				return;
			}

			populateEffectiveDateCombinedPercentMap( returnVO, repository );

			HDecisionsAtIssueVO	hDecisionsAtIssue	= 	returnVO.getHDecisionsAtIssue();
			if ( hDecisionsAtIssue == null ) {
			    logger.debug("hDecisionsAtIssue is null, exiting determineFCDR");
				return;
			}

			evaluateDisability( repository, hDecisionsAtIssue );
			evaluateIndividualUnemployability( repository, hDecisionsAtIssue );
			evaluateHSpecialMoComp( repository, hDecisionsAtIssue );
			evaluateSpecialProvisionLaw( repository, hDecisionsAtIssue );
			setFCDRWithRatingEffectiveDateIfFCDRisEarilier( repository );
		}
		finally {
			
			eventDateCombinedPercentMap = null;
		}
	}

	
    public void populateEffectiveDateCombinedPercentMap( final DecisionsAtIssueServiceReturnVO returnVO, RbpsRepository repository) {
    	
    	List<CombinedPercentVO>	combinedPercentVOList = returnVO
    													.getCombinedPercentList()
    													.getCombinedPercentForThisProfile();
    	
    	for ( CombinedPercentVO combinedPercent : combinedPercentVOList ) {
    		
    		XMLGregorianCalendar effectiveDate 	= combinedPercent.getEffectiveDate();
    		Date				 effectiveDt	= SimpleDateUtils.xmlGregorianCalendarToDay( effectiveDate );
    		int 				 scPercent		= combinedPercent.getServiceConnCombinedPct();

    		eventDateCombinedPercentMap.put( effectiveDt, new Integer( scPercent ) );

    	}
    }
	
	
	private void evaluateDisability(final RbpsRepository repository, HDecisionsAtIssueVO hDecisionsAtIssue ) {
	
		
		HDisabilityList	hDisabilityList	= hDecisionsAtIssue.getHDisabilityList();
		if ( null == hDisabilityList ) {
			return;
		}

		
		List<HDisabilityVO>	hDisabilityVOList	= hDisabilityList.getHDisabilityVO();
		if ( CollectionUtils.isEmpty( hDisabilityVOList ) ){
			return;
		}
	
		
		for ( HDisabilityVO hDisabilityVO	: hDisabilityVOList ) {
			
			if ( hDisabilityVO.getDecisionType() == null ) {
				continue;
			}
			
			if ( hDisabilityVO.getDecisionType().equals( "SVCCONNCTED" ) || hDisabilityVO.getDecisionType().equals( "1151GRANTED" ) ) {

				
				HDisabilityEvaluationList		hDisabilityEvaluationList		= hDisabilityVO.getHDisabilityEvaluationList();
				if ( null == hDisabilityEvaluationList ) {
					continue;
					
				}
				
				List<HDisabilityEvaluationVO>	hDisabilityEvaluationVOList		= hDisabilityEvaluationList.getHDisabilityEvaluationVO();
				if ( CollectionUtils.isEmpty( hDisabilityEvaluationVOList ) ){
					continue;
				}
				
				for ( HDisabilityEvaluationVO	hDisabilityEvaluationVO	:	hDisabilityEvaluationVOList ) {
						checkIfExceptionShouldBeThrownForRatingDateOverRide( hDisabilityEvaluationVO, repository );
						setFCDRForEvaluateDisability( repository, hDisabilityEvaluationVO );
				}
				
			}
		}
	}


	private void setFCDRForEvaluateDisability( final RbpsRepository repository, HDisabilityEvaluationVO hDisabilityEvaluationVO) {
	
		
		HDecisionAnalysisVO	hDecisionAnalysisVO	= hDisabilityEvaluationVO.getHDecisionAnalysis();
		
		
		if( hDecisionAnalysisVO.getIncreased().equalsIgnoreCase("Y") ) {
			
				Date	effectiveDate	= SimpleDateUtils.xmlGregorianCalendarToDay( hDisabilityEvaluationVO.getEffectiveDate() );
				
				if ( ! ifEffectiveDateHasMoreThan30Percent( effectiveDate ) ) {
					return;
				}
				
				if ( null == repository.getVeteran().getFirstChangedDateofRating() ) {
					repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
					logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
				}

				
				if ( effectiveDate.before( repository.getVeteran().getFirstChangedDateofRating() ) ) {
					repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
					logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
				}
		}
	}


	private void checkIfExceptionShouldBeThrownForRatingDateOverRide( HDisabilityEvaluationVO hDisabilityEvaluationVO, final RbpsRepository repository ) {
		
		if ( hDisabilityEvaluationVO.getEffectiveDateOverrideInd().equalsIgnoreCase( "Y" ) ) {
			
				String message = String.format( " Throwing Exception for Rating Over Ride. Override Type >%s<", 
																		hDisabilityEvaluationVO.getEffectiveDateOverrideType() );
				logUtils.log( message, repository );
				
				throwExceptionForRatingDateOverRide( GenericUserInformationValidatorImpl.FAIL + message );
		}
		
	}


	private void evaluateIndividualUnemployability(  RbpsRepository repository, HDecisionsAtIssueVO hDecisionsAtIssue ) {
		
	
		HIndividualUnemployabilityList hIndividualUnemployabilityList = hDecisionsAtIssue.getHIndividualUnemployabilityList();
		if ( null == hIndividualUnemployabilityList ) {
			logUtils.log( "No IndividualUnemployabilityList list received", repository );
			return;
		}
	
		
		List<HIndividualUnemployabilityVO>	hIndividualUnemployabilityVOList = hIndividualUnemployabilityList.getHIndividualUnemployabilityVO();
		if ( CollectionUtils.isEmpty( hIndividualUnemployabilityVOList ) ){
			logUtils.log( "IndividualUnemployabilityVOList is empty", repository );
			return;
		}
		
		
		for ( HIndividualUnemployabilityVO hIndividualUnemployability	: hIndividualUnemployabilityVOList ) {
			setFCDRForIndividualUnemployability(repository, hIndividualUnemployability );
		}
		
	}


	private void setFCDRForIndividualUnemployability( RbpsRepository repository, HIndividualUnemployabilityVO hIndividualUnemployability ) {
		
		
		HDecisionAnalysisVO	hDecisionAnalysisVO	= hIndividualUnemployability.getHDecisionAnalysis();

		if ( null == hDecisionAnalysisVO ) {
			logUtils.log( "hDecisionAnalysisVO is null", repository );
			return;
		}
		
		if( hDecisionAnalysisVO.getIncreased().equalsIgnoreCase("Y") ) {
					
				Date	effectiveDate	= SimpleDateUtils.xmlGregorianCalendarToDay( hIndividualUnemployability.getEffectiveDate() );
			
				if ( ! ifEffectiveDateHasMoreThan30Percent( effectiveDate ) ) {
					return;
				}
				
				if ( null == repository.getVeteran().getFirstChangedDateofRating() ) {
					repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
					logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
				}
				
				if ( effectiveDate.before( repository.getVeteran().getFirstChangedDateofRating() ) ) {
					repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
					logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
				}
		}
	}

	

	private void evaluateHSpecialMoComp(  RbpsRepository repository, HDecisionsAtIssueVO hDecisionsAtIssue ) {
		
		HSpecialMoCompList 		hSpecialMoCompList 		= hDecisionsAtIssue.getHSpecialMoCompList();
		if ( null == hSpecialMoCompList ) {
			logUtils.log( "No SpecialMoCompList list received", repository );
			return;
		}

		
		List<HSpecialMoCompVO> 	hSpecialMoCompVOList	= hSpecialMoCompList.getHSpecialMoCompVO();
		if ( CollectionUtils.isEmpty( hSpecialMoCompVOList ) ){
			logUtils.log( "SpecialMoCompVOList is empty", repository );
			return;
		}
		
		
		for ( HSpecialMoCompVO specialMoCompVO	: hSpecialMoCompVOList ) {
				setFCDRForHSpecialMoComp(repository, specialMoCompVO );
		}
	}


	private void setFCDRForHSpecialMoComp( RbpsRepository repository, HSpecialMoCompVO specialMoCompVO ) {
	
		HDecisionAnalysisVO	hDecisionAnalysisVO	= specialMoCompVO.getHDecisionAnalysis();
		
		if( hDecisionAnalysisVO.getIncreased().equalsIgnoreCase("Y") ) {
			
			Date	effectiveDate	= SimpleDateUtils.xmlGregorianCalendarToDay( specialMoCompVO.getEffectiveDate() );
			
			if ( ! ifEffectiveDateHasMoreThan30Percent( effectiveDate ) ) {
				return;
			}
			
			if ( null == repository.getVeteran().getFirstChangedDateofRating() ) {
				repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
				logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
			}				
			
			
			if ( effectiveDate.before( repository.getVeteran().getFirstChangedDateofRating() ) ) {
				repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
				logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
			}
		}
	}	
	

	private void evaluateSpecialProvisionLaw( RbpsRepository repository,  HDecisionsAtIssueVO hDecisionsAtIssue) {
	
		
		HSpecialProvisionLawList 		hSpecialProvisionLawList 	= hDecisionsAtIssue.getHSpecialProvisionLawList();
		if ( null == hSpecialProvisionLawList ) {
			logUtils.log( "No SpecialProvisionLawList list received", repository );
			return;
		}

		
		List<HSpecialProvisionLawVO> 	hSpecialProvisionLawVOList	= hSpecialProvisionLawList.getHSpecialProvisionLawVO();
		if ( CollectionUtils.isEmpty( hSpecialProvisionLawVOList ) ){
			logUtils.log( "SpecialProvisionLawVOList is empty", repository );
			return;
		}
		
		throw new RbpsRuntimeException( "Special Provision Rating Decision on Award, Please review" );
		
//		for ( HSpecialProvisionLawVO hSpecialProvisionLawVO	: hSpecialProvisionLawVOList ) {
//			setFCDRForSpecialProvisionLaw(repository, hSpecialProvisionLawVO );
//		}
	}



	private void setFCDRForSpecialProvisionLaw( RbpsRepository repository,HSpecialProvisionLawVO hSpecialProvisionLawVO ) {
	
		
		HDecisionAnalysisVO	hDecisionAnalysisVO	=  hSpecialProvisionLawVO.getHDecisionAnalysis();
		
 		if( hDecisionAnalysisVO.getIncreased().equalsIgnoreCase("Y") ) {
		
			Date	effectiveDate	= SimpleDateUtils.xmlGregorianCalendarToDay( hSpecialProvisionLawVO.getEffectiveDate() );
			
			if ( ! ifEffectiveDateHasMoreThan30Percent( effectiveDate ) ) {
				return;
			}
			
			if ( null == repository.getVeteran().getFirstChangedDateofRating() ) {
				repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
				logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
			}
			
			if ( effectiveDate.before( repository.getVeteran().getFirstChangedDateofRating() ) ) {
					repository.getVeteran().setFirstChangedDateofRating( effectiveDate );
					logUtils.log( String.format( "Assigning FCDR %s", effectiveDate ), repository );
			}
		}
 		
	}	

	
	@SuppressWarnings("unchecked")
	private boolean ifEffectiveDateHasMoreThan30Percent( Date effectiveDate ) {
		
		List<Integer> combinedPercentList = (List<Integer>) eventDateCombinedPercentMap.get(effectiveDate);
		boolean flag = false;
		
        if ( combinedPercentList == null ) {

        	combinedPercentList = new ArrayList<Integer>();
        }
        
        for ( Integer percent	:	combinedPercentList ) {
        	
        	if ( percent.intValue() >= 30 ) {
        		
        		flag = true;
        		break;
        	}
        }
        
		return flag;
	}


	private void setFCDRWithRatingEffectiveDateIfFCDRisEarilier( RbpsRepository repository ) {
		
		Date firstChangedDateofRating	= repository.getVeteran().getFirstChangedDateofRating();
		Date ratingEffectiveDate		= repository.getVeteran().getRatingEffectiveDate();	 
		
		if ( null == firstChangedDateofRating ) {
			return;
		}
		if ( null == ratingEffectiveDate ) {
			return;
		}		
		
		if ( firstChangedDateofRating.before( ratingEffectiveDate ) ) {
			repository.getVeteran().setFirstChangedDateofRating(ratingEffectiveDate );
			logUtils.log( String.format( "FCDR %s is set with Rating Effective Date %s, since FCDR is earlier than Rating Effective Date. New FCDR is %s ",
																																					firstChangedDateofRating,																												  
																																					ratingEffectiveDate, 
																																					firstChangedDateofRating ), repository );
		}
	}
	
	private void throwExceptionForRatingDateOverRide( String message ) {
		
		throw new RbpsRuntimeException( message );
		
	}
}
