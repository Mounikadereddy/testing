package gov.va.vba.rbps.lettergeneration.docgen.genstore.client.serialize;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

  @Override
  public void serialize(LocalDate localDate, JsonGenerator jsonGenerator,
      SerializerProvider serializerProvider) throws IOException {
    if (null == localDate) {
      jsonGenerator.writeNull();
      return;
    }

    jsonGenerator.writeString(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
  }

}
