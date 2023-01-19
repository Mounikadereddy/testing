package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import feign.Headers;
import feign.RequestLine;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationRequestWrapper;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationResponseRef;

public interface GenstoreFeignClientOther {

  @RequestLine("POST /api/v1/documents")
  @Headers({"Content-Type: application/json"})
  DocumentGenerationResponseRef generateDocument(DocumentGenerationRequestWrapper request);
 }
