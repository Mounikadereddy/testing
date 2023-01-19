package gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api;

public class DocumentGenerationResponseRef {

  private String usi;

  private String documentRef;

  public DocumentGenerationResponseRef usi(String usi) {
    this.usi = usi;
    return this;
  }

  public String getUsi() {
    return usi;
  }

  public void setUsi(String usi) {
    this.usi = usi;
  }

  public DocumentGenerationResponseRef documentRef(String documentRef) {
    this.documentRef = documentRef;
    return this;
  }

  public String getDocumentRef() {
    return documentRef;
  }

  public void setDocumentRef(String documentRef) {
    this.documentRef = documentRef;
  }

}
