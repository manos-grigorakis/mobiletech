package com.mgrigorakis.mobiletech.repository;

import com.mgrigorakis.mobiletech.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByCategoryId(Long id);
}
