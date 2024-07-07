package com.amit.ecommerce.product_service_catalog.services;

import com.amit.ecommerce.product_service_catalog.models.Product;
import com.amit.ecommerce.product_service_catalog.models.SortParam;
import com.amit.ecommerce.product_service_catalog.models.SortType;
import com.amit.ecommerce.product_service_catalog.repositories.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SearchService {

    @Autowired
    private ProductRepo productRepo;

    public Page<Product> searchProduct(String query, int pageSize, int pageNumber, List<SortParam> sortParams) {
        //Descending order of price and if price is same, then ascending order of id
        //Sort sort = Sort.by("price").descending().and(Sort.by("id"));
        Sort sort = null;

        if(!sortParams.isEmpty()) {
            if(sortParams.get(0).getSortType().equals(SortType.ASC))
                sort = Sort.by(sortParams.get(0).getParamName());
            else
                sort = Sort.by(sortParams.get(0).getParamName()).descending();
        }

        for(int i=1; i<sortParams.size();i++) {
            if(sortParams.get(i).getSortType().equals(SortType.ASC))
                sort = sort.and(Sort.by(sortParams.get(i).getParamName()));
            else
                sort = sort.and(sort.by(sortParams.get(i).getParamName()).descending());
        }

        return productRepo.findByNameEquals(query, PageRequest.of(pageNumber,pageSize,sort));

    }
}
