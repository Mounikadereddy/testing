package gov.va.vba.rbps.services.populators;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.services.ws.client.mapping.awards.awardWebService.FindStationOfJurisdictionResponse;

public class AwardWebServicePopulator {

    private static Logger logger = Logger.getLogger(AwardWebServicePopulator.class);

    public static final void populateStationOfJurisdiction(final FindStationOfJurisdictionResponse response, RbpsRepository repo) {

        if (repo == null) {

            logger.debug(AwardWebServicePopulator.class.getName() + ": " + new Exception().getStackTrace()[0].getMethodName() + " => RbpsRepository is null.");
            return;
        }
        if (response == null || response.getReturn() == null ) {

            logger.debug(AwardWebServicePopulator.class.getName() + ": " + new Exception().getStackTrace()[0].getMethodName() + " => FindStationOfJurisdictionResponse is null.");
            return;
        }

        repo.setSjnId(response.getReturn());
    }

}
