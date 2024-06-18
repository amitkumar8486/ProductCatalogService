package com.amit.ecommerce.product_service_catalog.services;

import com.amit.ecommerce.product_service_catalog.models.Product;

import java.util.List;

/*
Adapter design pattern:
The Controller class talks to Service class via interface.
 */
public interface IProductService {

    List<Product> getAllProducts();
    Product getProduct(Long productId);
    Product createProduct(Product product);
    void deleteProduct(Long productId);
    Product updateProduct(Long id,Product product);
    Product getProductDetails(Long productId, Long userId);
}
