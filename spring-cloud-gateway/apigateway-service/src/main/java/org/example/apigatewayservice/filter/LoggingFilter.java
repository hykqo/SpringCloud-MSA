package org.example.apigatewayservice.filter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.OrderedGatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        //Custom Pre Filter
//        return ((exchange, chain) -> {
//            ServerHttpRequest request = exchange.getRequest();
//            ServerHttpResponse response = exchange.getResponse();
//
//            log.info("Global Filter baseMessage: {}", config.getBaseMessage());
//
//            if(config.isPreLogger()) {
//                log.info("Global Filter requerst id -> {}", request.getId());
//            }
//            //Custom Post Filter
//            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
//                if(config.isPostLogger()) {
//                    log.info("Global Filter response status code -> {}", response.getStatusCode());
//                }
//            }));
//        });
        GatewayFilter filter = new OrderedGatewayFilter((exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            ServerHttpResponse response = exchange.getResponse();

            log.info("Logging Filter baseMessage: {}", config.getBaseMessage());

            if(config.isPreLogger()) {
                log.info("Logging PRE Filter: requerst id -> {}", request.getId());
            }
            //Custom Post Filter
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                if(config.isPostLogger()) {
                    log.info("Logging POST Filter: response code -> {}", response.getStatusCode());
                }
            }));
        }, Ordered.LOWEST_PRECEDENCE);
        /**
         * Ordered.HIGHEST_PRECEDENCE : 가장 높은 순위
         * Ordered.LOWEST_PRECEDENCE : 가장 낮은 순위
         *
        * */
        return filter;
    }

    @Data
    public static class Config {
        //Put the configuration properties
        private String baseMessage;
        private boolean preLogger;
        private boolean postLogger;
    }


}
