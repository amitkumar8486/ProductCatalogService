package com.amit.ecommerce.product_service_catalog.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
public class Category extends BaseModel {

    private String name;
    private String description;
    @JsonBackReference
    @OneToMany(mappedBy = "category") /*Consider this relation once only i.e. it is used to avoid creating separate mapping table of product and category.*/
    private List<Product> products;
}
