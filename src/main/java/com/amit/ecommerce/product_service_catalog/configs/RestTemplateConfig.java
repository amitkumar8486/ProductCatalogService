package com.amit.ecommerce.product_service_catalog.configs;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
    /* Client side Load Balancer = LoadBalanced. For userAuthService ProductCatalog is the client.
    e.g. If two userAuthService is running then this annotation will balance the load equally.
    one request go to one service another will go to next available server.
    i.e. it will do load balancing at client side.
     */
}
