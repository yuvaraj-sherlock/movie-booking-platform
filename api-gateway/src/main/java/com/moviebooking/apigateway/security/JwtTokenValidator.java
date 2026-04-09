package com.moviebooking.apigateway.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Component
public class JwtTokenValidator {

    private final SecretKey signingKey;

    public JwtTokenValidator(@Value("${jwt.secret}") String jwtSecret) {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Validates a JWT token and returns its claims.
     *
     * WHY returning Claims instead of boolean:
     * The gateway needs to extract username and roles to forward
     * as headers to downstream services. A simple boolean
     * would require parsing the token twice.
     *
     * @param token raw JWT string (without "Bearer " prefix)
     * @return parsed Claims if valid
     * @throws JwtException if token is invalid or expired
     */
    public Claims validateAndExtractClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(signingKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("JWT token expired for subject: {}", e.getClaims().getSubject());
            throw e;
        } catch (JwtException e) {
            log.error("JWT token validation failed: {}", e.getMessage());
            throw e;
        }
    }

    /**
     * Extracts roles from token claims.
     *
     * WHY roles in JWT:
     * Embedding roles in the token enables authorization decisions
     * at the gateway WITHOUT any downstream service call or DB lookup.
     * This is the foundation of stateless RBAC in microservices.
     */
    @SuppressWarnings("unchecked")
    public List<String> extractRoles(Claims claims) {
        Object roles = claims.get("roles");
        if (roles instanceof List) {
            return (List<String>) roles;
        }
        return List.of();
    }

    public String extractUsername(Claims claims) {
        return claims.getSubject();
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new java.util.Date());
    }
}
