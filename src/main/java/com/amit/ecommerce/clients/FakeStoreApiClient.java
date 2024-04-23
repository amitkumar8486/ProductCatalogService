package com.amit.ecommerce.clients;

import com.amit.ecommerce.dtos.FakeStoreProductDto;
import com.amit.ecommerce.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FakeStoreApiClient {

    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public FakeStoreProductDto getProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",FakeStoreProductDto.class,productId).getBody();
        return fakeStoreProductDto;
    }
}
