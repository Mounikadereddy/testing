package gov.va.vba.rbps.services.ws.client.handler.share;


import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.ws.client.core.WebServiceTemplate;

import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsWebServiceClientException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.DecisionAtIssueInputVO;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.GetDecisionsAtIssue;
import gov.va.vba.rbps.services.ws.client.mapping.share.ratingComparison.GetDecisionsAtIssueResponse;
import gov.va.vba.rbps.services.ws.client.util.HeaderSetter;




public class GetDecisionsAtIssueWSHandler {

	

    private static Logger logger = Logger.getLogger(RatingComparisonWSHandler.class);

    private final LogUtils              logUtils    = new LogUtils( logger, true );
    private WebServiceTemplate          webServiceTemplate;
    private String                      decisionsAtIssueUri;
	
	
    public GetDecisionsAtIssueResponse getDecisionsAtIssue( XMLGregorianCalendar profileDate, final RbpsRepository repository ) throws RbpsWebServiceClientException {

        logUtils.debugEnter( repository );

        GetDecisionsAtIssue request = buildDecisionsAtIssueRequest( repository, profileDate );
 
        Object response;
        try {
            response = webServiceTemplate.marshalSendAndReceive( decisionsAtIssueUri,
                                                                 request,
                                                                 new HeaderSetter( decisionsAtIssueUri, 
                                                                		 repository.getClaimStationAddress() ) );
        }
        catch (Throwable rootCause) {

            String detailMessage = "Call to WS RatingComparisonEJBService.decisionsAtIssue failed";

            repository.addValidationMessage( CommonUtils.getValidationMessage( decisionsAtIssueUri,
                                                                               request.getClass().getSimpleName(),
                                                                               rootCause.getMessage() ) );
            throw new RbpsWebServiceClientException(detailMessage, rootCause);
        }
        finally {

            logUtils.debugExit( repository );
        }

        return (GetDecisionsAtIssueResponse) response;
    }
    
    
    
    private GetDecisionsAtIssue buildDecisionsAtIssueRequest( final RbpsRepository repository, final XMLGregorianCalendar profileDate ) {
    	
    	GetDecisionsAtIssue 	atIssue		= new GetDecisionsAtIssue();
    	DecisionAtIssueInputVO	inputVO 	= new DecisionAtIssueInputVO();
    	inputVO.setProfileDate( profileDate );
    	inputVO.setVeteranParticipantId( repository.getVeteran().getCorpParticipantId() );
    	
    	atIssue.setDecisionAtIssue( inputVO );
    	return atIssue;
    }
    

    public void setDecisionsAtIssueUri( final String decisionsAtIssueUri ) {
    	String clusterAddr = CommonUtils.getClusterAddress();
        this.decisionsAtIssueUri = clusterAddr + "/" + decisionsAtIssueUri;
    }
    
    public void setWebServiceTemplate( final WebServiceTemplate webServiceTemplate ) {
        this.webServiceTemplate = webServiceTemplate;
    }
}
