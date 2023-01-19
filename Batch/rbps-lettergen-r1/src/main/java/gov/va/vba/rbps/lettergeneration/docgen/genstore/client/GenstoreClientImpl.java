package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationRequestWrapper;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.DocumentGenerationResponseRef;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.RetrieveDocumentResponse;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.GenstoreFeignClientGenerate;
public class GenstoreClientImpl implements GenstoreClient {

  private final GenstoreFeignClientGenerate genstoreFeignClientGenerate;
  private final GenstoreFeignClientOther genstoreFeignClientOther;

  public GenstoreClientImpl(
      final GenstoreFeignClientGenerate genstoreFeignClientGenerate,
      final GenstoreFeignClientOther genstoreFeignClientOther) {
    this.genstoreFeignClientGenerate = genstoreFeignClientGenerate;
    this.genstoreFeignClientOther = genstoreFeignClientOther;
  }

  @Override
  public DocumentGenerationResponseRef generateDocument(
      DocumentGenerationRequestWrapper documentGenerationRequestWrapper) {
	  return genstoreFeignClientOther.generateDocument(documentGenerationRequestWrapper);
  }
  @Override
  public RetrieveDocumentResponse retrieveDocument(String docref) {
    return genstoreFeignClientGenerate.retrieveDocument(docref);
  }

}
