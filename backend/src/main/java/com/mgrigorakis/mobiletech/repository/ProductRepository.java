package com.mgrigorakis.mobiletech.repository;

import com.mgrigorakis.mobiletech.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findProductByCategory_Slug(String slug, Pageable pageable);

    boolean existsByCategoryId(Long id);

    @Query("SELECT COUNT(*) FROM Product AS p WHERE p.stock <= 10")
    Integer getCountOfProductsToReStock();

    @Query("SELECT SUM(p.price * p.stock) AS stock_value FROM Product AS p")
    BigDecimal getStockValue();
}
