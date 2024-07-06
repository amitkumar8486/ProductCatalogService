package com.amit.ecommerce.product_service_catalog.services;

import com.amit.ecommerce.product_service_catalog.dtos.UserDto;
import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
@Transactional // helps to delete data
public class RDBMSProductService implements IProductService{
    private ProductRepo productRepo;
    public RDBMSProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product getProduct(Long productId) {
        Optional<Product> productById = productRepo.findProductById(productId);
        return productById.orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public void deleteProduct(Long productId) {
        productRepo.deleteProductById(productId);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }

    // getProductDetails- For Service Discovery
    @Override
    public Product getProductDetails(Long productId, Long userId) {
        //RestTemplate restTemplate = new RestTemplate();
        UserDto userDto = restTemplate.getForEntity("http://UserAuthenticationService/users/{uid}", UserDto.class, userId).getBody();
        System.out.println("User email: " + userDto.getEmail());
        if(userDto != null) {
            Product product = productRepo.findProductById(productId).get();
            return product;
        }
        return null;
    }
}
