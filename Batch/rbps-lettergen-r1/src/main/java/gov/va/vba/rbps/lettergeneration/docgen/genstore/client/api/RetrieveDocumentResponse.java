package gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api;

import javax.activation.DataHandler;

public class RetrieveDocumentResponse {

  private String usi;

  private DataHandler content;

  public RetrieveDocumentResponse(final String usi, final DataHandler content) {
    this.usi = usi;
    this.content = content;
  }

  public String getUsi() {
    return usi;
  }

  public void setUsi(String usi) {
    this.usi = usi;
  }

  public DataHandler getContent() {
    return content;
  }

  public void setContent(DataHandler content) {
    this.content = content;
  }
}
