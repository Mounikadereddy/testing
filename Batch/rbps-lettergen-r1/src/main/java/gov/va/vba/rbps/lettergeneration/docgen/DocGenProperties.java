package gov.va.vba.rbps.lettergeneration.docgen;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.io.ClassPathResource;

import gov.va.vba.framework.logging.Logger;

import gov.va.vba.rbps.lettergeneration.docgen.genstore.service.RbpsDocGenGenstoreServiceImpl;
@ImportResource("classpath:rbps-core-spring-config.xml")
public class DocGenProperties {
    private final String outputDirectory;
   // private final String url;
   // private final String jwt;
    private final String subjectArea;
    //genstore
    private final String gentstoreUrl;

    private String genstoreSamlTokenPath;
    private final String genstoreUserId;
    private final String genstoreStationId;
    private final String genstoresubjectArea;
    private final String genstoreApplicationName;
    private final String genstoreSharedSecret;
    private final String genstoreContentSource;
	
  //TODO Mounika
    private final Long documentTypeId;
 
	private final Long awardPrintDocumentTypeId;
	
    //TODO END 
    private static Logger logger = Logger.getLogger(DocGenProperties.class);

   /* public DocGenProperties(String url, String outputDirectory, String jwt, String subjectArea) {
        this.url = url;
        this.outputDirectory = outputDirectory;
        this.jwt = jwt;
        this.subjectArea = subjectArea;
    }*/

       //TODO Mounika
    public DocGenProperties(String gentstoreUrl, String outputDirectory,
    		        String subjectArea, String genstoreUserId,
    		       String genstoreStationId,String genstoreApplicationName,
    		       String genstoreSharedSecret,String genstorecontentSource, 
    		       String genstoresubjectArea,Long documentTypeId, 
    		       Long awardPrintDocumentTypeId) {
        this.gentstoreUrl = gentstoreUrl;
        this.outputDirectory = outputDirectory;
       // this.genstoreSamlTokenPath = genstoreSamlToken;
        this.subjectArea = subjectArea;
        this.genstoreUserId = genstoreUserId;
        this.genstoreStationId = genstoreStationId;
        this.genstoreApplicationName = genstoreApplicationName;
        this.genstoreSharedSecret = genstoreSharedSecret;
        this.genstoreContentSource = genstorecontentSource;
        this.genstoresubjectArea = genstoresubjectArea;
        this.documentTypeId = documentTypeId;
        this.awardPrintDocumentTypeId= awardPrintDocumentTypeId;
    }
	  //TODO END
    //TODO Mounika
    public Long getDocumentTypeId() {
		return documentTypeId;
	}


	public Long getAwardPrintDocumentTypeId() {
		return awardPrintDocumentTypeId;
	}
	//TODO END


    public String getOutputDirectory() {
        return outputDirectory;
    }

  

    public String getSubjectArea() {
        return subjectArea;
    }


	public String getGentstoreUrl() {
		return gentstoreUrl;
	}


	public String getGenstoreSamlTokenPath() {
		return genstoreSamlTokenPath;
	}
	/*public void setGenstoreSamlTokenPath(String genstoreSamlTokenPath) {
		logger.debug("******************************genstoreSamlTokenPath:"+genstoreSamlTokenPath);
		
		ClassPathResource resource = new ClassPathResource(genstoreSamlTokenPath);
		try {
			String xmlString = FileUtils.readFileToString(resource.getFile());
			System.out.println("xmlstring" + xmlString);
			logger.debug("path" + genstoreSamlTokenPath);
			logger.debug("xmlstring" + xmlString);
		} catch (IOException e) {
			logger.debug("got exception in getTokenFromXml" + e.getMessage());
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.genstoreSamlTokenPath=genstoreSamlTokenPath;
	}
*/

	public String getGenstoreUserId() {
		return genstoreUserId;
	}


	public String getGenstoreStationId() {
		return genstoreStationId;
	}


	public String getGenstoreApplicationName() {
		return genstoreApplicationName;
	}


	public String getGenstoreSharedSecret() {
		return genstoreSharedSecret;
	}
	public String getGenstoreContentSource() {
		return genstoreContentSource;
	}


	public String getGenstoresubjectArea() {
		return genstoresubjectArea;
	}
	
}
