package it.disim.univaq.sose.account_service.security;

import it.disim.univaq.sose.account_service.domain.Role;
import org.apache.cxf.rs.security.jose.common.JoseType;
import org.apache.cxf.rs.security.jose.jws.JwsHeaders;
import org.apache.cxf.rs.security.jose.jws.JwsJwtCompactProducer;
import org.apache.cxf.rs.security.jose.jwt.JwtClaims;
import org.apache.cxf.rs.security.jose.jwt.JwtToken;

/**
 * A utility class for generating JWT tokens.
 */
public class JWTGenerator {
    private JWTGenerator() {
        throw new IllegalStateException("JWTGenerator JWT Utility class");
    }

    /**
     * Creates a JWT token for the given user credentials.
     *
     * @param username  the username of the user
     * @param idAccount the ID of the account
     * @param role      the role of the user
     * @return the generated JWT token as a string
     */
    public static String createJwtToken(String username, long idAccount, Role role) {
        // Create JWS headers with the JWT type and signature algorithm
        JwsHeaders headers = new JwsHeaders(JoseType.JWT, JWTSignatureProviderVerifier.getSignatureAlgorithm());

        JwtClaims claims = new JwtClaims();
        claims.setSubject(username);// The user for whom the token is valid
        claims.setClaim("identifier", idAccount); // The account ID
        claims.setClaim("role", role.toString()); // The role in string
        claims.setIssuedAt(System.currentTimeMillis() / 1000); // Issued at current time in seconds
        claims.setExpiryTime((System.currentTimeMillis() / 1000) + 3600);  // Token valid for 1 hour

        // Create a new JWT token with headers and claims
        JwtToken token = new JwtToken(headers, claims);

        // Create a JWT compact producer to sign the token
        JwsJwtCompactProducer producer = new JwsJwtCompactProducer(token);
        // Sign the token using the signature provider and return the compact representation
        return producer.signWith(JWTSignatureProviderVerifier.getSignatureProvider());
    }
}
