package it.disim.univaq.sose.account_service.security;


import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.cxf.rs.security.jose.jws.JwsCompactConsumer;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jwt.JoseJwtConsumer;
import org.apache.cxf.rs.security.jose.jwt.JwtClaims;
import org.apache.cxf.rs.security.jose.jwt.JwtToken;


/**
 * Class verifying JWT tokens.
 */
@Slf4j
public class JWTVerify {
    private JWTVerify() {
        throw new IllegalStateException("JWTVerify JWT Utility class");
    }

    /**
     * Verifies the validity of a JWT token.
     *
     * @param jwtToken the JWT token to verify
     * @return true if the token is valid, false otherwise
     */
    public static boolean verifyJwtToken(String jwtToken) {
        log.info("Verifying JWT token: {}", jwtToken);
        // Create a JWS compact consumer for the given token
        JwsCompactConsumer consumer = new JwsCompactConsumer(jwtToken);
        log.info("consumer: {}", consumer.toString());
        // Get the signature verifier
        JwsSignatureVerifier verifier = JWTSignatureProviderVerifier.getSignatureVerifier();
        log.info("verifier: {}", verifier.toString());
        // Verify the signature of the token
        boolean signatureValid = consumer.verifySignatureWith(verifier);
        log.info("signatureValid: {}", signatureValid);


        if (signatureValid) {
            // Extract and use JWT claims if necessary
            JoseJwtConsumer jwtConsumer = JWTSignatureProviderVerifier.getJwtConsumer();
            log.info("jwtConsumer: {}", jwtConsumer.toString());
            JwtToken token = jwtConsumer.getJwtToken(jwtToken);
            log.info("token: {}", token.toString());
            JwtClaims claims = token.getClaims();
            log.info("claims: {}", claims.toString());

            // Additional verification can be done on the claims
            // Example: check if the token is expired
            long currentTimeInSecs = System.currentTimeMillis() / 1000L;
            log.info("currentTimeInSecs: {}", currentTimeInSecs);
            if (claims.getExpiryTime() != null && claims.getExpiryTime() < currentTimeInSecs) {
                return false; // Token is expired
            }

            return true; // Signature is valid and token is not expired
        }
        return false;// Signature is not valid
    }
}
