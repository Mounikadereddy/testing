package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import feign.Response;
import feign.codec.ErrorDecoder;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.exceptions.RetryableException;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.exceptions.UnrecoverableException;

public class GenstoreClientErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String s, Response response) {
    if (response.status() >= 500 && response.status() <= 599) {
      return new RetryableException("Retryable error code: " + response.status() + " returned by GenStore server a reason of: " + response.reason());
    }
    return new UnrecoverableException("Unrecoverable error code: " + response.status() + " returned by GenStore server with a reason of: " + response.reason());
  }
}
