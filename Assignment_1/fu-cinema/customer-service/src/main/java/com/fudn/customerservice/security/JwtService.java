package com.fudn.customerservice.security;

import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.util.Map;

@Service
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

    public String generateToken(long userId, String email, String role) {
        byte[] keyBytes = secret.getBytes();
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, "HmacSHA256");

        NimbusJwtEncoder encoder = new NimbusJwtEncoder(new ImmutableSecret<>(secretKey));

        Instant now = Instant.now();
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .subject(email)
                .issuedAt(now)
                .expiresAt(now.plusMillis(expirationMs))
                .claims(c -> c.putAll(Map.of(
                        "uid", userId,
                        "role", role
                )))
                .build();

        JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();
        return encoder.encode(JwtEncoderParameters.from(header, claims)).getTokenValue();
    }
}
