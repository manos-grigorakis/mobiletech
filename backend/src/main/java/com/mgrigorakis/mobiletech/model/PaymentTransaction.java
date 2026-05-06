package com.mgrigorakis.mobiletech.model;

import com.mgrigorakis.mobiletech.model.enums.PaymentProvider;
import com.mgrigorakis.mobiletech.model.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"order"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "payment_transactions")
@Entity
public class PaymentTransaction extends BaseModel {
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_provider", nullable = false)
    private PaymentProvider paymentProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "amount", nullable = false, scale = 2, precision = 19)
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;
}
