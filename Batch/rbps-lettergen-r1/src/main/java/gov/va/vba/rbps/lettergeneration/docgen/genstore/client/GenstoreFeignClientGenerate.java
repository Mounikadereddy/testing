package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import feign.Param;
import feign.RequestLine;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.RetrieveDocumentResponse;
import org.springframework.web.bind.annotation.PathVariable;

public interface GenstoreFeignClientGenerate {

  @RequestLine("GET /api/v1/documents/{docref}")
 RetrieveDocumentResponse retrieveDocument(@Param(value = "docref") String docref);

}
