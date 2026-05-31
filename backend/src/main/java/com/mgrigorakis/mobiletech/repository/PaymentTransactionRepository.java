package com.mgrigorakis.mobiletech.repository;

import com.mgrigorakis.mobiletech.dto.MonthlySalesTrendResponse;
import com.mgrigorakis.mobiletech.model.PaymentTransaction;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PaymentTransactionRepository extends JpaRepository<PaymentTransaction, Long> {
    @Query("SELECT SUM(p.netAmount) FROM PaymentTransaction AS p WHERE p.paymentStatus = :status")
    BigDecimal getTotalRevenue(@Param("status") PaymentStatus status);

    @Query("SELECT MONTH(pt.createdAt) AS month, YEAR(pt.createdAt) AS year, SUM(pt.netAmount) AS revenue " +
            "FROM PaymentTransaction AS pt " +
            "WHERE pt.paymentStatus = :status " +
            "GROUP BY YEAR(pt.createdAt), MONTH(pt.createdAt) " +
            "ORDER BY YEAR(pt.createdAt), MONTH(pt.createdAt)")
    List<MonthlySalesTrendResponse> getMonthlySalesTrend(@Param("status") PaymentStatus status);
}
