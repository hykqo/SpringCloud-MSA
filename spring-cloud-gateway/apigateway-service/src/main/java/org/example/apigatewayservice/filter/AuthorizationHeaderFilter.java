package org.example.apigatewayservice.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Set;

@Component
@Slf4j
//api게이트웨이에서 커스텀 필터를 등록하기 위해서는 AbstractGatewayFilterFactory를 상속받아서 구현하면된다.
public class AuthorizationHeaderFilter extends AbstractGatewayFilterFactory<AuthorizationHeaderFilter.Config> {

    Environment env;

    public AuthorizationHeaderFilter(Environment env) {
        super(Config.class);
        this.env = env;
    }

    public static class Config {
        //Put configuration properties here
    }

    @Override
    public GatewayFilter apply(Config config) {
        return ((exchange, chain) -> {
           ServerHttpRequest request = exchange.getRequest();
            //1. authorication 헤더가 있는지?
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)){
                return onError(exchange, "The request does not contain the authorization header.", HttpStatus.UNAUTHORIZED);
            }

            //header 로깅
            HttpHeaders headers = request.getHeaders();
            Set<String> keys = headers.keySet();
            log.info(">>");
            keys.stream().forEach(v -> log.info(v + "=" + request.getHeaders().get(v)));
            log.info("<<<");

            //2. Bearer 이 포함되어 있는지?
            // 3. Bearer토큰이 유효한 토큰인지?
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String jwt = authorizationHeader.replace("Bearer ", "");
            if(!isJwtValid(jwt)){
                return onError(exchange, "The token is invalid.", HttpStatus.UNAUTHORIZED);
            }

            return chain.filter(exchange);
        });
    }

    public Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(err);

        byte[] bytes = "The requested token is invalid.".getBytes(StandardCharsets.UTF_8);
        DataBuffer buffer = exchange.getResponse().bufferFactory().wrap(bytes);
        return response.writeWith(Flux.just(buffer));
    }

    private boolean isJwtValid(String jwt){
        boolean returnValue = false;

        String subject;

        SecretKey key = Keys.hmacShaKeyFor(env.getProperty("token.secret").getBytes(StandardCharsets.UTF_8));

        try {
            subject = Jwts
                    .parser()
                    .verifyWith(key)
                    .build()
                    .parseClaimsJws(jwt)
                    .getBody()
                    .getSubject();
        }catch (Exception ex){
            return false;
        }

        if (subject == null || subject.isEmpty()) {
            returnValue = false;
        }

        return true;
    }
}
