package gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api;

import gov.va.bip.docgen.service.api.model.v1.DocumentGenerationRequest;

public class DocumentGenerationRequestWrapper {

  private DocumentGenerationRequest document;

  private DocumentGenerationRequestMetadata metadata;

  public DocumentGenerationRequestWrapper document(DocumentGenerationRequest document) {
    this.document = document;
    return this;
  }

  public DocumentGenerationRequest getDocument() {
    return document;
  }

  public void setDocument(DocumentGenerationRequest document) {
    this.document = document;
  }

  public DocumentGenerationRequestWrapper metadata(DocumentGenerationRequestMetadata metadata) {
    this.metadata = metadata;
    return this;
  }

  public DocumentGenerationRequestMetadata getMetadata() {
    return metadata;
  }

  public void setMetadata(DocumentGenerationRequestMetadata metadata) {
    this.metadata = metadata;
  }
}
