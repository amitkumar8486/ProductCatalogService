package com.amit.ecommerce.product_service_catalog.controllers;

import com.amit.ecommerce.product_service_catalog.dtos.ProductDto;
import com.amit.ecommerce.product_service_catalog.models.Category;
import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.services.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
/*
@RequestMapping - This helps us to define the root url so that we don't have to repeat in other places.
 */
@RequestMapping("/products")
public class ProductController {

   private IProductService productService;
   public ProductController(IProductService productService) {
       this.productService = productService;
   }
    // TODO make return type to ProductDto
   @GetMapping("{pid}/{uid}")
   public Product getProductDetailsBasedOnUserScope(@PathVariable Long pid, @PathVariable Long uid){

       Product product = productService.getProductDetails(pid, uid);
       return product;
   }

    /*
    The path is '/products'. This is the beauty of @RequestMapping.
     */
    @GetMapping()
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    /*
    The path is '/products/{id}'.
     */
    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId) {
        Product product = productService.getProduct(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    /*
    We want to restrict the seller to add certain values like - is the product is for prime day sales, COD available or not etc.
    This value can only set by amazon admin.
    That is why we pass productDto not the product object.
     */
    // TODO make return type to ProductDto
    @PostMapping
    public Product createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(getProduct(productDto));
    }

    // TODO make return type to ProductDto
    @PutMapping("{id}")
    public Product replaceProduct(@PathVariable("id") Long id,@RequestBody ProductDto productDto) {
        return productService.updateProduct(id,getProduct(productDto));
    }

    @DeleteMapping("{id}")
    public void deleteProduct(@PathVariable("id") Long productId) {
        productService.deleteProduct(productId);
    }
/*
client -> dto to controller
controller -> product to service
service -> fakeDto to FakeStoreApi
FakeStoreApi -> fakeDto to service
service -> product to controller
controller -> product to client.
 */
    /**
     * Mapper method: client send productDto object and receive product object.
     * @param productDto
     * @return product obj.
     */
    private Product getProduct(ProductDto productDto) {
        Product product = new Product();
        product.setId(productDto.getId());
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setImageUrl(productDto.getImageUrl());
        product.setDescription(productDto.getDescription());
        Category category = new Category();
        if(productDto.getCategoryDto() != null) {
            category.setId(productDto.getCategoryDto().getId());
            category.setName(productDto.getCategoryDto().getName());
        }
        product.setCategory(category);
        return product;
    }

}
