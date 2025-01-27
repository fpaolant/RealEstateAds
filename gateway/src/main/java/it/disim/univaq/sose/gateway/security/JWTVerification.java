package it.disim.univaq.sose.gateway.security;

import java.util.List;


import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm;
import org.apache.cxf.rs.security.jose.jws.HmacJwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jws.JwsCompactConsumer;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactConsumer;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jwt.JwtClaims;
import org.springframework.stereotype.Service;


/**
 * The JWTVerification class is a service that provides methods to verify a JWT token.
 */
@Slf4j
@Service
public class JWTVerification {

    private static final String SECRET_KEY = "3f8bRb6!fc84c8#8^aB5Df99*45d&Ef2";

    /**
     * Verify the token.
     *
     * @param token the token
     * @throws AuthenticationException if the token is invalid
     */
    public void verifyToken(String token) throws AuthenticationException {
        JwsCompactConsumer consumer = new JwsCompactConsumer(token);
        HmacJwsSignatureVerifier hmacJwsSignatureVerifier = new HmacJwsSignatureVerifier(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256);
        boolean signatureValid = consumer.verifySignatureWith((JwsSignatureVerifier)hmacJwsSignatureVerifier);
        if (!signatureValid) {
            log.error("Token Verification failed");
            throw new AuthenticationException("Token Verification failed");
        }
        JwsJwtCompactConsumer jwtConsumer = new JwsJwtCompactConsumer(token);
        JwtClaims claims = jwtConsumer.getJwtClaims();
        long currentTimeInSecs = System.currentTimeMillis() / 1000L;
        if (claims.getExpiryTime() != null && claims.getExpiryTime().longValue() < currentTimeInSecs) {
            log.error("Token Verification failed");
            throw new AuthenticationException("Token Verification failed");
        }
    }

    /**
     * Check the roles.
     *
     * @param token the token
     * @param roles the roles
     * @throws AuthenticationException if the roles are not allowed
     */
    public void checkRoles(String token, List<String> roles) throws AuthenticationException {
        JwsJwtCompactConsumer jwtConsumer = new JwsJwtCompactConsumer(token);
        String role = (String)jwtConsumer.getJwtClaims().getClaim("role");
        log.info("Role: {}", role);
        log.info("Roles: {}", roles);
        log.info("Contains: {}", Boolean.valueOf(roles.contains(role)));
        log.info("Role is null: {}", Boolean.valueOf((role == null)));
        log.info("Token: {}", token);
        if (role == null || !roles.contains(role)) {
            log.error("Forbidden: role not allowed: {} in Roles: {}", role, roles);
            throw new AuthenticationException("Forbidden: role not allowed");
        }
    }

    /**
     * Get the role.
     *
     * @param token the token
     * @return the role
     * @throws AuthenticationException if the role is not found
     */
    public String getRole(String token) throws AuthenticationException {
        JwsJwtCompactConsumer jwtConsumer = new JwsJwtCompactConsumer(token);
        String role = (String)jwtConsumer.getJwtClaims().getClaim("role");
        log.info("******************getRole: {}", role);
        if (role == null) {
            log.error("role not found");
            throw new AuthenticationException("role not found");
        }
        return role;
    }

    /**
     * Get the identifier.
     *
     * @param token the token
     * @return the identifier
     * @throws AuthenticationException if the identifier is not found
     */
    public long getIdentifier(String token) throws AuthenticationException {
        JwsJwtCompactConsumer jwtConsumer = new JwsJwtCompactConsumer(token);
        Object identifier = jwtConsumer.getJwtClaims().getClaim("identifier");
        if (identifier == null) {
            log.error("identifier not found");
            throw new AuthenticationException("identifier not found");
        }
        return Long.parseLong(identifier.toString());
    }
}
