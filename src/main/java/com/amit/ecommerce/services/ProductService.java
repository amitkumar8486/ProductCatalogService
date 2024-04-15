package com.amit.ecommerce.services;

import com.amit.ecommerce.dtos.FakeStoreProductDto;
import com.amit.ecommerce.dtos.ProductDto;
import com.amit.ecommerce.models.Category;
import com.amit.ecommerce.models.Product;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService implements IProductService{

    /*
    Dependency injection.
     */
    private RestTemplateBuilder restTemplateBuilder;
    public ProductService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
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
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate.getForEntity("https://fakestoreapi.com/products/{id}",FakeStoreProductDto.class,productId).getBody();
        return getProduct(fakeStoreProductDto);
    }

    /**
     * Create new product.
     * @param productDto
     * @return created product.
     */
    @Override
    public Product createProduct(ProductDto productDto) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        FakeStoreProductDto fakeStoreProductDto = restTemplate.postForEntity("https://fakestoreapi.com/products", productDto, FakeStoreProductDto.class).getBody();
        return getProduct(fakeStoreProductDto);
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
}
