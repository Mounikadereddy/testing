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


public class DocumentGenerationMetadata {

  private DocumentGenerationRequestClaimEvidenceMetadata claimEvidence ;

  public DocumentGenerationMetadata fileNumber(DocumentGenerationRequestClaimEvidenceMetadata claimEvidence) {
	    this.claimEvidence = claimEvidence;
	    return this;
	  }

public DocumentGenerationRequestClaimEvidenceMetadata getClaimEvidence() {
	return claimEvidence;
}

public void setClaimEvidence(DocumentGenerationRequestClaimEvidenceMetadata claimEvidence) {
	this.claimEvidence = claimEvidence;
}

	
}
