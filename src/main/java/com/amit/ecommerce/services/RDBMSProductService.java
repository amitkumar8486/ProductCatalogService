package com.amit.ecommerce.services;

import com.amit.ecommerce.models.Product;
import com.amit.ecommerce.repositories.ProductRepo;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
public class RDBMSProductService implements IProductService{
    private ProductRepo productRepo;
    public RDBMSProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
    @Override
    public List<Product> getAllProducts() {
        return List.of();
    }

    @Override
    public Product getProduct(Long productId) {
        Optional<Product> productById = productRepo.findProductById(productId);
        return productById.orElse(null);
    }

    @Override
    public Product createProduct(Product product) {
        Product createdProduct = productRepo.save(product);
        return createdProduct;
    }

    @Override
    public void deleteProduct(Long productId) {

    }

    @Override
    public Product updateProduct(Long id, Product product) {
        return null;
    }
}
