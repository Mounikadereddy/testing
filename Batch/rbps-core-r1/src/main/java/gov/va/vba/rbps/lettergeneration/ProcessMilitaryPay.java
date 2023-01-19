package gov.va.vba.rbps.lettergeneration;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.util.CollectionUtils;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.claimprocessor.impl.EP130ClaimPostProcessor;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.NullSafeGetter;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.lettergeneration.util.EffectiveDateNetAmount;
import gov.va.vba.rbps.services.ws.client.handler.awards.ProcessMilitaryPayWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.ReadRetiredPayDecnResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.RetiredPayDecnVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.AwardLineSummaryVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.amend.ProcessAwardDependentResponse;




public class ProcessMilitaryPay {
	
	private static Logger logger = Logger.getLogger(ProcessMilitaryPay.class);
	
	
	 //private CommonUtils           		utils;
	private	ProcessMilitaryPayWSHandler	processMilitaryPayWSHandler;
//	private	SimpleDateUtils				dateUtils					= new SimpleDateUtils();
	
	
	
	public void processMilitaryPayInformation( RbpsRepository repository, ProcessAwardDependentResponse	dependentResponse ) {
		
		//processMilitaryPayWSHandler.setCommonUtils(utils);
		ReadRetiredPayDecnResponse	retiredPayDecnResponse	= processMilitaryPayWSHandler.call( repository );
		
		if ( isEmptyRetiredPayDecnResponse(retiredPayDecnResponse) ) {
			
			repository.getVeteran().setHasMilitaryPay( false );
			return;
		}

		Date maxToDate		=	getMaxToDateFromRetiredPayDecnList(retiredPayDecnResponse);
		logger.debug("maxToDate" + maxToDate);
		
		String partOrAll	= 	getPartOrAll( dependentResponse, maxToDate );
		logger.debug("partOrAll" + partOrAll);
		repository.setPartOrAll( partOrAll );
		repository.setToDate( maxToDate );
		repository.getVeteran().setHasMilitaryPay( true );
	}
	/*
  public void processMilitaryPayInformationFromJunit( RbpsRepository repository, ProcessAwardDependentResponse	dependentResponse ,
		ReadRetiredPayDecnResponse	retiredPayDecnResponse) {
		
		//processMilitaryPayWSHandler.setCommonUtils(utils);
		//ReadRetiredPayDecnResponse	retiredPayDecnResponse	= processMilitaryPayWSHandler.call( repository );
		
		if ( isEmptyRetiredPayDecnResponse(retiredPayDecnResponse) ) {
			
			repository.getVeteran().setHasMilitaryPay( false );
			return;
		}

		Date maxToDate		=	getMaxToDateFromRetiredPayDecnList(retiredPayDecnResponse);
		logger.debug("maxToDate" + maxToDate);
		
		String partOrAll	= 	getPartOrAll( dependentResponse, maxToDate );
		logger.debug("partOrAll" + partOrAll);
		repository.setPartOrAll( partOrAll );
		repository.setToDate( maxToDate );
		repository.getVeteran().setHasMilitaryPay( true );
	}
  */
	public static final String  getPartOrAll( ProcessAwardDependentResponse dependentResponse, Date maxToDate ) {
		
		String partOrAll	= 	"all or part";
		/*CMDMRP-3184
		 * "commenting out the code and return all or parts for now 
		 * as client is working on the requirement for this "
		 * 
		  boolean allZero		= false;
		
		List<EffectiveDateNetAmount>	listOfEffectiveDateNetAmount	=	getListOfEffectiveDateNetAmount( dependentResponse );
		
		for ( EffectiveDateNetAmount dateAmt	:	listOfEffectiveDateNetAmount ) {
			System.out.println("maxToDate"+maxToDate);
			System.out.println("dateAmt.getEffectiveDate"+dateAmt.getEffectiveDate());
			System.out.println("dateAmt.getNetAmount()"+dateAmt.getNetAmount());
			
			//if ( dateAmt.getEffectiveDate().before( maxToDate ) ) {
			if (dateAmt.getEffectiveDate().compareTo(maxToDate)<=0 ) {
				if ( dateAmt.getNetAmount() > 0  ) {
	
					partOrAll 	= "part";
					allZero 	= false;
					break;
				}
			
				if (  dateAmt.getNetAmount() == 0  ) {
				
					allZero 	= true;
				}
			}
		}
		
		if ( allZero ) {
			
			partOrAll = "all";
		}
		*/
		return partOrAll;	
	}


