package com.mgrigorakis.mobiletech.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"category"})
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "products")
@Entity
public class Product extends BaseModel {
    @Column(name = "brand", nullable = false, length = 50)
    private String brand;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "price", nullable = false, scale = 2, precision = 19)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "image_url", nullable = false , length = 500)
    private String imageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;
}
