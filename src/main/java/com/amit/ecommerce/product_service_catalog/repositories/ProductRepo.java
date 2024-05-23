package com.amit.ecommerce.product_service_catalog.repositories;

import com.amit.ecommerce.product_service_catalog.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    //Product is what we want to store and Long is datatype of PK.

    Product save(Product product);

    Optional<Product> findProductById(Long id);

    void deleteProductById(Long id);

    @Override
    List<Product> findAll();

}
