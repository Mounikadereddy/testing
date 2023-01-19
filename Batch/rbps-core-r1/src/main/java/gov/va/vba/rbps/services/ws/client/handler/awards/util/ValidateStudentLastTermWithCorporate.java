package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.coreframework.xom.Education;
import gov.va.vba.rbps.services.populators.FindDependencyDecisionByAwardPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;

import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

public class ValidateStudentLastTermWithCorporate {

	
	
	private static Logger logger = Logger.getLogger( CheckIfVeteranHasChildrenPreviouslyMarried.class );
	
    private LogUtils            					logUtils        	= new LogUtils( logger, true );
	private FindDependencyDecisionByAwardPopulator	populator			= new FindDependencyDecisionByAwardPopulator();  

	
	
	public void checkIfChildrenHaveLastTermInCorporate( final RbpsRepository repository, final FindDependencyDecisionByAwardResponse response ) {
		
		List<Child>	children	= repository.getVeteran().getChildren();
		
		if ( CollectionUtils.isEmpty( children ) ) {
			
			return;
		}
		
		for ( Child child	:	children ) {
			
			if (! hasLastTerm( child ) ){
				continue;
			}
			
			checkIfChildHasLastTermInCorporate( response, child, repository );
		}
        
	}
	
	
	private boolean hasLastTerm( Child child ) {
		
        Education lastTerm = child.getLastTerm();
        
        if ( lastTerm == null ){
        	return false;
        }
        
        if ( lastTerm.getCourseEndDate() == null ) {
        	return false;
        }
        return true;
	}
	
	
	private void checkIfChildHasLastTermInCorporate( FindDependencyDecisionByAwardResponse response, Child child, final RbpsRepository repository ) { 

		List<DependencyDecisionVO>	decisionList	=	populator.getDependencyDecisionListByParticipantId( response, child.getCorpParticipantId(), repository );
		
		iterateThruDecisionsToCheckIfChildHasLastTermInCorporate( decisionList, child, repository );
	}
	
	
	private void iterateThruDecisionsToCheckIfChildHasLastTermInCorporate( List<DependencyDecisionVO>	decisionList, Child child, RbpsRepository repository ) {
		
        if ( CollectionUtils.isEmpty( decisionList ) ) {

            logUtils.log( String.format("Empty dependencyDecisionList for Child %s %s, hence returning", child.getFirstName(), child.getLastName() ), repository );
            child.setLastTerm( null );
            return;
        }
        
        Date endDate		= 	child.getLastTerm().getCourseEndDate();
        Date corporateDate	=	getLatestEndDateOfLastTerm( decisionList );
        
        if ( corporateDate == null ) {
        	
        	logUtils.log( String.format("No Last Term in Corporate for Child %s %s.", child.getFirstName(), child.getLastName() ), repository );
        	//child.setLastTerm( null );
        	return;
        }
        
        //throwExceptionIfMoreThan30DaysDifference( child, endDate, corporateDate );

	}	
	
	
	private Date getLatestEndDateOfLastTerm( List<DependencyDecisionVO>	decisionList ) {
		
		Date latestDate = null;
		
		for ( DependencyDecisionVO decision : decisionList ) {
			
			if ( decision.getDependencyStatusType().equalsIgnoreCase( "NAWDDEP" ) && 
					decision.getDependencyDecisionType().equalsIgnoreCase("SCHATTT") )  {
				
				if ( latestDate == null ){
					latestDate = SimpleDateUtils.xmlGregorianCalendarToDay( decision.getEventDate() );
				}
				
				if ( latestDate.before( SimpleDateUtils.xmlGregorianCalendarToDay( decision.getEventDate() ) ) ) {
					latestDate = SimpleDateUtils.xmlGregorianCalendarToDay( decision.getEventDate() );
				}
			}
		}
		return latestDate;
	}
	
	
	private void throwExceptionIfMoreThan30DaysDifference( Child child, Date endDate, Date corporateDate ) {
		
		long days = SimpleDateUtils.getDifferenceBetweenDatesInDays( endDate, corporateDate );
		
		if ( days > 30 ) {
			throw new RbpsRuntimeException( 
						String.format( "Child %s %s has School termination date discrepancy . Please review.", child.getFirstName(), child.getLastName() ) );
		}
	}
	
}
