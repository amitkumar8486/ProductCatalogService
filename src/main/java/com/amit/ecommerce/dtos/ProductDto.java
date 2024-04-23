package com.amit.ecommerce.dtos;

import com.amit.ecommerce.models.BaseModel;
import com.amit.ecommerce.models.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductDto {

    private Long id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private CategoryDto categoryDto;
}
