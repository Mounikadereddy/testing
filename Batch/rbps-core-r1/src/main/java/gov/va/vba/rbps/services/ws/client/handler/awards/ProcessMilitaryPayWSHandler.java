package gov.va.vba.rbps.services.ws.client.handler.awards;



import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.handler.BaseWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.ReadRetiredPayDecn;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.ReadRetiredPayDecnResponse;
import gov.va.vba.rbps.services.ws.client.mapping.awards.adjustmentDecision.AwardKeyInputVO;



public class ProcessMilitaryPayWSHandler  extends BaseWSHandler<ReadRetiredPayDecn, ReadRetiredPayDecnResponse> {
	
	
    @Override
    protected ReadRetiredPayDecn buildRequest( final RbpsRepository	repository ) {

    	ReadRetiredPayDecn	readRetiredPayDecn	= new ReadRetiredPayDecn();
    	AwardKeyInputVO		awardKeyInputVO		= new AwardKeyInputVO();
    	
    	awardKeyInputVO.setAwardType( RbpsConstants.AWARD_TYPE );
    	awardKeyInputVO.setBeneficiaryID( repository.getVeteran().getCorpParticipantId() );
    	awardKeyInputVO.setVeteranID( repository.getVeteran().getCorpParticipantId() );
    	
    	readRetiredPayDecn.setAwardKeyInput( awardKeyInputVO );
        return readRetiredPayDecn;
    }
}
