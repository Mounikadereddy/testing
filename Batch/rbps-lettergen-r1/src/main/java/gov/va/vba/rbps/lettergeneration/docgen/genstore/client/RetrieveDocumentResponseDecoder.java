package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.api.RetrieveDocumentResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

public class RetrieveDocumentResponseDecoder implements Decoder {

  @Override
  public Object decode(Response response, Type type)
      throws IOException, DecodeException, FeignException {
    if (response.body() == null) {
      return null;
    }

    ByteArrayDataSource bads =
        new ByteArrayDataSource(response.body().asInputStream(), "multipart/form-data");
    try {
      MimeMultipart multipart = new MimeMultipart(bads);

      String usi = "";
      DataHandler content = null;

      int parts = multipart.getCount();
      for (int i = 0; i < parts; i++) {
        BodyPart bodyPart = multipart.getBodyPart(i);
        if (bodyPart.isMimeType("application/octet-stream")) {
          content = bodyPart.getDataHandler();
        }
      }

      return new RetrieveDocumentResponse(usi, content);
    } catch (MessagingException e) {
      return null;
    }


  }
}