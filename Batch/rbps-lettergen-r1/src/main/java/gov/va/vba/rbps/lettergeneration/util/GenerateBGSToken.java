package gov.va.vba.rbps.lettergeneration.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import java.util.*;

@Component
public class GenerateBGSToken {


    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

    @Value("${css.security.jwt.secret}")
    private String secret;
    @Value("${css.security.jwt.expirationInSeconds}")
    private int expirationInSeconds;

    public GenerateBGSToken() {
    }

    public String generateToken(final String userID, final String stationID) {

        final Calendar currentTime = GregorianCalendar.getInstance(Locale.US);
        Calendar expiration = GregorianCalendar.getInstance(Locale.US);
        expiration.setTime(currentTime.getTime());
        expiration.add(Calendar.SECOND, expirationInSeconds);

        Key signingKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), signatureAlgorithm.getJcaName());

        String jwt = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setIssuer("benefits-services-rbps")
                .setIssuedAt(currentTime.getTime())
                .setId(UUID.randomUUID().toString())
                .setExpiration(expiration.getTime())
                .claim("userID", userID)
                .claim("stationID", stationID)
                .signWith(signatureAlgorithm, signingKey).compact();

        return jwt;
    }

}
