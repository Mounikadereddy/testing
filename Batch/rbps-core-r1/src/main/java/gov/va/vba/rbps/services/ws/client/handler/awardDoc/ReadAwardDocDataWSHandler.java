package gov.va.vba.rbps.services.ws.client.handler.awardDoc;

import org.springframework.ws.client.core.WebServiceTemplate;

import com.google.gson.Gson;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.coreframework.util.RbpsConstants;
import gov.va.vba.rbps.services.ws.client.interfaces.ReadAwardDocDataWSHandlerInterface;
import gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service.AwardDocParamVO;
import gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service.AwardEventVO;
import gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service.ReadAwardDocData;
import gov.va.vba.rbps.services.ws.client.mapping.awardDoc.service.ReadAwardDocDataResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;

public class ReadAwardDocDataWSHandler implements ReadAwardDocDataWSHandlerInterface {

	private static Logger logger = Logger.getLogger(ReadAwardDocDataWSHandler.class);

	private final LogUtils logUtils = new LogUtils(logger, true);

	// Spring beans references/injection
	private WebServiceTemplate webServiceTemplate;
	private String webServiceUri;

	@Override
	public ReadAwardDocDataResponse readAwardDocData(RbpsRepository repository) throws RbpsWebServiceClientException {
		ReadAwardDocData request = buildRequest(repository);
		logUtils.debugEnter(repository);
		
		Object response;

		try {
			Gson gson = new Gson();
			logger.debug("ReadAwardDocDataWSHandler.readAwardDocData webServiceUri = "+webServiceUri);
			logger.debug("Request Data to WS ReadAwardDocData "+gson.toJson(request));
			
			response = webServiceTemplate.marshalSendAndReceive(webServiceUri, request,
					new HeaderSetter(webServiceUri, repository.getClaimStationAddress()));
			
			logger.debug("Call to WS ReadAwardDocData Successful");

		} catch (Throwable rootCause) {
			String detailMessage = "Call to WS ReadAwardDocData failed";
			repository.addValidationMessage(CommonUtils.getValidationMessage(webServiceUri,
					request.getClass().getSimpleName(), rootCause.getMessage()));
			throw new RbpsWebServiceClientException(detailMessage, rootCause);
		} finally {
			logUtils.debugExit(repository);
		}

		return (ReadAwardDocDataResponse) response;
	}

	private ReadAwardDocData buildRequest(final RbpsRepository repo) {
		ReadAwardDocData readAwardDocData = new ReadAwardDocData();
		AwardEventVO awardEventVO = new AwardEventVO();
		AwardDocParamVO awardDocParamVO = new AwardDocParamVO();
		//long vetCorpId = 100000003623L;
		long vetCorpId = repo.getVeteran().getCorpParticipantId();

		awardEventVO.setAwardEventID(repo.getAwardEventID());
		awardEventVO.setAwardEventType(repo.getAwardEventType());
		awardEventVO.setAwardType(RbpsConstants.AWARD_TYPE);
		awardEventVO.setBeneficiaryID(vetCorpId);
		awardEventVO.setVeteranID(vetCorpId);

		awardDocParamVO.setUpdatedMedExpensesIndicator("");

		readAwardDocData.setAwardEvent(awardEventVO);
		readAwardDocData.setAwardDocParam(awardDocParamVO);

		return readAwardDocData;
	}

	public void setWebServiceUri(final String webServiceUri) {
		String clusterAddr = CommonUtils.getClusterAddress();
		this.webServiceUri = clusterAddr + "/" + webServiceUri;
	}

	public void setWebServiceTemplate(final WebServiceTemplate webServiceTemplate) {
		this.webServiceTemplate = webServiceTemplate;
	}
}
