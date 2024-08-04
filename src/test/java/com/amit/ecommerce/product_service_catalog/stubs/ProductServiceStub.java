package com.amit.ecommerce.product_service_catalog.stubs;

import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.services.IProductService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("productServiceStub")
public class ProductServiceStub implements IProductService {

    Map<Long, Product> productMap;

    public ProductServiceStub() {
        productMap = new HashMap<>();
    }

    @Override
    public List<Product> getAllProducts() {
        return (List)productMap.values();
    }

    @Override
    public Product getProduct(Long productId) {
        if(productMap.containsKey(productId)){
            return productMap.get(productId);
        }
        return null;
    }

    @Override
    public Product createProduct(Product product) {
        productMap.put(product.getId(), product);
        return product;
    }

    @Override
    public void deleteProduct(Long productId) {
        productMap.remove(productId);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        productMap.replace(id, product);
        return product;
    }

    @Override
    public Product getProductDetails(Long productId, Long userId) {
        return null;
    }
}
