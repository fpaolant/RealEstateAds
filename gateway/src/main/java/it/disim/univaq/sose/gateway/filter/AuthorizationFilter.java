package it.disim.univaq.sose.gateway.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import it.disim.univaq.sose.gateway.domain.ErrorResponse;
import it.disim.univaq.sose.gateway.security.AuthenticationException;
import it.disim.univaq.sose.gateway.security.JWTVerification;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final JWTVerification jwtVerification;

    public AuthorizationFilter(JWTVerification jwtVerification) {
        super(Config.class);
        this.jwtVerification = jwtVerification;
    }

    /**
     * Apply the filter to gateway.
     *
     * @param config the configuration
     * @return the GatewayFilter
     */
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            long identifier;
            String role;
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
            if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
                return onError(exchange, "Authorization header is missing or invalid", HttpStatus.BAD_REQUEST);
            String token = authorizationHeader.substring(7);
            try {
                this.jwtVerification.checkRoles(token, config.getRoles());
                identifier = this.jwtVerification.getIdentifier(token);
                role = this.jwtVerification.getRole(token);
                this.jwtVerification.verifyToken(token);
            } catch (AuthenticationException e) {
                return onError(exchange, e.getMessage(), HttpStatus.UNAUTHORIZED);
            }
            if (config.isCheckPathWithIdentifier() && "USER".equals(role) && !exchange.getRequest().getPath().toString().contains(Long.toString(identifier))) {
                log.error("Errore conf:{}; User:{} Ident:{}", new Object[] { Boolean.valueOf(config.isCheckPathWithIdentifier()), Boolean.valueOf("USER".equals(role)), Boolean.valueOf(!exchange.getRequest().getPath().toString().contains(Long.toString(identifier))) });
                return onError(exchange, "Forbidden: operation not allowed", HttpStatus.FORBIDDEN);
            }
            return chain.filter(exchange);
        };
    }

    /**
     * Configuration class for the AuthorizationFilter.
     */
    @Getter
    @Setter
    @Builder
    public static class Config {
        private List<String> roles;
        private boolean checkPathWithIdentifier;
    }

    /**
     * Handles errors by sending an appropriate response.
     *
     * @param exchange   the ServerWebExchange.
     * @param error      the error message.
     * @param httpStatus the HTTP status to set.
     * @return a Mono that completes the response handling.
     */
    private Mono<Void> onError(ServerWebExchange exchange, String error, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode((HttpStatusCode)httpStatus);
        ErrorResponse errorResponse = new ErrorResponse(error);
        MediaType mediaType = exchange.getRequest().getHeaders().getAccept().stream().filter(mt -> (mt.equals(MediaType.APPLICATION_JSON) || mt.equals(MediaType.APPLICATION_XML))).findFirst().orElse(MediaType.APPLICATION_JSON);
        exchange.getResponse().getHeaders().setContentType(mediaType);
        try {
            byte[] bytes;
            if (mediaType.equals(MediaType.APPLICATION_JSON)) {
                bytes = this.objectMapper.writeValueAsBytes(errorResponse);
            } else {
                JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { ErrorResponse.class });
                Marshaller marshaller = jaxbContext.createMarshaller();
                StringWriter sw = new StringWriter();
                marshaller.marshal(errorResponse, sw);
                bytes = sw.toString().getBytes();
            }
            return exchange.getResponse().writeWith((Publisher)Mono.just(exchange.getResponse().bufferFactory().wrap(bytes)));
        } catch (Exception e) {
            return Mono.error(e);
        }
    }
}