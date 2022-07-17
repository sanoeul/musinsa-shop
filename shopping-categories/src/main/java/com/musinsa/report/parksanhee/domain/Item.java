package com.musinsa.report.parksanhee.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item {
    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Money price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    public void addBrand(Brand brand){
        this.brand = brand;
        this.brand.getItem().add(this);
    }

    public void update(Brand brand, BigDecimal newPrice){
        this.brand = brand;
        this.brand.getItem().add(this);
        this.price = Money.wons(newPrice);
    }
}
