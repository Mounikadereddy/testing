package gov.va.vba.rbps.lettergeneration.docgen.genstore.client.serialize;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import java.io.IOException;
import java.time.LocalDate;

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

  @Override
  public LocalDate deserialize(JsonParser p, DeserializationContext ctxt)
      throws IOException {
    String candidateText = p.getText();

    if (null == candidateText || candidateText.isEmpty()) {
      return null;
    }

    return LocalDate.parse(p.getText());
  }

}
