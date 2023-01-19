package gov.va.vba.rbps.services.populators;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.AuthorizationEventVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.CurrAndPropAwardResponse.AuthorizationEventList;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.HAwardEventVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.HAwardEventVO.HAwardLineList;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.HAwardLineVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAwardResponse;

import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;


public class ReadCurrentAndProposedAwardPopulator {
	
	private static Logger logger = Logger.getLogger(ReadCurrentAndProposedAwardPopulator.class);
			
	public static final void processReadCurrentAndProposedAwardResponse( RbpsRepository repository, ReadCurrentAndProposedAwardResponse response ) {
		
		
		checkIfAwardIsProposed( response );
		populateCurrentMonthlyAmount( repository, response );
		
	}

	private static final void checkIfAwardIsProposed( ReadCurrentAndProposedAwardResponse response) {
		

		AuthorizationEventList authorizationEventList	= response.getCurrAndPropAwardResponse().getAuthorizationEventList();
		
		if ( null == authorizationEventList) {
			
			throw new RbpsRuntimeException( "RBPS could not verify if Award is proposed because ReadCurrentAndProposedAwardResponse did not return AuthorizationEventList" );
		}
		
		List <AuthorizationEventVO> eventList			= authorizationEventList.getAuthorizationEvent();
		
		// if there is no object with authorizationStatusType = "A", then award is proposed - exception should be thrown.
		boolean isApresent	= false;
		
		for ( AuthorizationEventVO authorizationEvent: eventList ) {
			
			if ( authorizationEvent.getAuthorizationStatusType().equalsIgnoreCase( "A" ) ) {
				
				isApresent = true;
				break;
			}
		}

		if ( !isApresent ) {
			
			throw new RbpsRuntimeException( "Auto Dependency Processing Reject Reason - Changes to the Veterans Last Authorized Award have been made. Please review." );
		}
	}
	
	
	
	public static final void populateCurrentMonthlyAmount( RbpsRepository repository, ReadCurrentAndProposedAwardResponse response ) {
		
		
		List<HAwardEventVO> hAwardEventList	= response.getCurrAndPropAwardResponse().getAwardEventList().getAwardEvent();
		double	currentMonthlyAmount		= 	0;
		Date	toDay						=  	Calendar.getInstance().getTime();
		Date    tempDate					=	SimpleDateUtils.addYearsToDate( -200, toDay );
	
		
		for ( HAwardEventVO awardEventVO	: hAwardEventList ) {
			
			HAwardLineList	hAwardLineList	= awardEventVO.getHAwardLineList();
			
			if ( hAwardLineList == null ){
				continue;
			}
			
			List<HAwardLineVO>	hAwardLineVOList	= awardEventVO.getHAwardLineList().getHAwardLineVO();
		
			for ( HAwardLineVO hAwardLineVO	:	hAwardLineVOList) {
				
				if ( hAwardLineVO.getEffectiveDate() == null ) {
					
					CommonUtils.log(logger,"hAwardLineVO.getEffectiveDate is null");
					continue;
				}
				
				Date 	effectiveDate	=	SimpleDateUtils.xmlGregorianCalendarToDay( hAwardLineVO.getEffectiveDate() );
				
				if ( effectiveDate.before( toDay ) &&  effectiveDate.after( tempDate ) ) {
					
					currentMonthlyAmount	= 	hAwardLineVO.getGrossAmount().doubleValue(); 
					tempDate				= 	effectiveDate;
				}
			}
		}
		
		
		repository.getVeteran().setCurrentMonthlyAmount( currentMonthlyAmount );
	}

	public static final boolean isPensionAward( ReadCurrentAndProposedAwardResponse response) {
		
		List<HAwardEventVO> hAwardEventList	= response.getCurrAndPropAwardResponse().getAwardEventList().getAwardEvent();
		
		Date	toDay						=  	Calendar.getInstance().getTime();

		// there should be only be one HAwardEventVO, the current award, at this point. no proposed award
		List<HAwardLineVO>	curAwardLineList	= hAwardEventList.get(0).getHAwardLineList().getHAwardLineVO();
		
		 
		Collections.sort(curAwardLineList, 
		        	new Comparator<HAwardLineVO>()
		        	{
		        		@Override 	public int compare(HAwardLineVO o1, HAwardLineVO o2) 
		        		{
		        			return o1.getEffectiveDate().compare(o2.getEffectiveDate());
		        		}
		        	});
		 
		 
		Date today = Calendar.getInstance().getTime();
		HAwardLineVO lastAwardLine = null;
			 
		CommonUtils.log(logger,"check if last awardline is pension ");
		for  (HAwardLineVO awardLine:curAwardLineList)
		{
			CommonUtils.log(logger,"awardLine date = " + awardLine.getEffectiveDate() + ", awardLineType = " + awardLine.getAwardLineType());

			Date 	effectiveDate	=	SimpleDateUtils.xmlGregorianCalendarToDay( awardLine.getEffectiveDate() );
			if (effectiveDate.compareTo(today) > 0) {
				break; 
			}
			lastAwardLine = awardLine;
		}
		 	 
		if (lastAwardLine != null && lastAwardLine.getAwardLineType().equals("IP")) {
			CommonUtils.log(logger,"last awardline is Pension ");
			return true;
		}
		
		return false;
	}
}