	public static final Date getMaxToDateFromRetiredPayDecnList( ReadRetiredPayDecnResponse 	retiredPayDecnResponse) {
		
		
		List<RetiredPayDecnVO>	retiredPayDecnList = retiredPayDecnResponse.getRetiredDecnResponse().getRetiredPayDecns().getRetiredPayDecn();
		
		Date	maxToDate	= SimpleDateUtils.addYearsToDate(-200, new Date() );
		
		
		
		for ( RetiredPayDecnVO	retiredPayDecn	:	retiredPayDecnList ) {
			
			if ( SimpleDateUtils.xmlGregorianCalendarToDay( retiredPayDecn.getToDate() ).after( maxToDate ) ) {
				
				maxToDate = SimpleDateUtils.xmlGregorianCalendarToDay( retiredPayDecn.getToDate() );
			}
		}
		
		return maxToDate;
	}

	
	

	private static final boolean isEmptyRetiredPayDecnResponse( ReadRetiredPayDecnResponse retiredPayDecnResponse) {
		
		boolean returnValue = false;
		
		if ( retiredPayDecnResponse == null ) {

			returnValue = true;
			return returnValue;
		}
		
		if ( retiredPayDecnResponse.getRetiredDecnResponse() == null ) {

			returnValue = true;
			return returnValue;
		}
		
		if ( retiredPayDecnResponse.getRetiredDecnResponse().getRetiredPayDecns() == null ) {

			returnValue = true;
			return returnValue;
		}
		
		if ( CollectionUtils.isEmpty( retiredPayDecnResponse.getRetiredDecnResponse().getRetiredPayDecns().getRetiredPayDecn() )  ) {

			returnValue = true;
			return returnValue;
		}
		
		return returnValue;
	}

	
	
	private static final List<EffectiveDateNetAmount> getListOfEffectiveDateNetAmount( ProcessAwardDependentResponse	dependentResponse ) {
		
		XMLGregorianCalendar		firstChangedDate	= 	dependentResponse.getReturn().getAwardSummary().getAwardEventSummary().getFirstChangedDate();
		Date						firstChangedDt		= 	SimpleDateUtils.xmlGregorianCalendarToDay( firstChangedDate );
		List<AwardLineSummaryVO>    lineSummaries       = 	getLineSummaries( dependentResponse );
		
		return getLineSummariesWithEffectiveDateGreaterThanOrEqualToFirstChangedDate( lineSummaries, firstChangedDt );
	}
	
	

    @SuppressWarnings( "unchecked" )
    private static final List<AwardLineSummaryVO> getLineSummaries( final ProcessAwardDependentResponse awardResponse ) {

//        NullSafeGetter  grabber = new NullSafeGetter();
        Object          summary = NullSafeGetter.getAttribute( awardResponse, "return.awardSummary.awardEventSummary.awardLineSummary" );

        if ( summary == null ) {

            summary = new ArrayList<AwardLineSummaryVO>();
        }

        return (List<AwardLineSummaryVO>) summary;
    }
    
    
    
    private static final List<EffectiveDateNetAmount> getLineSummariesWithEffectiveDateGreaterThanOrEqualToFirstChangedDate( List<AwardLineSummaryVO> lineSummaries, 
    																											Date firstChangedDt ) {
    	List<EffectiveDateNetAmount> dateAmountList	= new ArrayList<EffectiveDateNetAmount>();
    	
    	for ( AwardLineSummaryVO lineSummary	:	lineSummaries ) {
    		
    		EffectiveDateNetAmount	dateAmount	=  new EffectiveDateNetAmount();
    		Date effectiveDate =  SimpleDateUtils.xmlGregorianCalendarToDay( lineSummary.getEffectiveDate() );
    		//if ( SimpleDateUtils.xmlGregorianCalendarToDay( lineSummary.getEffectiveDate() ).after( firstChangedDt ) ) {
    		logger.debug("effectiveDate:"+effectiveDate+":firstChangedDt:"+firstChangedDt);
    		//System.out.println("effectiveDate:"+effectiveDate+":firstChangedDt:"+firstChangedDt);
    		//if ( firstChangedDt.before(effectiveDate)) {
    		if ( firstChangedDt.compareTo(effectiveDate)<=0) {
    			logger.debug("effectiveDate"+effectiveDate);
    			logger.debug("lineSummary.getNetAmount().doubleValue()"+lineSummary.getNetAmount().doubleValue());
    			dateAmount.setEffectiveDate( SimpleDateUtils.xmlGregorianCalendarToDay( lineSummary.getEffectiveDate() ) );
    			dateAmount.setNetAmount( lineSummary.getNetAmount().doubleValue() );
    			dateAmountList.add( dateAmount );
    		}
    	}
    	return dateAmountList;
    }
    
    /*
    
	public void setCommonUtils(CommonUtils utils) {
		
		this.utils = utils;
	}
	*/
    

    /**
     * @param ProcessMilitaryPayWSHandler
     */
    public void setProcessMilitaryPayWSHandler( final ProcessMilitaryPayWSHandler processMilitaryPayWSHandler ) {

        this.processMilitaryPayWSHandler = processMilitaryPayWSHandler;
    }
    
    
    
}
