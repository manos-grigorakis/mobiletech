package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.dto.ProductRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductResponse> getAllProducts(PageFilterRequest filterRequest, PageSortRequest sortRequest);

    ProductResponse getProductById(Long id);

    ProductResponse createProduct(ProductRequest dto);

    ProductResponse updateProductById(Long id, ProductRequest dto);

    void deleteProductById(Long id);
}
