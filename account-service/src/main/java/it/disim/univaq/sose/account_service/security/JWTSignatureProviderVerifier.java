package it.disim.univaq.sose.account_service.security;


import org.apache.cxf.rs.security.jose.jwa.SignatureAlgorithm;
import org.apache.cxf.rs.security.jose.jws.HmacJwsSignatureProvider;
import org.apache.cxf.rs.security.jose.jws.HmacJwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureProvider;
import org.apache.cxf.rs.security.jose.jws.JwsSignatureVerifier;
import org.apache.cxf.rs.security.jose.jwt.JoseJwtConsumer;

/**
 * A utility class for providing and verifying JWT signatures.
 */
public class JWTSignatureProviderVerifier {
    // The secret key used for HMAC signature
    private static final String SECRET_KEY = "3f8bRb6!fc84c8#8^aB5Df99*45d&Ef2";

    private JWTSignatureProviderVerifier() {
        throw new IllegalStateException("JWTSignatureProviderVerifier JWT Utility class");
    }

    /**
     * Returns the signature algorithm used for signing the JWT.
     *
     * @return the signature algorithm
     */
    public static SignatureAlgorithm getSignatureAlgorithm() {
        return SignatureAlgorithm.HS256;
    }

    /**
     * Returns the signature provider used for signing the JWT.
     *
     * @return the signature provider
     */
    public static JwsSignatureProvider getSignatureProvider() {
        return new HmacJwsSignatureProvider(SECRET_KEY.getBytes(), getSignatureAlgorithm());
    }

    /**
     * Returns the signature verifier used for verifying the JWT.
     *
     * @return the signature verifier
     */
    public static JwsSignatureVerifier getSignatureVerifier() {
        return new HmacJwsSignatureVerifier(SECRET_KEY.getBytes(), getSignatureAlgorithm());
    }

    public static JoseJwtConsumer getJwtConsumer() {

        // Crea il JWT consumer
        JoseJwtConsumer jwtConsumer = new JoseJwtConsumer();
        jwtConsumer.setJwsVerifier(getSignatureVerifier());
        return jwtConsumer;
    }
}
