package gov.va.vba.rbps.lettergeneration;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import gov.va.vba.framework.logging.Logger;
import gov.va.vba.rbps.coreframework.dto.RbpsRepository;
import gov.va.vba.rbps.coreframework.exception.RbpsRuntimeException;
import gov.va.vba.rbps.coreframework.util.CommonUtils;

import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;



public class LetterFieldsMarshaller {
	
	 private static Logger 					logger 		= Logger.getLogger(LetterFieldsMarshaller.class);
	 //private 		CommonUtils          	utils;
	 private 	    ByteArrayOutputStream 	baoStream 	= new ByteArrayOutputStream();
	
	
	public ByteArrayOutputStream marshalLettterFields( LetterFieldsMarshal letterFieldsMarshal, RbpsRepository repository ) {
		
		try {
			
			XStream xStream 			= 	new XStream(new DomDriver("UTF-8"));
			
            String  xmlString			=	xStream.toXML( letterFieldsMarshal );
            String cleanedXML 			= 	cleanXmlString(xmlString);
            CommonUtils.log( logger, String.format( "LetterFieldsMarshaller cleanedXML for ProcId: %s\n", repository.getVnpProcId() ) + CommonUtils.stringBuilder( cleanedXML )  );
            
            byte[] bArray				= 	cleanedXML.getBytes();
            
            logger.debug("***LetterFieldsMarshaller **** after generating cleanedXML byte array");
            
            baoStream.write(bArray);
            
            logger.debug("***LetterFieldsMarshaller **** after writing cleanedXML byte array");
            
//			uncomment to write the xml             
//            writeXMLToFile(cleanedXML);

		} catch ( Exception ex ) {
		
			logger.debug("***LetterFieldsMarshaller **** exception at writing cleanedXML byte array " + ex.getMessage() );
			CommonUtils.log( logger, ex.getMessage() );
			throw new RbpsRuntimeException( "Error marshalling LetterFields." );
		} 
		
		return baoStream;
	}




	private void writeXMLToFile(String cleanedXML) throws IOException {
		
		StringBuffer stringBuffer	= 	new StringBuffer();
		stringBuffer.append( cleanedXML );
		//Fortify Unreleased Resource: Stream fix
		try (BufferedWriter out = new BufferedWriter(new FileWriter("letterfields.xml"))) {
			String outText = stringBuffer.toString();
			out.write(outText);
			out.close();
		}
	}

	
	
	
	private String cleanXmlString(String xmlString) {
		

		
//		String 	rootElement					= 	"<gov.va.vba.rbps.lettergeneration.LetterFieldsMarshal>";
//		int 	rootIndex 					= 	xmlString.indexOf( rootElement );
//		String  rootCleanedXMLString		= 	xmlString.substring( rootIndex );
		
		String 	rootElemetToClean			=	"gov.va.vba.rbps.lettergeneration.LetterFieldsMarshal";
		String 	correctRootElement			= 	"LetterFields";
		String 	cleanedRootElementXMLString	= 	xmlString.replaceAll( rootElemetToClean, correctRootElement );
		
		String  approvalString				=	"<approvals class=\"list\"";
		String  correctApprovalString		= 	"<approvals";
		String cleanedApprovalXML			= 	cleanedRootElementXMLString.replaceAll( approvalString , correctApprovalString );
		
		String  denialString				=	"<denials class=\"list\"";
		String  correctDenialString			= 	"<denials";
		String  cleanedDenialXML			= 	cleanedApprovalXML.replaceAll( denialString , correctDenialString );
		
		String 	awardSummaryWithPath 		= 	"gov.va.vba.rbps.lettergeneration.AwardSummaryMarshal";
		String 	awardSummary	   			= 	"AwardSummary";
		String 	cleanedXML 					= 	cleanedDenialXML.replaceAll( awardSummaryWithPath, awardSummary );
		
		return cleanedXML;
	}
	

/*
    public void setCommonUtils( final CommonUtils utils ) {

        this.utils = utils;
    }
	*/
}
