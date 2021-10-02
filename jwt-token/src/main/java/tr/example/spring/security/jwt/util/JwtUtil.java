package tr.example.spring.security.jwt.util;

import io.fusionauth.jwt.Signer;
import io.fusionauth.jwt.Verifier;
import io.fusionauth.jwt.domain.JWT;
import io.fusionauth.jwt.hmac.HMACSigner;
import io.fusionauth.jwt.hmac.HMACVerifier;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final UserDetailsService userDetailsService;

    private String SECRET_KEY = "secret";

    public String extractUsername(String token) {
        Verifier verifier = HMACVerifier.newVerifier(SECRET_KEY);
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return jwt.subject;
    }

    public Date extractExpiration(String token) {
        Verifier verifier = HMACVerifier.newVerifier(SECRET_KEY);
        JWT jwt = JWT.getDecoder().decode(token, verifier);
        return Date.from(jwt.expiration.toInstant());
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        return createToken(userDetails.getUsername());
    }

    private String createToken(String subject) {
        Signer signer = HMACSigner.newSHA256Signer(SECRET_KEY);
        JWT jwt = new JWT()
                .setSubject(subject)
                .setIssuedAt(ZonedDateTime.now(ZoneOffset.UTC))
                .setExpiration(ZonedDateTime.now(ZoneOffset.UTC).plusMinutes(60));
        return JWT.getEncoder().encode(jwt, signer);
    }

    public Boolean validateToken(String token) {
        final String username = extractUsername(token);
        userDetailsService.loadUserByUsername(username);
        return (!isTokenExpired(token));
    }
}
