package gov.va.vba.rbps.lettergeneration.docgen.genstore.client;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class ThreadLocalFeignRequestInterceptor implements RequestInterceptor {

  String tokenHeaderKey;

  private final ThreadLocal<String> threadLocalJWT = new ThreadLocal<>();

  public ThreadLocalFeignRequestInterceptor(final String tokenHeaderKey) {
    this.tokenHeaderKey = tokenHeaderKey;
  }

  @Override
  public void apply(final RequestTemplate requestTemplate) {
    if (threadLocalJWT.get() != null) {
      final String token = threadLocalJWT.get();
      threadLocalJWT.remove();

      final String key = tokenHeaderKey;

      final String prefix = token.startsWith("Bearer ") ? "" : "Bearer ";

      requestTemplate.header(key, prefix + token);
    }
  }

}
