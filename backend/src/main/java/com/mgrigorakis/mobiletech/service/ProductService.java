package com.mgrigorakis.mobiletech.service;

import com.mgrigorakis.mobiletech.common.dto.PageFilterRequest;
import com.mgrigorakis.mobiletech.common.dto.PageSortRequest;
import com.mgrigorakis.mobiletech.dto.ProductCreateRequest;
import com.mgrigorakis.mobiletech.dto.ProductResponse;
import com.mgrigorakis.mobiletech.dto.ProductUpdateRequest;
import org.springframework.data.domain.Page;

public interface ProductService {
    Page<ProductResponse> getAllProducts(PageFilterRequest filterRequest, PageSortRequest sortRequest, String category);

    ProductResponse getProductById(Long id);

    ProductResponse createProduct(ProductCreateRequest dto);

    ProductResponse updateProductById(Long id, ProductUpdateRequest dto);

    void deleteProductById(Long id);
}
