package gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.serialize.LocalDateDeserializer;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.serialize.LocalDateSerializer;
import java.time.LocalDate;

@JsonTypeInfo(use = Id.NAME, include = As.WRAPPER_OBJECT)
@JsonSubTypes({
    @Type(value = DocumentGenerationRequestClaimEvidenceMetadata.class, name = "claimEvidence")
})
public class DocumentGenerationRequestMetadata {

  private String fileNumber;

  private Long documentTypeId;

  private LocalDate dateVaReceivedDocument;

  private String uploadFileName;

  
  public DocumentGenerationRequestMetadata fileNumber(String fileNumber) {
    this.fileNumber = fileNumber;
    return this;
  }

  public String getFileNumber() {
    return fileNumber;
  }

  public void setFileNumber(String fileNumber) {
    this.fileNumber = fileNumber;
  }

  public DocumentGenerationRequestMetadata documentTypeId(Long documentTypeId) {
    this.documentTypeId = documentTypeId;
    return this;
  }

  public Long getDocumentTypeId() {
    return documentTypeId;
  }

  public void setDocumentTypeId(Long documentTypeId) {
    this.documentTypeId = documentTypeId;
  }

  public DocumentGenerationRequestMetadata dateVaReceivedDocument(LocalDate dateVaReceivedDocument) {
    this.dateVaReceivedDocument = dateVaReceivedDocument;
    return this;
  }

  public LocalDate getDateVaReceivedDocument() {
    return dateVaReceivedDocument;
  }

  public void setDateVaReceivedDocument(LocalDate dateVaReceivedDocument) {
    this.dateVaReceivedDocument = dateVaReceivedDocument;
  }

  public DocumentGenerationRequestMetadata uploadFileName(String uploadFileName) {
    this.uploadFileName = uploadFileName;
    return this;
  }

  public String getUploadFileName() {
    return uploadFileName;
  }

  public void setUploadFileName(String uploadFileName) {
    this.uploadFileName = uploadFileName;
  }

}
