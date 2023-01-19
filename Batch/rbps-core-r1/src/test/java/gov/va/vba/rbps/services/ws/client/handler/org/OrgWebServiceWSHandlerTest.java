package gov.va.vba.rbps.services.ws.client.handler.org;

import gov.va.vba.rbps.RbpsAbstractTest;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.xom.Veteran;
import gov.va.vba.rbps.services.populators.PoaPopulator;
import gov.va.vba.rbps.services.ws.client.mapping.org.FindPOAsByFileNumbersResponse;
import gov.va.vba.rbps.services.ws.client.util.TestUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class OrgWebServiceWSHandlerTest extends RbpsAbstractTest {


    private RbpsRepository repository;

    private OrgWebServiceWSHandler orgWebServiceWSHandler;

    private PoaPopulator populator = new PoaPopulator();

    private TestUtils testUtils = new TestUtils();

    @Override
    @Before
    public void setup() {
        super.setup();
        repository = new RbpsRepository();
        Veteran veteran = new Veteran();
        veteran.setFileNumber("753159784");
        repository.setVeteran(veteran);
        orgWebServiceWSHandler = (OrgWebServiceWSHandler) getBean("orgWebServiceWSHandler");
    }

    @Test
    public void findPOAsByFileNumber() {
        FindPOAsByFileNumbersResponse response = testUtils.getPoaResponseFromXml("gov/va/vba/rbps/services/populators/findPOAsByFileNumbersResponse.xml");
        populator.populatePoa(repository, response);
        assertTrue(repository.getVeteran().hasPOA());
        assertNotNull(repository.getPoaOrganizationName());
    }
}
