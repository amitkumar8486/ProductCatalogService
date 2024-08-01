package com.amit.ecommerce.product_service_catalog.controllers;

import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.services.IProductService;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController; // Arrange

    @MockBean
    private IProductService productService; // Arrange

    @Test
    public void Test_GetProduct_WithValidId_ReturnsProductSuccessfully() {

        // Arrange
        Product product = new Product();
        product.setPrice(1000D);
        product.setName("Iphone");

        when(productService.getProduct(any(Long.class))).thenReturn(product);

        // Act
        ResponseEntity<Product> responseEntity = productController.getProduct(1L);

        // Assert
        assertNotNull(responseEntity.getBody());
        assertEquals(1000D, responseEntity.getBody().getPrice());
        assertEquals("Iphone", responseEntity.getBody().getName());
    }

}