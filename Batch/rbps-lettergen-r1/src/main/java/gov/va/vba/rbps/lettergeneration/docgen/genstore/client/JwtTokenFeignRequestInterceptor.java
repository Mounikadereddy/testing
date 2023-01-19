package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import gov.va.bip.framework.security.model.Person;
import gov.va.bip.framework.security.util.GenerateToken;

public class JwtTokenFeignRequestInterceptor implements RequestInterceptor {

  private final String systemSamlToken;
  private final String userId;
  private final String stationId;

  private final String applicationName;
  private final String sharedSecret;

  public JwtTokenFeignRequestInterceptor(String systemSamlToken, String userId,
      String stationId, String applicationName, String sharedSecret) {
    this.systemSamlToken = systemSamlToken;
    this.userId = userId;
    this.stationId = stationId;
    this.applicationName = applicationName;
    this.sharedSecret = sharedSecret;
  }

  public void apply(final RequestTemplate requestTemplate) {
    final Person person = new Person();

    person.setAppToken(this.systemSamlToken);
    person.setUserID(this.userId);
    person.setStationID(this.stationId);

    final String token = GenerateToken.generateJwt(person, this.sharedSecret, this.applicationName);

    requestTemplate.header("Authorization", "Bearer " + token);
  }

}
