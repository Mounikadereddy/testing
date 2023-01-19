package gov.va.vba.rbps.services.ws.client.handler.awards;


import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.interfaces.ReadCurrentAndProposedAwardWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.AwardKeyInputVO;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAward;
import gov.va.vba.rbps.services.ws.client.mapping.awards.service.ReadCurrentAndProposedAwardResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;
import org.springframework.ws.client.core.WebServiceTemplate;


public class ReadCurrentAndProposedAwardWSHandler implements ReadCurrentAndProposedAwardWSHandlerInterface {

    private static Logger logger = Logger.getLogger(ReadCurrentAndProposedAwardWSHandler.class);

    //private CommonUtils       utils;
    private final LogUtils logUtils        = new LogUtils( logger, true );

    // Spring beans references/injection
    private WebServiceTemplate webServiceTemplate;
    private String webServiceUri;

    /**
     * This method calls the SHARE service
     * FamilyTree.findCurrentAndProposedAward
     *
     * @return readCurrentAndProposedAward
     * @throws RbpsWebServiceClientException
     */
    @Override
    public ReadCurrentAndProposedAwardResponse readCurrentAndProposedAward(RbpsRepository repository) throws RbpsWebServiceClientException {

        ReadCurrentAndProposedAward request = buildRequest(repository);

        logUtils.debugEnter( repository);

        // ReadCurrentAndProposedAwardResponse response;

        Object response;

        try {
            response = webServiceTemplate.marshalSendAndReceive(
                webServiceUri,
                request,
                new HeaderSetter( webServiceUri, repository.getClaimStationAddress()));

        } catch (Throwable rootCause) {

            String detailMessage = "Call to WS ReadCurrentAndProposedAward failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage( webServiceUri,
                    request.getClass().getSimpleName(),
                    rootCause.getMessage() ) );
            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        } finally {

            logUtils.debugExit(repository );
        }

        return (ReadCurrentAndProposedAwardResponse) response;
    }

    private ReadCurrentAndProposedAward buildRequest( final RbpsRepository        repo ) {

    	ReadCurrentAndProposedAward   	readCurrentAndProposedAward   	= new ReadCurrentAndProposedAward();
    	AwardKeyInputVO awardKeyInputVO					= new AwardKeyInputVO();
        long                            vetCorpId                       = repo.getVeteran().getCorpParticipantId();

        
        awardKeyInputVO.setAwardType( RbpsConstants.AWARD_TYPE );
        awardKeyInputVO.setBeneficiaryID( vetCorpId );
        awardKeyInputVO.setVeteranID( vetCorpId );
        readCurrentAndProposedAward.setAwardKeyInput( awardKeyInputVO );

        return readCurrentAndProposedAward;
    }

    public void setWebServiceUri( final String webServiceUri ) {

        String clusterAddr = CommonUtils.getClusterAddress();
        this.webServiceUri = clusterAddr + "/" + webServiceUri;
    }

    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {

        this.webServiceTemplate = webServiceTemplate;
    }
}
