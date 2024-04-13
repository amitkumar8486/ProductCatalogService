package com.amit.ecommerce.controllers;

import com.amit.ecommerce.dtos.ProductDto;
import com.amit.ecommerce.models.Product;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*
@RequestMapping - This helps us to define the root url so that we don't have to repeat in other places.
 */
@RequestMapping("/products")
public class ProductController {

    /*
    The path is '/products'. This is the beauty of @RequestMapping.
     */
    @GetMapping()
    public List<Product> getAllProducts(){
        return null;
    }

    /*
    The path is '/products/{id}'.
     */
    @GetMapping("{id}")
    public Product getProduct(@PathVariable("id") Long productId){
        Product product = new Product();
        product.setId(productId);
        product.setName("Iphone");
        product.setPrice(100000D);
        return product;

    }

    /*
    We want to restrict the seller to add certain values like - is the product is for prime day sales, COD available or not etc.
    This value can only set by amazon admin.
    That is why we pass productDto not the product object.
     */
    @PostMapping
    public ProductDto createProduct(@RequestBody ProductDto productDto){
        return productDto;
    }

}
