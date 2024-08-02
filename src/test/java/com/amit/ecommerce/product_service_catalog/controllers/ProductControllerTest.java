package com.amit.ecommerce.product_service_catalog.controllers;

import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.services.IProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductControllerTest {
    @Autowired
    private ProductController productController; // Arrange

    @MockBean
    private IProductService productService; // Arrange

    @Test
    @DisplayName("Product fetched successfully - happy path")
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

    @Test
    @DisplayName("Throw run time exception from mocked dependency - sad path")
    public void Test_GetProduct_ExternalDependencyThrowException() {
        // Arrange
        when(productService.getProduct(any(Long.class))).thenThrow(new RuntimeException("Something went wrong."));

        // Act and Assert
        assertThrows(RuntimeException.class, ()-> productController.getProduct(1L));
    }

    @Test
    @DisplayName("Throw IllegalArgumentException when product ID 0 is passed - sad path")
    public void Test_GetProduct_WithInvalidId_ThrowIllegalArgumentException() {

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> productController.getProduct(0L));
        // Checking no of times it go into productService. If there is an exception then it will never enter productService.
        verify(productService,times(0)).getProduct(0L);
    }

}