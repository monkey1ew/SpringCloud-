package com.fqh.springcloud.config;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

/**
 * @author 海盗狗
 * @version 1.0
 */
@Configuration
public class GateWayConfig {

    @Bean
    public RouteLocator customerRouteLocator(RouteLocatorBuilder routeLocatorBuilder) {

//        http://news.baidu.com/guonei
        RouteLocatorBuilder.Builder routes = routeLocatorBuilder.routes();
        routes.route("path_route_fqh1", r -> r.path("/guonei")
                .uri("http://news.baidu.com/guonei"))
                    .build();
//        routes.route("path_route_fqh2",
//                predicateSpec -> predicateSpec.path("/guonei")
//                        .uri("http://news.baidu.com/guonei")).build();

        return routes.build();
    }
}
