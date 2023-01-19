

package gov.va.vba.rbps.services.ws.client.handler.awards;




import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Child;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.personService.FindPersonsBySsns;
import gov.va.vba.rbps.services.ws.client.mapping.awards.personService.FindPersonsBySsnsResponse;


public class PersonServiceWSHandler extends BaseWSHandler<FindPersonsBySsns, FindPersonsBySsnsResponse>{
	
	
	@Override
    protected FindPersonsBySsns buildRequest( final RbpsRepository	repository ) {
	 
	 return addSsnsToRequest( repository );
	}
	

	private FindPersonsBySsns addSsnsToRequest( final RbpsRepository	repository ) {
			
			
		FindPersonsBySsns	findPersonBySsns	=	new FindPersonsBySsns();
			
		if ( ifSpouseDoesNotHaveCorpId( repository ) ){
				
			findPersonBySsns.getSsn().add( repository.getVeteran().getCurrentMarriage().getMarriedTo().getSsn() );
		}
		
		if ( latestPreviousMarriageSpouseDoesNotHaveCorpId( repository ) ) {
			
			findPersonBySsns.getSsn().add( repository.getVeteran().getLatestPreviousMarriage().getMarriedTo().getSsn() );
		}
			
		for ( Child	child	:	repository.getVeteran().getChildren() ) {
			
			
			if ( child.getCorpParticipantId() != 0 ) {
				
				continue;
			}
			
			findPersonBySsns.getSsn().add( child.getSsn() );
		}
		
		return findPersonBySsns;
	}
		
		
	private boolean ifSpouseDoesNotHaveCorpId( final RbpsRepository	repository ) {
			
		if  ( repository.getVeteran().getCurrentMarriage() == null ) {
				
			return false;
		}
				
		if ( repository.getVeteran().getCurrentMarriage().getMarriedTo() == null ) {
			
			return false;
		}
		
		
		if ( repository.getVeteran().getCurrentMarriage().getMarriedTo().getCorpParticipantId() != 0 ) {
			
			return false;
		}
		
		return true;
	}
	
	
	private boolean latestPreviousMarriageSpouseDoesNotHaveCorpId( final RbpsRepository	repository ) {
		
		if  ( repository.getVeteran().getLatestPreviousMarriage() == null ) {
			
			return false;
		}
				
		if ( repository.getVeteran().getLatestPreviousMarriage().getMarriedTo() == null ) {
			
			return false;
		}
		
		if ( repository.getVeteran().getLatestPreviousMarriage().getMarriedTo().getCorpParticipantId() != 0 ) {
			
			return false;
		}
		return true;
	}
}
