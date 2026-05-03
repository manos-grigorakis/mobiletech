package com.mgrigorakis.mobiletech.repository;

import com.mgrigorakis.mobiletech.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductByCategory_Slug(String slug, Pageable pageable);

    boolean existsByCategoryId(Long id);
}
