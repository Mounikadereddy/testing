package gov.va.vba.rbps.services.ws.client.handler.getPersonProfile;

import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.interfaces.GetPersonProfileWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.GetPersonProfile;
import gov.va.vba.vbmsc.services.ws.client.mapping.awardData.service.GetPersonProfileResponse;

/**
 * @author VBACOShahP
 *
 */
public class GetPersonProfileWSHandler implements GetPersonProfileWSHandlerInterface {
	
	private static Logger logger = Logger.getLogger(GetPersonProfileWSHandler.class);

	private final LogUtils logUtils = new LogUtils(logger, true);

	// Spring beans references/injection
	private WebServiceTemplate webServiceTemplate;
	private String webServiceUri;

	@Override
	public GetPersonProfileResponse getPersonProfile(RbpsRepository repository) throws RbpsWebServiceClientException {
		logger.debug("lsc -GetPersonProfileWSHandler.getPersonProfile Starts "+repository.getVeteran());
		if(repository.getVeteran() != null && repository.getVeteran().getCorpParticipantId() != 0) {
			logger.debug("lsc -GetPersonProfileWSHandler.getPersonProfile inside first if and CorpParticipantId "+repository.getVeteran().getCorpParticipantId());
			GetPersonProfile request = new GetPersonProfile();
			request.setInputParticipantID(repository.getVeteran().getCorpParticipantId()+"");
			//request.setInputParticipantID("355864");
			logUtils.debugEnter(repository);
			
			Object response;

			try {
				logger.debug("GetPersonProfileWSHandler.getPersonProfile webServiceUri = "+webServiceUri);
				logger.debug("Request Data to WS getPersonProfile "+request.getInputParticipantID());
				
				response = webServiceTemplate.marshalSendAndReceive(webServiceUri, request,
						new HeaderSetter(webServiceUri, repository.getClaimStationAddress()));
				
				logger.debug("Call to WS getPersonProfile Successful");

			} catch (Throwable rootCause) {
				logger.debug("Call to WS getPersonProfile Failed");
				logger.debug("RootCause => "+rootCause.getCause()+ " Message "+rootCause.getMessage());
				String detailMessage = "Call to WS getPersonProfile failed";
				repository.addValidationMessage(CommonUtils.getValidationMessage(webServiceUri,
						request.getClass().getSimpleName(), rootCause.getMessage()));
				throw new RbpsWebServiceClientException(detailMessage, rootCause);
			} finally {
				logUtils.debugExit(repository);
			}

			return (GetPersonProfileResponse) response;
		}else {
			return null;
		}
	}

	public void setWebServiceUri(final String webServiceUri) {
		String clusterAddr = CommonUtils.getClusterAddress();
		this.webServiceUri = clusterAddr + "/" + webServiceUri;
	}

	public void setWebServiceTemplate(final WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}
}
