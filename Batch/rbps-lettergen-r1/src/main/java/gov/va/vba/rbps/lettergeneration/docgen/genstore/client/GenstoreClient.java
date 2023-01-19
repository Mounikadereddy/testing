package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationRequestWrapper;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationResponseRef;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.RetrieveDocumentResponse;

public interface GenstoreClient {

  DocumentGenerationResponseRef generateDocument(
      DocumentGenerationRequestWrapper documentGenerationRequestWrapper);

  RetrieveDocumentResponse retrieveDocument(String docref);

}

