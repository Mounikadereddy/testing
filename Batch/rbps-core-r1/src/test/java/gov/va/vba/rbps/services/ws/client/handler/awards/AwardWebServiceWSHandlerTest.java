package gov.va.vba.rbps.services.ws.client.handler.awards;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.lettergeneration.GeneratePdf;
import gov.va.vba.rbps.services.populators.AwardWebServicePopulator;
import gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService.FindStationOfJurisdictionResponse;

import gov.va.vba.rbps.services.ws.client.util.TestUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;


public class AwardWebServiceWSHandlerTest extends RbpsAbstractTest {

    private RbpsRepository repository;
    private AwardWebServiceWSHandler awardWebServiceWSHandler;
    private TestUtils testUtils       = new TestUtils();

    @Override
    @Before
    public void setup() {
        super.setup();
        repository = new RbpsRepository();
        Veteran veteran = new Veteran();
        veteran.setCorpParticipantId(32423542L);
        repository.setVeteran(veteran);
        awardWebServiceWSHandler = (AwardWebServiceWSHandler) getBean( "awardWebServiceWSHandler" );
    }


    @Test
    public void testFindStationOfJurisdication() {
        FindStationOfJurisdictionResponse response =  testUtils.getSOJResponseFromXml( "gov/va/vba/rbps/services/populators/findStationOfJurisdictionResponse.xml" );

        AwardWebServicePopulator populator = new AwardWebServicePopulator();
        populator.populateStationOfJurisdiction(response, repository);

        assertNotNull(repository.getSjnId());
    }
}


