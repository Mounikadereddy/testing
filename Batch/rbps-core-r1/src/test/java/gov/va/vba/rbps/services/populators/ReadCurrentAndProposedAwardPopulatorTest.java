package gov.va.vba.rbps.services.populators;



import org.junit.Before;
import org.junit.Test;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.claimprocessor.impl.EP130ClaimPostProcessor;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.lettergeneration.GeneratePdf;
import gov.va.vba.rbps.services.ws.client.handler.awards.ReadCurrentAndProposedAwardWSHandler;
import gov.va.vba.rbps.services.ws.client.handler.share.ClearBenefitClaimWSHandler;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;




public class ReadCurrentAndProposedAwardPopulatorTest    extends RbpsAbstractTest {
	
    private ReadCurrentAndProposedAwardWSHandler 	readCurrentAndProposedAwardWSHandler;
    private ClearBenefitClaimWSHandler				clearBenefitClaimWSHandler;
    
    //EK not spring beans anymore so test must change FUTURE
    private GeneratePdf								pdf;
    private RbpsRepository              			repository;
    private TestUtils       						testUtils   = new TestUtils();

    @Override
    @Before
    public void setup() {

        super.setup();
        repository = ( RbpsRepository ) getBean ( "repository" );
        readCurrentAndProposedAwardWSHandler 	= (ReadCurrentAndProposedAwardWSHandler) getBean( "readCurrentAndProposedAwardWSHandler" );
        clearBenefitClaimWSHandler				= (ClearBenefitClaimWSHandler) getBean( "clearBenefitClaimWSHandler" );
        pdf										= ( GeneratePdf ) getBean("generatePdf");
    }
    
    
    @Test
    public void shouldReadCurrentAndProposedAwardPopulateForVeteranLessThan30() {

//        testUtils.populateUserInfo( repository, "gov/va/vba/rbps/lettergeneration/schoolBeginDate.userInfo.response.xml" );

    	repository.setClaimStationLocationId( 1943L );
        testUtils.populateSojAddress( repository, 1943L );
        repository.getVeteran().setServiceConnectedDisabilityRating( 20.0 );
        
        ReadCurrentAndProposedAwardResponse 	response    = readCurrentAndProposedAwardWSHandler.call( repository );
		ReadCurrentAndProposedAwardPopulator	populator 	= new ReadCurrentAndProposedAwardPopulator();
		populator.processReadCurrentAndProposedAwardResponse( repository, response );
		
		pdf.generateLetterAndCsv( repository, null );

    }

}
