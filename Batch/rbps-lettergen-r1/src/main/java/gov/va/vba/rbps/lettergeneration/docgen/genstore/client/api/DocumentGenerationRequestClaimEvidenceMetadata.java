package gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api;

import java.util.List;

public class DocumentGenerationRequestClaimEvidenceMetadata extends DocumentGenerationRequestMetadata {

  private Boolean actionable;

  private List<String> associatedClaimIds = null;

  private List<String> contentions = null;

  private String contentSource;

  private String subject;
  
  private String endProductCode;

  public DocumentGenerationRequestClaimEvidenceMetadata actionable(Boolean actionable) {
    this.actionable = actionable;
    return this;
  }

  public Boolean getActionable() {
    return actionable;
  }

  public void setActionable(Boolean actionable) {
    this.actionable = actionable;
  }

  public DocumentGenerationRequestClaimEvidenceMetadata associatedClaimIds(List<String> associatedClaimIds) {
    this.associatedClaimIds = associatedClaimIds;
    return this;
  }

  public List<String> getAssociatedClaimIds() {
    return associatedClaimIds;
  }

  public void setAssociatedClaimIds(List<String> associatedClaimIds) {
    this.associatedClaimIds = associatedClaimIds;
  }

  public DocumentGenerationRequestClaimEvidenceMetadata contentions(List<String> contentions) {
    this.contentions = contentions;
    return this;
  }

  public List<String> getContentions() {
    return contentions;
  }

  public void setContentions(List<String> contentions) {
    this.contentions = contentions;
  }

  public DocumentGenerationRequestClaimEvidenceMetadata contentSource(String contentSource) {
    this.contentSource = contentSource;
    return this;
  }

  public String getContentSource() {
    return contentSource;
  }

  public void setContentSource(String contentSource) {
    this.contentSource = contentSource;
  }

  public DocumentGenerationRequestClaimEvidenceMetadata subject(String subject) {
    this.subject = subject;
    return this;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String subject) {
    this.subject = subject;
  }

public String getEndProductCode() {
	return endProductCode;
}

public void setEndProductCode(String endProductCode) {
	this.endProductCode = endProductCode;
}

}
