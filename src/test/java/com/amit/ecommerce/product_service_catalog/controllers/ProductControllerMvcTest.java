package com.amit.ecommerce.product_service_catalog.controllers;

import com.amit.ecommerce.product_service_catalog.dtos.ProductDto;
import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductController.class)
class ProductControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IProductService productService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private ProductController productController;
    @Captor
    private ArgumentCaptor<Long> productIdCaptor;

    @Test
    public void Test_GetAllProducts_FetchSuccessfully() throws Exception {

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("Iphone15");
        products.add(product);

        Product product2 = new Product();
        product2.setName("Iphone14");
        products.add(product2);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products")).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(products)))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Iphone15"))
                .andExpect(jsonPath("$[1].name").value("Iphone14"));
        /*
        {
            "name": "Iphone15",  -> $.length == 2, $[0].name == Iphone15
            "name": "Iphone14
        }
        */
    }

    @Test
    public void Test_CreateProduct_CreatedSuccessfully() throws Exception {
        // Arrange
        Product expectedProduct = new Product();
        expectedProduct.setName("Macbook Pro");
        expectedProduct.setId(3L);

        ProductDto productToBeCreated = new ProductDto();
        productToBeCreated.setName("Macbook Pro");
        productToBeCreated.setId(3L);


        when(productService.createProduct(any(Product.class))).thenReturn(expectedProduct);

        // Act and Assert
        mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productToBeCreated)))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(expectedProduct)))
                .andExpect(jsonPath("$.length()").value(9))
                .andExpect(jsonPath("$.name").value("Macbook Pro"))
                .andExpect(jsonPath("$.id").value(3L));

        /*
        {
            "name": "Macbook Pro",  -> $.length == 2, $.name == Macbook Pro
            "id": 2
        }
        */
    }

    @Test
    public void Test_ProductServiceCalledWithExceptedArgument_Successfully() {

        // Arrange
        Long productId = 1L;

        // Act
        productController.getProduct(productId);

        // Assert
        verify(productService).getProduct(productIdCaptor.capture());
        assertEquals(productId, productIdCaptor.getValue());
    }

}