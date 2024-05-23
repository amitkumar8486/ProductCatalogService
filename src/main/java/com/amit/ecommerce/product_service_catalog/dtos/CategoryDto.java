package com.amit.ecommerce.product_service_catalog.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {
    private Long id;
    private String name;
    private String description;
}
