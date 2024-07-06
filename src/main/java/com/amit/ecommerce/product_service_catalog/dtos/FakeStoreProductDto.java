package com.amit.ecommerce.product_service_catalog.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreProductDto implements Serializable {

    private Long id;
    private String title;
    private String description;
    private Double price;
    private String image;
    private String category;
    //private FakeStoreRatingDto rating;
}
