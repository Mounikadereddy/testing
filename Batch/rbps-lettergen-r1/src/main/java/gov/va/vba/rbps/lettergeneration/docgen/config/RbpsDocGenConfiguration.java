package gov.va.vba.rbps.lettergeneration.docgen.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.va.vba.rbps.lettergeneration.docgen.DocGenProperties;
/*import gov.va.vba.rbps.lettergeneration.docgen.client.RbpsDocGenClient;
import gov.va.vba.rbps.lettergeneration.docgen.client.RbpsDocGenClientImpl;
*/import gov.va.vba.rbps.lettergeneration.docgen.exception.DocGenException;
/*import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.RbpsDocGenGenstoreClient;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.RbpsDocGenGenstoreClientImpl;
*/import gov.va.vba.rbps.lettergeneration.docgen.genstore.service.RbpsDocGenGenstoreService;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.service.RbpsDocGenGenstoreServiceImpl;
/*import gov.va.vba.rbps.lettergeneration.docgen.service.RbpsDocGenService;
import gov.va.vba.rbps.lettergeneration.docgen.service.RbpsDocGenServiceImpl;
*/import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;


@Configuration
@ImportResource("classpath:rbps-core-spring-config.xml")
public class RbpsDocGenConfiguration {

   /* @Value("${rbps.lettergen.url}")
    private String docgen_url;
*/
    @Value("${rbps.lettergen.homedir}")
    private String output_dir;

    @Value("${rbps.lettergen.subjectArea}")
    private String subjectArea;

  /*  @Value("${rbps.lettergen.jwt}")
    private String jwt;
*/
  //genstore
    @Value("${genstore.url}")
    private  String genstoreUrl;
    
   // @Value("${genstore.samltoken.path}")
   // private String genstoreSamlTokenPath;
    
    @Value("${genstore.userid}")
    private  String genstoreUserId;
    
    @Value("${genstore.stationid}")
    private  String genstoreStationId;
    
    @Value("${genstore.subjectArea}")
    private  String genstoresubjectArea;
    
    @Value("${genstore.applicationname}")
    private String genstoreApplicationName;
    
    @Value("${genstore.sharedsecret}")
    private  String genstoreSharedSecret;
    
    @Value("${genstore.contentSource}")
    private  String genstoreContentSource;
	
	
    //TODO Mounika 
    @Value("${genstore.documentTypeId}")
    private  Long documentTypeId;
    @Value("${genstore.awardPrintDocumentTypeId}")
    private  Long awardPrintDocumentTypeId;
    
    //TODO END
    
    
    @Bean(name = "docGenProperties")
    public DocGenProperties getDocGenProperties() {
        return new DocGenProperties(genstoreUrl, output_dir,subjectArea,
        		genstoreUserId,
        		genstoreStationId,genstoreApplicationName,genstoreSharedSecret, genstoreContentSource,genstoresubjectArea,
				documentTypeId,awardPrintDocumentTypeId);
    }

    
   

    @Bean(name = "docGenGenStoreService")
    public RbpsDocGenGenstoreService getDocGenService() {
        return new RbpsDocGenGenstoreServiceImpl(getDocGenProperties());
    }

}
