package com.mgrigorakis.mobiletech.repository;

import com.mgrigorakis.mobiletech.dto.RevenueByCategoryResponse;
import com.mgrigorakis.mobiletech.dto.TopSellingProductResponse;
import com.mgrigorakis.mobiletech.dto.ValueResponse;
import com.mgrigorakis.mobiletech.model.OrderItem;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("SELECT c.name, SUM(oi.price * oi.quantity) " +
            "FROM OrderItem AS oi " +
            "INNER JOIN oi.order AS o " +
            "INNER JOIN oi.product AS p " +
            "INNER JOIN p.category AS c " +
            "INNER JOIN PaymentTransaction AS pt ON pt.order.id = o.id " +
            "WHERE pt.paymentStatus = :status " +
            "GROUP BY c.id, c.name")
    List<RevenueByCategoryResponse> getRevenueByCategory(@Param("status") PaymentStatus status);

    @Query("SELECT COUNT(oi.quantity) " +
            "FROM OrderItem AS oi " +
            "INNER JOIN Order AS o ON o.id = oi.order.id " +
            "INNER JOIN PaymentTransaction AS pt on pt.order.id = o.id " +
            "WHERE pt.paymentStatus = :status")
    ValueResponse<Integer> getUnitsSold(@Param("status") PaymentStatus status);

    @Query("SELECT p.name, SUM(oi.quantity) AS totalSold " +
            "FROM OrderItem AS oi " +
            "INNER JOIN oi.product AS p " +
            "INNER JOIN oi.order AS o " +
            "INNER JOIN PaymentTransaction AS pt ON pt.order.id = o.id " +
            "WHERE pt.paymentStatus = :status " +
            "GROUP BY p.id, p.name " +
            "ORDER BY SUM(oi.quantity) DESC")
    List<TopSellingProductResponse> getTopSellingProducts(@Param("status") PaymentStatus status, Pageable pageable);
}
