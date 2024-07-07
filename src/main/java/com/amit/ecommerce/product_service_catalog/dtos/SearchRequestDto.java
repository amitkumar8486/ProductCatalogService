package com.amit.ecommerce.product_service_catalog.dtos;

import com.amit.ecommerce.product_service_catalog.models.SortParam;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class SearchRequestDto {
    private String query;
    private int pageNumber;
    private int pageSize;
    private List<SortParam> sortParamList = new ArrayList<>();


}
