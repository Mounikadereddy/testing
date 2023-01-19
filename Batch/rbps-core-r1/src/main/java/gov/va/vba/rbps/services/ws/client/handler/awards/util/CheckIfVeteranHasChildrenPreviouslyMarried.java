package gov.va.vba.rbps.services.ws.client.handler.awards.util;

import java.util.Date;
import java.util.List;

import org.springframework.util.CollectionUtils;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.SimpleDateUtils;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.services.populators.FindDependencyDecisionByAwardPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.DependencyDecisionVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.dependencydecision.FindDependencyDecisionByAwardResponse;






public class CheckIfVeteranHasChildrenPreviouslyMarried {
	
	
	private static Logger logger = Logger.getLogger( CheckIfVeteranHasChildrenPreviouslyMarried.class );
	
	private CommonUtils 							utils;
    private LogUtils            					logUtils        	= new LogUtils( logger, true );
	private FindDependencyDecisionByAwardPopulator	populator			= new FindDependencyDecisionByAwardPopulator();  

	
	
	public void checkIfChildrenPreviouslyMarried( RbpsRepository repository, FindDependencyDecisionByAwardResponse response ) {
		
		List<Child>	children	= repository.getVeteran().getChildren();
		
		if ( CollectionUtils.isEmpty( children ) ) {
			
			return;
		}
		
		for ( Child child	:	children ) {
			
			checkIfChildPreviouslyMarried( response, child, repository );
		}
        
	}

	private void checkIfChildPreviouslyMarried( FindDependencyDecisionByAwardResponse response, Child child, RbpsRepository repository ) { 

		List<DependencyDecisionVO>	decisionList	=	populator.getDependencyDecisionListByParticipantId( response, child.getCorpParticipantId(), repository );
		
		iterateThruDecisionsToCheckChildPreviouslyMarried( decisionList, child, repository );
	}
	
	private void iterateThruDecisionsToCheckChildPreviouslyMarried( List<DependencyDecisionVO>	decisionList, Child child, RbpsRepository repository ) {
		
        if ( CollectionUtils.isEmpty( decisionList ) ) {

            logUtils.log( "Empty dependencyDecisionList for Spouse, hence returning", repository );
            return;
        }
        
		for ( DependencyDecisionVO decision : decisionList ) {
			
			if ( decision.getDependencyStatusType().equalsIgnoreCase( "NAWDDEP" ) && 
					decision.getDependencyDecisionType().equalsIgnoreCase("MARR") )  {
				
				child.setPreviouslyMarried( true );
			}
		}
	}
	
    
    public void setCommonUtils( final CommonUtils utils ) {

        this.utils = utils;
    }
}
