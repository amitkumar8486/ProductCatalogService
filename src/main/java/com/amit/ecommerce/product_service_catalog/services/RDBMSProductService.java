package com.amit.ecommerce.product_service_catalog.services;

import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Primary
@Service
@Transactional // helps to delete data
public class RDBMSProductService implements IProductService{
    private ProductRepo productRepo;
    public RDBMSProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }
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
}
