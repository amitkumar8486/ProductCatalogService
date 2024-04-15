package com.amit.ecommerce.services;

import com.amit.ecommerce.dtos.ProductDto;
import com.amit.ecommerce.models.Product;

import java.util.List;

/*
Adapter design pattern:
The Controller class talks to Service class via interface.
 */
public interface IProductService {

    List<Product> getAllProducts();
    Product getProduct(Long productId);
    Product createProduct(ProductDto productDto);
}
