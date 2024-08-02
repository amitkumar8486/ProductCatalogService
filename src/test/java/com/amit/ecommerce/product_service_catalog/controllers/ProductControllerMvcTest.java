package com.amit.ecommerce.product_service_catalog.controllers;

import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.services.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
class ProductControllerMvcTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private IProductService productService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void Test_GetAllProducts_FetchSuccessfully() throws Exception {

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setName("Iphone15");
        products.add(product);

        when(productService.getAllProducts()).thenReturn(products);

        mockMvc.perform(get("/products")).andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(products)));
    }

}