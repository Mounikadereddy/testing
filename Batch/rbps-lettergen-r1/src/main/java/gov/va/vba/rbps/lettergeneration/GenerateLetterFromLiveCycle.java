package gov.va.vba.rbps.lettergeneration;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;


import gov.va.vba.framework.common.AuditContext;
import gov.va.vba.framework.esb.documentmanagement.PdfDocument;
import gov.va.vba.framework.esb.documentmanagement.PdfDocumentType;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.framework.services.CorrespondenceServiceException;
import gov.va.vba.framework.services.CorrespondenceServiceRemoteV2;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.LogUtils;
import gov.va.vba.rbps.lettergeneration.RbpsApplicationDetails;




public class GenerateLetterFromLiveCycle {
	
	 private static Logger 					logger 		= Logger.getLogger(GenerateLetterFromLiveCycle.class);
	 private 		LogUtils        		logUtils    = new LogUtils( logger, true );
	
	public PdfDocument generateLetter( final RbpsRepository 			repository, 
									   final RbpsApplicationDetails 	rbpsApplicationDetails, 
									   final ByteArrayOutputStream 		baoStream, 
									   final String 					pdfFileName,
	    							   final boolean					hasApprovals,
	    							   final boolean					hasDenials,
	    							   final boolean					hasMilitaryPay ) {
		
		PdfDocument	pdfDocument	= null;
		
		try {
			
			logger.debug("***GenerateLetterFromLiveCycle**** sending letter to Adobe server");
//			To test locally uncomment following 5 lines
			
//			Properties properties = new Properties();
//			properties.put( Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
//			properties.put(Context.PROVIDER_URL, "t3://vbawbtappdev2.vba.va.gov:23015" );
//			properties.put(weblogic.jndi.WLContext.RELAX_VERSION_LOOKUP, true); 
//			InitialContext cntxt = new InitialContext(properties);
			
//			To test locally comment out the following line
			InitialContext cntxt = new InitialContext();
			
			Object obj = cntxt.lookup("vba/CorrespondenceServiceV2#" + CorrespondenceServiceRemoteV2.class.getName());
			CorrespondenceServiceRemoteV2 correspService = (CorrespondenceServiceRemoteV2) obj;

				AuditContext auditContext = new AuditContext();			
				String stationId = repository.getClaimStationAddress().getStationId();

				auditContext.setApplicationName( rbpsApplicationDetails.getAppname() );
				auditContext.setClientIPAddress( "0.0.0.0" );
				auditContext.setUserId( rbpsApplicationDetails.getUsername()  );
				auditContext.setStationID( stationId );
			
				pdfDocument =  getPDFDocument( baoStream, pdfFileName, correspService, auditContext, hasApprovals, hasDenials, hasMilitaryPay, repository );
			
			
		} catch (Exception ex) {
			logger.debug("***GenerateLetterFromLiveCycle**** Exception " + ex.getMessage());
			logUtils.log( ex.getMessage(), repository );
			throw new RbpsRuntimeException( "Error generating Rbps pdf letter from LiveCycle template" );
		}
		
		return pdfDocument;
	}


	private PdfDocument getPDFDocument( final ByteArrayOutputStream 		baoStream,
										final String 						pdfFileName,
										final CorrespondenceServiceRemoteV2 correspService,
										final AuditContext 					auditContext, 
										final boolean						hasApprovals,
		    							final boolean						hasDenials,
		    							final boolean 						hasMilitaryPay,
		    							final RbpsRepository 				repository )  throws CorrespondenceServiceException {
		
		PdfDocument	pdfDocument	= null;
		
		if ( hasApprovals && hasDenials && !hasMilitaryPay ) {
			
			logUtils.log( String.format( "Creating Approval Denial Letter %s", pdfFileName ), repository );
			
			pdfDocument =  correspService.generatePDF( PdfDocumentType.RBPSDocument.DEPENDENCY_CLAIM_AWARD_APPROVAL_DENIAL, 
										baoStream.toString(), true, pdfFileName.toString(), true, auditContext, null);
			return pdfDocument;
		}
	
		if ( hasApprovals && !hasMilitaryPay ) {
			
			logUtils.log( String.format( "Creating Approval Letter %s", pdfFileName ), repository );
			
			pdfDocument =  correspService.generatePDF( PdfDocumentType.RBPSDocument.DEPENDENCY_CLAIM_AWARD_APPROVAL, 
										baoStream.toString(), true, pdfFileName.toString(), true, auditContext, null);
			return pdfDocument;
		}

		
		if ( hasApprovals && hasDenials && hasMilitaryPay ) {
			
			logUtils.log( String.format( "Creating Military Approval Denial Letter %s", pdfFileName ), repository );
			
			pdfDocument =  correspService.generatePDF( PdfDocumentType.RBPSDocument.DEPENDENCY_CLAIM_MILITARY_AWARD_APPROVAL_DENIAL, 
										baoStream.toString(), true, pdfFileName.toString(), true, auditContext, null);
			return pdfDocument;
		}
		
		if ( hasApprovals && hasMilitaryPay ) {
			
			logUtils.log( String.format( "Creating Military Approval Letter %s", pdfFileName ), repository );
			
			pdfDocument =  correspService.generatePDF( PdfDocumentType.RBPSDocument.DEPENDENCY_CLAIM_MILITARY_AWARD_APPROVAL, 
										baoStream.toString(), true, pdfFileName.toString(), true, auditContext, null);
			return pdfDocument;
		}

		if ( hasDenials ) {
			logger.debug("***GenerateLetterFromLiveCycle**** Denial letter " + pdfFileName);
			logUtils.log( String.format( "Creating Denial Letter %s", pdfFileName ), repository );
			
			pdfDocument =  correspService.generatePDF( PdfDocumentType.RBPSDocument.DEPENDENCY_CLAIM_AWARD_DENIAL, 
										baoStream.toString(), true, pdfFileName.toString(), true, auditContext, null);
			return pdfDocument;
		}	
		return pdfDocument;
		
	}
	
}