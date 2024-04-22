package com.amit.ecommerce.controllers;

import com.amit.ecommerce.dtos.ProductDto;
import com.amit.ecommerce.models.Product;
import com.amit.ecommerce.services.IProductService;
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
    public ResponseEntity<Product> getProduct(@PathVariable("id") Long productId){

        try {
            if(productId < 1) {
                throw new IllegalArgumentException("id is invalid");
            }
            Product product = productService.getProduct(productId);
            return new ResponseEntity<>(product, HttpStatus.OK);

        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /*
    We want to restrict the seller to add certain values like - is the product is for prime day sales, COD available or not etc.
    This value can only set by amazon admin.
    That is why we pass productDto not the product object.
     */
    @PostMapping
    public Product createProduct(@RequestBody ProductDto productDto){
        return productService.createProduct(getProduct(productDto));
    }

    @PutMapping("{id}")
    public Product replaceProduct(@PathVariable("id") Long id,@RequestBody ProductDto productDto) {
        return productService.updateProduct(id,getProduct(productDto));
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
        product.setCategory(productDto.getCategory());
        return product;
    }

}
