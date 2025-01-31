package com.amit.ecommerce.product_service_catalog.services;

import com.amit.ecommerce.product_service_catalog.clients.FakeStoreApiClient;
import com.amit.ecommerce.product_service_catalog.dtos.FakeStoreProductDto;
import com.amit.ecommerce.product_service_catalog.models.Category;
import com.amit.ecommerce.product_service_catalog.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Primary
@Service
public class ProductService implements IProductService{

    /*
    Dependency injection.
     */
    private RestTemplateBuilder restTemplateBuilder;
    private FakeStoreApiClient fakeStoreApiClient;
    private RedisTemplate<String, Object> redisTemplate;

    public ProductService(RestTemplateBuilder restTemplateBuilder, FakeStoreApiClient fakeStoreApiClient, RedisTemplate<String,Object> redisTemplate) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.fakeStoreApiClient = fakeStoreApiClient;
        this.redisTemplate = redisTemplate;
    }
    /*
    Spring Boot provides a convenient way to make HTTP requests through the use of the RestTemplate class.
    This class is a powerful tool for making requests to REST-ful web services and can be used for both synchronous and asynchronous requests.
     */

    /**
     * Get all products.
     * @return list of products.
     */
    @Override
    public List<Product> getAllProducts() {
        RestTemplate restTemplate = restTemplateBuilder.build();
        //Compiler is unable to determine datatype of list which is generics, that's why we can't use List<FakeStoreProductDto>.class , instead using array.
        FakeStoreProductDto[] fakeStoreProductDtos = restTemplate.getForEntity("https://fakestoreapi.com/products", FakeStoreProductDto[].class).getBody();
        List<Product> products = new ArrayList<>();
        for(FakeStoreProductDto fakeStoreProductDto : fakeStoreProductDtos) {
            products.add(getProduct(fakeStoreProductDto));
        }
        return products;
    }

    /**
     * Get a single product.
     * @param productId id of the product to be searched.
     * @return searched product.
     */
    @Override
    public Product getProduct(Long productId) {

        if(productId == 0){
            throw new IllegalArgumentException("Invalid product id");
        }

        /* Check if product is in cache
        *       return product
        * else
        *   call fakeStore fakeStoreApiClient
        *   store cache and return*/

        FakeStoreProductDto fakeStoreProductDto = null;
        fakeStoreProductDto = (FakeStoreProductDto) redisTemplate.opsForHash().get("PRODUCTS", productId);

        if(fakeStoreProductDto != null) { // found in cache
            System.out.println("Found in cache");
            return getProduct(fakeStoreProductDto);
        }

        // if not found in cache
        fakeStoreProductDto = fakeStoreApiClient.getProduct(productId);
        System.out.println("Called FakeStore");
        redisTemplate.opsForHash().put("PRODUCTS",productId, fakeStoreProductDto);
        return getProduct(fakeStoreProductDto);
    }

    /**
     * Create new product.
     * @param product
     * @return created product.
     */
    @Override
    public Product createProduct(Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoReq = getFakeStoreProductDto(product);
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products", fakeStoreProductDtoReq, FakeStoreProductDto.class).getBody();
        return getProduct(fakeStoreProductDto);
    }

    /**
     * Delete a product.
     * @param productId product ID to be deleted.
     */
    @Override
    public void deleteProduct(Long productId) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        restTemplate.delete("https://fakestoreapi.com/products/{id}",productId);

    }

    /**
     * Update a product.
     * @param id
     * @param product
     * @return
     */

    @Override
    public Product updateProduct(Long id,Product product) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDtoreq = getFakeStoreProductDto(product);
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                putForEntity("https://fakestoreapi.com/products/{id}",
                        fakeStoreProductDtoreq, FakeStoreProductDto.class,id);
        return getProduct(fakeStoreProductDtoResponseEntity.getBody());
    }

    @Override
    public Product getProductDetails(Long productId, Long userId) {
        return null;
    }

    private <T> ResponseEntity<T> putForEntity(String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, HttpMethod.PUT, requestCallback, responseExtractor, uriVariables);
    }

    /**
     * Get the product from Fake API and store in our product object.
     * @param fakeStoreProductDto from API.
     * @return product
     */
    private Product getProduct(FakeStoreProductDto fakeStoreProductDto) {
        Product product = new Product();
        product.setId(fakeStoreProductDto.getId());
        product.setName(fakeStoreProductDto.getTitle());
        product.setPrice(fakeStoreProductDto.getPrice());
        product.setDescription(fakeStoreProductDto.getDescription());
        product.setImageUrl(fakeStoreProductDto.getImage());
        Category category = new Category();
        category.setName(fakeStoreProductDto.getCategory());
        product.setCategory(category);
        return product;
    }

    /**
     * Reverse Mapping: Get Fake store product dto obj from product obj.
     * @param product
     * @return FakeStoreProductDto obj.
     */

    private FakeStoreProductDto getFakeStoreProductDto(Product product) {
        FakeStoreProductDto fakeStoreProductDto = new FakeStoreProductDto();
        fakeStoreProductDto.setDescription(product.getDescription());
        fakeStoreProductDto.setPrice(product.getPrice());
        fakeStoreProductDto.setImage(product.getImageUrl());
        fakeStoreProductDto.setTitle(product.getName());
        if(product.getCategory() != null) {
            fakeStoreProductDto.setCategory(product.getCategory().getName());
        }
        return fakeStoreProductDto;
    }

}
