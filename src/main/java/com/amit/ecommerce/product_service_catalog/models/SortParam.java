package com.amit.ecommerce.product_service_catalog.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortParam {

    private String paramName;
    private SortType sortType;
}
