package com.mgrigorakis.mobiletech.repository;

import com.mgrigorakis.mobiletech.model.Order;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    @NonNull
    @EntityGraph(attributePaths = {"orderItems"})
    Page<Order> findAll(@NonNull Pageable pageable);
}
