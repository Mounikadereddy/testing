package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import feign.Feign;
import feign.Logger.Level;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.serialize.LocalDateDeserializer;
import gov.va.vba.rbps.lettergeneration.docgen.genstore.client.serialize.LocalDateSerializer;
import java.time.LocalDate;

public class GenstoreClientBuilder {

  private String baseUrl;

  private String systemSamlToken;
  private String userId;
  private String stationId;

  private String applicationName;
  private String sharedSecret;

  private Level logLevel;

  public GenstoreClientBuilder() {}

  public GenstoreClientBuilder withBaseUrl(final String baseUrl) {
    this.baseUrl = baseUrl;
    return this;
  }

  public GenstoreClientBuilder withSystemSamlToken(final String systemSamlToken) {
    this.systemSamlToken = systemSamlToken;
    return this;
  }

  public GenstoreClientBuilder withUserId(final String userId) {
    this.userId = userId;
    return this;
  }

  public GenstoreClientBuilder withStationId(final String stationId) {
    this.stationId = stationId;
    return this;
  }

  public GenstoreClientBuilder withApplicationName(final String applicationName) {
    this.applicationName = applicationName;
    return this;
  }

  public GenstoreClientBuilder withSharedSecret(final String sharedSecret) {
    this.sharedSecret = sharedSecret;
    return this;
  }

  public GenstoreClientBuilder withLogLevel(final Level logLevel) {
    this.logLevel = logLevel;
    return this;
  }

  public GenstoreClient build() {
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(LocalDate.class, new LocalDateSerializer());
    simpleModule.addDeserializer(LocalDate.class, new LocalDateDeserializer());

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(simpleModule);

    JwtTokenFeignRequestInterceptor jwtInterceptor = new JwtTokenFeignRequestInterceptor(
        this.systemSamlToken,
        this.userId,
        this.stationId,
        this.applicationName,
        this.sharedSecret);

    GenstoreFeignClientGenerate generateClient = Feign.builder()
        .errorDecoder(new GenstoreClientErrorDecoder())
        .encoder(new JacksonEncoder(mapper))
        .decoder(new RetrieveDocumentResponseDecoder())
        .requestInterceptor(jwtInterceptor)
        .doNotCloseAfterDecode()
        .logger(new Slf4jLogger(GenstoreFeignClientGenerate.class))
        .logLevel(this.logLevel)
        .target(GenstoreFeignClientGenerate.class, this.baseUrl);

    GenstoreFeignClientOther otherClient = Feign.builder()
        .errorDecoder(new GenstoreClientErrorDecoder())
        .encoder(new JacksonEncoder(mapper))
        .decoder(new JacksonDecoder(mapper))
        .requestInterceptor(jwtInterceptor)
        .logger(new Slf4jLogger(GenstoreFeignClientOther.class))
        .logLevel(this.logLevel)
        .target(GenstoreFeignClientOther.class, this.baseUrl);

    return new GenstoreClientImpl(generateClient, otherClient);
  }

}
