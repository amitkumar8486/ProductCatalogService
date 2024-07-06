package com.amit.ecommerce.product_service_catalog.dtos;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class FakeStoreRatingDto {
    private Long count;
    private Double rate;
}
