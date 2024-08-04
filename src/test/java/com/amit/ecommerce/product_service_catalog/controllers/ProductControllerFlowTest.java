package com.amit.ecommerce.product_service_catalog.controllers;

import com.amit.ecommerce.product_service_catalog.dtos.ProductDto;
import com.amit.ecommerce.product_service_catalog.models.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ProductControllerFlowTest {

    @Autowired
    private ProductController productController;

    @Test
    public void Test_Create_Replace_Get_Product_WithStub_RunSuccessfully() {

        // Arrange
        ProductDto productDto = new ProductDto();
        productDto.setId(1L);
        productDto.setName("Iphone12");
        productDto.setDescription("Good Iphone");

        // Act
        Product product = productController.createProduct(productDto);

        ResponseEntity<Product> productResponseEntity = productController.getProduct(productDto.getId());

        productDto.setName("Iphone15");
        Product replacedProduct = productController.replaceProduct(product.getId(), productDto);

        ResponseEntity<Product> replacedProductResponseEntity = productController.getProduct(replacedProduct.getId());

        // Assert
        assertEquals("Iphone12", productResponseEntity.getBody().getName());
        assertEquals("Iphone15", replacedProductResponseEntity.getBody().getName());

    }
}
